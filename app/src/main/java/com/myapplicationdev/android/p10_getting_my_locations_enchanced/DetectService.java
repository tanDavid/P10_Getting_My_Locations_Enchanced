package com.myapplicationdev.android.p10_getting_my_locations_enchanced;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class DetectService extends Service {
    boolean started;
    double lat, lng;
    LocationCallback mLocationCallback;

    @Override
    public void onCreate() {

        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(started = false){
                    if(locationResult != null){
                        Location data = locationResult.getLastLocation();
                        lat = data.getLatitude();
                        lng = data.getLongitude();
                        try {
                            String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
                            File targetFile = new File(folderLocation, "data.txt");
                            FileWriter writer = new FileWriter(targetFile, false);
                            writer.write("Latitude: " + lat + " Longitude: " + lng);
                            writer.flush();
                            writer.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    started = true;
                }

            }
        };


        Log.d("My Service", "Service Created");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (started == false) {
            started = true;
        } else {
            Log.d("Detect Service", "Service is still running");
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.d("Detect Service", "Service Exited");
        try {
            String folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Folder";
            File targetFile = new File(folderLocation, "data.txt");
            FileWriter writer = new FileWriter(targetFile, false);
            writer.write("");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}