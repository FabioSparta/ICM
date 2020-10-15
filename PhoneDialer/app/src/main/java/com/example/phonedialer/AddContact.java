package com.example.phonedialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AddContact extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.PhoneDialer.Name";
    public static final String EXTRA_NUMBER = "com.example.PhoneDialer.Number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }


    public void createContact(View v){
        TextView view_name = (TextView) findViewById(R.id.p_name);
        TextView view_num = (TextView) findViewById(R.id.p_number);
        Intent previous = getIntent();
        int id = previous.getIntExtra(MainActivity.EXTRA_ID,0);

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_NAME, view_name.getText().toString());
        replyIntent.putExtra(EXTRA_NUMBER, view_num.getText().toString());
        replyIntent.putExtra(MainActivity.EXTRA_ID,id);

        Log.i("Nome a adicionar ...",view_name.getText().toString());
        Log.i("Num a adicionar ...",view_num.getText().toString());

        if(view_name.getText().length()>0 && view_num.getText().length()>0){
            setResult(RESULT_OK, replyIntent);
        }
        else
            setResult(RESULT_CANCELED);
        finish();

    }
}