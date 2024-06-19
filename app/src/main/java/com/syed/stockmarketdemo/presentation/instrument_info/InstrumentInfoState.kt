package com.syed.stockmarketdemo.presentation.instrument_info

import com.syed.stockmarketdemo.domain.model.InstrumentInfo

data class InstrumentInfoState(
    val instrument: InstrumentInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
