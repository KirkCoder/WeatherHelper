package ru.kcoder.weatherhelper.presentation.weather.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.utils.Permissions


class FragmentAddNewWeather : BaseFragment() {

    private var map: GoogleMap? = null
    private var mapView: MapView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_add_fragment, container, false)

        initMap(view, savedInstanceState)
        initPlace()
        return view
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
                map?.let{
                    it.addMarker(
                        MarkerOptions()
                            .position(place.latLng)
                            .title(place.name.toString()).also {option ->
                                place.address?.let { _ -> option.snippet(place.address.toString()) }
                            })
                    val cameraPosition = CameraPosition.Builder().target(place.latLng).zoom(10f).build()
                    it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
                log(place.name.toString())
            }

            override fun onError(status: Status) {
                log(status.toString())
            }
        });
    }

    @SuppressLint("MissingPermission") //"smart cast" not work
    private fun initMap(view: View, savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mapView = view.findViewById(R.id.mapView) as MapView
        mapView?.onCreate(mapViewBundle)
        mapView?.getMapAsync { mMap ->
            map = mMap

            map?.uiSettings?.isZoomControlsEnabled = true
            map?.uiSettings?.isCompassEnabled = true
            activity?.let {
                if (Permissions.checkLocation(it)) {
                    map?.isMyLocationEnabled = true
                }
            }
        }
    }


    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onStart() {
        mapView?.onStart()
        super.onStart()
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
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView?.onSaveInstanceState(mapViewBundle)
    }

    companion object {
        const val TAG = "FRAGMENT_ADD_NEW_WEATHER_TAG"
        private const val PLACES_FRAGMENT_TAG = "PLACES_FRAGMENT_TAG"
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        @JvmStatic
        fun newInstance() = FragmentAddNewWeather()
    }
}