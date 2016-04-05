package com.nutrifood.cisner_d.nutrifood2.Profil;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nutrifood.cisner_d.nutrifood2.Client;
import com.nutrifood.cisner_d.nutrifood2.DataHolder;
import com.nutrifood.cisner_d.nutrifood2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText login;
    private EditText password;
    private Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        loginbutton = (Button)findViewById(R.id.login_button);

        loginbutton.setOnClickListener(this);
    }

    public void onLogin()
    {
        RequestParams params = new RequestParams();
        String _login = login.getText().toString();
        String _password = password.getText().toString();
        params.put(getString(R.string.username_key), _login);
        params.put(getString(R.string.password_key), _password);
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
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    onLogin();
                } else {

                }
                break;
        }
    }
}
