package fr.fges.fixmycity.common.ui.activitiesAndIntents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.adapters.DegradationsAdapter;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations.AllReportedDegradationsActivity;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations.GetCurrentLocation;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations.ReportDegradationActivity;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations.ReportedDegradationActivity;
import fr.fges.fixmycity.common.utils.ItemClickSupport;

public class MainActivity extends BaseActivity {

    private Button mButton;
    private DegradationsAdapter mDegradationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        mDrawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GetCurrentLocation.class);
                startActivity(intent);
            }
        });

        this.mDegradationsAdapter = new DegradationsAdapter(mDegradationFactory.getInstance().getmDegradationList());

        mButton = (Button) findViewById(R.id.main_report_btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ReportDegradationActivity.class);
                startActivity(intent);
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_reported_degradations_rcv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(mDegradationsAdapter);
        recyclerView.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getBaseContext(), ReportedDegradationActivity.class);
                intent.putExtra("degradationPosition", (int)position);
                startActivity(intent);
            }
        });

    }



}
