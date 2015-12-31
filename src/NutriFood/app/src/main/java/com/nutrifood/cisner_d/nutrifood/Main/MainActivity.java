package com.nutrifood.cisner_d.nutrifood.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nutrifood.cisner_d.nutrifood.DataHolder;
import com.nutrifood.cisner_d.nutrifood.Login.LoginActivity;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.Category.Categories;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.CreateCategory.CreateCategory;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.CreateMeal.CreateDishes;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.Home.Home;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.Meals.Meals;
import com.nutrifood.cisner_d.nutrifood.Main.Pages.Profil.Profil;
import com.nutrifood.cisner_d.nutrifood.R;
import com.nutrifood.cisner_d.nutrifood.adapter.*;

import java.util.ArrayList;

enum DrawView{
    HOME,
    CATEGORY,
    MEALS,
    PROFIL,
    CREATE_MEAL,
    CREATE_CATEGORY
}

public class MainActivity extends ActionBarActivity {
    private DrawerLayout drawerLayout;
    private RelativeLayout drawerPane;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    ArrayList<NavItem> navItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Client.setContext(this);

        navItems.add(new NavItem("Home", "newest dishes", R.drawable.ic_home_black_24dp));
        navItems.add(new NavItem("Category", "categories of dishes", R.drawable.ic_view_grid_black_24dp));
        navItems.add(new NavItem("Meals", "list of dishes", R.drawable.ic_food_black_24dp));
        navItems.add(new NavItem("Profil", "view profil", R.drawable.ic_account_black_24dp));
        navItems.add(new NavItem("Create Meal", "create new meal", R.drawable.ic_plus_circle_black_24dp));
        navItems.add(new NavItem("Create Category", "create new meal", R.drawable.ic_folder_plus_black_24dp));

        // DrawerLayout

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawerPane);

        drawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, navItems);
        drawerList.setAdapter(adapter);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        // Drawer Item click listeners
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });


        SharedPreferences settings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        DataHolder.token = settings.getString(getString(R.string.token_key), null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DataHolder.token == null) { startActivity(new Intent(this, LoginActivity.class)); }
        else {
            Client.addHeader(DataHolder.token);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(getString(R.string.token_key), DataHolder.token);
        editor.commit();
        super.onStop();
    }

    public android.support.v4.app.Fragment getItem(int i) {
        DrawView view = DrawView.values()[i];
        switch (view) {
            case HOME: {
                return new Home();
            }
            case CATEGORY: {
                return new Categories();
            }
            case MEALS: {
                return new Meals();
            }
            case PROFIL: {
                return new Profil();
            }
            case CREATE_MEAL: {
                return new CreateDishes();
            }
            case CREATE_CATEGORY: {
                return new CreateCategory();
            }
        }
        return null;
    }

    private void selectItemFromDrawer(int position) {
        drawerList.setItemChecked(position, true);

//        viewPager.setCurrentItem(position);
        Fragment fragment = getItem(position);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        clearBackStack();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        // Highlight the selected item, update the title, and close the drawer
        setTitle(navItems.get(position)._title);

        drawerLayout.closeDrawer(drawerPane);
    }

    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            SharedPreferences settings = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
