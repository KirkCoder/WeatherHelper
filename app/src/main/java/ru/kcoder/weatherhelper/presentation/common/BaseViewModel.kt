package ru.kcoder.weatherhelper.presentation.common

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    open val errorLiveData: MutableLiveData<Int> = SingleLiveData()

    open fun errorCallback(resErr: Int){
        errorLiveData.value = resErr
    }
}