package fr.fges.fixmycity.common.ui.activitiesAndIntents.degradation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.models.Degradation;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.BaseActivity;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.MainActivity;

public class ReportDegradationActivity extends BaseActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private Button mTakePhotoBtn;
    private Spinner mDegradationType;
    private ImageView mImageView;
    private EditText mDescriptionEdt;
    private File mPhotoFile;
    private File mStorageDir;
    private String mCurrentPhotoPath = null;
    private Uri mCapturedImageURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_report_degradation, null, false);
        mDrawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStorageDir = new File(Environment.getExternalStorageDirectory()+"/FixMyCity/pictures/");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Degradation degradation = new Degradation();
                if(mPhotoFile.exists())
                    degradation.setmImagePath(mPhotoFile.getAbsolutePath());

                degradation.setmDescription(mDescriptionEdt.getText().toString());
                degradation.setmReference("NULL-PTR?");
                mDegradationFactory.getInstance().addDegradation(degradation);
                Snackbar.make(view, "Degradation reportée. Merci!", Snackbar.LENGTH_LONG) //TODO - Load text from strings?
                        .setAction("Action", null).show();

                //TODO - Redirect to degradation activity view for this new degradation created with snackbar saying everything is allright.
            }
        });

        mImageView = (ImageView) findViewById(R.id.report_degradation_photo_imv);

        mDescriptionEdt = (EditText) findViewById(R.id.report_degradation_edt);

        mDegradationType = (Spinner) findViewById(R.id.report_degradation_sp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.degradations_types_array, android.R.layout.simple_spinner_dropdown_item);
        mDegradationType.setAdapter(adapter);

        mTakePhotoBtn = (Button) findViewById(R.id.report_degradation_take_photo_btn);
        mTakePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStoragePermissions();
                mPhotoFile = null;
                dispatchTakePictureIntent();
            }
        });
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
            Toast.makeText(this, "This device does not have a camera.", Toast.LENGTH_SHORT).show();
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
                Toast toast = Toast.makeText(this, "There was a problem saving the photo...", Toast.LENGTH_SHORT);
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

}
