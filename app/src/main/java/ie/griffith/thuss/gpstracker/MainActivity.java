package ie.griffith.thuss.gpstracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    private TrackerEntryManager manager;
    TrackingTask t;
    Date startTime;
    Date endTime;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new TrackerEntryManager(getApplicationContext());

        checkPermission();

        Button start = findViewById(R.id.start);
        Button stop =  findViewById(R.id.stop);
        Button history = findViewById(R.id.history);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t == null) {
                    startTime = new Date();
                    Log.e("MainActivity", "Tracking started");
                    t = new TrackingTask();
                    t.setKeepTracking(true);
                    t.execute(MainActivity.this);
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTime = new Date();
                Log.e("MainActivity", "Tracking stopped");

                if(t != null){
                    t.setKeepTracking(false);
                    TrackerEntry entry = t.getTrackerEntry();
                    t = null;
                    entry.setTime((int) (endTime.getTime() - startTime.getTime()));
                    manager.saveNewEntry(entry);
                    DetailActivity.entry = entry;
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    startActivity(intent);
                }



                }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.list = manager.getEntries(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

    }



    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Log.e("MainActivity","GPS Permission granted");
        } else {
            // Show rationale and request permission.
            Log.e("MainActivity","GPS Permission declined \n request permission");
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

