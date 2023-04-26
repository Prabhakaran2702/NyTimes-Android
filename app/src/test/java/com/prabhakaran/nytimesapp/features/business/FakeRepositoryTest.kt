package com.prabhakaran.nytimesapp.features.business

import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem
import retrofit2.Response


class FakeArticlesRepository() : ArticlesRepository {

    override suspend fun getArticles(category: String): Response<NytNewsItem> {
     return when(category) {
            "Most Viewed" -> return Response.success(NytNewsItem(articles = listOf(NytNewsItem.Article("Title", "Content"))))
            "Most Shared" ->   return Response.success(NytNewsItem(articles = listOf(NytNewsItem.Article("Title", "Content"))))
         "Most Emailed" -> return Response.success(NytNewsItem(articles = listOf(NytNewsItem.Article("Title", "Content"))))
         else ->  return Response.success(NytNewsItem(articles = listOf(NytNewsItem.Article("Title", "Content"))))
        }

    }
}
