package com.meaningstest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meaningstest.di.NetworkRepository
import com.meaningstest.model.MeaningsResponse
import com.meaningstest.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MeaningsViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {
    private val _meaningsList = MutableLiveData<DataHandler<List<MeaningsResponse>>>()
    val meaningsList: LiveData<DataHandler<List<MeaningsResponse>>> = _meaningsList

    fun getMeaningsList(input: String) {
        _meaningsList.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getMeanings(input)
            _meaningsList.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<List<MeaningsResponse>>): DataHandler<List<MeaningsResponse>> {
        if (response.isSuccessful) {
            response.body()?.let { usersList ->
                return DataHandler.SUCCESS(usersList)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }

}