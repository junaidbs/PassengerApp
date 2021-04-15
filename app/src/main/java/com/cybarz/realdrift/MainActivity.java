package com.cybarz.realdrift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public int a=0;




    public static   void calllog(FragmentTransaction ftlog,String vid) {
        System.out.println("called");
        ftlog.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);





        ftlog.replace(R.id.logfrag,new otpfragment(vid),"dd").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();



    }

    public static   void callotp(FragmentTransaction ftlog) {
       // System.out.println("called");
      //  ftlog.detach(new otpfragment());


        //ftlog.replace(R.id.logfrag,new loginfrag1(),"dd").commit();




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = (LayoutInflater)   getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        a=3;
        otpfragment frag=new otpfragment();
        Fragment fr=getSupportFragmentManager().findFragmentById(R.id.logfrag);
        FragmentTransaction  ft = getSupportFragmentManager().beginTransaction();
        loginfrag1 log=new loginfrag1();        //initat login fragment
        if(FirebaseAuth.getInstance().getUid()==null){
            ft.replace(R.id.logfrag,log, "NewFragmentTag").commit();    //replace current fragment
        }
        else {

            Intent intent=new Intent(getApplicationContext(),dashboard.class);
            startActivity(intent);
        }




    }

}