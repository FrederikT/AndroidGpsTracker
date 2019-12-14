package ie.griffith.thuss.gpstracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrackerEntry {
    private Date date;
    private double distance;
    private int time;
    private TrackingAttribute speed;
    private TrackingAttribute altitude;

    TrackerEntry(Date date, double distance, int time, TrackingAttribute speed, TrackingAttribute altitude) {
        this.date = date;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
        this.altitude = altitude;
    }

    Date getDate() {
        return date;
    }

    void setDate(Date date) {
        this.date = date;
    }

    double getDistance() {
        return distance;
    }

    void setDistance(double distance) {
        this.distance = distance;
    }

    int getTime() {
        return time;
    }

    void setTime(int time) {
        this.time = time;
    }

    TrackingAttribute getSpeed() {
        return speed;
    }

    void setSpeed(TrackingAttribute speed) {
        this.speed = speed;
    }

    TrackingAttribute getAltitude() {
        return altitude;
    }

    void setAltitude(TrackingAttribute altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy  hh:mm a");
        return format.format(date) +"  "+ distance + " m ";// + speed.getAverage()+" m/s";
    }
}
