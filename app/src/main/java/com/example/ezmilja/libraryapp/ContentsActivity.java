package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContentsActivity extends AppCompatActivity {

    private Button btn_list;
    private Button btn31list;
    private Button btn_rqst;
    private Button btn_check;


    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Greeting");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);


        createButton();
    }

    private void createButton(){

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");
        btn31list = (Button) findViewById(R.id.btn31list);
        btn31list.setTypeface(myTypeFace1);


        btn_rqst = (Button) findViewById(R.id.btn_rqst);
        btn_rqst.setTypeface(myTypeFace1);


        btn_check = (Button) findViewById(R.id.btn_check);
        btn_check.setTypeface(myTypeFace1);


        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setTypeface(myTypeFace1);

        btn_list = (Button) findViewById(R.id.btn31list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    public static final String TAG = "";

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
                Intent intent = new Intent(ContentsActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
        btn_rqst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentsActivity.this, LeaderboardList.class);
                startActivity(intent);
            }

        });
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentsActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    };
}