package ru.kcoder.weatherhelper.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import 	androidx.preference.PreferenceFragmentCompat
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class FragmentSettings:
    PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_preferences);
    }

    companion object {
        const val TAG = "FRAGMENT_SETTINGS_TAG"

        @JvmStatic
        fun newInstance(): Fragment{
            return FragmentSettings()
        }
    }
}