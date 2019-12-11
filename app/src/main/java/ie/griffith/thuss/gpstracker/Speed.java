package ie.griffith.thuss.gpstracker;

import android.util.Log;

public class Speed {
    //all in m/s
    private Double min;
    private Double max;
    private Double current;
    private Double sum;
    private Double average;
    private int count;

    public Speed(){
        sum = 0.0;
        count = 0;
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
    }
    public void addValue(Double newSpeed){
        current = newSpeed;
        sum += newSpeed;
        count++;
        average = sum/count;
        if(newSpeed < min && newSpeed>0)
            min = newSpeed;
        if(newSpeed > max)
            max = newSpeed;
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
        return current;
    }
}


