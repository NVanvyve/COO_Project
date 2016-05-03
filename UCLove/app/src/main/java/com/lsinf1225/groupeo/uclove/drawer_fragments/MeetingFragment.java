package com.lsinf1225.groupeo.uclove.drawer_fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lsinf1225.groupeo.uclove.GPSTracker;
import com.lsinf1225.groupeo.uclove.R;
import static java.lang.Math.*;


public class MeetingFragment extends Fragment {


    private View myFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myFragmentView = inflater.inflate(R.layout.fragment_meeting, container, false);


        /*
        Formatage de lat./long. au format "latitude;longitude"

        Ex. LLN  :  LAT = 50.6430501 (NORD)
                    LONG = 4.7137993 (EST)

           --> "50.6430501;4.7137993"
        */

        GPSTracker gps = new GPSTracker(getActivity());
        String coord = gps.getCoord();

        TextView position = (TextView) myFragmentView.findViewById(R.id.pos);
        position.setText(coord);



        return myFragmentView;
    }


}
