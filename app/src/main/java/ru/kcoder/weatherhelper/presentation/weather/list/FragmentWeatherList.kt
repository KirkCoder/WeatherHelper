package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.weather_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import ru.kcoder.weatherhelper.toolkit.android.AppRouter


class FragmentWeatherList : BaseFragment() {

    private val viewModel: ViewModelWeatherList by sharedViewModel()

    private val adapter = AdapterWeatherList({
        showDetailFragment(it)
    }, {
        viewModel.forceUpdate(it)
    }
    )

    private fun showDetailFragment(id: Long) {
        activity?.let {
            AppRouter.showWeatherDetailFragment(it, id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.weather_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        subscribeUi()
    }

    private fun initView(view: View) {
        setHasOptionsMenu(true)
        addCompatActivity?.title = resources.getString(R.string.app_name)
        initRecycler(view.context)
        fab.setOnClickListener { _ ->
            activity?.let {
                AppRouter.showAddWeatherFragment(it)
            }
        }
    }

    private fun initRecycler(context: Context) {
        recyclerViewWeatherList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerViewWeatherList.adapter = adapter
    }

    private fun subscribeUi() {
        viewModel.weatherList.observe(this, Observer { list ->
            list?.let { adapter.setData(it) }
        })

        viewModel.weatherUpdate.observe(this, Observer { holder ->
            holder?.let { adapter.updateUnit(it) }
        })

        viewModel.updateStatus.observe(this, Observer { item ->
            item?.let { adapter.updateStatus(it) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TAG = "WEATHER_FRAGMENT_LIST_TAG"
        @JvmStatic
        fun newInstance() = FragmentWeatherList()
    }

}