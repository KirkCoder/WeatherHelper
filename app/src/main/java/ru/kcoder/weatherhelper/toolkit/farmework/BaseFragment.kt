package ru.kcoder.weatherhelper.toolkit.farmework

import androidx.appcompat.app.AppCompatActivity

abstract class BaseFragment : androidx.fragment.app.Fragment() {

    protected val addCompatActivity: AppCompatActivity?
        get() {
            return (activity as? AppCompatActivity)
        }

    open fun onBackPressed(): Boolean {
        return true
    }
}