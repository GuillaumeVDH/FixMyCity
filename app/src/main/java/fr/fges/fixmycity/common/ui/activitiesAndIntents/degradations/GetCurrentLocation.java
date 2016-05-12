package fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Date;

import fr.fges.fixmycity.R;

public class GetCurrentLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private static final int MY_PERMISSION_REQUEST_LOCATION=1;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastConnection;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private Location mCurrentLocation;
    private LocationRequest mLocationRequest;
    private String mLastUpdateTime;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_current_location);
        buildGoogleApiClient();
        latitudeTextView = (TextView)findViewById(R.id.latitude_value);
        longitudeTextView = (TextView)findViewById(R.id.longitude_value);

        final Button button = (Button) findViewById(R.id.btnLocation);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startLocationUpdate();
            }
        });
    }

    private synchronized void buildGoogleApiClient(){
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            createLocationRequest();
        }
    }

    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        startLocationUpdate();
    }

    protected void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
       /* Snackbar.make(findViewById(R.id.container), R.string.connection_location_failed, Snackbar.LENGTH_LONG).show();*/
    }

    private void startLocationUpdate(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
        }else{
            if (mCurrentLocation==null){
                mCurrentLocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                mLastUpdateTime= java.text.DateFormat.getDateTimeInstance().format(new Date());
            }else {
              LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, (LocationListener) this);
            }
            updateUI();
        }
    }

    private void updateUI(){
        if (mCurrentLocation!=null) {
            latitudeTextView.setText(String.valueOf(mCurrentLocation.getLatitude()));
            longitudeTextView.setText(String.valueOf(mCurrentLocation.getLongitude()));
        }
    }
    private void stopLocationUpdate(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    private void OnRequestPermissionResult(int requestCode, String[] permission,int[] grantResults){
        if(requestCode == MY_PERMISSION_REQUEST_LOCATION){
            if(grantResults.length == 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startLocationUpdate();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getDateTimeInstance().format(new Date());
        updateUI();
        /*LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        stopLocationUpdate();*/
    }

    public void onMapReady(GoogleMap map){
        mMap=map;
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
        }
        else{
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}