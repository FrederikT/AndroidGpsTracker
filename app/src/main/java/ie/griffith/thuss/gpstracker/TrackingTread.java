package ie.griffith.thuss.gpstracker;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class TrackingTread extends Thread {
    Context context;


    TrackingTread(Context context) {
            this.context = context;
    }

    public void run() {
        while(true) {
            LocationTrack locationTrack = new LocationTrack(context);
            if (locationTrack.canGetLocation()) {


                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();
                Log.e("yee", "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude));
                //Toast.makeText(context, "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            } else {

                locationTrack.showSettingsAlert();
            }
            SystemClock.sleep(100);
        }
    }
}
