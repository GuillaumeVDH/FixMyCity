package fr.fges.fixmycity.common.ui.activitiesAndIntents.degradation;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.File;

import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.adapters.PhotosAdapter;

public class AllReportedDegradationsActivity extends AppCompatActivity {

    private PhotosAdapter mPhotosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reported_degradations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        File storageDir = new File(Environment.getExternalStorageDirectory()+"/FixMyCity/pictures/");
        File[] photos = storageDir.listFiles();
        this.mPhotosAdapter = new PhotosAdapter(this, photos, width);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.all_reported_degradations_rcv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(mPhotosAdapter);
        recyclerView.hasFixedSize();
    }

}
