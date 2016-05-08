package fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.adapters.DegradationsAdapter;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.BaseActivity;
import fr.fges.fixmycity.common.utils.ItemClickSupport;

public class AllReportedDegradationsActivity extends BaseActivity {

    private DegradationsAdapter mDegradationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_all_reported_degradations, null, false);
        mDrawer.addView(contentView, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mDegradationsAdapter = new DegradationsAdapter(mDegradationFactory.getInstance().getmDegradationList());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.all_reported_degradations_rcv);
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
