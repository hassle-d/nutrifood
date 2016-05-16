package com.nutrifood2.Profil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.nutrifood2.Models.BasicItem;
import com.nutrifood2.Models.User;
import com.nutrifood2.R;
import com.nutrifood2.Utils.DataHolder;
import java.util.ArrayList;
import java.util.List;


public class ProfilLogoutFragment extends Fragment implements View.OnClickListener {
    private View profil_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profil_view = inflater.inflate(R.layout.fragment_profil_logout, container, false);

        Button loginbutton = (Button)profil_view.findViewById(R.id.login_button);
        Button registerbutton = (Button)profil_view.findViewById(R.id.register_button);

        loginbutton.setOnClickListener(this);
        registerbutton.setOnClickListener(this);

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

