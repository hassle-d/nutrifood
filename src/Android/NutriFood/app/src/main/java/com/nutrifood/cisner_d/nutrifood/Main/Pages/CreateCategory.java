package com.nutrifood.cisner_d.nutrifood.Main.Pages;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CreateCategory extends Fragment implements View.OnClickListener {
    private TextView error;
    private View createcategory_view;
    private EditText name;
    private Button create;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createcategory_view = inflater.inflate(R.layout.fragment_create_category, container, false);

        name = (EditText)createcategory_view.findViewById(R.id.category_name);
        create = (Button)createcategory_view.findViewById(R.id.create_category);

        create.setOnClickListener(this);
        return createcategory_view;
    }

    private void onCreateCategory() {
        RequestParams params = new RequestParams();
        String categoryname = name.getText().toString();
        params.put(getString(R.string.name_key), categoryname);

        Client.post(getString(R.string.category_URL), params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("SUCCESS", "ONE");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS", "TWO");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {
                Log.e("ERROR", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("ERROR", String.valueOf(statusCode));
            }
        });
    }

    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

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
            case R.id.create_category:
                ConnectivityManager connMgr = (ConnectivityManager)
                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    onCreateCategory();
                } else {
                    error.setText("ERROR : no connection");
                }
                break;
        }
    }
}
