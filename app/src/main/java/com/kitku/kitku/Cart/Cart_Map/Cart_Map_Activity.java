package com.kitku.kitku.Cart.Cart_Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kitku.kitku.BackgroundProcess.z_AsyncServerAccess;
import com.kitku.kitku.BackgroundProcess.z_BackendPreProcessing;
import com.kitku.kitku.R;

import java.util.Objects;

public class Cart_Map_Activity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    // set storage location
    LatLng storageLocation = new LatLng(-0.9238126,100.3645052);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart__map_);

        // check whether location access is permitted
        askPermission();

        // declare map
        MapView mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // needed to get the map to display immediately

        // reference : https://stackoverflow.com/questions/19353255/how-to-put-google-maps-v2-on-a-fragment-using-viewpager
        try { MapsInitializer.initialize(this); }
        catch (Exception e) { /*e.printStackTrace();*/ }
        mapView.getMapAsync(this);

        // set back button
        findViewById(R.id.buttonToolbarMapBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart_Map_Activity.this.finish();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        // connect to Google and get current location
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    // procedure will execute after connected to google service
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // get current location
        final Task<Location> mLastLocation = LocationServices.getFusedLocationProviderClient(this)
                .getLastLocation();
        // get latitude and longitude
        mLastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                LatLng currentCoordinate = new LatLng(
                        Objects.requireNonNull(mLastLocation.getResult()).getLatitude(),
                        mLastLocation.getResult().getLongitude());
                // mark location on map and point to it
                mMap.addMarker(new MarkerOptions().position(currentCoordinate).title("Lokasi anda saat ini."));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentCoordinate));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinate, 16.0f));
                String urlDistance = getDirectionsUrl(currentCoordinate, storageLocation);
                Log.d("url", urlDistance);
                sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.d("output",output);
                        try { new z_BackendPreProcessing().readDistance(output); }
                        catch (Exception e) { /*e.printStackTrace();*/ }
                    }
                });
                sendData.execute(urlDistance);
            }
        });

        /*if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }*/
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(
                this,
                "Gagal terhubung dengan Google. Periksa kembali koneksi anda.",
                Toast.LENGTH_LONG)
                .show();
    }

    int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } /*else {
            // Permission has already been granted
        }*/
    }

    // Inisiasi AsyncTask dari z_AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends z_AsyncServerAccess {
        backgroundTask(AsyncResponse delegate) { this.delegate = delegate; }
    }
    backgroundTask sendData;

    // reference : https://www.journaldev.com/13373/android-google-map-drawing-route-two-points
    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        /*String sensor = "sensor=false";
        String mode = "mode=driving";*/
        String key = "key=" + getResources().getString(R.string.google_maps_key);
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + key;// + "&" + sensor + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        return z_BackendPreProcessing.URL_GetDistance + output + "?" + parameters;
    }
}