package ru.kcoder.weatherhelper.presentation.place

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.place_add_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.AppRouter
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.utils.Permissions

class FragmentAddPlace : BaseFragment(), DialogFragmentAddPlace.Callback {

    private val viewModel: ViewModelAddPlace by viewModel()
    private var map: GoogleMap? = null
    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.place_add_fragment, container, false)

        initMap(view, savedInstanceState)
        initPlace()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeData()
    }

    @SuppressLint("MissingPermission") //"smart cast" not work
    private fun initMap(view: View, savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        mapView = view.findViewById(R.id.mapView) as MapView
        mapView?.onCreate(mapViewBundle)
        mapView?.getMapAsync { mMap ->
            map = mMap
            with(mMap) {
                if (BuildConfig.DEBUG) uiSettings?.isZoomControlsEnabled = true // just for emulator
                activity?.let {
                    if (Permissions.checkLocation(it)) {
                        isMyLocationEnabled = true
                    }
                }
                setMarker(this)

                setOnMapLongClickListener { it ->
                    clear()
                    updateViewModel(PlaceMarker(it.latitude, it.longitude))
                }
            }
        }
    }

    private fun initPlace() {
        val placeFragment =
            childFragmentManager.findFragmentByTag(PLACES_FRAGMENT_TAG)
                    as? SupportPlaceAutocompleteFragment?
                ?: SupportPlaceAutocompleteFragment()

        childFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutPlaceContainer, placeFragment, PLACES_FRAGMENT_TAG)
            .commit()

        placeFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                map?.let {
                    it.clear()
                    updateViewModel(
                        PlaceMarker(
                            place.latLng.latitude, place.latLng.longitude,
                            place.name.toString(), place.address?.toString()
                        )
                    )
                    val cameraPosition = CameraPosition.Builder().target(place.latLng).zoom(10f).build()
                    it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
            }

            override fun onError(status: Status) {
                showError(R.string.place_add_error_google_places)
                log(status.statusMessage ?: status.toString())
            }
        })
    }

    private fun initView() {
        fabSelectPlace.setOnClickListener { _ ->
            viewModel.markerLiveData.observe(this, Observer { place ->
                place?.let {
                    if (it.name == null) {
                        showPlaceDialog()
                    } else {
                        selectPlace(it)
                    }
                }
            })
        }
    }


    private fun subscribeData() {
        viewModel.errorLiveData.observe(this, android.arch.lifecycle.Observer { res ->
            res?.let { super.showError(it) }
        })

        viewModel.addedPlaceIdLiveData.observe(this, Observer { id ->
            activity?.let {
                if (id != null) {
                    AppRouter.showWeatherDetailFragment(it, id)
                }
            }
        })
    }

    private fun selectPlace(place: PlaceMarker) {
        viewModel.savePlace(place)
    }

    private fun showPlaceDialog() {
        DialogFragmentAddPlace.newInstance()
            .show(childFragmentManager, DialogFragmentAddPlace.TAG)
    }

    @SuppressLint("RestrictedApi")
    private fun setMarker(mMap: GoogleMap) {
        viewModel.markerLiveData.observe(this, android.arch.lifecycle.Observer {
            it?.let { place ->
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(place.lat, place.lon))
                        .title(place.name).also { option ->
                            place.address?.let { option.snippet(place.address.toString()) }
                        })
                fabSelectPlace.visibility = View.VISIBLE
            }
        })
    }

    private fun updateViewModel(place: PlaceMarker) {
        viewModel.updateViewModel(place)
    }

    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }

    override fun onStart() {
        mapView?.onStart()
        super.onStart()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView?.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapView?.onLowMemory()
        super.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView?.onSaveInstanceState(mapViewBundle)
    }

    override fun selectName(name: String?) {
        name?.let { mName ->
            viewModel.markerLiveData.observe(this, Observer { place ->
                place?.let { selectPlace(it.apply { this.name = mName }) }
            })
        }
    }

    companion object {
        const val TAG = "FRAGMENT_ADD_WEATHER_TAG"
        private const val PLACES_FRAGMENT_TAG = "PLACES_FRAGMENT_TAG"
        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
        @JvmStatic
        fun newInstance() = FragmentAddPlace()
    }
}