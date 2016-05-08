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

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.models.Degradation;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.BaseActivity;

public class ReportedDegradationActivity extends BaseActivity {

    private Degradation mDegradation;
    @BindView(R.id.reported_degradation_imv) ImageView mPhoto;
    @BindView(R.id.reported_degradation_cagetory) TextView mCategory;
    @BindView(R.id.reported_degradation_reference) TextView mReference;
    @BindView(R.id.reported_degradation_description) TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_reported_degradation, null, false);
        mDrawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Integer position = extras.getInt("degradationPosition");
            if(position >= 0 && position < mDegradationFactory.getInstance().getmDegradationList().size()) {
                mDegradation = mDegradationFactory.getInstance().getmDegradationList().get(position);
                mDescription.setText(mDegradation.getmDescription());
                mReference.setText(mDegradation.getmReference());
                mCategory.setText(mDegradation.getmCategory());
                Glide
                        .with(this)
                        .load(mDegradation.getmImagePath())
                        .into(mPhoto);
            }
            else
                Snackbar.make(contentView, "Impossible d'ouvrir la dégradation demandée.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "La dégradation a été confirmée, merci!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
