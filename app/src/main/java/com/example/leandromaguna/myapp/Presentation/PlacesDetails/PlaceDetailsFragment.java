package com.example.leandromaguna.myapp.Presentation.PlacesDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leandromaguna.myapp.Application.PlaceService;
import com.example.leandromaguna.myapp.Model.PlacesFactory;
import com.example.leandromaguna.myapp.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by thecocacolauser on 6/14/16.
 */
public class PlaceDetailsFragment extends Fragment {

    private Button saveButton;
    private PlaceService _placeService;
    public static PlaceDetailsFragment newInstance() {

        Bundle args = new Bundle();

        PlaceDetailsFragment fragment = new PlaceDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _placeService = new PlaceService(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_place_detail,container,false);

        saveButton = (Button) v.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _placeService.savePlace("description","name",false,new LatLng(10,10));
                getActivity().finish();
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
