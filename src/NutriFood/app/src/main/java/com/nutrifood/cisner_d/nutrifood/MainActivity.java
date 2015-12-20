package com.nutrifood.cisner_d.nutrifood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nutrifood.cisner_d.nutrifood.adapter.TabViewAdapter;


public class MainActivity extends ActionBarActivity {

    private ViewPager viewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Client.setContext(this);

        SharedPreferences settings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        DataHolder.token = settings.getString(getString(R.string.token_key), null);
        DataHolder.login = settings.getString(getString(R.string.username_key), null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (DataHolder.token == null) { startActivity(new Intent(this, LoginActivity.class)); }
        else {
            Client.addHeader(DataHolder.token);
            viewPager = (ViewPager) findViewById(R.id.pager);
            DataHolder.Adapter = new TabViewAdapter(getSupportFragmentManager());
            viewPager.setAdapter(DataHolder.Adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("SETTINGS", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(getString(R.string.token_key), DataHolder.token);
        editor.putString(getString(R.string.username_key), DataHolder.login);
        editor.commit();
        super.onStop();
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

        if (id == R.id.action_home) {
            viewPager.setCurrentItem(0);
            return true;
        }

        if (id == R.id.action_profil) {
            viewPager.setCurrentItem(1);
            return true;
        }

        if (id == R.id.action_logout) {
            DataHolder.Clear();
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
