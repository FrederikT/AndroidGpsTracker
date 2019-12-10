package ie.griffith.thuss.gpstracker;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

public class TrackingTread extends Thread {
    private Context context;
    private LocationTrack locationTrack = null;
    private int gpsReadDelay = 100;

    TrackingTread(Context context) {
            this.context = context;
    }

    public void run() {
        while(true) {
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
    }

    public void cancel() {
        interrupt();
        if(locationTrack != null)
            locationTrack.stopListener();
    }
}
