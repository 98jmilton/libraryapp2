package com.example.ezmilja.libraryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ezmilja.libraryapp.ContentsActivity.currentIsbn;
import static com.example.ezmilja.libraryapp.ContentsActivity.listcurrentPage;
import static com.example.ezmilja.libraryapp.ContentsActivity.detailscurrentPage;
import static com.example.ezmilja.libraryapp.SplashScreen.BookRef;

public class BookDetailsPage extends AppCompatActivity {
    TextView bookISBN,bookName,bookAuthor,bookPublisher,bookDescription,bookRating,bookPages,bookGenre;
    private ImageView bookImage;
    private String setRating;
    String ourRating="0.0";
    RatingBar theratingBar;
    float ratingbarValue;

    private String ISBN, Name, Author, Publisher, Description, Rating, NumRating, Pages, imageAddress, Genre;
    private URL imageUrl;
    private String done;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        bookISBN=  findViewById(R.id.bookISBN);
        bookName= findViewById(R.id.bookName);
        bookAuthor= findViewById(R.id.bookAuthor);
        bookPublisher= findViewById(R.id.bookPublisher);
        bookDescription= findViewById(R.id.bookDescription);
        bookRating= findViewById(R.id.bookRating);
        bookPages= findViewById(R.id.bookPages);
        bookImage= findViewById(R.id.bookImage);
        bookGenre = findViewById(R.id.bookGenre);

        theratingBar = (RatingBar) findViewById(R.id.ratingBar);



        BookRef.child("/Books/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (detailscurrentPage) {

                    DataSnapshot snapshot = dataSnapshot.child(currentIsbn);
                    ISBN = (String) snapshot.child("ISBN").getValue();
                    Name = (String) snapshot.child("BookName").getValue();
                    Author = (String) snapshot.child("Author").getValue();
                    Publisher = (String) snapshot.child("Publisher").getValue();
                    Description = (String) snapshot.child("Description").getValue();
                    Rating = (String) snapshot.child("Rating").getValue();
                    NumRating = (String) snapshot.child("NumRating").getValue();
                    Pages = (String) snapshot.child("Pages").getValue();
                    imageAddress = (String) snapshot.child("ImageAddress").getValue();
                    Genre = (String) snapshot.child("Genre").getValue();

                    if (Rating == null && NumRating == null) {
                        done = "Not yet rated";
                    } else {
                        if (Integer.valueOf(NumRating) == 0) {
                            done = "Not yet rated";
                        } else {
                            double maths = Double.valueOf(Rating) / Double.valueOf(NumRating);
                            done = String.valueOf(maths);
                        }
                    }


                    try {
                        if (ISBN != null && Name != null && Author != null && Publisher != null && Description != null && Rating != null && Pages != null && imageAddress != null && Genre != null) {
                            bookISBN.setText("ISBN: " + ISBN);
                            bookName.setText("Title: " + Name);
                            bookAuthor.setText("Author: " + Author);
                            bookPublisher.setText("Publisher: " + Publisher);
                            bookDescription.setText("Description: " + Description);
                            bookRating.setText("User Rating: " + done);
                            bookPages.setText("Page Count:" + Pages);
                            bookGenre.setText("Genre:" + Genre);
                            imageUrl = new URL(imageAddress);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Glide.with(BookDetailsPage.this).load(imageUrl).into(bookImage);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Button btn_rate =findViewById(R.id.btn_rate);
        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRatingDialog();
            }
        });
        Button btn_checkout = findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailscurrentPage=false;
                Intent intent = new Intent(BookDetailsPage.this, CheckoutActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    private void makeRatingDialog(){

        BookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot BookSnapshot : dataSnapshot.getChildren()) {
                    imageAddress = (String) BookSnapshot.child(currentIsbn).child("ImageAddress").getValue();
                    Name = (String) BookSnapshot.child(currentIsbn).child("BookName").getValue();
                    Author = (String) BookSnapshot.child(currentIsbn).child("Author").getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Dialog dialog = new Dialog(BookDetailsPage.this);
        dialog.setContentView(R.layout.rating_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));;
        dialog.show();



        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");

        TextView title = dialog.findViewById(R.id.tbx_title);
        title.setTypeface(myTypeFace1);

        TextView bookName = dialog.findViewById(R.id.tbx_bookname);
        bookName.setText(Name);

        TextView author = dialog.findViewById(R.id.tbx_author);
        author.setText(Author);
        try {
            imageUrl =new URL(imageAddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView dialogBookImg = (ImageView) dialog.findViewById(R.id.img_bookcover);
        Glide.with(BookDetailsPage.this).load(imageUrl).into(dialogBookImg);


        Button close = dialog.findViewById(R.id.close);
        close.setTypeface(myTypeFace1);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        Button submit_button = dialog.findViewById(R.id.submit_button);
        submit_button.setTypeface(myTypeFace1);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRating();
            }
        });
    }
    public void addRating(){

        setRating = String.valueOf(Float.valueOf(ourRating)+Float.valueOf(Rating));
        NumRating = String.valueOf(Integer.valueOf(NumRating)+1);
        BookRef.child("/Books/").child(currentIsbn).child("Rating").setValue(setRating);
        BookRef.child("/Books/").child(currentIsbn).child("NumRating").setValue(NumRating);

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(), ContentsActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        detailscurrentPage=false;
        listcurrentPage=true;
        Intent intent = new Intent( this, BookList.class);
        finish();
        startActivity(intent);
    }
}
