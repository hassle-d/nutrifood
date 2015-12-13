package com.nutrifood.cisner_d.nutrifood;

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

public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {

    private Button registerbutton = null;
    private EditText login = null;
    private EditText password = null;
    private EditText email = null;
    private EditText firstname = null;
    private EditText lastname = null;
    private EditText age = null;
    private TextView error = null;
    private DataHolder dataHolder = DataHolder.getInstence();

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register:
                RequestParams params = new RequestParams();
                dataHolder.login = login.getText().toString();
                dataHolder.password = password.getText().toString();
                dataHolder.email = email.getText().toString();
                dataHolder.firstname = firstname.getText().toString();
                dataHolder.lastname = lastname.getText().toString();
                dataHolder.age = age.getText().toString();
                params.put(getString(R.string.username_key), dataHolder.login);
                params.put(getString(R.string.password_key), dataHolder.password);
                params.put(getString(R.string.email_key), dataHolder.email);
                params.put(getString(R.string.firstname_key), dataHolder.firstname);
                params.put(getString(R.string.lastname_key), dataHolder.lastname);
                params.put(getString(R.string.age_key), Integer.parseInt(dataHolder.age));

                Client.post(getString(R.string.register_URL), params, new JsonHttpResponseHandler() {

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
                break;
        }
    }
}
