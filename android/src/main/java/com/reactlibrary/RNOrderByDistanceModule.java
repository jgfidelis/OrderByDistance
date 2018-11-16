
package com.reactlibrary;

import android.location.Location;
import android.support.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Arrays;

public class RNOrderByDistanceModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  private LocationRequest mLocationRequest;
  private FusedLocationProviderClient mFusedLocationClient;
  private Location mLastLocation;

  public RNOrderByDistanceModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNOrderByDistance";
  }

  @ReactMethod
  public void startModule() {
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.reactContext);
    mFusedLocationClient.getLastLocation()
            .addOnSuccessListener(new OnSuccessListener<Location>() {
              @Override
              public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                mLastLocation = location;
              }
            })
            .addOnFailureListener(new OnFailureListener() {
              @Override
              public void onFailure(@NonNull Exception e) {

              }
            });
  }

  @ReactMethod
  public void orderByDistance(
          ReadableArray coords,
          Promise promise)
  {
      ReadableMap[] mycoords = new ReadableMap[coords.size()];
      for (int i = 0; i < coords.size(); i++) {
          ReadableMap map = coords.getMap(i);
          mycoords[i] = map;
      }

    Arrays.sort(mycoords, new LocationComparator(mLastLocation));
      WritableMap map = Arguments.createMap();
      WritableArray myarray = Arguments.createArray();
      for (int i = 0; i < coords.size(); i++) {
          WritableMap temp = Arguments.createMap();
          temp.putDouble("latitude", mycoords[i].getDouble("latitude"));
          temp.putDouble("longitude", mycoords[i].getDouble("longitude"));
          temp.putString("name", mycoords[i].getString("name"));
          temp.putDouble("rating", mycoords[i].getDouble("rating"));
          myarray.pushMap(temp);
      }

      map.putArray("response", myarray);
    promise.resolve(map);
  }
}