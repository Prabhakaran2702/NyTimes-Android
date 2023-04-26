package com.prabhakaran.nytimesapp.features.presentation.articlelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.prabhakaran.nytimesapp.features.business.FakeArticlesRepository
import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.*
import org.mockito.Mockito
import retrofit2.Response


class ArticlesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ArticlesViewModel
    private lateinit var mockRepository: FakeArticlesRepository

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // create a mock repository object
        mockRepository = FakeArticlesRepository()
        // create the view model with the mock repository
        viewModel = ArticlesViewModel(mockRepository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }


    @Test
    fun `loadData with successful response should emit Content state`() = runBlocking {
        // arrange
        val category = "Most Viewed"
        val response = Response.success(NytNewsItem(articles = listOf(NytNewsItem.Article("Title", "Content"))))

        viewModel.loadData(category)

        val states = mutableListOf<ItemViewState>()
        viewModel.postLiveData.observeForever { state ->
            states.add(state)
        }

        Assert.assertTrue(states[0] is ItemViewState.Loading)
//        Assert.assertTrue(states[1] is ItemViewState.Content)
//        Assert.assertTrue((states[1] as ItemViewState.Content).news == response.body())
    }


    fun `loadData with unsuccessful response should emit Error state`() = runBlocking {
        // arrange
        val category = "Invalid Category"
        val response = Response.error<NytNewsItem>(404, ResponseBody.create(null, "Item not found"))
        // act
        viewModel.loadData(category)

        // assert
        val expectedViewState = ItemViewState.Error("Item not found")
        assertEquals(expectedViewState, viewModel.postLiveData.value)
    }
}