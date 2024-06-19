package com.syed.stockmarketdemo.domain.model

data class InstrumentInfo(
    val symbol: String,
    val description: String,
    val name: String,
    val country: String,
    val industry: String,
)