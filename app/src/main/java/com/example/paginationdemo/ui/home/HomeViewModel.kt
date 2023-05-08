package com.example.paginationdemo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.paginationdemo.repository.PassengerRepository
import com.example.paginationdemo.utils.PersonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val passengerRepository: PassengerRepository,
) : ViewModel() {

    fun getPassengers(): Flow<PagingData<PersonResponse>> {
        return passengerRepository
            .getPassengersPager()
            .flow
            .cachedIn(viewModelScope)
    }
}
