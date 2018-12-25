package ru.kcoder.weatherhelper.presentation.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    open val errorLiveData: MutableLiveData<Int> = SingleLiveData()

    open fun errorCallback(resErr: Int){
        errorLiveData.value = resErr
    }
}