package com.project.plantapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.plantapp.data.DataArticles
import com.project.plantapp.model.Articles
import kotlinx.coroutines.launch

class ArticlesVM: ViewModel() {

    private var _listOfArticles : MutableLiveData<List<Articles>> =  MutableLiveData()
    val listOfArticles: LiveData<List<Articles>>
        get() = _listOfArticles

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun loadData() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            val dataSet = DataArticles.getDataSet()

            _isLoading.postValue(false)
            _listOfArticles.postValue(dataSet)
        }
    }

    fun handleItemWhenClicked() {
        /// TODO
    }


}