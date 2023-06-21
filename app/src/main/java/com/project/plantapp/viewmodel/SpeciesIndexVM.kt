package com.project.plantapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.plantapp.data.DataApp
import com.project.plantapp.data.DataSpecies
import com.project.plantapp.model.SpecieIndex
import com.project.plantapp.model.Species
import kotlinx.coroutines.launch

class SpeciesIndexVM : ViewModel() {
    private var _listOfSpeciesIndex: MutableLiveData<List<String>> =  MutableLiveData()
    private var _data = DataApp.getInstance()
    val listOfSpeciesIndex: LiveData<List<String>>
    get() = _listOfSpeciesIndex

    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
    get() = _isLoading


    fun loadData() {
        _isLoading.postValue(true)

        viewModelScope.launch {
            val dataSet = _data.getSpeciesCategory()
            _isLoading.postValue(false)
            _listOfSpeciesIndex.postValue(dataSet)
        }
    }


}