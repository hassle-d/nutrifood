package com.nutrifood.cisner_d.nutrifood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button registerbutton = null;
    private EditText login = null;
    private EditText password = null;
    private EditText email = null;
    private EditText firstname = null;
    private EditText lastname = null;
    private EditText age = null;
    private TextView error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerbutton = (Button)findViewById(R.id.register);
        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        age = (EditText)findViewById(R.id.age);
        error = (TextView)findViewById(R.id.error);

        registerbutton.setOnClickListener(this);
        login.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        firstname.addTextChangedListener(textWatcher);
        lastname.addTextChangedListener(textWatcher);
        age.addTextChangedListener(textWatcher);
    }

    @Override
    public void onResume() { super.onResume(); }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void onRegister()
    {
        RequestParams params = new RequestParams();
        DataHolder.login = login.getText().toString();
        DataHolder.password = password.getText().toString();
        DataHolder.email = email.getText().toString();
        DataHolder.firstname = firstname.getText().toString();
        DataHolder.lastname = lastname.getText().toString();
        try
        {
            DataHolder.age = Integer.parseInt(age.getText().toString());
        }
        catch (NumberFormatException e)
        {
            DataHolder.age = 0;
        }
        params.put(getString(R.string.username_key), DataHolder.login);
        params.put(getString(R.string.password_key), DataHolder.password);
        params.put(getString(R.string.email_key), DataHolder.email);
        params.put(getString(R.string.firstname_key), DataHolder.firstname);
        params.put(getString(R.string.lastname_key), DataHolder.lastname);
        params.put(getString(R.string.age_key), DataHolder.age);

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
                error.setText("ERROR");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error.setText("ERROR");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    onRegister();
                } else {
                    error.setText("ERROR : no connection");
                }
                break;
        }
    }
}
