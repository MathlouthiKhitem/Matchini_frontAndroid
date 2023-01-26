package com.example.matchinii.ui.Activities
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.matchinii.R
import com.example.matchinii.models.User
import com.example.matchinii.models.data
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference


var mapView: MapView? = null
private var loginIntent: String? = null
private var image: String? = null
lateinit var  locationlist: ArrayList<String>
private var point: MutableList<Double>? = null
class MapActivity : AppCompatActivity()  {
    private lateinit var locationManager: LocationManager
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private val locationPermissionCode = 2
    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }
    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
        point = it.coordinates();
    }
    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = MapView(this)
        setContentView(mapView)

        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
        locationlist =ArrayList()

    }
    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }
    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }
    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location

        locationComponentPlugin.updateSettings {
            this.enabled = true

            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@MapActivity,
                    R.drawable.ic_baseline_location_on_24_2,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@MapActivity,
                    R.drawable.ic_baseline_location_on_24_2,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }

        /*val locationComponentPlugin2 = locationlist[0] as LocationComponentPlugin
        locationComponentPlugin2.updateSettings {
            this.enabled = true

            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@MapActivity,
                    R.drawable.ic_check_select,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@MapActivity,
                    R.drawable.ic_check_select,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin2.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin2.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)*/
        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        loginIntent = intent.getStringExtra("login")
        val map: HashMap<String, String> = HashMap()
        map["login"] = loginIntent.toString()
        map["longitude"] = point?.get(0).toString()
        map["latitude"] = point?.get(1).toString()
        apiservice.updateLocation(map).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                print("sucess");
                Log.e("hhhhhhhh" ,map.toString())
                Log.e("yoyoyoyo" , point.toString())

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                print("fail")
            }
        })

        map["login"] = loginIntent.toString()
        apiservice.getId(map).enqueue(object :Callback<data>{
            override fun onResponse(
                call: Call<data>,
                response: Response<data>
            ) {
                Log.e("idddddddddd",response.body()!!.value)

                apiservice.amie(response.body()!!.value).enqueue(object : Callback<ArrayList<User>> {
                    override fun onResponse(
                        call: Call<ArrayList<User>>,
                        response: Response<ArrayList<User>>
                    ) {
                        val user = response.body()
                        if (user != null) {
                            for (i in user!!.indices) {
                                    locationlist.add(user[i].latitude)
                                    locationlist.add(user[i].longitude)
                                    image = user[i].Image
                                    Log.e("latitude :", user.toString())

                                    for (i in user!!.indices) {
                                        val annotationApi = mapView?.annotations
                                        val pointAnnotationManager =
                                            annotationApi?.createPointAnnotationManager(mapView)
                                        val pointAnnotationOptions: PointAnnotationOptions =
                                            PointAnnotationOptions()
                                                .withPoint(
                                                    Point.fromLngLat(
                                                        user[i].longitude.toDouble(),
                                                        user[i].latitude.toDouble()
                                                    )
                                                )
                                                .withIconImage(
                                                    BitmapFactory.decodeResource(
                                                        resources,
                                                        R.drawable.red_marker
                                                    )
                                                )
                                                .withIconSize(0.40)
                                        pointAnnotationManager?.create(pointAnnotationOptions)
                                    }
                            }
                        }
                    }
                    override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                        println("non")
                    }

                })
            }

            override fun onFailure(call: Call<data>, t: Throwable) {
                println("leee")
            }

        })


        val onIndicatorPositionChangedListener2 = OnIndicatorPositionChangedListener {
            mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(Point.fromLngLat(
                map["location"]!!.get(0).toDouble(), map["location"]!!.get(1).toDouble()))

        }

    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
    }

}

class LocationPermissionHelper(val activity: WeakReference<Activity>) {
    private lateinit var permissionsManager: PermissionsManager

    fun checkPermissions(onMapReady: () -> Unit) {
        if (PermissionsManager.areLocationPermissionsGranted(activity.get())) {
            onMapReady()
        } else {
            permissionsManager = PermissionsManager(object : PermissionsListener {
                override fun onExplanationNeeded(permissionsToExplain: List<String>) {
                    Toast.makeText(
                        activity.get(), "You need to accept location permissions.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionResult(granted: Boolean) {
                    if (granted) {
                        onMapReady()
                    } else {
                        activity.get()?.finish()
                    }
                }
            })
            permissionsManager.requestLocationPermissions(activity.get())
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}