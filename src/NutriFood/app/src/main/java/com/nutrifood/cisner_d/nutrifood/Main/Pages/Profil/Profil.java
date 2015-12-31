package com.nutrifood.cisner_d.nutrifood.Main.Pages.Profil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nutrifood.cisner_d.nutrifood.DataHolder;
import com.nutrifood.cisner_d.nutrifood.Main.Client;
import com.nutrifood.cisner_d.nutrifood.Main.ListItem;
import com.nutrifood.cisner_d.nutrifood.Main.User;
import com.nutrifood.cisner_d.nutrifood.R;
import com.nutrifood.cisner_d.nutrifood.adapter.ListAdapter;

import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

enum ProfilInfo {
    USERNAME,
    EMAIL,
    FIRSTNAME,
    LASTNAME,
    AGE
}

public class Profil extends Fragment {
    private View profil_view;
    private User user = null;
    private ArrayList<ListItem> items;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profil_view = inflater.inflate(R.layout.fragment_profil, container, false);

        items = new ArrayList<ListItem>();

        items.add(new ListItem("Username"));
        items.add(new ListItem("Email"));
        items.add(new ListItem("Firstname"));
        items.add(new ListItem("Lastname"));
        items.add(new ListItem("Age"));

        listView = (ListView)profil_view.findViewById(R.id.list_data);
        ListAdapter adapter = new ListAdapter(getActivity(), items);
        listView.setAdapter(adapter);
        return profil_view;
    }

    @Override
    public void onStart()
    {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getUserProfile();
        } else {
        }
        super.onStart();
    }

    private void setData()
    {
        items.get(ProfilInfo.USERNAME.ordinal())._subtitle = user.Username();
        items.get(ProfilInfo.EMAIL.ordinal())._subtitle = user.Email();
        items.get(ProfilInfo.AGE.ordinal())._subtitle = String.valueOf(user.Age());
        items.get(ProfilInfo.FIRSTNAME.ordinal())._subtitle = user.Firstname();
        items.get(ProfilInfo.LASTNAME.ordinal())._subtitle = user.Lastname();
        ((ListAdapter)listView.getAdapter()).setList(items);
    }

    private void newUser(JSONObject obj) {
        user = new User();
        try {
            user.Age(obj.getInt(getString(R.string.age_key)));
            user.Username(obj.getString(getString(R.string.username_key)));
            user.Firstname(obj.getString(getString(R.string.firstname_key)));
            user.Lastname(obj.getString(getString(R.string.lastname_key)));
            user.Email(obj.getString(getString(R.string.email_key)));
            user.Allergy(DataHolder.getArrayList(new ArrayList<String>(),
                    obj.getJSONArray(getString(R.string.allergy_key))));

            setData();

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
}
