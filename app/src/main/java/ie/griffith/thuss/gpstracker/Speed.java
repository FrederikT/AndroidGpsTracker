package ie.griffith.thuss.gpstracker;


import java.util.ArrayList;

public class Speed {
    //all in m/s
    private Double min;
    private Double max;
    private Double current;
    private Double sum;
    private Double average;
    private int count;
    private ArrayList<Double> speedList;

    public Speed(){
        sum = 0.0;
        count = 0;
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
        speedList = new ArrayList<>();
    }
    public void addValue(Double newSpeed){
        speedList.add(newSpeed);
        //Log.e("speed", "added speed"+newSpeed);
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

    public ArrayList<Double> getSpeedList() {
        return speedList;
    }
}


