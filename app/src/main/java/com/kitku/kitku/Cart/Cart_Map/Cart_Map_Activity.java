package com.kitku.kitku.Cart.Cart_Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kitku.kitku.BackgroundProcess.AsyncServerAccess;
import com.kitku.kitku.BackgroundProcess.BackendPreProcessing;
import com.kitku.kitku.BackgroundProcess.ImageCaching;
import com.kitku.kitku.R;

import java.io.File;
import java.util.Objects;

public class Cart_Map_Activity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationSource.OnLocationChangedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    MapView mapView;
    // set storage location
    LatLng storageLocation = new LatLng(-0.9238126,100.3645052);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart__map_);

        // check whether location access is permitted
        askPermission();

        // declare map
        mapView = findViewById(R.id.mapView);
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
        findViewById(R.id.buttonGetCurrentLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart_Map_Activity.this.onConnected(null);
            }
        });
        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart_Map_Activity.this.buttonSaveLocation(v);
            }
        });
    }

    public void buttonSaveLocation(View v) {
        File folder = null;
        try {
            folder = getApplicationContext().getExternalFilesDir("Location");
        } catch (Exception e) {
            ImageCaching.createDir(folder);
            folder = getApplicationContext().getExternalFilesDir("Location");
        }
        final String mapPictureLocation = folder.getAbsolutePath().concat("/lokasi");
        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                ImageCaching.saveImageIn(mapPictureLocation, bitmap);
            }
        });
        //startActivity(new Intent(this, Checkout_SetAddressActivity.class));
        this.finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(this, Checkout_SetAddressActivity.class));
        this.finish();
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
        //mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
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
                setMarkerLocation(mLastLocation);
            }
        });
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

    int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;
    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_FINE_LOCATION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION))) {
            Toast.makeText(
                    this,
                    "Jika lokasi tidak tampil, tekan tombol merah pada sudut kanan bawah layar anda.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(
                    this,
                    "Mohon aktifkan izin akses lokasi supaya dapat menampilkan lokasi anda.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("att", "location changed");
        setMarkerLocation(location);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("att", "on map click");
        setMarkerLocation(latLng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d("att", "on map long click");
        setMarkerLocation(latLng);
    }

    // Inisiasi AsyncTask dari AsyncServerAccess supaya dapat mengakses Activity
    static class backgroundTask extends AsyncServerAccess {
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
        return BackendPreProcessing.URL_GetDistance + output + "?" + parameters;
    }

    public void setMarkerLocation(Task<Location> mLastLocation) {
        double latitude = 0, longitude = 0;
        try {
            latitude = Objects.requireNonNull(mLastLocation.getResult()).getLatitude();
            longitude = mLastLocation.getResult().getLongitude();
        } catch (Exception e) { e.printStackTrace(); }
        if (latitude != 0)
            setMarker(latitude, longitude);
    }

    public void setMarkerLocation(Location location) {
        double latitude = 0, longitude = 0;
        try {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        } catch (Exception e) { e.printStackTrace(); }
        if (latitude != 0)
            setMarker(latitude, longitude);
    }

    public void setMarkerLocation(LatLng latLng) {
        double latitude = 0, longitude = 0;
        try {
            latitude = latLng.latitude;
            longitude = latLng.longitude;
        } catch (Exception e) { e.printStackTrace(); }
        if (latitude != 0)
            setMarker(latitude, longitude);
    }

    private void setMarker(double latitude, double longitude) {
        LatLng currentCoordinate = new LatLng(latitude, longitude);
        mMap.clear();
        // mark location on map and point to it
        mMap.addMarker(new MarkerOptions().position(currentCoordinate).title("Lokasi yang anda pilih."));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentCoordinate));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinate, 16.0f));
        String urlDistance = getDirectionsUrl(currentCoordinate, storageLocation);
        Log.d("url", urlDistance);
        sendData = new backgroundTask(new backgroundTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                //Log.d("output", output);
                try {
                    BackendPreProcessing.readDistance(output);
                } catch (Exception e) { /*e.printStackTrace();*/ }
            }
        });
        sendData.execute(urlDistance, null);
    }
}
