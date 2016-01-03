package com.nutrifood.cisner_d.nutrifood.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nutrifood.cisner_d.nutrifood.DataHolder;
import com.nutrifood.cisner_d.nutrifood.Client;
import com.nutrifood.cisner_d.nutrifood.R;
import com.nutrifood.cisner_d.nutrifood.Register.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button loginbutton = null;
    private EditText login = null;
    private EditText password = null;
    private TextView registerlink = null;
    private TextView error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LoginActivity", "onCreate in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbutton = (Button)findViewById(R.id.connect);
        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        registerlink = (TextView)findViewById(R.id.register);
        error = (TextView)findViewById(R.id.error);

        loginbutton.setOnClickListener(this);
        registerlink.setOnClickListener(this);
        login.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        Log.d("LoginActivity", "onCreate out");
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

    public void onLogin() {
        Log.d("onLogin", "1");
        RequestParams params = new RequestParams();
        String _login = login.getText().toString();
        String _password = password.getText().toString();
        params.put(getString(R.string.username_key), _login);
        params.put(getString(R.string.password_key), _password);
        Log.d("onLogin", "2");
        Client.post(getString(R.string.login_URL), params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("Login", "SUCCESS");
                try {
                    DataHolder.token = object.getString(getString(R.string.token_key));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {
                error.setText("ERROR : " + string);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    String ret = "";
                    if (errorResponse != null)
                        ret = errorResponse.getString(getString(R.string.error_key));
                    error.setText("ERROR : " + ret);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        Log.d("onLogin", "3");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connect:
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    onLogin();
                } else {
                    error.setText("ERROR : no connection");
                }
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
