package com.cybarz.realdrift;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.text.SymbolTable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mapping#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mapping extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CODE =1 ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText ept;
    EditText starting;
    private testclass p;
    FirebaseAuth firebaseAuth;
    double lattitude;
    double longtitude;

    public mapping() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mapping.
     */
    // TODO: Rename and change types and number of parameters
    public static mapping newInstance(String param1, String param2) {
        mapping fragment = new mapping();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("type");
            mParam2 = getArguments().getString(ARG_PARAM2);
            System.out.println(mParam1);

        }
        firebaseAuth=FirebaseAuth.getInstance();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("permissionin");
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_mapping, container, false);
        Button b=(Button)v.findViewById(R.id.close);
        Button hirecart=(Button)v.findViewById(R.id.hirecart);
        hirecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue request= Volley.newRequestQueue(getContext());
                String postUrl = "http://192.168.43.63:3000/api/driverconnection";
                JSONObject postData=new JSONObject();

                try {
                    postData.put("Uid", firebaseAuth.getUid());
                    postData.put("Current_latitude", lattitude);
                    postData.put("Current_longitude", longtitude);
                    postData.put("dest_latitude", p.coordinates[1]);
                    postData.put("dest_longitude", p.coordinates[0]);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response.getInt("status"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                request.add(jsonObjectRequest);

            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("inside");
                FragmentTransaction trs=getFragmentManager().beginTransaction();
                trs.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);
                Fragment fr=getFragmentManager().findFragmentById(R.id.ss);
                trs.remove(fr).commit();

            }
        });
        starting=(EditText)v.findViewById(R.id.spt);
         ept=(EditText)v.findViewById(R.id.ept);

        EditText cab_type=(EditText)v.findViewById(R.id.type);
        cab_type.setText(mParam1+" is selected");
        final mapping mp=this;
        final tracking trk=new tracking();
        trk.onSetup(getContext(),mp);



        ept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new PlaceAutocomplete.IntentBuilder()
                        .accessToken("sk.eyJ1IjoianVuYWlka21sIiwiYSI6ImNrbmx0eTYzZjBvaDkyb3BtMmExOXhheW8ifQ.O38gl66EDyK06YXZc2BlYA")
                        .placeOptions(PlaceOptions.builder().backgroundColor(Color.parseColor("#000000")).build())
                        .build(getActivity());
                startActivityForResult(intent, CODE);

            }
        });
        return v;

    }


    public void check(){
        System.out.println("permissionin");
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CODE) {
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);

            Geometry position = (Point) feature.geometry();

            System.out.println(position);
            Gson g = new Gson();
             p = g.fromJson(position.toJson(), testclass.class);
            System.out.println(p.coordinates[0]);
            LatLng latLng = new LatLng(((Point) position).latitude(), ((Point) position).longitude());
            System.out.println(latLng);
            ept.setText(feature.text());
            Toast.makeText(getActivity(), feature.text(), Toast.LENGTH_LONG).show();
        }
    }
}