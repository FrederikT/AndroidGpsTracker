package ie.griffith.thuss.gpstracker;


import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;


public class TrackingTask extends AsyncTask<Context, Void, Void> {
    private Context context;
    LocationTrack locationTrack = null;
    //TODO set delay in in-app settings. min value 1 second. max value 300 seconds. -> slider?
    private int gpsReadDelay = 3000;
    private boolean keepTracking = true;
    private TrackingAttribute altitude = new TrackingAttribute();
    private TrackingAttribute speed = new TrackingAttribute();
    private double distance=0; // in meters




    void setKeepTracking(boolean keepTracking) {
        this.keepTracking = keepTracking;
    }

    protected Void doInBackground(Context... contexts) {
       if(Looper.myLooper() == null){
           Looper.prepare();
       }

        context = contexts[0];
        double longitude=0;
        double latitude=0;
        double lastDistance=0;
        while(keepTracking) {

            locationTrack = new LocationTrack(context);
            if (locationTrack.canGetLocation()) {

                double newLongitude = locationTrack.getLongitude();
                double newLatitude = locationTrack.getLatitude();


                //distance
                if(longitude !=0 && latitude != 0){
                    Location loc1 = new Location("");
                    loc1.setLatitude(latitude);
                    loc1.setLongitude(longitude);

                    Location loc2 = new Location("");
                    loc2.setLatitude(newLatitude);
                    loc2.setLongitude(newLongitude);
                    lastDistance = loc1.distanceTo(loc2);
                    distance += lastDistance;

                }
                                            // meter    /   seconds
                speed.addValue(lastDistance/(gpsReadDelay/1000));

                //coordinates SHOULD already contain the altitude in meters.
                //therefore it is not necessary to convert anything
                altitude.addValue(locationTrack.getAltitude());

                longitude = newLongitude;
                latitude = newLatitude;
            } else {

                locationTrack.showSettingsAlert();
            }
            SystemClock.sleep(gpsReadDelay);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(locationTrack != null)
            locationTrack.stopListener();
    }


    /***
     * debug method to print all data to Logcat
     */
    private void printData() {
        Log.e("printData","########GPS TRACKING RESULTS########");
        Log.e("printData","__________________\n");
        Log.e("printData","Overall distance: "+distance);
        Log.e("printData","__________________\n");
        Log.e("printData","Average TrackingAttribute: "+ speed.getAverage());
        Log.e("printData","Max TrackingAttribute: "+ speed.getMax());
        Log.e("printData","Min TrackingAttribute: "+ speed.getMin());
        Log.e("printData","__________________\n");
        Log.e("printData","Average Altitude: "+altitude.getAverage());
        Log.e("printData","Max Altitude: " +altitude.getMax());
        Log.e("printData","Min Altitude: "+altitude.getMin());
        Log.e("printData","\n\nThank you for using GPS TRACKING");

    }


    TrackingAttribute getSpeed(){
        return speed;
    }


    TrackingAttribute getAltitude(){
        return altitude;
    }


    Double getDistance() {
        return distance;
    }
}

