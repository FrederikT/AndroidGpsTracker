package ie.griffith.thuss.gpstracker;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    String logTag = "GPS_LOG_3013386";
    TrackingTread tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        Button btn = (Button) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            tt = new TrackingTread(MainActivity.this);
            tt.run();

            }
        });

    }



    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            /*Object mMap;
            mMap.setMyLocationEnabled(true);*/
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
        tt.stop();
    }
}

