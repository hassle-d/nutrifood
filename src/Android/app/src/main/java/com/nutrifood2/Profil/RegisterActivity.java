package com.nutrifood2.Profil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nutrifood2.R;
import com.nutrifood2.Utils.Client;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login = null;
    private EditText password = null;
    private EditText email = null;
    private EditText firstname = null;
    private EditText lastname = null;
    private EditText age = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button registerbutton = (Button)findViewById(R.id.register_button);
        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        age = (EditText)findViewById(R.id.age);

        if (registerbutton != null)
            registerbutton.setOnClickListener(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_register);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            //NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onRegister()
    {
        RequestParams params = new RequestParams();
        String _login = login.getText().toString();
        String _password = password.getText().toString();
        String _email = email.getText().toString();
        String _firstname = firstname.getText().toString();
        String _lastname = lastname.getText().toString();
        int _age;
        try
        {
            _age = Integer.parseInt(age.getText().toString());
        }
        catch (NumberFormatException e)
        {
            _age = 0;
        }
        params.put(getString(R.string.username_key), _login);
        params.put(getString(R.string.password_key), _password);
        params.put(getString(R.string.email_key), _email);
        params.put(getString(R.string.firstname_key), _firstname);
        params.put(getString(R.string.lastname_key), _lastname);
        params.put(getString(R.string.age_key), _age);

        Client.post(getString(R.string.users_URL), params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register_button:
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    onRegister();
                } else {

                }
                break;
        }
    }
}
