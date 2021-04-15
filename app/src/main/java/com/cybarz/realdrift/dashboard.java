package com.cybarz.realdrift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class dashboard extends AppCompatActivity {

    int [] images={R.drawable.uber,R.drawable.black};
    viewpageadapter views;
    ViewPager pager;
    Button but;
    FrameLayout fr;
    Button auto;
    Button bike;
    Button other;




    public static void closefragment(FragmentTransaction trs){

        trs.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);
        trs.remove(new mapping()).commit();



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        System.out.println((Button)findViewById(R.id.car));
        views=new viewpageadapter(dashboard.this,images);
        pager=(ViewPager)dashboard.this.findViewById(R.id.cruisol);
        System.out.println(pager);
        System.out.println(views);
        pager.setAdapter(views);
        fr=(FrameLayout) findViewById(R.id.ss);
        but=(Button)findViewById(R.id.car);
        fr.bringToFront();

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapping mp=new mapping();
                Bundle argument=new Bundle();
                argument.putString("type","Car");
                mp.setArguments(argument);

                FragmentTransaction trs=getSupportFragmentManager().beginTransaction();
                trs.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
                trs.replace(R.id.ss,mp).commit();
            }
        });

        auto=(Button)findViewById(R.id.auto);
        bike=(Button)findViewById(R.id.bike);
        other=(Button)findViewById(R.id.other);
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapping mp=new mapping();
                Bundle argument=new Bundle();
                argument.putString("type","Auto");
                mp.setArguments(argument);

                FragmentTransaction trs=getSupportFragmentManager().beginTransaction();
                trs.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
                trs.replace(R.id.ss,mp).commit();
            }
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
                serverreq();
            }
        });


    }


    public  void serverreq(){
        RequestQueue request= Volley.newRequestQueue(this);
        String postUrl = "https://junaisnodejs.herokuapp.com/sms";
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