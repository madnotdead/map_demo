package com.example.leandromaguna.myapp.Presentation.PlacesDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leandromaguna.myapp.Application.IPlaceRepository;
import com.example.leandromaguna.myapp.Application.PlaceService;
import com.example.leandromaguna.myapp.Model.PlacesFactory;
import com.example.leandromaguna.myapp.R;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

/**
 * Created by thecocacolauser on 6/14/16.
 */
public class PlaceDetailsFragment extends Fragment {

    private Button saveButton;

    @Inject
    private PlaceService _placeService;

    public static PlaceDetailsFragment newInstance() {

        Bundle args = new Bundle();

        PlaceDetailsFragment fragment = new PlaceDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_place_detail,container,false);

        saveButton = (Button) v.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _placeService.savePlace("nombre","descripcion",true,new LatLng(10,10));
            }
        });
        return v;
    }


    private void setSavePlaceResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra("savedPlace", isAnswerShown);
        getActivity().setResult(getActivity().RESULT_OK, data);
    }
}
