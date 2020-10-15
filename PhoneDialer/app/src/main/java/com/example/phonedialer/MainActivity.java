package com.example.phonedialer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CONTACT_ACTIVITY = 1;
    public HashMap<String,String> Contacts= new HashMap<>();

    public static final String EXTRA_ID = "com.example.PhoneDialer.ButtonId";

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
        if (tview.getText().length()>0){
            tview.setText(texto.substring(0, tview.length() - 1)) ;
        }

    }

    public void dial(View v){
        TextView number_view = (TextView) findViewById(R.id.digitsView);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ number_view.getText().toString()));
        startActivity(intent);
    }


    public void AcessContact(View v){
        Button choosen_memory= (Button) findViewById(v.getId());

        //There is already a saved contact here
        if(Contacts.containsKey(choosen_memory.getText().toString())){
            TextView number_view = (TextView) findViewById(R.id.digitsView);
            number_view.setText(Contacts.get(choosen_memory.getText()));
        }
        //This contact slot is still empty
        else {
            Intent intent = new Intent(this, AddContact.class);
            intent.putExtra(EXTRA_ID, choosen_memory.getId());
            startActivityForResult(intent, ADD_CONTACT_ACTIVITY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ADD_CONTACT_ACTIVITY) : {
                if (resultCode == Activity.RESULT_OK) {
                    String name = data.getStringExtra(AddContact.EXTRA_NAME);
                    String number = data.getStringExtra(AddContact.EXTRA_NUMBER);
                    int button_id=data.getIntExtra(EXTRA_ID,0);
                    Contacts.put(name,number);
                    Button choosen_memory= (Button) findViewById(button_id);
                    choosen_memory.setText(name);
                }
                break;
            }
        }
    }
}