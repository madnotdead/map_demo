package com.example.leandromaguna.myapp.Presentation.PlacesList;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leandromaguna.myapp.Application.PlaceService;
import com.example.leandromaguna.myapp.Model.Place;
import com.example.leandromaguna.myapp.Model.PlacesFactory;
import com.example.leandromaguna.myapp.Presentation.PlacesMap.PlacesMapActivity;
import com.example.leandromaguna.myapp.R;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlacesListFragment extends Fragment {

    RecyclerView recyclerView = null;
    PlacesAdapter mAdapter = null;
    List<Place> mPlaces = null;
    PlaceService _placeService;

    public PlacesListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _placeService = new PlaceService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.placesList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadPlaces();
    }

    private void loadPlaces(){
        PlacesFactory placesFactory = PlacesFactory.get(getActivity());
        mPlaces = _placeService.getAllPlaces();

        if(mAdapter == null){
            mAdapter = new PlacesAdapter(mPlaces);
            recyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.SetList(mPlaces);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class PlacesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Place mPlace = null;

        private TextView mNameTextView = null;
        private TextView mAdressTextView = null;

        public PlacesHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_place_title_text_view);
            mAdressTextView = (TextView)itemView.findViewById(R.id.list_item_place_adress_text_view);

            itemView.setOnClickListener(this);
        }

        private void bindPlace(Place place){

            mPlace = place;
            mNameTextView.setText(place.getTitle());
            mAdressTextView.setText(place.getAdress());

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(),PlacesMapActivity.class);
            i.putExtra("selectedLocation",mPlace.getLocation());
            startActivity(i);
        }
    }
    private class PlacesAdapter extends RecyclerView.Adapter<PlacesHolder>{

        private List<Place> mPlacesList;

        public void SetList(List<Place> places){
            mPlacesList = places;
        }
        public PlacesAdapter(List<Place> places){

            mPlacesList = places;
        }

        @Override
        public PlacesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_place,parent,false);

            return new PlacesHolder(view);
        }

        @Override
        public void onBindViewHolder(PlacesHolder holder, int position) {

            Place place = mPlacesList.get(position);

            holder.bindPlace(place);
        }

        @Override
        public int getItemCount() {
            return mPlacesList.size();
        }

    }
}
