package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContentsActivity extends AppCompatActivity {

    private Button btn_list;
    private Button btn31list;
    private Button btn_rqst;
    private Button btn_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);

        createButton();
    }

    private void createButton(){

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");

        //List
        btn_list =  findViewById(R.id.btn_list);
        btn_list.setTypeface(myTypeFace1);

        //Requests
        btn_rqst =  findViewById(R.id.btn_rqst);
        btn_rqst.setTypeface(myTypeFace1);

        //Check In/Out
        btn_check = findViewById(R.id.btn_check);
        btn_check.setTypeface(myTypeFace1);


        TextView textView2 = findViewById(R.id.textView2);
        textView2.setTypeface(myTypeFace1);

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentsActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

//       btn_rqst.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//           Intent intent = new Intent(ContentsActivity.this, LeaderboardList.class);
//           startActivity(intent);
//         }
//       });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentsActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    };
}