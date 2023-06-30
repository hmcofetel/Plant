package com.project.plantapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.plantapp.data.DataApp
import com.project.plantapp.model.Articles
import kotlinx.coroutines.launch

class ArticlesVM: ViewModel() {

    private var _data = DataApp.getInstance()

    private var _listOfArticles : MutableLiveData<ArrayList<Articles>> =  MutableLiveData()
    val listOfArticles: LiveData<ArrayList<Articles>>
        get() = _listOfArticles

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun loadData() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val dataSet = _data.getArticles()
            _isLoading.postValue(false)
            _listOfArticles.postValue(dataSet)
        }
    }

    fun handleItemWhenClicked() {
        /// TODO
    }


}