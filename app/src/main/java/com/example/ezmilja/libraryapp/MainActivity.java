package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference BookRef = database.getReference("Books");

                                BookRef.child("Book1").child("Title").setValue("Paul");
                                BookRef.child("Book1").child("Author").setValue("do you mean");
                                BookRef.child("Book1").child("ISBN").setValue("are you going");
                                BookRef.child("Book2").child("Title").setValue("Sean");
                                BookRef.child("Book2").child("Author").setValue("are you doing");
                                BookRef.child("Book2").child("ISBN").setValue("are you done");


                                Intent intent = new Intent(MainActivity.this, ContentsActivity.class);
                                finish();
                                startActivity(intent);
                            }


                }, 3472);
    }


}


