package com.project.plantapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.plantapp.data.DataSpecies
import com.project.plantapp.model.Species
import kotlinx.coroutines.launch

class SpecieVM : ViewModel() {
    private var _listOfArticles : MutableLiveData<List<Species>> =  MutableLiveData()
    val listOfArticles: LiveData<List<Species>>
        get() = _listOfArticles

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun loadData(category: String) {
        _isLoading.postValue(true)

        viewModelScope.launch {
            val dataSet = DataSpecies.getDataSet(category)

            _isLoading.postValue(false)
            _listOfArticles.postValue(dataSet)
        }
    }


    fun handleItemWhenClicked() {
        /// TODO
    }
}