package ie.griffith.thuss.gpstracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    String logTag = "GPS_LOG_3013386";
    TrackingTask t;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MainActivity", "Tracking started");
                t = new TrackingTask();
                t.setKeepTracking(true);
                t.execute(MainActivity.this);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MainActivity", "Tracking stopped");

                if(t != null){
                    SpeedGraph.speed = t.getSpeed();
                    AltitudeGraph.altitude = t.getAltitude();
                    t.setKeepTracking(false);
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    startActivity(intent);
                }



                }
        });

    }



    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Log.e(logTag,"GPS Permission granted");
        } else {
            // Show rationale and request permission.
            Log.e(logTag,"GPS Permission declined \n request permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{ACCESS_FINE_LOCATION}, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.setKeepTracking(false);
    }
}

