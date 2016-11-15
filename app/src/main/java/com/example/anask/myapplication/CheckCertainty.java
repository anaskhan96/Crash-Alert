package com.example.anask.myapplication;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by anask on 16-04-2016.
 */
public class CheckCertainty extends Activity implements LocationListener {
    private LocationManager lm;
    public double latitude, longitude;
    public String No1,No2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_certainty);
        try {
            File myFile = new File("/sdcard/.emergencyNumbers.txt");
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));
            No1=myReader.readLine();
            No2=myReader.readLine();
            myReader.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        final SmsManager sms = SmsManager.getDefault();
        lm=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMessage(No1, null, "Help! I've met with an accident at http://maps.google.com/?q="+String.valueOf(latitude)+","+String.valueOf(longitude), null, null);
                sms.sendTextMessage(No1, null, "Nearby Hospitals http://maps.google.com/maps?q=hospital&mrt=yp&sll="+String.valueOf(latitude)+","+String.valueOf(longitude)+"&output=kml", null, null);
                sms.sendTextMessage(No2, null, "Help! I've met with an accident at http://maps.google.com/?q="+String.valueOf(latitude)+","+String.valueOf(longitude), null, null);
                sms.sendTextMessage(No2, null, "Nearby Hospitals http://maps.google.com/maps?q=hospital&mrt=yp&sll="+String.valueOf(latitude)+","+String.valueOf(longitude)+"&output=kml", null, null);
                System.exit(1);
            }
        }, 15000);

        Button dismiss = (Button) findViewById(R.id.dismissB);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });


    }
    @Override
    public void onLocationChanged(Location location){
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        Toast.makeText(getApplicationContext(),"Lat and Long extracted",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onProviderDisabled(String provider){
    }
    @Override
    public void onProviderEnabled(String provider){
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}