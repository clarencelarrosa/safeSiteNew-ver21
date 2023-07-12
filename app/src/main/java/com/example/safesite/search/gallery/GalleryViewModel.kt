package com.example.safesite.search.gallery

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.safesite.search.data.BirdRepository

class GalleryViewModel @ViewModelInject constructor(
    private val repository: BirdRepository,
    @Assisted state: SavedStateHandle

): ViewModel() {
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    val photos=currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope) //this will cache the live data which prevents to crash
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "birds" //when we click the SEARCH tab, the images displayed are birds.
    }
}