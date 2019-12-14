package ie.griffith.thuss.gpstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
                R.layout.mytextview,
                list);

        listView.setAdapter(trackerEntryListArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                DetailActivity.entry = list.get(position);
                startActivity(intent);


            }

        });

    }
}
