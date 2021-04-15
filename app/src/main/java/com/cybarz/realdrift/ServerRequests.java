package com.cybarz.realdrift;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ServerRequests {

    ServerRequests(Context con){
        RequestQueue request= Volley.newRequestQueue(con);
    }
}
