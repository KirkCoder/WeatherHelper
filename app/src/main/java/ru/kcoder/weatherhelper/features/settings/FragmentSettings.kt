package ru.kcoder.weatherhelper.features.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import 	androidx.preference.PreferenceFragmentCompat
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class FragmentSettings:
    PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_preferences);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)
    }

    companion object {
        const val TAG = "FRAGMENT_SETTINGS_TAG"

        @JvmStatic
        fun newInstance(): Fragment{
            return FragmentSettings()
        }
    }
}