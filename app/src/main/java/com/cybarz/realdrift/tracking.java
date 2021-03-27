package com.cybarz.realdrift;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class tracking  implements LocationListener {
    LocationManager locationmnger;
    mapping start_point;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onSetup(Context ctx,mapping mp) {


        locationmnger = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        if (ctx.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ctx.checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mp.check();
            return;
        }

        start_point=mp;


        locationmnger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 13, 0, this);


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        System.out.println(location.getLatitude()+"  logdd "+location.getLongitude());
        double a=location.getLatitude();

        start_point.starting.setText(String.valueOf(a));
        locationmnger.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
