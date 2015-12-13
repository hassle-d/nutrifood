package com.nutrifood.cisner_d.nutrifood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private DataHolder dataHolder = DataHolder.getInstence();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Client.setContext(this);
        /*
        if (dataHolder.Token() == null) {
            SharedPreferences settings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
            dataHolder.Token(settings.getString(getString(R.string.token_key), null));
        }
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (dataHolder.Token() == null) { startActivity(new Intent(this, LoginActivity.class)); }
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
        editor.putString(getString(R.string.token_key), dataHolder.Token());
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

        return super.onOptionsItemSelected(item);
    }
}
