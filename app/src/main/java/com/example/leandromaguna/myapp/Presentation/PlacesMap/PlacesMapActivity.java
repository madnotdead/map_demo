
 /* Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package com.example.leandromaguna.myapp.Presentation.PlacesMap;

        import com.example.leandromaguna.myapp.Application.PlaceService;
        import com.example.leandromaguna.myapp.Presentation.PlacesDetails.PlaceDetailsActivity;
        import com.example.leandromaguna.myapp.R;
        import com.example.leandromaguna.myapp.Utils.PermissionUtils;
        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.location.places.Place;
        import com.google.android.gms.maps.CameraUpdate;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
        import com.google.android.gms.maps.LocationSource;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import android.Manifest;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

 /**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link android.Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
public class PlacesMapActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMapLongClickListener{

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final String TAG = PlacesMapActivity.class.getSimpleName();
    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private GoogleApiClient mApiClient;

    private Location currentLocation;
     private LatLng selectedLocation;

     private final int REQUEST_PLACE = 1;

     private PlaceService _placeService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_location_demo);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        mLocationSource = new LongPressLocationSource();

        buildGoogleApiClient();

        if(savedInstanceState != null)
            firstTime = savedInstanceState.getBoolean("firstTime");

        _placeService = new PlaceService(this);

        Log.i(TAG,"onCreate");
        getMarkers();

        if(getIntent().getExtras() != null){
            selectedLocation = (LatLng)getIntent().getExtras().get("selectedLocation");
        }


    }

     private void getMarkers(){

         for (com.example.leandromaguna.myapp.Model.Place place:_placeService.getAllPlaces()) {
             LatLng location = place.getLocation() == null ? new LatLng(10,10) : place.getLocation();
             markers.add(new MarkerOptions().position(location).title(place.getTitle()).snippet(place.getAdress()));
         }
     }
    @Override
    protected void onStart() {
        super.onStart();
        mApiClient.connect();
        Log.d(TAG,"onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mApiClient.isConnected()){
            mApiClient.disconnect();
        }
        Log.d(TAG,"onStop()");
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.i(TAG,"onMapReady()");
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.remove();
            }
        });
        setMarkersOnMap();
        enableMyLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {

            final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                buildAlertMessageNoGps();
                return;
            }

            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
//            mMap.setLocationSource(mLocationSource);
//            mMap.setOnMapLongClickListener(mLocationSource);
//
                Log.d(TAG,"enableMyLocation()");

            if(selectedLocation != null){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation,15));
            }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        mMap.setMyLocationEnabled(true);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }



    }

    @Override
    protected void onPause() {
        super.onPause();
//        mLocationSource.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setMarkersOnMap(){
        Log.i(TAG,"setMarkersOnMap()");
        Log.i(TAG,"markers.size()" + markers.size() );
        mMap.clear();
        for (MarkerOptions marker:markers) {
            mMap.addMarker(marker);
        }
    }
    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    private void buildGoogleApiClient(){
        mApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    private boolean firstTime = true;
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        currentLocation = LocationServices.FusedLocationApi.getLastLocation(mApiClient);

        if(currentLocation != null) {
            Log.w("CurrentLocation", "Latitud: " + currentLocation.getLatitude() + " - " + "Latitud: " + currentLocation.getLongitude());
            if(mMap!=null && firstTime){

//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),15));
                firstTime = false;

            }
        }else{
            Log.w("CurrentLocation","Todo va bien :)");
        }
        Log.d(TAG,"onConnected()");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Connection failed: " + connectionResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("firstTime",firstTime);
    }

    private List<MarkerOptions> markers = new ArrayList<>();
    @Override
    public void onMapLongClick(LatLng latLng) {


        Intent i = new Intent(this, PlaceDetailsActivity.class);
        i.putExtra("currentLocation",latLng);
        startActivityForResult(i,REQUEST_PLACE);
   /*     // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(latLng);

        // Setting snippet for the InfoWindow
        markerOptions.snippet("Tap here to remove this marker");

        // Setting title for the InfoWindow
        markerOptions.title("Marker Demo");

        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);*/
//        if(markers.size() > 0){
//
//            markers.add(new MarkerOptions().position(latLng));
//        }latLng

    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if(requestCode == REQUEST_PLACE){
             if(resultCode == RESULT_OK){
                 Log.i(TAG,"REQUEST_PLACE/RESULT_OK");
                 //Agrego marker a la lista
                 Bundle newPlace = data.getBundleExtra("savedPlace");
                 MarkerOptions newMarker = new MarkerOptions()
                         .position((LatLng)newPlace.get("position"))
                         .title((String)newPlace.get("title"))
                         .snippet((String)newPlace.get("description"));
                 markers.add(newMarker);

                 setMarkersOnMap();

             }else{
                 Log.i(TAG,"REQUEST_PLACE/ELSE");
             }
         }
     }
 }