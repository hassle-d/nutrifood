package com.nutrifood2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood2.Categories.CategoryFragment;
import com.nutrifood2.Home.HomeFragment;
import com.nutrifood2.Meals.MealListFragment;
import com.nutrifood2.Models.User;
import com.nutrifood2.Profil.ProfilFragment;
import com.nutrifood2.Utils.Client;
import com.nutrifood2.Utils.DataHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Client.setContext(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences settings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        DataHolder.token = settings.getString(getString(R.string.token_key),null) ;
        if (DataHolder.token == null) {
            setNavigationItem(R.id.nav_profil);
        }
        else {
            setNavigationItem(R.id.nav_home);
            Client.addHeader(DataHolder.token);
            getUserProfile();
        }
    }

    @Override
    protected void onStop() {
        if (DataHolder.token != null) {
            SharedPreferences settings = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(getString(R.string.token_key), DataHolder.token);
            editor.commit();
        }
        super.onStop();
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
        else if (id == R.id.action_logout) {
            DataHolder.token = null;
            SharedPreferences settings = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(getString(R.string.token_key));
            Client.removeHeader();
            editor.commit();
            setNavigationItem(R.id.nav_profil);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean setFragment(int id)
    {
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_home :
                fragment = new HomeFragment();
                break;
            case R.id.nav_category :
                fragment = new CategoryFragment();
                break;
            case R.id.nav_meals :
                fragment = new MealListFragment();
                break;
            case R.id.nav_profil :
                fragment = new ProfilFragment();
                break;
            case R.id.nav_share :
                break;
        }

        if (fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContent, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
            return true;
        }
        return false;
    }

    public void setNavigationItem(int id)
    {
        MenuItem item = navigationView.getMenu().findItem(id);
        if (item != null) {
            setFragment(id);
            item.setChecked(true);
            setTitle(item.getTitle());
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean ret = false;

        if (DataHolder.token == null)
            setNavigationItem(R.id.nav_profil);
        else if (ret = setFragment(id))
            setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return ret;
    }

    private void newUser(JSONObject obj) {
        if (DataHolder.user == null)
            DataHolder.user = new User();
        try {
            DataHolder.user.Age(obj.getInt(getString(R.string.age_key)));
            DataHolder.user.Username(obj.getString(getString(R.string.username_key)));
            DataHolder.user.Firstname(obj.getString(getString(R.string.firstname_key)));
            DataHolder.user.Lastname(obj.getString(getString(R.string.lastname_key)));
            DataHolder.user.Email(obj.getString(getString(R.string.email_key)));
            DataHolder.user.Allergy(DataHolder.getArrayList(new ArrayList<String>(),
                    obj.getJSONArray(getString(R.string.allergy_key))));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserProfile()
    {
        Client.get(getString(R.string.user_URL), null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("SUCCESS ONE", String.valueOf(response));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS TWO", String.valueOf(object));
                newUser(object);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {
                Log.d("ERROR ONE", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR TWO", String.valueOf(statusCode));
            }

        });
    }
}
