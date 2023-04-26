package com.prabhakaran.nytimesapp.features.presentation.articlelist

import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem


sealed class ItemViewState{

    object Loading : ItemViewState()
    data class Content( val news : NytNewsItem) : ItemViewState()
    data class Error( val error : String) : ItemViewState()

}
