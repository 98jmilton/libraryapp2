package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");
        TextView TextView1 = (TextView) findViewById(R.id.TextView1);
        TextView1.setTypeface(myTypeFace1);



                Timer startTimer = new Timer();
                startTimer.schedule(new TimerTask() {

                            @Override
                            public void run() {

                                // Write a message to the database
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Greeting");

                                myRef.setValue("edfedd");

                                Intent intent = new Intent(MainActivity.this, ContentsActivity.class);
                                finish();
                                startActivity(intent);
                            }


                }, 3472);
    }


}


