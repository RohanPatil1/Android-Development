package com.rohan.waymaps.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.rohan.waymaps.Adapter.PlacesAdapter;
import com.rohan.waymaps.DataModel.Place;
import com.rohan.waymaps.R;
import com.rohan.waymaps.RemoteUtils.Constants;
import com.rohan.waymaps.RemoteUtils.GMapServices;
import com.rohan.waymaps.pojo.MyPlaces;
import com.rohan.waymaps.pojo.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener, PlacesAdapter.OnPlaceClickListener {


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
    private String placeType;
//    FloatingActionButton searchFAB;
    Button start_btn;
    ToggleButton toggle_btn;

    Double currLAT, currLONG;
    List<Place> dataList = new ArrayList<Place>();
    PlacesAdapter placeAdapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private CoordinatorLayout bottomSheetLayout;
    private RecyclerView placesRecyclerView;
    TextView btm_placeName;
    String placeTypeDisplayName;
    LottieAnimationView mLottieAnimationView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        Typeface typeface_quicksand = Typeface.createFromAsset(getAssets(), "fonts/quicksand.ttf");
        Typeface typeface_m_light = Typeface.createFromAsset(getAssets(), "fonts/montserratlight.ttf");
        Typeface typeface_m_bold = Typeface.createFromAsset(getAssets(), "fonts/montserratbold.ttf");




        mLottieAnimationView = findViewById(R.id.lottie_animationView);
        mLottieAnimationView.playAnimation();
        mLottieAnimationView.setVisibility(View.VISIBLE);
        mapView = findViewById(R.id.mapView);
        start_btn = findViewById(R.id.button);
        mapView.getMapAsync(mapboxMap -> MainActivity.this.mapboxMap = mapboxMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        mServices = Constants.getGoogleServices();
        bottomSheetLayout = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        toggle_btn = findViewById(R.id.toggleButton);
        placeAdapter = new PlacesAdapter(this, dataList, this);
        placesRecyclerView = findViewById(R.id.placesRecyclerView);
        placesRecyclerView.setAdapter(placeAdapter);
        placesRecyclerView.setVisibility(GONE);
        btm_placeName = findViewById(R.id.placeName_bottom);
        btm_placeName.setTypeface(typeface_m_bold);
        start_btn.setOnClickListener(view -> {

            NavigationLauncher.startNavigation(this, NavigationLauncherOptions.builder().directionsRoute(currentRoute).shouldSimulateRoute(false).build());


        });


//        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/your-mapbox-username/your-style-ID"), new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//
//            }
//        });

        Intent intent = getIntent();
        placeType = intent.getStringExtra("placeType");
        placeTypeDisplayName = intent.getStringExtra("placeTypeDisplayName");
        currLAT = Double.valueOf(intent.getStringExtra("mylat"));
        currLONG = Double.valueOf(intent.getStringExtra("mylng"));
        btm_placeName.setText(placeTypeDisplayName + " Near You ");
        MapsInitializer.initialize(getApplicationContext());


        AsyncGetPlaces myTask = new AsyncGetPlaces();
        myTask.execute();


        toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    toggle_btn.setChecked(true);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    toggle_btn.setChecked(false);
                }
            }

            @Override
            public void onSlide(View view, float v) {

            }
        });


        placesRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                v.onTouchEvent(event);
                return true;
            }
        });

    }//onCreateEnds

    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }

        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(point));
        destinationPostion = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        originPosition = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
        getRoute(originPosition, destinationPostion);


        start_btn.setEnabled(true);


        return true;
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        mapboxMap.setStyle(Style.TRAFFIC_NIGHT, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                enableLocationComponent();


            }
        });
//        mapboxMap.setStyle(Style.LIGHT);

        this.mapboxMap = mapboxMap;
        mapboxMap.addOnMapClickListener(this);


//        if (locationComponent == null) {
//            mapboxMap.setStyle(Style.LIGHT);
//            enableLocationComponent();
//        }


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
                        mLottieAnimationView.setVisibility(GONE);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        mLottieAnimationView.setVisibility(GONE);

    }

    @SuppressLint("WrongConstant")
    private void enableLocationComponent() {

        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            this.locationComponent = this.mapboxMap.getLocationComponent();
            this.locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, mapboxMap.getStyle()).build());
            this.locationComponent.setLocationComponentEnabled(true);
            this.locationComponent.setCameraMode(24);
            this.locationComponent.setRenderMode(4);

            Log.d(TAG, "enableLocationComponent:  LocationComponent SET!");
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

            enableLocationComponent();
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
   
        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location=" + latitude + "," + longitude);
        googlePlaceUrl.append("&radius=" + 1000);
        googlePlaceUrl.append("&type=" + placeType);
        googlePlaceUrl.append("&sensor=" + true);
        //API Key Removed purposely
        googlePlaceUrl.append("&key=" + "PUT YOUR OWN API KEY HERE");

        Log.d("WayMaps", "getURL: " + googlePlaceUrl);


        return googlePlaceUrl.toString();
    }


    private void nearByPlaces(final String placeType) {


//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=30.328093,-81.4855&radius=1000&type=market&sensor=true&key=AIzaSyDlu4ZCkLzXFaHiRIZ5Z6xHozoEZLjgJ0Q
        String url = getURL(currLAT, currLONG, placeType);
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
                        int rating;
                        if (googlePlace.getRating() == null) {
                            rating = 3;
                        } else {
                            rating = googlePlace.getRating().intValue();
                        }

                        String placeIcon = googlePlace.getIcon();
                        String vicinity = googlePlace.getVicinity();
                        Log.d(TAG, "onResponse: " + placeIcon + "Vicinity : " + vicinity + "Rating :" + rating);
                        Place currPlace = new Place(lat_, lng_, placeName, vicinity, rating, placeIcon);
                        dataList.add(currPlace);

                        //----------------SET MARKER-----------------
//                        gMarker = mapboxMap.addMarker(markerOptions);
//                        gMarker.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                        gMarker.animateCamera(CameraUpdateFactory.zoomTo(12));


//
//                        switch (_placeType) {
//                            case "restaurant":
//                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//
//                                break;
//
//                            case "school":
//                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                                break;
//
//                            case "market":
//                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//                                break;
//                        }
//
//                        CameraPosition position = new CameraPosition.Builder()
//                                .target(new LatLng(lat_, lng_)) // Sets the new camera position
//                                .zoom(15) // Sets the zoom
//                                .bearing(180) // Rotate the camera
//                                .tilt(30) // Set the camera tilt
//                                .build();
//
//                        LatLng latLng1 = new LatLng(lat_, lng_);
//                        gMarker = mapboxMap.addMarker(new MarkerOptions().position(latLng1));
//                        gMarker.setTitle(placeName);
//                        mapboxMap.animateCamera(CameraUpdateFactory
//                                .newCameraPosition(position), 7000);


                    }


                    placeAdapter.notifyDataSetChanged();
                    placesRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MyPlaces> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onPlaceClick(int position) {
        mLottieAnimationView.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }

        Place currentPlace = dataList.get(position);
        Log.d(TAG, "onItemClick: Clicked On " + currentPlace.getPlaceName() + "Lat : " + currentPlace.getLAT());
        LatLng point = new LatLng(currentPlace.getLAT(), currentPlace.getLNG());
        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(point));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(point.getLatitude(), point.getLongitude())) // Sets the new camera position
                .zoom(15) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build();

        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        destinationPostion = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        originPosition = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
        getRoute(originPosition, destinationPostion);


        start_btn.setEnabled(true);


    }


    private class AsyncGetPlaces extends AsyncTask<String, String, String> {


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected String doInBackground(String... strings) {


            Log.d(TAG, "doInBackground: ==>Getting NearByPlaces =>" + placeType);
            nearByPlaces(placeType);
            Log.d(TAG, "doInBackground: ==>DONE! =>" + placeType);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    mLottieAnimationView.setVisibility(View.GONE);
                }
            }, 2000);


        }
    }

}
/*
if (dataList != null) {

                bottomLoading.setVisibility(View.GONE);
                btm_placeName.setText(dataList.get(0).getPlaceName());

                String imgUrl = dataList.get(0).getImgUrl();
                Picasso.get()
                        .load(imgUrl)
                        .placeholder(R.drawable.firefighter)
                        .error(R.drawable.ic_close)
                        .into(btm_img);
            }
 */
