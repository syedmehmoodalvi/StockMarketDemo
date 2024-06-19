package com.syed.stockmarketdemo.di

import com.syed.stockmarketdemo.data.csv.CSVParser
import com.syed.stockmarketdemo.data.csv.InstrumentListingsParser
import com.syed.stockmarketdemo.data.repository.StockRepositoryImpl
import com.syed.stockmarketdemo.domain.model.InstrumentListing
import com.syed.stockmarketdemo.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindInstrumentListingsParser(
        instrumentListingsParser: InstrumentListingsParser
    ): CSVParser<InstrumentListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}