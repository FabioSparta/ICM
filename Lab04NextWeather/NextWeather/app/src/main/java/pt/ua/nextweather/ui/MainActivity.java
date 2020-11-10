package pt.ua.nextweather.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.List;

import pt.ua.nextweather.CityItemAdapter;
import pt.ua.nextweather.fragments.CitiesHolderFragment;
import pt.ua.nextweather.fragments.ForecastResultsFragment;
import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.CityResultsObserver;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;
import pt.ua.nextweather.network.WeatherTypesResultsObserver;

public class MainActivity extends AppCompatActivity {
    private String feedback="";
    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GET CITIES FROM API
        LoadCities();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadCities();
            }
        });
    }


    public void LoadCities(){
        client.retrieveCitiesList(new CityResultsObserver() {
            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                MainActivity.this.cities = citiesCollection;
                if(cities.size()!=0){
                    CitiesHolderFragment fragment = CitiesHolderFragment.newInstance(cities);
                    displayFragment(fragment);
                }
            }
            @Override
            public void onFailure(Throwable cause) {
                feedback+="Failed to get cities list!";
            }
        });
    }


    public void displayFragment(Fragment fragment) {
        // Get  FragmentManager and start transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the new fragment for the current broadcast request
        fragmentTransaction.add(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
    }


}
