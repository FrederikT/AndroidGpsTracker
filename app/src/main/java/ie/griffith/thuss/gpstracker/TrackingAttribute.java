package ie.griffith.thuss.gpstracker;


import java.util.ArrayList;

public class TrackingAttribute {
    //all in m/s
    private Double min;
    private Double max;
    private Double current;
    private Double sum;
    private Double average;
    private int count;
    private ArrayList<Double> list;

    TrackingAttribute(){
        sum = 0.0;
        count = 0;
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
        list = new ArrayList<>();
    }
    void addValue(Double newValue){
        list.add(newValue);
        //Log.e("trackingAttribute", "added trackingAttribute"+newValue);
        current = newValue;
        sum += newValue;
        count++;
        average = sum/count;
        if(newValue < min)
            min = newValue;
        if(newValue > max)
            max = newValue;
    }

    Double getAverage() {
        return average;
    }

    Double getMin() {
        return min;
    }

    Double getMax() {
        return max;
    }

    public Double getCurrentValue() {
        return current;
    }

    ArrayList<Double> getList() {
        return list;
    }
}


