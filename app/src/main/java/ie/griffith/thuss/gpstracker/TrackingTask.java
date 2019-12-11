package ie.griffith.thuss.gpstracker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class TrackingTask extends AsyncTask<Context, Void, Void> {
    private Context context;
    LocationTrack locationTrack = null;
    private int gpsReadDelay = 100;
    private boolean keepTracking = true;

    public void setKeepTracking(boolean keepTracking) {
        this.keepTracking = keepTracking;
    }

    protected Void doInBackground(Context... contexts) {
        Looper.prepare();
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

