package ru.kcoder.weatherhelper.presentation.common

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class BaseFragment: Fragment() {

    protected val addCompatActivity: AppCompatActivity?
    get(){
        return (activity as? AppCompatActivity)
    }

    open fun onBackPressed():Boolean{
        return true
    }
}