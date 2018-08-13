package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContentsActivity extends AppCompatActivity {

    private Button btn_list,btn31list,btn_rqst,btn_check;
    static int j;

    static  String currentIsbn="";
    public static Book[] books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
        createButton();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference BookRef = database.getReference("/Books/");

        BookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                j= (int) dataSnapshot.getChildrenCount();
                books = new Book[j];
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        BookRef.addValueEventListener(new ValueEventListener() {
            int i = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot BookSnapshot : dataSnapshot.getChildren()) {

                    String isbn         = (String) BookSnapshot.child("ISBN").getValue();
                    String bookName     = (String) BookSnapshot.child("BookName").getValue();
                    String author       = (String) BookSnapshot.child("Author").getValue();
                    String imageAddress = (String) BookSnapshot.child("ImageAddress").getValue();
                    String genre        = (String) BookSnapshot.child("Genre").getValue();

                    try{
                        books[i] = new Book(isbn ,bookName, author, imageAddress, genre);
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                        Toast.makeText(ContentsActivity.this,"Book data updated ",Toast.LENGTH_LONG).show();

                    }
                    i++;
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                Intent intent = new Intent(ContentsActivity.this, BookList.class);
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