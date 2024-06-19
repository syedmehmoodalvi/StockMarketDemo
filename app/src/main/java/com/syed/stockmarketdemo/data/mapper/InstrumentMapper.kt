package com.syed.stockmarketdemo.data.mapper

import com.syed.stockmarketdemo.data.local.InstrumentListingEntity
import com.syed.stockmarketdemo.data.remote.dto.InstrumentInfoDto
import com.syed.stockmarketdemo.domain.model.InstrumentInfo
import com.syed.stockmarketdemo.domain.model.InstrumentListing

fun InstrumentListingEntity.toInstrumentListing(): InstrumentListing {
    return InstrumentListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun InstrumentListing.toInstrumentListingEntity(): InstrumentListingEntity {
    return InstrumentListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun InstrumentInfoDto.toInstrumentInfo(): InstrumentInfo {
    return InstrumentInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}