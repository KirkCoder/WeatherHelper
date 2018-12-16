package ru.kcoder.weatherhelper.presentation.common

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class BaseFragment : Fragment() {

    protected val addCompatActivity: AppCompatActivity?
        get() {
            return (activity as? AppCompatActivity)
        }

    open fun onBackPressed(): Boolean {
        return true
    }

    open fun error(resErr: Int) {
        view?.let {
            Snackbar
                .make(it, it.context.resources.getString(resErr), Snackbar.LENGTH_LONG)
                .show()
        }
    }
}