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

import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        MapboxGeocoding reverseGeocode = MapboxGeocoding.builder()
                .accessToken("sk.eyJ1IjoianVuYWlka21sIiwiYSI6ImNrbmx0eTYzZjBvaDkyb3BtMmExOXhheW8ifQ.O38gl66EDyK06YXZc2BlYA")
                .query(Point.fromLngLat(location.getLongitude(),location.getLatitude() ))
                .geocodingTypes(GeocodingCriteria.TYPE_PLACE)
                .build();

        reverseGeocode.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                System.out.println("caaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaalllllllllllllllllllllllleeeeeeeeeeeeeedd");
               if(response.body()!=null){

                   GeocodingResponse g = response.body();

                   start_point.starting.setText(String.valueOf(g.features().get(0).placeName()));


                   System.out.println(g.features().get(0).placeName());

               }

            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable t) {

            }
        });


        start_point.lattitude=location.getLatitude();
        start_point.longtitude=location.getLongitude();
        locationmnger.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
