package com.syed.stockmarketdemo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [InstrumentListingEntity::class],
    version = 1
)
abstract class StockDatabase: RoomDatabase() {
    abstract val dao: StockDao
}