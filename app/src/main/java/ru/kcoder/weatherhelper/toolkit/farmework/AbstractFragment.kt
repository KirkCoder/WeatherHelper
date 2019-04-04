package ru.kcoder.weatherhelper.toolkit.farmework

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import ru.kcoder.weatherhelper.toolkit.android.mObserver

abstract class AbstractFragment : BaseFragment() {

    abstract val viewModel: BaseViewModel

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    @CallSuper
    protected open fun subscribeUi() {
        viewModel.errorLiveData.observe(this, mObserver { showError(it) })
    }

    protected open fun showError(resErr: Int) {
        view?.let {
            Snackbar
                .make(it, it.context.resources.getString(resErr), Snackbar.LENGTH_LONG)
                .show()
        }
    }
}