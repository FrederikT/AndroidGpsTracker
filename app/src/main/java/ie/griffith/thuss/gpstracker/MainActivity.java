package ie.griffith.thuss.gpstracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;

    private TrackerEntryManager manager;
    private TrackingSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new TrackerEntryManager(getApplicationContext());

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTracking();
            }
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTracking();
            }
        });
        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.list = manager.getEntries(getApplicationContext());
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }

    private void startTracking() {
        if (session != null) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }

        LocationManager locations = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locations == null || !locations.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Enable GPS to start recording.", Toast.LENGTH_SHORT).show();
            return;
        }

        TrackingSession newSession = new TrackingSession(locations);
        try {
            newSession.start();
            session = newSession;
        } catch (SecurityException exception) {
            Toast.makeText(this, "Location permission is required.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startTracking();
        }
    }

    private void stopTracking() {
        if (session == null) {
            return;
        }

        TrackerEntry entry = session.finish();
        session = null;
        if (entry == null) {
            Toast.makeText(this, "No GPS fix was recorded.", Toast.LENGTH_SHORT).show();
            return;
        }
        manager.saveNewEntry(entry);
        DetailActivity.entry = entry;
        startActivity(new Intent(this, DetailActivity.class));
    }

    @Override
    protected void onStop() {
        if (session != null) {
            session.cancel();
            session = null;
        }
        super.onStop();
    }
}
