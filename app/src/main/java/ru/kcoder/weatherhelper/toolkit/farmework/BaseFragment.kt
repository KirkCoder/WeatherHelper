package ru.kcoder.weatherhelper.toolkit.farmework

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected val addCompatActivity: AppCompatActivity?
        get() {
            return (activity as? AppCompatActivity)
        }

    protected fun setTitle(title: String){
        addCompatActivity?.supportActionBar?.title = title
    }

    open fun onBackPressed(): Boolean {
        return true
    }
}