package ie.griffith.thuss.gpstracker;

import android.util.Log;

public class Altitude {
    private Double sum;
    private int count;
    private Double average;
    private Double min;
    private Double max;
    private Double currentValue;

    public Altitude(){
        sum = 0.0;
        count = 0;
        max = -2496.0;// Deepest point on land (Bentley Subglacial Trench)
        min = 8848.0;// Highest point on land (Mount Everest)
    }

    public void addValue(Double newAltitude){
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
}
