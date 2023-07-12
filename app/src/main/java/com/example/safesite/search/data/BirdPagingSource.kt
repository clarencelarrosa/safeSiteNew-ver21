package com.example.safesite.search.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.annotation.GlideModule
import com.example.safesite.search.BirdApi
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX=1
class BirdPagingSource (
    private val birdApi: BirdApi,
    private val query: String

    //this part will turn the data into pages
) : PagingSource<Int,BirdPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BirdPhoto> {
        val position = params.key ?: STARTING_PAGE_INDEX //which page the images want to load

        return try {
            val response = birdApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BirdPhoto>): Int? {
        TODO("Not yet implemented")
    }
}