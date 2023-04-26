package com.prabhakaran.nytimesapp.features.presentation.articlelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prabhakaran.nytimesapp.features.business.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(private val repository: ArticlesRepository) : ViewModel() {


    private val _postLiveData = MutableLiveData<ItemViewState>()

    val postLiveData : LiveData<ItemViewState>
        get() = _postLiveData

    fun loadData(category: String) {

        _postLiveData.postValue(ItemViewState.Loading)

        viewModelScope.launch {

            repository.getArticles(category).let {

                if(it.isSuccessful) {
                    _postLiveData.postValue(ItemViewState.Content(it.body()!!))
                }
                else{
                    _postLiveData.postValue(ItemViewState.Error("Item not found"))
                }
            }

        }
    }

}