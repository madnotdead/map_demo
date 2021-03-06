package com.example.leandromaguna.myapp.Presentation.PlacesDetails;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.leandromaguna.myapp.Presentation.Base.SingleFragmentActivity;
import com.example.leandromaguna.myapp.R;
import com.google.android.gms.maps.model.LatLng;

public class PlaceDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

            LatLng location = getIntent().getParcelableExtra("currentLocation");
            return PlaceDetailsFragment.newInstance(location);
    }


}
