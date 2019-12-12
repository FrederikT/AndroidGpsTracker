package ie.griffith.thuss.gpstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView distanceValue, timeValue, avgSpeedValue, maxSpeedValue, minSpeedValue, avgAltitudeValue, maxAltitudeValue, minAltitudeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        AltitudeGraph altitudeGraph = findViewById(R.id.altitudeGraph);
        SpeedGraph speedGraph = findViewById(R.id.speedGraph);

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        speedGraph.getLayoutParams().height = (int) (height*0.4);
        altitudeGraph.getLayoutParams().height = (int) (height*0.25);

        distanceValue = findViewById(R.id.total_distance_value);
        timeValue = findViewById(R.id.time_value);
        avgSpeedValue = findViewById(R.id.average_speed_value);
        maxSpeedValue = findViewById(R.id.max_speed_value);
        minSpeedValue = findViewById(R.id.min_speed_value);
        avgAltitudeValue = findViewById(R.id.average_altitude_value);
        maxAltitudeValue = findViewById(R.id.max_altitude_value);
        minAltitudeValue = findViewById(R.id.min_altitude_value);

    }
}
