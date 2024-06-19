package com.syed.stockmarketdemo.data.repository

import com.syed.stockmarketdemo.data.csv.CSVParser
import com.syed.stockmarketdemo.data.local.StockDatabase
import com.syed.stockmarketdemo.data.mapper.toInstrumentInfo
import com.syed.stockmarketdemo.data.mapper.toInstrumentListing
import com.syed.stockmarketdemo.data.mapper.toInstrumentListingEntity
import com.syed.stockmarketdemo.data.remote.StockApi
import com.syed.stockmarketdemo.domain.model.InstrumentInfo
import com.syed.stockmarketdemo.domain.model.InstrumentListing
import com.syed.stockmarketdemo.domain.repository.StockRepository
import com.syed.stockmarketdemo.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val instrumentListingsParser: CSVParser<InstrumentListing>,
): StockRepository {

    private val dao = db.dao

    override suspend fun getInstrumentListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<InstrumentListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchInstrumentListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toInstrumentListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                instrumentListingsParser.parse(response.byteStream())
            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearInstrumentListings()
                dao.insertInstrumentListings(
                    listings.map { it.toInstrumentListingEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchInstrumentListing("")
                        .map { it.toInstrumentListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }


    override suspend fun getInstrumentInfo(symbol: String): Resource<InstrumentInfo> {
        return try {
            val result = api.getInstrumentInfo(symbol)
            Resource.Success(result.toInstrumentInfo())
        } catch(e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load instrument info"
            )
        } catch(e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = "Couldn't load instrument info"
            )
        }
    }
}