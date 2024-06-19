package com.syed.stockmarketdemo.presentation.instrument_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syed.stockmarketdemo.domain.repository.StockRepository
import com.syed.stockmarketdemo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstrumentInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
): ViewModel() {

    var state by mutableStateOf(InstrumentInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            when(val instrumentInfoResult = repository.getInstrumentInfo(symbol)) {
                is Resource.Success -> {
                    state = state.copy(
                        instrument = instrumentInfoResult.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = instrumentInfoResult.message,
                        instrument = null
                    )
                }
                else -> Unit
            }
        }
    }
}