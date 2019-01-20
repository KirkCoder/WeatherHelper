package ru.kcoder.weatherhelper.presentation.weather.list

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class DialogFragmentDelete : DialogFragment() {

    private var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = when {
            parentFragment is Callback -> parentFragment as Callback
            else -> throw ClassCastException("Host fragment not implemented DialogFragmentDelete.Callback, for dialog result")
        }
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { act ->
            arguments?.let {
                val name = it.getString(TAG_NAME, " ")
                val id = it.getLong(TAG_ID)
                AlertDialog.Builder(act)
                    .setMessage(act.getString(R.string.weather_ask_delete, name))
                    .setPositiveButton(R.string.common_ok) { _, _ ->
                        callback?.needDelete(if (id == 0L) null else id)
                    }
                    .setNegativeButton(R.string.common_cancel) { _, _ ->
                        callback?.needDelete(null)
                    }.create()
            }
        } ?: throw Exception("context not found")
    }

    companion object {
        const val TAG = "DIALOG_FRAGMENT_DELETE"
        private const val TAG_ID = "tag_id"
        private const val TAG_NAME = "tag_name"
        @JvmStatic
        fun newInstance(id: Long, name: String): DialogFragment {
            val fragment = DialogFragmentDelete()
            val bundle = Bundle()
            bundle.putLong(TAG_ID, id)
            bundle.putString(TAG_NAME, name)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * колбэк для запускающего диалог фрагмента
     */
    interface Callback {
        fun needDelete(id: Long?)
    }
}