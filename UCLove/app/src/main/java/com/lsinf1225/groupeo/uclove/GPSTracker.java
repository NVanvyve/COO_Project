package com.lsinf1225.groupeo.uclove;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    double altitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                showGPSSettingsAlert();
            }
            else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                            else if(latitude == 0.0 && longitude == 0.0 && !isNetworkEnabled){
                                // when the GPS cannot return exact coordonates
                                showAlertNoInfo();//show alert message
                                // Set arbitrary position
                                // Auditoires Sainte-Barbe (EPL)
                                latitude = 50.668239;
                                longitude = 4.621409;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }
    /**
     * Function to get altitude
     * */
    public double getAltitude(){
        if(location != null){
            altitude = location.getAltitude();
        }

        // return longitude
        return altitude ;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showGPSSettingsAlert() {

        Context context = getApplicationContext();
        CharSequence text = "GPS is not enabled.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void showWifiSettingsAlert(){

        Context context = getApplicationContext();
        CharSequence text = "Wifi is not enabled.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /*
     * show an alert message
     */
    public void showAlertNoInfo(){

        Context context = getApplicationContext();
        CharSequence text = "We cannot determine your location with the GPS. Try with an internet connection. Arbitrary possition define";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }



    public String CoordToString(double lat, double lon)
    {
        return (Double.toString(lat)+";"+Double.toString(lon));
    }

    public double Distance(String coordA, String coordB)
    {
        double LongA = ExtractLongitude(coordA);
        double LongB = ExtractLongitude(coordB);
        double LatA = ExtractLatitude(coordA);
        double LatB = ExtractLatitude(coordB);

        double R = 6371000; // Rayon de la Terre en mètres
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

    public String getCoord()
    {
        return CoordToString(latitude,longitude);
    }

}