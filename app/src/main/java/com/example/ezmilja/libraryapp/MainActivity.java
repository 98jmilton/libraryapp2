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

                                BookRef.child("Book1").child("name").setValue("Paul");
                                BookRef.child("Book2").child("name").setValue("Sean");
                                BookRef.child("Book1").child("what").setValue("do you mean");
                                BookRef.child("Book2").child("what").setValue("are you doing");
                                BookRef.child("Book1").child("James").setValue("are you going");
                                BookRef.child("Book2").child("James").setValue("are you done");


//                                BookRef.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                        for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
//                                            Log.v("tmz",""+ chidSnap.getKey()); //displays the key for the node
//                                          // Log.v("tmz",""+ chidSnap.child("Book A").getValue());   //gives the value for given keyname
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//
//                                    }
//                                });


                                Intent intent = new Intent(MainActivity.this, ContentsActivity.class);
                                finish();
                                startActivity(intent);
                            }


                }, 3472);
    }


}


