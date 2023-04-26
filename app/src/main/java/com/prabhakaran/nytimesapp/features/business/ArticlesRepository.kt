package com.prabhakaran.nytimesapp.features.business


import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem
import retrofit2.Response

interface ArticlesRepository {
    suspend fun getArticles(category: String) : Response<NytNewsItem>
}