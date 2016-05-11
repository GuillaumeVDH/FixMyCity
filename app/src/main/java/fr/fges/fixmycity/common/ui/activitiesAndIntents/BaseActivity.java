package fr.fges.fixmycity.common.ui.activitiesAndIntents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

import fr.fges.fixmycity.R;
import fr.fges.fixmycity.common.persistence.DBManager;
import fr.fges.fixmycity.common.persistence.DatabaseHelper;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations.AllReportedDegradationsActivity;
import fr.fges.fixmycity.common.ui.activitiesAndIntents.degradations.ReportDegradationActivity;
import fr.fges.fixmycity.factories.DegradationFactory;

import static com.j256.ormlite.android.apptools.OpenHelperManager.setOpenHelperClass;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setOpenHelperClass(DatabaseHelper.class);
        DBManager.Init(this);


        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, ReportDegradationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, AllReportedDegradationsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("message/rfc822");
            email.putExtra(android.content.Intent.EXTRA_EMAIL,new String [] {"franck.mahieu59@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Signaler un bug");
            email.putExtra(Intent.EXTRA_TEXT, "Saisir votre demande ici...");
            email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(email, "Choisir le logiciel"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
