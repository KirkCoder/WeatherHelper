package ru.kcoder.weatherhelper.presentation.place

import android.app.Dialog
import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.place_add_dialog_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class DialogFragmentAddPlace : androidx.fragment.app.DialogFragment() {

    private var callback: Callback? = null
    private val viewModel: ViewModelAddPlace by viewModel()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = when {
            parentFragment is Callback -> parentFragment as Callback
            else -> throw ClassCastException("Host fragment not implemented DialogFragmentAddPlace.Callback, for dialog result")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewModel.markerLiveData.observe(this, Observer { place ->
            place?.let {
                val name = it.name
                if (it.name != null && TextUtils.isEmpty(editTextPlaceName.text)) {
                    editTextPlaceName.setText(name)
                }
            }
        })
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { act ->
            val view = act.layoutInflater.inflate(R.layout.place_add_dialog_fragment, null)
            AlertDialog.Builder(act)
                .setView(view)
                .setPositiveButton(R.string.common_ok) { _, _ ->
                    val editText = view.findViewById<AppCompatEditText>(R.id.editTextPlaceName)
                    callback?.selectName(editText.text?.toString())
                }
                .setNegativeButton(R.string.common_cancel) { _, _ ->
                    callback?.selectName(null)
                }.create()
        } ?: throw Exception("context not found")
    }

    companion object {
        const val TAG = "DIALOG_FRAGMENT_ADD_PLACE_TAG"
        @JvmStatic
        fun newInstance() = DialogFragmentAddPlace()
    }

    /**
     * колбэк для запускающего диалог фрагмента
     */
    interface Callback {
        fun selectName(name: String?)
    }
}