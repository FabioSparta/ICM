package com.example.phonedialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void digitClick(View v){
        TextView tview = (TextView) findViewById(R.id.digitsView);
        if(tview.length()<15) {
            Button b = (Button) v;
            tview.append(b.getText());
        }

    }

    public void delete(View v){
        TextView tview = (TextView) findViewById(R.id.digitsView);
        String texto=  tview.getText().toString();

        tview.setText(texto.substring(0, tview.length() - 1)) ;
    }

    public void createContact(View v){
        TextView number_to_call = (TextView) findViewById(R.id.digitsView);

    }

    public void dial(View v){
        TextView number_to_call = (TextView) findViewById(R.id.digitsView);

    }
}