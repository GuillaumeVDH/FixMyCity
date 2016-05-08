package fr.fges.fixmycity.common.ui.activitiesAndIntents.degradation;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.models.Degradation;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.BaseActivity;

public class ReportedDegradationActivity extends BaseActivity {

    private Degradation mDegradation;
    private ImageView mPhoto;
    private TextView mCategory;
    private TextView mReference;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_reported_degradation, null, false);
        mDrawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPhoto = (ImageView) findViewById(R.id.reported_degradation_imv);
        mReference = (TextView) findViewById(R.id.reported_degradation_reference);
        mDescription = (TextView) findViewById(R.id.reported_degradation_description);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Integer position = extras.getInt("degradationPosition");
            if(position >= 0 && position < mDegradationFactory.getInstance().getmDegradationList().size()) {
                mDegradation = mDegradationFactory.getInstance().getmDegradationList().get(position);
                mDescription.setText(mDegradation.getmDescription());
                mReference.setText(mDegradation.getmReference());
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
