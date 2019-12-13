package ie.griffith.thuss.gpstracker;

import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * TrackerEntryManager manages the database.
 * this class is the connection between the app and the database
 * all method names are self-explanatory
 */
public class TrackerEntryManager {
    TrackingDB db;

    private List<TrackerEntry> entries = new ArrayList();

    public TrackerEntryManager(Context context){
        db = new TrackingDB(context);
    }

    public void saveNewEntry(Date date, double distance, int time, TrackingAttribute speed, TrackingAttribute altitude){
        TrackerEntry entry = new TrackerEntry(date,distance,time,speed,altitude);
        db.addEntry(entry);
    }

    public void saveNewEntry(TrackerEntry entry){
        db.addEntry(entry);
    }


    public void deleteEntry(TrackerEntry entry){
        db.removeItem(entry);
    }

    public void deleteEntry(Date date){
        db.removeItem(date);
    }

    public void deleteEntries(){
        db.removeAllItems();
    }

    public List<TrackerEntry> getEntries(Context context){
        entries = db.getEntries();
        return  entries;
    }


    public TrackerEntry getEntry(Date date){
        TrackerEntry entry = db.getEntry(date);
        return entry;
    }

}