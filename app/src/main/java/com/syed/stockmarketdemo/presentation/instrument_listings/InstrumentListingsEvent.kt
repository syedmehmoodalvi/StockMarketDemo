package com.syed.stockmarketdemo.presentation.instrument_listings

sealed class InstrumentListingsEvent {
    object Refresh: InstrumentListingsEvent()
    data class OnSearchQueryChange(val query: String): InstrumentListingsEvent()
}
