package com.cybarz.realdrift;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link otpfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class otpfragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public OtpTextView otp;
    private ProgressBar pg;
    String mid;

    public otpfragment() {
        // Required empty public constructor
    }

    public otpfragment(String mid){
        this.mid=mid;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment otpfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static otpfragment newInstance(String param1, String param2) {
        otpfragment fragment = new otpfragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_otpfragment, container, false);
        Button but=view.findViewById(R.id.otp);
        otp=view.findViewById(R.id.otp_view);
        pg=getActivity().findViewById(R.id.pgbar);
        otp.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {

                pg.setVisibility(View.VISIBLE);
                System.out.println("otp enterd completed");
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mid, "904843");
                signInWithPhoneAuthCredential(credential);

            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction tr=getFragmentManager().beginTransaction();
                //MainActivity.callotp(tr);
                Intent intent=new Intent(getContext(),dashboard.class);
                startActivity(intent);


            }
        });
        return view;
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        System.out.println(getActivity());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://ey-auto-2017a.firebaseio.com/");
                            DatabaseReference mref = database.getReference();


                            FirebaseUser user = task.getResult().getUser();
                            checkUser(user.getUid(), mref);


                            System.out.println("sign in succesfull");
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                System.out.println("sign in failed");
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public  void checkUser(final String uid, final DatabaseReference mref){
        final User user=new User("junaid","9048438943");
        final trackptype positionInfo=new trackptype(0.0,0.0);

        mref.child("passenger").child("user").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    if(task.getResult().getValue()==null){
                        System.out.println("new user");

                        mref.child("passenger").child("user").child(uid).child("Personinfo").setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                System.out.println("Succefully completed");
                            }
                        });

                        //track info
                        mref.child("passenger").child("user").child(uid).child("Passengerpos").setValue(positionInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                System.out.println("Succefully completed");

                                Intent intent=new Intent(getContext(),dashboard.class);
                                startActivity(intent);
                            }
                        });
                        History his=new History("xxxxxxxx",0.0,0.0);
                        mref.child("passenger").child("user").child(uid).child("History").setValue(his).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                System.out.println("Succefully completed");

                                Intent intent=new Intent(getContext(),dashboard.class);
                                startActivity(intent);
                            }
                        });


                    }

                    else{
                        Intent intent=new Intent(getActivity().getApplicationContext(),dashboard.class);
                        getActivity().startActivity(intent);
                        //old user

                    }

                }
            }
        });

    }
}