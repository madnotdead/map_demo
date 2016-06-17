package com.example.leandromaguna.myapp.Presentation.PlacesDetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.leandromaguna.myapp.Application.PlaceService;
import com.example.leandromaguna.myapp.Model.Place;
import com.example.leandromaguna.myapp.R;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by thecocacolauser on 6/14/16.
 */
public class PlaceDetailsFragment extends Fragment {

    private Button mSaveButton;
    private EditText mTitle;
    private EditText mDetail;
    private CheckBox mIsPublic;

    boolean isPublic;
    String title;
    String detail;
    LatLng _currentLocation;

    private PlaceService _placeService;
    public static PlaceDetailsFragment newInstance(LatLng currentPosition) {

        Bundle args = new Bundle();
        args.putParcelable("position",currentPosition);
        PlaceDetailsFragment fragment = new PlaceDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    boolean saved =false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _placeService = new PlaceService(getActivity());
        _currentLocation = (LatLng)getArguments().get("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_place_detail,container,false);

        mSaveButton = (Button) v.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saved =_placeService.savePlace(mTitle.getText().toString(),mDetail.getText().toString(),mIsPublic.isChecked(),_currentLocation);
                setSavePlaceResult(saved);
                getActivity().finish();
            }
        });

        mDetail = (EditText)v.findViewById(R.id.place_detail_text);

        mIsPublic = (CheckBox)v.findViewById(R.id.place_public);
        mTitle = (EditText)v.findViewById(R.id.place_title);

        return v;
    }


    private void setSavePlaceResult(boolean saved) {
        Intent data = new Intent();
        Bundle newData = new Bundle();
        if(saved) {
            newData.putParcelable("position", _currentLocation);
            newData.putSerializable("title", mTitle.getText().toString());
            newData.putSerializable("description", mDetail.getText().toString());
            data.putExtra("savedPlace", newData);
            getActivity().setResult(getActivity().RESULT_OK, data);
        }
    }
}
