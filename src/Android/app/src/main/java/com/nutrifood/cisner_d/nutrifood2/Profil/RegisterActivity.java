package com.nutrifood.cisner_d.nutrifood2.Profil;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nutrifood.cisner_d.nutrifood2.Client;
import com.nutrifood.cisner_d.nutrifood2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity  extends Activity implements View.OnClickListener {

    private Button registerbutton = null;
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

        registerbutton = (Button)findViewById(R.id.register_button);
        login = (EditText)findViewById(R.id.login);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        age = (EditText)findViewById(R.id.age);

        registerbutton.setOnClickListener(this);
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
