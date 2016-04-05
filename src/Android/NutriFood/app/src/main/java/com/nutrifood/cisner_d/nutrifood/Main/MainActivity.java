package com.nutrifood.cisner_d.nutrifood.Main;

import android.content.ClipData;
import android.content.Intent;
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

import com.nutrifood.cisner_d.nutrifood.Client;
import com.nutrifood.cisner_d.nutrifood.DataHolder;
import com.nutrifood.cisner_d.nutrifood.Login.LoginActivity;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.Category.Categories;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.CreateMeal;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.Home.Home;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.Meals.Meals;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.Profil.Profil;
import com.nutrifood.cisner_d.nutrifood.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Client.setContext(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_meal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Create New Meal", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                setFragment(R.id.add_meal);
                setTitle("Create Meal");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences settings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        DataHolder.token = settings.getString(getString(R.string.token_key), null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DataHolder.token == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        else {
            Client.addHeader(DataHolder.token);
            if (setFragment(R.id.nav_home))
                setTitle("Home");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(getString(R.string.token_key), DataHolder.token);
        editor.commit();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        } else if (id == R.id.action_logout) {
            DataHolder.token = null;
            clearBackStack();
            SharedPreferences settings = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean setFragment(int id)
    {
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            fragment = new Home();
        } else if (id == R.id.nav_category) {
            fragment = new Categories();
        } else if (id == R.id.nav_meals) {
            fragment = new Meals();
        } else if (id == R.id.nav_profil) {
            fragment = new Profil();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.add_meal) {
            fragment = new CreateMeal();
        }

        if (fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            clearBackStack();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainContent, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
            return true;
        }
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean ret = setFragment(id);

        // Highlight the selected item, update the title, and close the drawer
        if (ret)
            setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
