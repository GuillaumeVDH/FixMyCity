package fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.Constants;
import fr.fges.fixmycity.common.models.Degradation;
import fr.fges.fixmycity.common.services.DegradationService;
import fr.fges.fixmycity.common.services.DegradationServicesImpl;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.BaseActivity;

public class ReportDegradationActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Bind(R.id.report_degradation_take_photo_btn)
    Button mTakePhotoBtn;
    @Bind(R.id.report_degradation_get_position_btn)
    Button mGetPositionBtn;
    @Bind(R.id.report_degradation_photo_imv)
    ImageView mImageView;
    @Bind(R.id.report_degradation_edt)
    EditText mDescriptionEdt;
    @Bind(R.id.report_degradation_sp)
    Spinner mDegradationType;
    private File mPhotoFile;
    private File mStorageDir;
    private DegradationService mDegradationService;
    private String mCurrentPhotoPath = null;
    private Uri mCapturedImageURI = null;
    private GoogleApiClient mGoogleApiClient;
    private Double mLatitude = 0.0;
    private Double mLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_report_degradation, null, false);
        ButterKnife.bind(this, contentView);

        mDrawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDegradationService = new DegradationServicesImpl();
        mStorageDir = new File(Environment.getExternalStorageDirectory()+Constants.APP_PHOTOS_PATH);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Degradation degradation = new Degradation();
                    if (mPhotoFile != null && mPhotoFile.exists()) {
                        degradation.setmImagePath(mPhotoFile.getAbsolutePath());

                        degradation.setmDescription(mDescriptionEdt.getText().toString());
                        Random r = new Random();
                        int randomreferences = r.nextInt(100000000 - 0) + 0;
                        degradation.setmReference("REF-" + randomreferences);
                        degradation.setmCategory(mDegradationType.getSelectedItem().toString());

                        degradation.setmLatitude(mLatitude);
                        degradation.setmLongitude(mLongitude);

                        long id = mDegradationService.addDegradation(degradation);
                        degradation.setmId(id);

                        mDegradationService.updateDegradation(degradation);
                        if (id!=-1) {
                            Intent intent = new Intent(getBaseContext(), AllReportedDegradationsActivity.class);
                            intent.putExtra("report_state", "report_ok");
                            startActivity(intent);
                        }

                    }
                    else {
                        Snackbar.make(view, R.string.report_error, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }

        buildGoogleApiClient();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.degradations_types_array, android.R.layout.simple_spinner_dropdown_item);
        mDegradationType.setAdapter(adapter);

    }

    @OnClick(R.id.report_degradation_take_photo_btn)
    protected void onTakePhotoBtn() {
        verifyStoragePermissions();
        mPhotoFile = null;
        dispatchTakePictureIntent();
    }

    @OnClick(R.id.report_degradation_get_position_btn)
    protected void onGetPositionBtn(View view) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
        }else{
            if (isGPSEnabled(this)) {
                Location location = null;
                if (location==null){
                    location=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();
                }else {
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(10000);
                    locationRequest.setFastestInterval(5000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,locationRequest, this);
                }
            }
            else{
                showGPSDisabledAlertToUser();
            }

        }
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.would_you_active_gps)
                .setCancelable(false)
                .setPositiveButton(R.string.active_gps,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    /**
     * Start the camera by dispatching a camera intent.
     */
    protected void dispatchTakePictureIntent() {

        // Check if there is a camera.
        PackageManager packageManager = this.getPackageManager();
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
            Toast.makeText(this, R.string.dont_have_camera, Toast.LENGTH_SHORT).show();
            return;
        }

        // Camera exists? Then proceed...
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go.
            // If you don't do this, you may get a crash in some devices.

            try {
                mPhotoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast toast = Toast.makeText(this, R.string.pb_save_image, Toast.LENGTH_SHORT);
                toast.show();
            }
            // Continue only if the File was successfully created
            if (mPhotoFile != null) {
                Uri fileUri = Uri.fromFile(mPhotoFile);
                this.setCapturedImageURI(fileUri);
                this.setCurrentPhotoPath(fileUri.getPath());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        this.getCapturedImageURI());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Log.d("photo", "OK");
            Bitmap bmp = BitmapFactory.decodeFile(mCurrentPhotoPath);

            mImageView.setImageBitmap(bmp);
        } else {
            Log.d("photo", "NOK");
        }
    }

    private File createImageFile() throws IOException {
        // create a File object for the parent directory

        if (!mStorageDir.exists()) {
            // have the object build the directory structure, if needed.
            mStorageDir.mkdirs();
            // now attach the OutputStream to the file object, instead of a String representation
            try {
                new FileOutputStream(mStorageDir);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Create an image file name
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File image = File.createTempFile(
                fileName,  /* prefix */
                ".jpg",         /* suffix */
                mStorageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        this.setCurrentPhotoPath("file:" + image.getAbsolutePath());
        return image;
    }

    public void setCapturedImageURI(Uri mCapturedImageURI) {
        this.mCapturedImageURI = mCapturedImageURI;
    }

    public void setCurrentPhotoPath(String mCurrentPhotoPath) {
        this.mCurrentPhotoPath = mCurrentPhotoPath;
    }

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public Uri getCapturedImageURI() {
        return mCapturedImageURI;
    }

    private synchronized void buildGoogleApiClient(){
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
