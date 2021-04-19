package com.cybarz.realdrift;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.os.Parcel;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class dashboard extends AppCompatActivity {

    int [] images={R.drawable.uber,R.drawable.black};
    viewpageadapter views;
    ViewPager pager;
    Button but;
    FrameLayout fr;
    Button auto;
    Button bike;
    Button other;
    public int CODE=1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CODE) {
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);

            Geometry position =(Point)feature.geometry();
            System.out.println(position.toJson());
            Gson g = new Gson();
            testclass p = g.fromJson(position.toJson(), testclass.class);
            System.out.println(p.coordinates[0]);









            Toast.makeText(this, feature.text(), Toast.LENGTH_LONG).show();
        }

    }

    public static void closefragment(FragmentTransaction trs){

        trs.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);
        trs.remove(new mapping()).commit();



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



       /* Intent intent = new PlaceAutocomplete.IntentBuilder()
               .accessToken("sk.eyJ1IjoianVuYWlka21sIiwiYSI6ImNrbmx0eTYzZjBvaDkyb3BtMmExOXhheW8ifQ.O38gl66EDyK06YXZc2BlYA")
                .placeOptions(PlaceOptions.builder().build())
                .build(this);
        startActivityForResult(intent, CODE);*/

        System.out.println((Button)findViewById(R.id.car));
        views=new viewpageadapter(dashboard.this,images);
        pager=(ViewPager)dashboard.this.findViewById(R.id.cruisol);
        System.out.println(pager);
        System.out.println(views);
        pager.setAdapter(views);
        fr=(FrameLayout) findViewById(R.id.ss);
        but=(Button)findViewById(R.id.car);
        fr.bringToFront();

        but.setOnClickListener(view -> {
            mapping mp=new mapping();
            Bundle argument=new Bundle();
            argument.putString("type","Car");
            mp.setArguments(argument);

            FragmentTransaction trs=getSupportFragmentManager().beginTransaction();
            trs.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
            trs.replace(R.id.ss,mp).commit();
        });

        auto=(Button)findViewById(R.id.auto);
        bike=(Button)findViewById(R.id.bike);
        other=(Button)findViewById(R.id.other);
        auto.setOnClickListener(view -> {
            mapping mp=new mapping();
            Bundle argument=new Bundle();
            argument.putString("type","Auto");
            mp.setArguments(argument);

            FragmentTransaction trs=getSupportFragmentManager().beginTransaction();
            trs.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
            trs.replace(R.id.ss,mp).commit();
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapping mp=new mapping();
                Bundle argument=new Bundle();
                argument.putString("type","Bike");
                mp.setArguments(argument);

                FragmentTransaction trs=getSupportFragmentManager().beginTransaction();
                trs.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
                trs.replace(R.id.ss,mp).commit();
            }
        });


        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapping mp=new mapping();
                Bundle argument=new Bundle();
                argument.putString("type","Not spacified");
                mp.setArguments(argument);

                FragmentTransaction trs=getSupportFragmentManager().beginTransaction();
                trs.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
                trs.replace(R.id.ss,mp).commit();

            }
        });


    }


    public  void serverreq(){
        RequestQueue request= Volley.newRequestQueue(this);
        String postUrl = "http://192.168.43.63:3000/api/driverconnection";
        JSONObject postData=new JSONObject();

        try {
            postData.put("name", "Junaid");
            postData.put("job", "Software Engineer");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        request.add(jsonObjectRequest);

    }
}