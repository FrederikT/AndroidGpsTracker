package ie.griffith.thuss.gpstracker;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Spinner;


public class TrackingTask extends AsyncTask<Context, Void, Void> {
    private Context context;
    LocationTrack locationTrack = null;
    private int gpsReadDelay = 3000;
    private boolean keepTracking = true;
    private Altitude altitude = new Altitude();
    private Speed speed = new Speed();
    private double distance=0; // in meters



    public void setKeepTracking(boolean keepTracking) {
        this.keepTracking = keepTracking;
    }

    protected Void doInBackground(Context... contexts) {
        //TODO prepare Looper only once or make sure to have completely new task when starting second tracking
        Looper.prepare();
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

                altitude.addValue(locationTrack.getAltitude());//TODO: Convert into meters

                longitude = newLongitude;
                latitude = newLatitude;
            } else {

                locationTrack.showSettingsAlert();
            }
            SystemClock.sleep(gpsReadDelay);
        }

        return null;
    }


    protected void onPostExecute(Long result) {
        if(locationTrack != null)
            locationTrack.stopListener();

        printData();
    }

    public void printData() {
        Log.e("printData","########GPS TRACKING RESULTS########");
        Log.e("printData","__________________\n");
        Log.e("printData","Overall distance: "+distance);
        Log.e("printData","__________________\n");
        Log.e("printData","Average Speed: "+speed.getAverage());
        Log.e("printData","Max Speed: "+speed.getMax());
        Log.e("printData","Min Speed: "+speed.getMin());
        Log.e("printData","__________________\n");
        Log.e("printData","Average Altitude: "+altitude.getAverage());
        Log.e("printData","Max Altitude: " +altitude.getMax());
        Log.e("printData","Min Altitude: "+altitude.getMin());
        Log.e("printData","\n\nThank you for using GPS TRACKING");

    }


}

