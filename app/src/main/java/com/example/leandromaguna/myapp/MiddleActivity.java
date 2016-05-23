package com.example.leandromaguna.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by O23002 on 19/05/2016.
 */
public class MiddleActivity extends AppCompatActivity {

    private Button btn = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle_activity);

        btn = (Button) findViewById(R.id.button_map);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),DemoMapActivity.class));
                finish();
            }
        });
    }


}
