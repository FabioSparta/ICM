package pt.ua.nextweather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastResultsFragment extends Fragment {
    IpmaWeatherClient client = new IpmaWeatherClient();
    public static final String CITY_CODE="citycode";


    ArrayList<HashMap<String,String>> forecastResults=new ArrayList<>();
    String feedback="";
    TextView tv;
    int current_day=0;

    public ForecastResultsFragment() {
        // Required empty public constructor
    }


    public static ForecastResultsFragment newInstance(int city_code) {
        ForecastResultsFragment f = new ForecastResultsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CITY_CODE, city_code);
        f.setArguments(arguments);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_forecast_results, container, false);
        tv = rootView.findViewById(R.id.t1);

        Button prev = (Button) rootView.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(current_day>0)
                    ShowDay(--current_day);
            }
        });

        Button next = (Button) rootView.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(current_day<4)
                    ShowDay(++current_day);
            }
        });


        if (getArguments().containsKey(CITY_CODE)) {
            tv.setText(String.valueOf(this.getArguments().getInt(CITY_CODE)));
            getForecast(this.getArguments().getInt(CITY_CODE));
        }
        return rootView;
    }


    private void getForecast(int localId) {
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                for (Weather day : forecast) {
                    HashMap<String,String> dayMap =new HashMap<String,String>();
                    feedback += day.toString();
                    feedback += "\n";
                    dayMap.put("date",day.getForecastDate());
                    dayMap.put("precepitation",String.valueOf(day.getPrecipitaProb()));
                    dayMap.put("tMin",String.valueOf(day.getTMin()));
                    dayMap.put("tMax",String.valueOf(day.getTMax()));
                    dayMap.put("windDir",String.valueOf(day.getPredWindDir()));
                    dayMap.put("WindSpeed",String.valueOf(day.getClassWindSpeed()));
                    forecastResults.add(dayMap);
                }
                ShowDay(0);
                //Log.i("ForecastResults:",feedback);
            }
            @Override
            public void onFailure(Throwable cause) {
                feedback+= "Failed to get forecast for 5 days";
            }
        });

    }

    private void ShowDay(int indice){
        System.out.println(forecastResults.toString());
        HashMap<String,String> dayMap =new HashMap<String,String>();
        dayMap=forecastResults.get(indice);

        TextView generic=getView().findViewById(R.id.t0);
        generic.setText("Date:" + dayMap.get("date"));

        generic=getView().findViewById(R.id.t1);
        generic.setText("Precepition: " + dayMap.get("precepitation"));

        generic=getView().findViewById(R.id.t2);
        generic.setText("Temp_Min: " + dayMap.get("tMin"));

        generic=getView().findViewById(R.id.t3);
        generic.setText("Temp_Max: " + dayMap.get("tMax"));

        generic=getView().findViewById(R.id.t4);
        generic.setText("Wind Direction: " + dayMap.get("windDir"));

        generic=getView().findViewById(R.id.t5);
        generic.setText("Wind Speed: " + dayMap.get("WindSpeed"));

    }

    public void OnClickNext(View view){
        if(current_day<4)
            ShowDay(++current_day);
    }
    public void OnClickPrev(View view){
        if(current_day>0)
            ShowDay(--current_day);
    }
}