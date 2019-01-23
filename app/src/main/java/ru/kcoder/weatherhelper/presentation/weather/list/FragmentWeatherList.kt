package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import ru.kcoder.weatherhelper.toolkit.android.AppRouter

class FragmentWeatherList : BaseFragment(), DialogFragmentDelete.Callback {

    private val viewModel: ViewModelWeatherList by sharedViewModel()

    private val adapter = AdapterWeatherList({
        showDetailFragment(it)
    }, {
        viewModel.forceUpdate(it)
    }, { id, name ->
        askDelete(id, name)
    }, {
        viewModel.changedData(it)
    }, {
        viewModel.notifyChange(it)
    }, this::startMotion)

    private val callback = TouchCallback(adapter::onItemMove)

    private val touchHelper = ItemTouchHelper(callback)

    private fun showDetailFragment(id: Long) {
        activity?.let { AppRouter.showWeatherDetailFragment(it, id) }
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
        fabAdd.setOnClickListener { _ ->
            activity?.let { AppRouter.showAddWeatherFragment(it) }
        }
        fabOk.setOnClickListener {
            viewModel.setEditStatus(false)
        }
    }

    private fun initRecycler(context: Context) {
        recyclerViewWeatherList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerViewWeatherList.adapter = adapter
        touchHelper.attachToRecyclerView(recyclerViewWeatherList)
    }

    private fun subscribeUi() {
        viewModel.weatherList.observe(this, Observer { list ->
            list?.let { adapter.setData(it) }
        })

        viewModel.updateStatus.observe(this, Observer { item ->
            item?.let { adapter.updateStatus(it) }
        })

        viewModel.editStatus.observe(this, Observer { status ->
            status?.let {
                adapter.setEditStatus(it)
                if (it) {
                    fabAdd.visibility = View.GONE
                    fabOk.visibility = View.VISIBLE
                } else {
                    fabAdd.visibility = View.VISIBLE
                    fabOk.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_change -> {
                viewModel.setEditStatus(true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun askDelete(id: Long, name: String) {
        DialogFragmentDelete.newInstance(id, name)
            .show(childFragmentManager, DialogFragmentDelete.TAG)
    }

    override fun needDelete(id: Long?) {
        id?.let {
            adapter.deleteItem(id)?.let { list ->  viewModel.delete(it, list)}
        }
    }

    private fun startMotion(holder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(holder)
    }

    companion object {
        const val TAG = "WEATHER_FRAGMENT_LIST_TAG"
        @JvmStatic
        fun newInstance() = FragmentWeatherList()
    }

}