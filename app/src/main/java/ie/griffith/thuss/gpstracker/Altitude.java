package ie.griffith.thuss.gpstracker;

import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;

public class Altitude {
    private Double sum;
    private int count;
    private Double average;
    private Double min;
    private Double max;
    private Double currentValue;
    private ArrayList<Double> altitudeList = new ArrayList<>();

    public Altitude(){
        sum = 0.0;
        count = 0;
        max = -2496.0;// Deepest point on land (Bentley Subglacial Trench)
        min = 8848.0;// Highest point on land (Mount Everest)
    }

    public void addValue(Double newAltitude){
        //Log.e("Altitude:", "Added new Altitude"+newAltitude);
        altitudeList.add(newAltitude);
        currentValue = newAltitude;
        sum += newAltitude;
        count++;
        average = sum/count;
        if(newAltitude < min)
            min = newAltitude;
        if(newAltitude > max)
            max = newAltitude;
    }

    public Double getAverage() {
        return average;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public ArrayList<Double> getAltitudeList() {
        return altitudeList;
    }
}
