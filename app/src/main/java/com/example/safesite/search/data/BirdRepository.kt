package com.example.safesite.search.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.safesite.search.BirdApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BirdRepository @Inject constructor(private val birdApi: BirdApi) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BirdPagingSource(birdApi, query)}
        ).liveData
}