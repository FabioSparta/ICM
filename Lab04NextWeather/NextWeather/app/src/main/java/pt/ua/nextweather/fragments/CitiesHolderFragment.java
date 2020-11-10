package pt.ua.nextweather.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.LinkedList;

import pt.ua.nextweather.CityItemAdapter;
import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitiesHolderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class CitiesHolderFragment extends Fragment {
    public static final String CITIES="cities";
    private HashMap<String, City> cities;

    Context context;
    private RecyclerView mRecyclerView;
    private CityItemAdapter mAdapter;

    public CitiesHolderFragment() { /*Required empty public constructor*/}

    public static CitiesHolderFragment newInstance(HashMap<String,City> cities) {
        CitiesHolderFragment f = new CitiesHolderFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(CITIES,cities);
        f.setArguments(arguments);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        context = container.getContext();
        Bundle b = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_cities_holder, container, false);

        if(b.getSerializable(CITIES) != null) {
            cities = (HashMap<String, City>) b.getSerializable(CITIES);

            for(String city: cities.keySet())
                //Log.i("z",city);

            mRecyclerView = (RecyclerView) view.findViewById(R.id.citiesrecycler);
            mAdapter = new CityItemAdapter(context, cities);                // Create an adapter and supply the data to be displayed.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));   // Give the RecyclerView a default layout manager.
            mRecyclerView.setAdapter(mAdapter);                                 // Connect the adapter with the RecyclerView.
        }

        return view;
    }


}