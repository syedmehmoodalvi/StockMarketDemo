package com.syed.stockmarketdemo.presentation.instrument_listings

import com.syed.stockmarketdemo.domain.model.InstrumentListing

data class InstrumentListingsState(
    val instruments: List<InstrumentListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
