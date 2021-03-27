package com.cybarz.realdrift;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.SymbolTable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText starting;

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
        EditText cab_type=(EditText)v.findViewById(R.id.type);
        cab_type.setText(mParam1+" is selected");
        final mapping mp=this;
        final tracking trk=new tracking();
        trk.onSetup(getContext(),mp);

        starting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {







            }
        });
        return v;

    }


    public void check(){
        System.out.println("permissionin");
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);

    }
}