package ie.griffith.thuss.gpstracker;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

public class TrackingTask extends AsyncTask<Context, Void, Void> {
    private Context context;
    LocationTrack locationTrack = null;
    private int gpsReadDelay = 100;
    private boolean keepTracking = true;

    public void setKeepTracking(boolean keepTracking) {
        this.keepTracking = keepTracking;
    }

    protected Void doInBackground(Context... contexts) {

        context = contexts[0];

        while(keepTracking) {
            locationTrack = new LocationTrack(context);
            if (locationTrack.canGetLocation()) {


                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();
                Log.e("yee", "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude));
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
    }


}

