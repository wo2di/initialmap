package com.madcamp.myapplication;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.lang.Math;


//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        // Prompt the user for permission.
//
//    }
//
//}



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = MapsActivity.class.getSimpleName();
    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private Button btnFindPath;
    private TextView time;
    private TextView distance;
    LatLng kaistN1 = new LatLng(36.374438, 127.365583);
    LatLng northRes = new LatLng(36.373669, 127.359132);
    LatLng westRes = new LatLng(36.366932, 127.360485);
    LatLng eastRes = new LatLng(36.369180, 127.363580);
    LatLng lotte = new LatLng(36.361910, 127.379042);
    LatLng gungdong = new LatLng(36.362309, 127.351120);
    LatLng aeundong = new LatLng(36.364016, 127.358699);
    LatLng kaistdorm = new LatLng(36.373604, 127.357537);

    double yourlat, yourlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btnFindPath = (Button) findViewById(R.id.btnFindPath);

        distance = (TextView) findViewById(R.id.tvDistance);

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item){
                        switch (item.getItemId()){
                            case R.id.m1:
                                Toast.makeText(getApplicationContext(), "KAIST N1", Toast.LENGTH_SHORT).show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.374438, 127.365583));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kaistN1, 15));

                                break;
                            case R.id.m2:
                                Toast.makeText(getApplicationContext(), "북측식당", Toast.LENGTH_SHORT).show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.373669, 127.359132));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(northRes, 15));

                                break;
                            case R.id.m3:
                                Toast.makeText(getApplicationContext(), "서측식당", Toast.LENGTH_SHORT).show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.366932, 127.360485));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(westRes, 15));

                                break;
                            case R.id.m4:
                                Toast.makeText(getApplicationContext(), "동측식당", Toast.LENGTH_SHORT).show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.369180, 127.363580));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eastRes, 15));

                                break;
                            case R.id.m5:
                                Toast.makeText(getApplicationContext(), "둔산동 롯데시네마", Toast.LENGTH_SHORT).show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.361910, 127.379042));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lotte, 15));

                                break;
                            case R.id.m6:
                                Toast.makeText(getApplicationContext(), "궁동 로데오거리", Toast.LENGTH_SHORT).show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.362309, 127.351120));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gungdong, 15));

                                break;
                            case R.id.m7:
                                Toast.makeText(getApplicationContext(), "어은동 한빛교회", Toast.LENGTH_SHORT).show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.364016, 127.358699));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aeundong, 15));

                                break;
                            case R.id.m8:
                                Toast.makeText(getApplicationContext(), "기숙사", Toast.LENGTH_SHORT).show();
                                distance.setText(distanceLatLong2(yourlat, yourlng, 36.373604, 127.357537));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kaistdorm, 15));

                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(kaistN1).title("N1").icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin)));
        mMap.addMarker(new MarkerOptions().position(northRes).title("북측식당"));
        mMap.addMarker(new MarkerOptions().position(westRes).title("서측식당"));
        mMap.addMarker(new MarkerOptions().position(eastRes).title("동측식당"));
        mMap.addMarker(new MarkerOptions().position(lotte).title("둔산동 롯데시네마"));
        mMap.addMarker(new MarkerOptions().position(gungdong).title("궁동 로데오거리"));
        mMap.addMarker(new MarkerOptions().position(aeundong).title("어은동 한빛교회"));
        mMap.addMarker(new MarkerOptions().position(kaistdorm).title("기숙사"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kaistN1, 15));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();


    }

    public static String distanceLatLong2(double lat1, double lng1, double lat2, double lng2)
    {
        double earthRadius = 6371.0d; // KM: use mile here if you want mile result

        double dLat = toRadian(lat2 - lat1);
        double dLng = toRadian(lng2 - lng1);

        double a = Math.pow(Math.sin(dLat/2), 2)  +
                Math.cos(toRadian(lat1)) * Math.cos(toRadian(lat2)) *
                        Math.pow(Math.sin(dLng/2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return Double.toString(Math.round(earthRadius * c * 1000d)/1000d * 1000) + " m"; // returns result kilometers
    }

    public static double toRadian(double degrees)
    {
        return (degrees * Math.PI) / 180.0d;
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            yourlat = mLastKnownLocation.getLatitude();
                            yourlng = mLastKnownLocation.getLongitude();
                                                    } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }


    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
