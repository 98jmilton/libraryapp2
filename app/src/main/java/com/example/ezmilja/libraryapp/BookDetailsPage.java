package com.example.ezmilja.libraryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static com.example.ezmilja.libraryapp.ContentsActivity.currentIsbn;
import static com.example.ezmilja.libraryapp.ContentsActivity.listcurrentPage;
import static com.example.ezmilja.libraryapp.ContentsActivity.detailscurrentPage;
import static com.example.ezmilja.libraryapp.SplashScreen.BookRef;

public class BookDetailsPage extends AppCompatActivity {
    TextView bookISBN,bookName,bookAuthor,bookPublisher,bookDescription,bookRating,bookPages,bookGenre;
    private ImageView bookImage;
    String ourRating="0.0";
    Float ratingbarValue;
    RatingBar ratingBar;
    Dialog ratingdialog;
    FirebaseAuth firebaseAuth;

    private String ISBN, Name, Author, Publisher, Description, Rating, NumRating, Pages, imageAddress, Genre, ratedBy;
    private URL imageUrl;
    private String done;
    String[] ratingEmails;
    String theUsersRating;
    boolean hasRated = false;
    String setRating;
    String curUser;
    int current;
    String combinedRates="";


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        curUser =String.valueOf(user.getEmail());

        bookISBN=  findViewById(R.id.bookISBN);
        bookName= findViewById(R.id.bookName);
        bookAuthor= findViewById(R.id.bookAuthor);
        bookPublisher= findViewById(R.id.bookPublisher);
        bookDescription= findViewById(R.id.bookDescription);
        bookRating= findViewById(R.id.bookRating);
        bookPages= findViewById(R.id.bookPages);
        bookImage= findViewById(R.id.bookImage);
        bookGenre = findViewById(R.id.bookGenre);

        BookRef.child("/Books/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (detailscurrentPage) {

                    ISBN         = (String) dataSnapshot.child(currentIsbn).child("ISBN").getValue();
                    Name         = (String) dataSnapshot.child(currentIsbn).child("BookName").getValue();
                    Author       = (String) dataSnapshot.child(currentIsbn).child("Author").getValue();
                    Publisher    = (String) dataSnapshot.child(currentIsbn).child("Publisher").getValue();
                    Description  = (String) dataSnapshot.child(currentIsbn).child("Description").getValue();
                    Rating       = (String) dataSnapshot.child(currentIsbn).child("Rating").getValue();
                    NumRating    = (String) dataSnapshot.child(currentIsbn).child("NumRating").getValue();
                    Pages        = (String) dataSnapshot.child(currentIsbn).child("Pages").getValue();
                    imageAddress = (String) dataSnapshot.child(currentIsbn).child("ImageAddress").getValue();
                    Genre        = (String) dataSnapshot.child(currentIsbn).child("Genre").getValue();
                    ratedBy      = (String) dataSnapshot.child(currentIsbn).child("RatedBy").getValue();

                    if(ratedBy!=null)
                    {
                        ratingEmails = ratedBy.split(",");

                        for (int x=0; x<ratingEmails.length; x++ )
                        {
                            if (ratingEmails[x].contains(curUser))
                            {
                               String foundRatedUser = (ratingEmails[x]);
                               theUsersRating = foundRatedUser.substring(curUser.length());

                               hasRated = true;

                            }
                        }
                    }


                    if (Rating == null && NumRating == null) {
                        done = "Not yet rated";
                    } else {
                        if (Integer.valueOf(NumRating) == 0) {
                            done = "Not yet rated";
                        } else {
                            double maths = Double.valueOf(Rating) / Double.valueOf(NumRating);
                            maths = Math.round(maths*100)/100;
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

        ratingdialog = new Dialog(BookDetailsPage.this);
        ratingdialog.setContentView(R.layout.rating_dialog);
        ratingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));;
        ratingdialog.show();

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");

        TextView title = ratingdialog.findViewById(R.id.rating_title);
        title.setTypeface(myTypeFace1);

        TextView bookName = ratingdialog.findViewById(R.id.rating_bookname);
        bookName.setText(Name);




        TextView author = ratingdialog.findViewById(R.id.rating_author);
        author.setText(Author);
        try {
            imageUrl =new URL(imageAddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView dialogBookImg = ratingdialog.findViewById(R.id.rating_bookcover);
        Glide.with(BookDetailsPage.this).load(imageUrl).into(dialogBookImg);

        Button close = ratingdialog.findViewById(R.id.close);
        close.setTypeface(myTypeFace1);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingdialog.dismiss();

            }
        });

        Button submit_button = ratingdialog.findViewById(R.id.submit_button);
        submit_button.setTypeface(myTypeFace1);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ratingBar = ratingdialog.findViewById(R.id.bookRatingBar);
                ratingbarValue = ratingBar.getRating();
                addRating();
            }
        });
    }

    public void addRating(){

        ourRating = String.valueOf(ratingbarValue);

        if(hasRated){
            for (int x=0; x<ratingEmails.length; x++ )
            {
                if (ratingEmails[x].contains(curUser))
                {
                    // email with the rating number together
                    String foundRatedUser = (ratingEmails[x]);
                    // Number at the end of users email on its own
                    theUsersRating = foundRatedUser.substring(curUser.length());

                    hasRated = true;
                    current = x;
                }
            }

            float totalRating = Math.round(Float.valueOf(ourRating) + Float.valueOf(Rating));
            totalRating = totalRating - Float.valueOf(theUsersRating);
            setRating = String.valueOf(totalRating);
            ratingEmails[current] = curUser+ourRating;
            for (String ratingEmail : ratingEmails) {
                ratedBy = ratingEmail+",";
                combinedRates +=ratedBy;
            }
        }

        else {
            float totalRating = Math.round(Float.valueOf(ourRating) + Float.valueOf(Rating));
            setRating = String.valueOf(totalRating);
            NumRating = String.valueOf(Integer.valueOf(NumRating) + 1);
            ratingEmails[current] = curUser+ourRating;
            combinedRates = ratedBy +curUser+ourRating+"," ;
        }

        BookRef.child("/Books/").child(currentIsbn).child("Rating").setValue(setRating);
        BookRef.child("/Books/").child(currentIsbn).child("NumRating").setValue(NumRating);
        BookRef.child("/Books/").child(currentIsbn).child("RatedBy").setValue(combinedRates);

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(), BookDetailsPage.class);
                    startActivity(intent);
                    ratingdialog.dismiss();

                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

        Toast.makeText(BookDetailsPage.this,"Rating submitted ",Toast.LENGTH_LONG).show();
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
