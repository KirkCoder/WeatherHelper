package ru.kcoder.weatherhelper.presentation.common

import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

abstract class BaseFragment : androidx.fragment.app.Fragment() {

    protected val addCompatActivity: AppCompatActivity?
        get() {
            return (activity as? AppCompatActivity)
        }

    open fun onBackPressed(): Boolean {
        return true
    }

    open fun showError(resErr: Int) {
        view?.let {
            Snackbar
                .make(it, it.context.resources.getString(resErr), Snackbar.LENGTH_LONG)
                .show()
        }
    }
}