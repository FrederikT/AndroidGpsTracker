package ie.griffith.thuss.gpstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    static List<TrackerEntry> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView listView = findViewById(R.id.list);

        ArrayAdapter<TrackerEntry> trackerEntryListArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list);

        listView.setAdapter(trackerEntryListArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                TrackerEntry entry = list.get(position);
                DetailActivity.speed = entry.getSpeed();
                DetailActivity.altitude = entry.getAltitude();
                DetailActivity.distance = entry.getDistance();
                DetailActivity.timeInMs = entry.getTime();


                startActivity(intent);


            }

        });

    }
}
