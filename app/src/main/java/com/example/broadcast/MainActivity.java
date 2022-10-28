package com.example.broadcast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText weight;
    EditText height;
    TextView txt;
    Button enter;
    ImageView img;
    double bmi = 18.5;
    IntentFilter filter;
    IncomingCall_Receiver incomingCall_receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, Manifest.permission.SEND_SMS},1);
        weight  = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        enter = findViewById(R.id.enter);
        txt = findViewById(R.id.txt);
        img = findViewById(R.id.img);
        enter.setOnClickListener(this::onClick);
        incomingCall_receiver = new IncomingCall_Receiver();
        filter = new IntentFilter("android.intent.action.PHONE_STATE");
        registerReceiver(incomingCall_receiver,filter);
    }
    public void onClick(View view){

        if (view == enter) {
            if (!weight.getText().toString().equals("")||!height.getText().toString().equals("")) {
                double mass = Double.parseDouble(weight.getText().toString());
                double length = Double.parseDouble(height.getText().toString());
                bmi = mass/(length*length);
                bmi = Math.round(bmi * 100) / 100;
                txt.setText(""+bmi);
                txt.setVisibility(View.VISIBLE);
                if (bmi <18.5) {
                    img.setImageResource(R.drawable.under);
                    img.setVisibility(View.VISIBLE);
                }
                else if ( bmi>=18.5 && bmi<=24.9) {
                    img.setImageResource(R.drawable.normal);
                    img.setVisibility(View.VISIBLE);
                }
                else if (bmi >24.9 && bmi<=29.9) {
                    img.setImageResource(R.drawable.over);
                    img.setVisibility(View.VISIBLE);
                }
                else if(bmi>29.9){
                    img.setImageResource(R.drawable.obesity);
                    img.setVisibility(View.VISIBLE);
                }
            }
            else{
                Toast.makeText(MainActivity.this, "enter weight and height", Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(incomingCall_receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            unregisterReceiver(incomingCall_receiver);
        }
        catch (IllegalArgumentException e) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(incomingCall_receiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            unregisterReceiver(incomingCall_receiver);
        }
        catch (IllegalArgumentException e) {
        }
    }
}