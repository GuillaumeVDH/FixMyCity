package fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.models.Degradation;
import fr.fges.fixmycity.common.services.DegradationService;
import fr.fges.fixmycity.common.services.DegradationServicesImpl;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.BaseActivity;

public class ReportedDegradationActivity extends BaseActivity {

    @Bind(R.id.reported_degradation_imv)
    ImageView mPhoto;
    @Bind(R.id.reported_degradation_cagetory)
    TextView mCategory;
    @Bind(R.id.reported_degradation_reference)
    TextView mReference;
    @Bind(R.id.reported_degradation_latitude)
    TextView mLatitude;
    @Bind(R.id.reported_degradation_longitude)
    TextView mLongitude;
    @Bind(R.id.reported_degradation_description)
    TextView mDescription;
    private Degradation mDegradation;
    private DegradationService mDegradationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_reported_degradation, null, false);

        ButterKnife.bind(this, contentView);

        mDrawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDegradationService = new DegradationServicesImpl();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Integer position = extras.getInt("degradationId");

            if(position >= 0 && position <= mDegradationService.countNbDegradations()) {

                mDegradation = mDegradationService.findDegradationById(position);
                mDescription.setText(mDegradation.getmDescription());
                mReference.setText(mDegradation.getmReference());
                mCategory.setText(mDegradation.getmCategory());
                mLatitude.setText(String.format(Locale.getDefault(), "%1$,.2f", mDegradation.getmLatitude()));
                mLongitude.setText(String.format(Locale.getDefault(), "%1$,.2f", mDegradation.getmLongitude()));
                Glide
                        .with(this)
                        .load(mDegradation.getmImagePath())
                        .into(mPhoto);
            }
            else
                Snackbar.make(contentView, R.string.cant_open_degradation, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.confirm_degradation, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
