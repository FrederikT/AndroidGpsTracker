package ie.griffith.thuss.gpstracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrackerEntry {
    private Date date;
    private double distance;
    private int time;
    private TrackingAttribute speed;
    private TrackingAttribute altitude;

    public TrackerEntry(Date date, double distance, int time, TrackingAttribute speed, TrackingAttribute altitude) {
        this.date = date;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
        this.altitude = altitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TrackingAttribute getSpeed() {
        return speed;
    }

    public void setSpeed(TrackingAttribute speed) {
        this.speed = speed;
    }

    public TrackingAttribute getAltitude() {
        return altitude;
    }

    public void setAltitude(TrackingAttribute altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy  hh:mm a");
        return format.format(date) +"  "+ distance + " m ";// + speed.getAverage()+" m/s";
    }
}
