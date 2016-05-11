package fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;

import fr.fges.fixmycity.R;

public class GetCurrentLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final int MY_PERMISSION_REQUEST_LOCATION=1;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastConnection;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private Location mCurrentLocation;
    private LocationRequest mLocationRequest;
    private String mLastUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_current_location);
        buildGoogleApiClient();
        latitudeTextView = (TextView)findViewById(R.id.latitude_value);
        longitudeTextView = (TextView)findViewById(R.id.longitude_value);

        Button button = (Button) findViewById(R.id.btnLocation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createLocationRequest();
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
        displayLocation();
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

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
       /* Snackbar.make(findViewById(R.id.container), R.string.connection_location_failed, Snackbar.LENGTH_LONG).show();*/
    }

    private void displayLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
        }else{
            mLastConnection = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastConnection!=null){
                latitudeTextView.setText(""+mLastConnection.getLatitude());
                longitudeTextView.setText(""+mLastConnection.getLongitude());
            }else {
                /* Snackbar.make(findViewById(R.id.container), R.string.connection_location_failed, Snackbar.LENGTH_LONG).show();*/
            }
        }
    }

    private void OnRequestPermissionResult(int requestCode, String[] permission,int[] grantResults){
        if(requestCode == MY_PERMISSION_REQUEST_LOCATION){
            if(grantResults.length == 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                displayLocation();
            }
        }
    }
}