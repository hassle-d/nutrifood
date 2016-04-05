package com.nutrifood.cisner_d.nutrifood2.Profil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nutrifood.cisner_d.nutrifood2.DataHolder;
import com.nutrifood.cisner_d.nutrifood2.R;

public class Profil extends Fragment implements View.OnClickListener {
    private View profil_view;
    private Button loginbutton;
    private Button registerbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (DataHolder.token == null) {
            profil_view = inflater.inflate(R.layout.fragment_profil_log_out, container, false);
            loginbutton = (Button)profil_view.findViewById(R.id.login_button);
            registerbutton = (Button)profil_view.findViewById(R.id.register_button);

            loginbutton.setOnClickListener(this);
            registerbutton.setOnClickListener(this);
        }
        else
            profil_view = inflater.inflate(R.layout.fragment_profil_log_in, container, false);

        return profil_view;
    }

    @Override
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                startActivity(new Intent(getContext(), LoginActivity.class));
                Log.d("LOGIN", "CLICKED");
                break;
            case R.id.register_button:
                startActivity(new Intent(getContext(), RegisterActivity.class));
                Log.d("REGISTER", "CLICKED");
                break;
        }
    }
}