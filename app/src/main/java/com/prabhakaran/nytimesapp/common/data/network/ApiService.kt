package com.prabhakaran.nytimesapp.common.data.network

import com.prabhakaran.nytimesapp.common.utils.Constant.API_KEY
import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {


    @GET("/svc/mostpopular/v2/{topics}/7.json?api-key=${API_KEY}")
    suspend fun getArticles(@Path("topics") topics: String) : Response<NytNewsItem>


}