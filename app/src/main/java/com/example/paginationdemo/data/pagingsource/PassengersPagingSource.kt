package com.example.paginationdemo.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.example.paginationdemo.utils.CustomDataSource
import com.example.paginationdemo.utils.PersonResponse
import javax.inject.Inject
import kotlinx.coroutines.CompletableDeferred

class PassengersPagingSource @Inject constructor(
    private val customDataSource: CustomDataSource
) : PagingSource<String, PersonResponse>() {

    private var nextPage: String? = null
    private var people: List<PersonResponse>? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PersonResponse> {
        return try {
            val page = params.key ?: FIRST_PAGE_STRING
            val result = CompletableDeferred<LoadResult<String, PersonResponse>>()

            customDataSource.fetch(page) { response, error ->
                if (response != null) {
                    people = response.people
                    nextPage = response.next
                    Log.d("testingg", "response not null" + nextPage.toString())
                    Log.d("testingg", people?.size.toString())
                    result.complete(
                        Page(
                            data = people.orEmpty(),
                            prevKey = null,
                            nextKey = nextPage
                        )
                    )
                } else if (error != null) {
                    Log.d("testingg", error.toString())
                    result.complete(Error(Exception()))
                } else {
                    result.complete(Error(Exception("Unknown error")))
                }
            }

            result.await()
        } catch (exception: Exception) {
            Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PersonResponse>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { closestPage ->
                closestPage.prevKey
            }
        }
    }

    companion object {
        const val EXPECTED_PERSON_PAGE_SIZE = 10
        private val FIRST_PAGE_STRING = null
    }
}
