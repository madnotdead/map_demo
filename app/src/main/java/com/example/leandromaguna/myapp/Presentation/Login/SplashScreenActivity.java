package com.example.leandromaguna.myapp.Presentation.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.leandromaguna.myapp.Presentation.PlacesList.PlacesListActivity;

/**
 * Created by O23002 on 19/05/2016.
 */
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Simulacion de precarga*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), PlacesListActivity.class);
                startActivity(intent);
                finish();
            }
        },500);
    }
}
