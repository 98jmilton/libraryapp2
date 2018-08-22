package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ezmilja.libraryapp.ContentsActivity.currentIsbn;
import static com.example.ezmilja.libraryapp.SplashScreen.BookRef;


public class BookDetailsPage extends AppCompatActivity {
    TextView bookISBN,bookName,bookAuthor,bookPublisher,bookDescription,bookRating,bookPages,bookGenre;
    private ImageView bookImage;

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

        BookRef.child("/Books/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHH"+ currentIsbn);
                    ISBN = (String) dataSnapshot.child(currentIsbn).child("ISBN").getValue();
                    Name = (String) dataSnapshot.child(currentIsbn).child("BookName").getValue();
                    Author = (String) dataSnapshot.child(currentIsbn).child("Author").getValue();
                    Publisher = (String) dataSnapshot.child(currentIsbn).child("Publisher").getValue();
                    Description = (String) dataSnapshot.child(currentIsbn).child("Description").getValue();
                    Rating = (String) dataSnapshot.child(currentIsbn).child("Rating").getValue();
                    NumRating = (String) dataSnapshot.child(currentIsbn).child("NumRating").getValue();
                    Pages = (String) dataSnapshot.child(currentIsbn).child("Pages").getValue();
                    imageAddress = (String) dataSnapshot.child(currentIsbn).child("ImageAddress").getValue();
                    Genre = (String) dataSnapshot.child(currentIsbn).child("Genre").getValue();
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHH"+ ISBN);

                    if (Rating == null && NumRating == null) {
                        done = "Not yet rated";
                    } else {
                        if(Integer.valueOf(NumRating)==0){done = "Not yet rated";}
                        else{
                            int maths = Integer.valueOf(Rating) / Integer.valueOf(NumRating);
                            done = String.valueOf(maths);
                        }
                    }


                    try {
                        if(ISBN !=null && Name !=null && Author !=null && Publisher !=null&&Description !=null&&Rating !=null&&Pages !=null&&imageAddress !=null&&Genre!=null ){
                        bookISBN.setText("ISBN: "+ ISBN);
                        bookName.setText("Title: "+ Name);
                        bookAuthor.setText("Author: "+ Author);
                        bookPublisher.setText("Publisher: "+ Publisher);
                        bookDescription.setText("Description: "+ Description);
                        bookRating.setText("User Rating: "+ done);
                        bookPages.setText("Page Count:"+ Pages);
                        bookGenre.setText("Genre:"+ Genre);
                        imageUrl =new URL(imageAddress);}
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Glide.with(BookDetailsPage.this).load(imageUrl).into(bookImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button btn_edit = findViewById(R.id.btn_checkout);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailsPage.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent intent = new Intent( this, BookList.class);
        finish();
        startActivity(intent);
    }
}
