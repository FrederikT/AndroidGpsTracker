package ie.griffith.thuss.gpstracker;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;

import java.util.Date;

final class TrackingSession implements LocationListener {
    private static final long SAMPLE_INTERVAL_MS = 3000;

    private final LocationManager locations;
    private final TrackingAttribute speed = new TrackingAttribute();
    private final TrackingAttribute altitude = new TrackingAttribute();
    private final long startedAt = SystemClock.elapsedRealtime();
    private Location previousFix;
    private double distance;
    private boolean tracking;

    TrackingSession(LocationManager locations) {
        this.locations = locations;
    }

    void start() {
        locations.requestLocationUpdates(LocationManager.GPS_PROVIDER, SAMPLE_INTERVAL_MS, 0,
                this, Looper.getMainLooper());
        tracking = true;
    }

    void cancel() {
        if (tracking) {
            tracking = false;
            locations.removeUpdates(this);
        }
    }

    TrackerEntry finish() {
        cancel();
        if (previousFix == null) {
            return null;
        }
        if (altitude.getList().isEmpty()) {
            altitude.setMin(0.0);
            altitude.setMax(0.0);
            altitude.setAverage(0.0);
        }
        int duration = (int) Math.min(Integer.MAX_VALUE, SystemClock.elapsedRealtime() - startedAt);
        return new TrackerEntry(new Date(), distance, duration, speed, altitude);
    }

    @Override
    public void onLocationChanged(Location fix) {
        if (!tracking || fix.getElapsedRealtimeNanos() < startedAt * 1_000_000L) {
            return;
        }

        double metersPerSecond = 0;
        if (previousFix != null) {
            long nanos = fix.getElapsedRealtimeNanos() - previousFix.getElapsedRealtimeNanos();
            if (nanos <= 0) {
                return;
            }
            double segment = previousFix.distanceTo(fix);
            distance += segment;
            metersPerSecond = segment * 1_000_000_000 / nanos;
        }

        speed.addValue(metersPerSecond);
        if (fix.hasAltitude()) {
            altitude.addValue(fix.getAltitude());
        }
        previousFix = fix;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
