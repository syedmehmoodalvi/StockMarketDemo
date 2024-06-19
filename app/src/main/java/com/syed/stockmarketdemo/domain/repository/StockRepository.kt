package com.syed.stockmarketdemo.domain.repository

import com.syed.stockmarketdemo.domain.model.InstrumentInfo
import com.syed.stockmarketdemo.domain.model.InstrumentListing
import com.syed.stockmarketdemo.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getInstrumentListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<InstrumentListing>>>

    suspend fun getInstrumentInfo(
        symbol: String
    ): Resource<InstrumentInfo>
}