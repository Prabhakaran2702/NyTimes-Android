package com.prabhakaran.nytimesapp.features.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel  : ViewModel()  {

    val category: MutableLiveData<String> = MutableLiveData("Most Viewed")

}