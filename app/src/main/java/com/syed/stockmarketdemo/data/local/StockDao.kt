package com.syed.stockmarketdemo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstrumentListings(
        companyListingEntities: List<InstrumentListingEntity>
    )

    @Query("DELETE FROM instrumentlistingentity")
    suspend fun clearInstrumentListings()

    @Query(
        """
            SELECT * 
            FROM instrumentlistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == symbol
        """
    )
    suspend fun searchInstrumentListing(query: String): List<InstrumentListingEntity>
}