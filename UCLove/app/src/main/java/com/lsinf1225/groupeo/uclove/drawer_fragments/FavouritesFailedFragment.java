package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsinf1225.groupeo.uclove.R;

public class FavouritesFailedFragment extends Fragment {

    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_favourites_failed, container, false);

        return myFragmentView;
    }
}