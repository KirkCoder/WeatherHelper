package ru.kcoder.weatherhelper.presentation.main

import android.os.Bundle
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import android.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.toolkit.android.AppRouter


class MainFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCompatActivity?.setSupportActionBar(toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            AppRouter.showWeatherListFragment(childFragmentManager)
        }
    }

    override fun onBackPressed(): Boolean {
        if (childFragmentManager.backStackEntryCount > 1) {
            childFragmentManager.popBackStack()
            return false
        }
        return super.onBackPressed()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

        const val TAG = "main_fragment_tag"
        const val FRAGMENT_CONTAINER = R.id.fragmentMainContainer
    }
}