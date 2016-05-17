package com.nutrifood2.Profil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood2.Adapter.ListRecyclerViewAdapter;
import com.nutrifood2.Models.BasicItem;
import com.nutrifood2.Models.User;
import com.nutrifood2.R;
import com.nutrifood2.Utils.Client;
import com.nutrifood2.Utils.DataHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

enum ProfilInfo {
    FIRSTNAME,
    LASTNAME,
    AGE
}

public class ProfilLoginFragment extends Fragment {
    private boolean mLogin = true;
    private User mUser;
    private RecyclerView recyclerView;
    private List<BasicItem> mListItem = new ArrayList<BasicItem>();
    private View profil_view;
    private TextView mUserName;
    private TextView mUserEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mUser = DataHolder.user;

        profil_view = inflater.inflate(R.layout.fragment_profil_login, container, false);

        mUserName = (TextView)profil_view.findViewById(R.id.Username);
        mUserEmail = (TextView)profil_view.findViewById(R.id.Email);

        mListItem.add(new BasicItem("FirstName"));
        mListItem.add(new BasicItem("LastName"));
        mListItem.add(new BasicItem("Age"));

        recyclerView = (RecyclerView)profil_view.findViewById(R.id.list_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ListRecyclerViewAdapter(mListItem, R.integer.BASIC_VIEW));

        if (mUser != null)
            setData();

        return profil_view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (mLogin) {
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                getUserProfile();
        }
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


    private void setData()
    {
        mUserName.setText(mUser.Username());
        mUserEmail.setText(mUser.Email());

        mListItem.get(ProfilInfo.AGE.ordinal()).Content(String.valueOf(mUser.Age()));
        mListItem.get(ProfilInfo.FIRSTNAME.ordinal()).Content(mUser.Firstname());
        mListItem.get(ProfilInfo.LASTNAME.ordinal()).Content(mUser.Lastname());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void newUser(JSONObject obj) {
        if (mUser == null)
            mUser = new User();
        try {
            mUser.Age(obj.getInt(getString(R.string.age_key)));
            mUser.Username(obj.getString(getString(R.string.username_key)));
            mUser.Firstname(obj.getString(getString(R.string.firstname_key)));
            mUser.Lastname(obj.getString(getString(R.string.lastname_key)));
            mUser.Email(obj.getString(getString(R.string.email_key)));
            mUser.Allergy(DataHolder.getArrayList(new ArrayList<String>(),
                    obj.getJSONArray(getString(R.string.allergy_key))));

            setData();
            DataHolder.user = mUser;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserProfile()
    {
        Client.get(getString(R.string.user_URL), null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("SUCCESS ONE", String.valueOf(response));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject object) {
                Log.d("SUCCESS TWO", String.valueOf(object));
                newUser(object);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String string, Throwable throwable) {
                Log.d("ERROR ONE", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR TWO", String.valueOf(statusCode));
            }

        });
    }
}
