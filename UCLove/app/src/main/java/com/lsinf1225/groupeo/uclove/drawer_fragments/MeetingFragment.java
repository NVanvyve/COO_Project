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
        Double latitude = gps.getLatitude();
        Double longitude = gps.getLongitude();
        String coord = CoordToString(latitude,longitude);

        TextView position = (TextView) myFragmentView.findViewById(R.id.pos);
        position.setText(coord);



        return myFragmentView;
    }
/*
    public String getCoord();
    {
        GPSTracker gps = new GPSTracker(getActivity());
        Double latitude = gps.getLatitude();
        Double longitude = gps.getLongitude();

        return CoordToString(latitude,longitude);
    }
*/
    public String CoordToString(double lat, double lon)
    {
        return (Double.toString(lat)+";"+Double.toString(lon));
    }

    public double Distance(double LongA, double LatA, double LongB, double LatB)
    {

        double R = 6371000; // Rayon de la Terre en metre
        double a = Math.toRadians(LatA); //latitude du point A (en radians)
        double b = Math.toRadians(LatB); //latitude du point B (en radians)
        double c = Math.toRadians(LongA); //longitude du point A (en radians)
        double d = Math.toRadians(LongB); //longitude du point B (en radians)

        double Dist;

        Dist = R*acos(cos(a)*cos(b)*cos(c-d)+sin(a)*sin(b)); //Distance en metre

        return Dist/1000; // En km

    }

    public double ExtractLatitude(String Coord)
    {
        String[] tab = Coord.split(";");
        return Double.parseDouble(tab[0]);
    }

    public double ExtractLongitude(String Coord)
    {
        String[] tab = Coord.split(";");
        return Double.parseDouble(tab[1]);
    }

}
