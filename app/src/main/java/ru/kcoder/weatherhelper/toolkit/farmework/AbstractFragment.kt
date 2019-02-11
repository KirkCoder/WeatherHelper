package ru.kcoder.weatherhelper.toolkit.farmework

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

abstract class AbstractFragment: BaseFragment() {

    abstract val errorLiveData: LiveData<Int>

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    @CallSuper
    protected open fun subscribeUi() {
        errorLiveData.observe(this, Observer {error ->
            error?.let { showError(it) }
        })
    }

    protected open fun showError(resErr: Int) {
        view?.let {
            Snackbar
                .make(it, it.context.resources.getString(resErr), Snackbar.LENGTH_LONG)
                .show()
        }
    }
}