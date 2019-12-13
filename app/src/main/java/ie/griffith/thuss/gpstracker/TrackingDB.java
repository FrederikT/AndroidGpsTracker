package ie.griffith.thuss.gpstracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackingDB extends SQLiteOpenHelper {
    SQLiteDatabase db;
    Context context;

    public TrackingDB(Context context){
        super(context, "tracking.db", null,1);
        this.context = context;
        this.db = getWritableDatabase();
        this.onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
        try {
            String sql = "CREATE TABLE trackerentry (date INTEGER Primary KEY, distance DOUBLE, time INTEGER, avgSpeed DOUBLE, maxSpeed DOUBLE, minSpeed DOUBLE, avgAltitude DOUBLE, maxAltitude DOUBLE, minAltitude DOUBLE)";
            db.execSQL(sql);
            Log.e("TrackingDB", "onCreate: table successfully created");
        }catch (Exception e){
            Log.e("TrackingDB", "onCreate: "+e.getMessage());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //No upgrade necessary in current version of the app
        Log.e("TrackingDB", "Upgrade not yet implemented");
    }


    public void addEntry(Date date, double distance, int time, TrackingAttribute speed, TrackingAttribute altitude){
        try {
            String sql = "Insert into trackerentry Values("+date.getTime()+", "+distance+", "+time+", " +speed.getAverage()+", "+speed.getMax()+", "+speed.getMin()+", "+altitude.getAverage()+", "+altitude.getMax()+", "+altitude.getMin()+")";
            db.execSQL(sql);
            Log.e("TrackingDB", "successfully added entry to db ");

        }catch (Exception e){
            Log.e("TrackingDB", "on Insert: "+e.getMessage());
            Toast toast = Toast.makeText(context, "ERROR: Entry could not be added to list", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void addEntry(TrackerEntry entry){
        try {
            String sql = "Insert into trackerentry Values("+entry.getDate().getTime()+", "+entry.getDistance()+", "+entry.getTime()+", " +entry.getSpeed().getAverage()+", "+entry.getSpeed().getMax()+", "+entry.getSpeed().getMin()+", "+entry.getAltitude().getAverage()+", "+entry.getAltitude().getMax()+", "+entry.getAltitude().getMin()+")";
            db.execSQL(sql);
            Log.e("TrackingDB", "successfully added entry to db ");

        }catch (Exception e){
            Log.e("TrackingDB", "on Insert: "+e.getMessage());
            Toast toast = Toast.makeText(context, "ERROR: Entry could not be added to list", Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    public void removeItem(Date date) {
        try {
            String sql = "DELETE FROM trackerentry where date = "+date.getTime();
            db.execSQL(sql);
            Toast toast = Toast.makeText(context, "Entry deleted", Toast.LENGTH_SHORT);
            toast.show();
            Log.e("TrackingDB", "successfully removed entry from db ");

        }catch (Exception e){
            Log.e("TrackingDB", "on Delete: "+e.getMessage());
            Toast toast = Toast.makeText(context, "ERROR: Entry could not be deleted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void removeItem(TrackerEntry entry) {
        try {
            String sql = "DELETE FROM trackerentry where date = "+entry.getDate().getTime();
            db.execSQL(sql);
            Toast toast = Toast.makeText(context, "Entry deleted", Toast.LENGTH_SHORT);
            toast.show();
            Log.e("TrackingDB", "successfully removed entry from db ");

        }catch (Exception e){
            Log.e("TrackingDB", "on Delete: "+e.getMessage());
            Toast toast = Toast.makeText(context, "ERROR: Entry could not be deleted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void removeAllItems(){
        String sql = "DELETE FROM trackerentry";
        db.execSQL(sql);
        sql = "VACUUM";
        db.execSQL(sql);
    }

    public List<TrackerEntry> getEntries(){
        List<TrackerEntry> entries = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery("Select * from trackerentry", null);
            cursor.moveToNext();
            while(!cursor.isAfterLast()){

                Date date = new Date(Long.parseLong(cursor.getString(0)));
                double distance = Double.parseDouble(cursor.getString(1));
                int time = Integer.parseInt(cursor.getString(2));
                TrackingAttribute speed = new TrackingAttribute();
                speed.setAverage(Double.parseDouble(cursor.getString(3)));
                speed.setMax(Double.parseDouble(cursor.getString(4)));
                speed.setMin(Double.parseDouble(cursor.getString(5)));
                TrackingAttribute altitude = new TrackingAttribute();
                altitude.setAverage(Double.parseDouble(cursor.getString(6)));
                altitude.setMax(Double.parseDouble(cursor.getString(7)));
                altitude.setMin(Double.parseDouble(cursor.getString(8)));
                entries.add(new TrackerEntry(date,distance,time,speed,altitude));
                cursor.moveToNext();
                Log.e("TrackingDB", "on Select: getEntries() successfully added entry from db to list ");
            }
            cursor.close();
            return entries;

        }catch (Exception e){
            Log.e("TrackingDB", "on Select: getEntries() "+e.getMessage());
        }

        return null;
    }


    public TrackerEntry getEntry(Date date) {
        TrackerEntry entry = null;
        try{
            Cursor cursor = db.rawQuery("Select * from trackerentry WHERE date = '"+date+"'", null);
            cursor.moveToNext();
            while(!cursor.isAfterLast()){
                Date dat = new Date(Long.parseLong(cursor.getString(0)));
                double distance = Double.parseDouble(cursor.getString(1));
                int time = Integer.parseInt(cursor.getString(2));
                TrackingAttribute speed = new TrackingAttribute();
                speed.setAverage(Double.parseDouble(cursor.getString(3)));
                speed.setMax(Double.parseDouble(cursor.getString(4)));
                speed.setMin(Double.parseDouble(cursor.getString(5)));
                TrackingAttribute altitude = new TrackingAttribute();
                altitude.setAverage(Double.parseDouble(cursor.getString(6)));
                altitude.setMax(Double.parseDouble(cursor.getString(7)));
                altitude.setMin(Double.parseDouble(cursor.getString(8)));
                entry = new TrackerEntry(dat,distance,time,speed,altitude);
                cursor.moveToNext();

            }
            cursor.close();
            return entry;

        }catch (Exception e){
            Log.e("TrackingDB", "on Select: getEntry() "+e.getMessage());
        }

        return entry;
    }
}
