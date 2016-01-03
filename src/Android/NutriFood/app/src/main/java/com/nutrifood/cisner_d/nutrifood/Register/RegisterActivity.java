package com.nutrifood.cisner_d.nutrifood.Register;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nutrifood.cisner_d.nutrifood.Client;
import com.nutrifood.cisner_d.nutrifood.R;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

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
