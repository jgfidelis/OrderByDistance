package com.reactlibrary;

import android.location.Location;

import com.facebook.react.bridge.ReadableMap;

import java.util.Comparator;

/**
 * Created by jgfidelis on 16/11/18.
 */

public class LocationComparator implements Comparator<ReadableMap> {
    private Location mLastLocation;

    LocationComparator(Location lastLocation) {
        this.mLastLocation = lastLocation;
    }

    @Override
    public int compare(ReadableMap coffee1, ReadableMap coffee2) {
        double firstLatitude = coffee1.getDouble("latitude");
        double firstLongitude = coffee1.getDouble("longitude");

        double secondLatitude = coffee2.getDouble("latitude");
        double secondLongitude = coffee2.getDouble("longitude");

        float[] resultsOne = new float[3];
        float[] resultsTwo = new float[3];
        Location.distanceBetween(mLastLocation.getLatitude(), mLastLocation.getLongitude(), firstLatitude, firstLongitude, resultsOne);
        Location.distanceBetween(mLastLocation.getLatitude(), mLastLocation.getLongitude(), secondLatitude, secondLongitude, resultsTwo);

        int distToFirst = (int) resultsOne[0];
        int distToSecond = (int) resultsTwo[0];
        return Math.abs(distToSecond) - Math.abs(distToFirst);
    }
}