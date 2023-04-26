package com.prabhakaran.nytimesapp.features.business


import com.prabhakaran.nytimesapp.common.data.network.ApiService
import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem
import retrofit2.Response
import javax.inject.Inject

class ArticlesRepositoryImp @Inject constructor(private val apiService: ApiService) :
    ArticlesRepository {

    override suspend fun getArticles(category: String): Response<NytNewsItem> =

        when(category){

            "Most Viewed" ->   apiService.getArticles("viewed")
            "Most Shared" ->   apiService.getArticles("shared")
            "Most Emailed" ->   apiService.getArticles("emailed")
            else -> apiService.getArticles("viewed")

        }



}