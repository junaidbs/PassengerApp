package com.cybarz.realdrift;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link loginfrag1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class loginfrag1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;
    ProgressBar pg;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public View view;

    Button login;
    private String verifid;

    public loginfrag1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginfrag1.
     */
    // TODO: Rename and change types and number of parameters
    public static loginfrag1 newInstance(String param1, String param2) {
        loginfrag1 fragment = new loginfrag1();
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
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_loginfrag1, container, false);
         Button log=(Button)view.findViewById(R.id.subit);
        final EditText mphonenumber=(EditText) view.findViewById(R.id.enterphn);
        pg=(ProgressBar) getActivity().findViewById(R.id.pgbar);

        //final testclass test=new testclass();

        log.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //test.a=4;
                // test.testclasss();
                 if(mphonenumber.getText().length()==10){
                    // pg=getActivity().findViewById(R.id.pgbar);
                     pg.setVisibility(View.VISIBLE);
                     System.out.println(mphonenumber.getText());
                     String num=mphonenumber.getText().toString();
                     System.out.println(num);

                     authenticate(num);
                    // FragmentTransaction ft=getFragmentManager().beginTransaction();
                    // MainActivity.calllog(ft);


                 }
                 else {
                     //enter a valid number
                 }





             }
         });


         


        return view;





    }


    public void authenticate(String Phonenumber){
        System.out.println("authentication");
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber("+919048438943").setTimeout(60L, TimeUnit.SECONDS).setActivity(getActivity()).setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                System.out.println("verification succes");

                signInWithPhoneAuthCredential(phoneAuthCredential);




            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                System.out.println("verification failed");
                pg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verifid=s;
                FragmentTransaction ft=getFragmentManager().beginTransaction();
                System.out.println("code sended succesfully");
                MainActivity.calllog(ft,verifid);

                pg.setVisibility(View.INVISIBLE);
                //ft.replace(R.id.logfrag,motpfrg,"otp fragment started").commit();

            }
        }).build();

        PhoneAuthProvider.verifyPhoneNumber(options);



    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        System.out.println(getActivity());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            pg.setVisibility(View.INVISIBLE);

                            FirebaseUser user = task.getResult().getUser();
                            System.out.println("sign in succesfull");
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                System.out.println("sign in failed");
                                pg.setVisibility(View.INVISIBLE);
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}