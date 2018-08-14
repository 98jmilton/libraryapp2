package com.example.ezmilja.libraryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ezmilja.libraryapp.ContentsActivity.currentIsbn;
import static com.example.ezmilja.libraryapp.ContentsActivity.isbns;
import static com.example.ezmilja.libraryapp.SplashScreen.BookRef;
import static com.example.ezmilja.libraryapp.SplashScreen.j;

public class CheckoutActivity extends AppCompatActivity {
private static String imageAddress,Name,Author;
private URL imageUrl;
private static RadioButton radioButton;
private static RadioGroup radioGroup;
private boolean on;
private String temp,temp2,setRating,numRating;
private int curNum,curRat;
private EditText editText;
boolean isIsbnBool=false;
float rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        editText = findViewById(R.id.dropDownTextView);
        if(currentIsbn!=null) editText.setText(currentIsbn);
        createButton();
        isbnInfo();
        scan();
    }

    //ImageButton to open xml ISBN info
    private void isbnInfo() {
        ImageButton info = findViewById(R.id.info_button);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeInfoDialog();
            }
        });
    }

    //Display ISBN info xml
    private void makeInfoDialog(){
        final Dialog dialog = new Dialog(CheckoutActivity.this);
        dialog.setContentView(R.layout.content_isbninfo);
        dialog.show();

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");

        TextView title =  dialog.findViewById(R.id.title);
        title.setTypeface(myTypeFace1);
        title.setText(currentIsbn);
        Button close = dialog.findViewById(R.id.close);

        close.setTypeface(myTypeFace1);

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }



    private void createButton() {
        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(), "yourfont.ttf");
        Button button = (Button) findViewById(R.id.submit_button);
        button.setTypeface(myTypeFace1);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        on = false;

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (on) {
                            currentIsbn = editText.getText().toString();
                            int selected_id = radioGroup.getCheckedRadioButtonId();
                            radioButton = (RadioButton) findViewById(selected_id);

                            BookRef.child("/Books/").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    temp = (String) dataSnapshot.child(currentIsbn).child("NumCopys").getValue();
                                    System.out.println("les oueff"+temp);
                                    curNum = Integer.valueOf(temp);
                                    isIsbn();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    }
                }
        );
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                on =true;
            }
        });

    }

    private void isIsbn(){
        if (currentIsbn.length()==13){isIsbnBool = true;isInDB();}
        else{isIsbnBool = false;}
        Toast.makeText(CheckoutActivity.this, "Please enter a valid ISBN", Toast.LENGTH_SHORT).show();return;
    }
    private void isInDB() {
        for (int d = 0; d < j; d++) {
            if (isbns[d].equals(currentIsbn)) {
                d = j;
                radioCheck();
            }
        }
    }
    private void radioCheck(){
        if (radioButton.getText().equals("Check IN")) {
            String in = String.valueOf(curNum + 1);
            System.out.println(in);
            BookRef.child("/Books/").child(currentIsbn).child("NumCopys").setValue(in);
            Toast.makeText(CheckoutActivity.this, "Book Checked IN", Toast.LENGTH_SHORT).show();
            makeRatingDialog();

        }
        else if (radioButton.getText().equals("Check OUT")){
            String out = String.valueOf(curNum - 1);
            System.out.println(out);
            BookRef.child("/Books/").child(currentIsbn).child("NumCopys").setValue(out);
            Toast.makeText(CheckoutActivity.this, "Book Checked OUT", Toast.LENGTH_SHORT).show();
            finish();
        }

        else{
            Toast.makeText(CheckoutActivity.this, "Book not found", Toast.LENGTH_SHORT).show(); return;
        }
    }

    //Open Barcode Scanner
    private void scan() {
        Button scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, ScanActivity.class);
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

            final Dialog dialog = new Dialog(CheckoutActivity.this);
            dialog.setContentView(R.layout.rating_dialog);
            dialog.show();

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");

        TextView title = (TextView) dialog.findViewById(R.id.tbx_title);
        title.setTypeface(myTypeFace1);

        TextView bookName = (TextView) dialog.findViewById(R.id.tbx_bookname);
        bookName.setText(Name);

        TextView author = (TextView) dialog.findViewById(R.id.tbx_author);
        author.setText(Author);
        try {
            imageUrl =new URL(imageAddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ImageView dialogBookImg = (ImageView) dialog.findViewById(R.id.img_bookcover);
        Glide.with(CheckoutActivity.this).load(imageUrl).into(dialogBookImg);


        Button close = (Button) dialog.findViewById(R.id.close);
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
                Toast.makeText(CheckoutActivity.this, "Rating submitted", Toast.LENGTH_SHORT).show();
                RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
                rating = ratingBar.getRating();
                BookRef.child("/Books/").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        temp2 = (String) dataSnapshot.child(currentIsbn).child("Rating").getValue();
                        numRating = (String) dataSnapshot.child(currentIsbn).child("NumRating").getValue();
                        System.out.println("les oueff"+temp2);
                        curRat = Integer.valueOf(temp2);
                        addRating();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    public void addRating(){

        setRating = String.valueOf(Float.valueOf(temp2)+rating);
        numRating = String.valueOf(Integer.valueOf(numRating)+1);
        BookRef.child("/Books/").child(currentIsbn).child("Rating").setValue(setRating);
        BookRef.child("/Books/").child(currentIsbn).child("numRating").setValue(numRating);
        finish();
    }
}


