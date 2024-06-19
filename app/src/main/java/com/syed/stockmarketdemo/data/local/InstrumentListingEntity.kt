package com.syed.stockmarketdemo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InstrumentListingEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey val id: Int? = null
)
