package com.example.paginationdemo.repository

import com.example.paginationdemo.utils.CustomDataSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.paginationdemo.data.pagingsource.PassengersPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PassengerRepository @Inject constructor() {
    fun getPassengersPager() = Pager(
        config = PagingConfig(
            pageSize = PassengersPagingSource.EXPECTED_PERSON_PAGE_SIZE,
            initialLoadSize = PassengersPagingSource.EXPECTED_PERSON_PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = { PassengersPagingSource(CustomDataSource()) }
    )
}
