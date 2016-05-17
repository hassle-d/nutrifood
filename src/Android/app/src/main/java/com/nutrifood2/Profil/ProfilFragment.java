package com.nutrifood2.Profil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nutrifood2.R;
import com.nutrifood2.Utils.DataHolder;

public class ProfilFragment extends Fragment {

    private boolean mLogin = true;
    private View profil_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profil_view = inflater.inflate(R.layout.fragment_profil, container, false);

        return profil_view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (DataHolder.token == null)
            mLogin = false;
        else
            mLogin = true;

        Fragment fragment;

        if (mLogin) {
            fragment = new ProfilLoginFragment();
        } else {
            fragment = new ProfilLogoutFragment();
        }

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.profil_content, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
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
