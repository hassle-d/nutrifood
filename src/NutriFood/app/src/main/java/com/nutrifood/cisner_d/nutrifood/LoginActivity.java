package com.nutrifood.cisner_d.nutrifood;

import android.app.ProgressDialog;
import android.content.Intent;
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

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private Button loginbutton = null;
    private EditText login = null;
    private EditText password = null;
    private TextView registerlink = null;
    private TextView error = null;
    private DataHolder dataHolder = DataHolder.getInstence();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connect:
                RequestParams params = new RequestParams();
                dataHolder.login = login.getText().toString();
                dataHolder.password = password.getText().toString();
                params.put(getString(R.string.username_key), dataHolder.login);
                params.put(getString(R.string.password_key), dataHolder.password);

                Client.post(getString(R.string.login_URL), params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                        try {
                            dataHolder.Token(object.getString(getString(R.string.token_key)));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
