package com.rohan.waymaps;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.rohan.waymaps.RemoteUtils.Constants;
import com.rohan.waymaps.RemoteUtils.GMapServices;
import com.rohan.waymaps.pojo.MyPlaces;
import com.rohan.waymaps.pojo.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {


    private MapView mapView;
    private PermissionsManager permissionsManager;

    public MapboxMap mapboxMap;
    private LocationComponent locationComponent;
    private GoogleApiClient mGoogleApiClient;

    GMapServices mServices;
    private static final int REQUEST_AUTOCOMPLETE_CODE = 1;
    private static final String TAG = "MapD";
    DirectionsRoute currentRoute;
    private Marker destinationMarker;
    private Point destinationPostion;
    public NavigationMapRoute navigationMapRoute;
    private Location originLocation;
    private Point originPosition;
    private Marker gMarker;

    FloatingActionButton searchFAB;
    Button start_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);


        mapView = findViewById(R.id.mapView);
        start_btn = findViewById(R.id.button);
        mapView.getMapAsync(mapboxMap -> MainActivity.this.mapboxMap = mapboxMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        mServices = Constants.getGoogleServices();

        start_btn.setOnClickListener(view -> {

            NavigationLauncher.startNavigation(this, NavigationLauncherOptions.builder().directionsRoute(currentRoute).shouldSimulateRoute(false).build());


        });


//        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/your-mapbox-username/your-style-ID"), new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//
//            }
//        });

    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }

        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(point));
        destinationPostion = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        originPosition = Point.fromLngLat(this.locationComponent.getLastKnownLocation().getLongitude(), this.locationComponent.getLastKnownLocation().getLatitude());
        getRoute(originPosition, destinationPostion);


        start_btn.setEnabled(true);


        return true;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        mapboxMap.setStyle(Style.TRAFFIC_NIGHT, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                enableLocationComponent(style);

            }
        });
        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);

    }


    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if (response.body() == null) {
                            Log.d(TAG, "onResponse: No ROUTES FOUND");
                            return;
                        } else if (response.body().routes().size() == 0) {
                            Toast.makeText(MainActivity.this, "No Routes Found!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap);
                        }

                        navigationMapRoute.addRoute(currentRoute);

                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @SuppressLint("WrongConstant")
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            this.locationComponent = this.mapboxMap.getLocationComponent();
            this.locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, loadedMapStyle).build());
            this.locationComponent.setLocationComponentEnabled(true);
            this.locationComponent.setCameraMode(24);
            this.locationComponent.setRenderMode(4);
            return;
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "Permission Required!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }


    //-----------GoogleMaps API------------
    private String getURL(double latitude, double longitude, String placeType) {
        /*
        https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=30.328093,-81.4855&
        radius=1000&type=market&sensor=true&key=AIzaSyDlu4ZCkLzXFaHiRIZ5Z6xHozoEZLjgJ0Q
         */
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + 10000);
        googlePlaceUrl.append("&sensor=" + true);
        googlePlaceUrl.append("&key=" + "AIzaSyDlu4ZCkLzXFaHiRIZ5Z6xHozoEZLjgJ0Q");

        Log.d("WayMaps", "getURL: " + googlePlaceUrl);


        return googlePlaceUrl.toString();
    }


    private void nearByPlaces(final String placeType) {

        String url = getURL(locationComponent.getLastKnownLocation().getLatitude(), locationComponent.getLastKnownLocation().getLongitude(), placeType);
        mServices.getNearByPlaces(url).enqueue(new Callback<MyPlaces>() {
            @Override
            public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        com.google.android.gms.maps.model.MarkerOptions markerOptions = new com.google.android.gms.maps.model.MarkerOptions();
                        Result googlePlace = response.body().getResults().get(i);
                        double lat_ = googlePlace.getGeometry().getLocation().getLat();
                        double lng_ = googlePlace.getGeometry().getLocation().getLng();
                        String placeName = googlePlace.getName();
                        String vicinity = googlePlace.getVicinity();
                        com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(lat_, lng_);
                        markerOptions.position(latLng);
                        markerOptions.title(placeName);

                        switch (placeType) {
                            case "restaurant":
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                break;

                            case "school":
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                break;

                            case "market":
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                                break;
                        }
                        //----------------SET MARKER-----------------
//                        gMarker = mapboxMap.addMarker(markerOptions);
//                        gMarker.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                        gMarker.animateCamera(CameraUpdateFactory.zoomTo(12));


                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(lat_, lng_)) // Sets the new camera position
                                .zoom(17) // Sets the zoom
                                .bearing(180) // Rotate the camera
                                .tilt(30) // Set the camera tilt
                                .build();

                        LatLng latLng1 = new LatLng(lat_, lng_);
                        gMarker = mapboxMap.addMarker(new MarkerOptions().position(latLng1));

                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position), 7000);

                    }
                }
            }

            @Override
            public void onFailure(Call<MyPlaces> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
