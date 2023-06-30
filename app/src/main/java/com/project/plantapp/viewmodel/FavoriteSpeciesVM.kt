package com.project.plantapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.plantapp.data.DataApp
import com.project.plantapp.model.Species
import kotlinx.coroutines.launch

class FavoriteSpeciesVM : ViewModel() {

    private var _data = DataApp.getInstance()

    private var _listOfSpecies : MutableLiveData<ArrayList<Species>> =  MutableLiveData()
    val listOfSpecies: LiveData<ArrayList<Species>>
        get() = _listOfSpecies

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun loadData() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val dataSet = _data.getFavoriteSpecies()
            _isLoading.postValue(false)
            _listOfSpecies.postValue(dataSet)
        }
    }

    fun handleItemWhenClicked() {
        /// TODO
    }


}