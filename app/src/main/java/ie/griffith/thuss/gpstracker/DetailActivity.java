package ie.griffith.thuss.gpstracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    static TrackingAttribute speed;
    static TrackingAttribute altitude;
    static Double distance=0.0;
    static long timeInMs=0;

    private TextView distanceValue, timeValue, avgSpeedValue, maxSpeedValue, minSpeedValue, avgAltitudeValue, maxAltitudeValue, minAltitudeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TrackingAttributeGraph altitudeGraph = findViewById(R.id.altitudeGraph);
        TrackingAttributeGraph speedGraph = findViewById(R.id.speedGraph);

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        speedGraph.getLayoutParams().height = (int) (height*0.4);
        speedGraph.markExtremes = true;
        speedGraph.trackingAttribute = speed;
        speedGraph.graphDescription = "Speed";


        altitudeGraph.getLayoutParams().height = (int) (height*0.25);
        altitudeGraph.trackingAttribute = altitude;
        altitudeGraph.graphDescription = "Altitude";

        setValues();

    }

    private void setValues() {
        distanceValue = findViewById(R.id.total_distance_value);
        timeValue = findViewById(R.id.time_value);
        avgSpeedValue = findViewById(R.id.average_speed_value);
        maxSpeedValue = findViewById(R.id.max_speed_value);
        minSpeedValue = findViewById(R.id.min_speed_value);
        avgAltitudeValue = findViewById(R.id.average_altitude_value);
        maxAltitudeValue = findViewById(R.id.max_altitude_value);
        minAltitudeValue = findViewById(R.id.min_altitude_value);

        distanceValue.setText(String.format("%.2f",distance)+"m/s");
        timeValue.setText(getTimeString());
        avgSpeedValue.setText(String.format("%.2f",speed.getAverage())+"m/s");
        maxSpeedValue.setText(String.format("%.2f",speed.getMax())+"m/s");
        minSpeedValue.setText(String.format("%.2f",speed.getMin())+"m/s");
        avgAltitudeValue.setText(String.format("%.2f",altitude.getAverage())+"m");
        maxAltitudeValue.setText(String.format("%.2f",altitude.getMax())+"m");
        minAltitudeValue.setText(String.format("%.2f",altitude.getMin())+"m");


    }

    private String getTimeString() {
        long seconds = timeInMs / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        String timeString="";
        if(hours != 0)
            timeString += hours + " Hours ";
        if(minutes != 0)
            timeString += minutes + " Minutes ";
        timeString += seconds + " Seconds";
        return timeString;

    }
}
