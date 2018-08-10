package com.example.ezmilja.libraryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ezmilja.libraryapp.ContentsActivity.currentIsbn;
import static com.example.ezmilja.libraryapp.SplashScreen.BookRef;

public class CheckoutActivity extends AppCompatActivity {

private Button scanButton;
private ImageButton info;
private static RadioButton radioButton;
private static Button button;
private static RadioGroup radioGroup;
private boolean on;
private String temp;
private int cur,in,out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        BookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot BookSnapshot: dataSnapshot.getChildren()) {
                    temp = (String) BookSnapshot.child(currentIsbn).child("NumCopys").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        isbnInfo();
        scan();
    }


    //ImageButton to open xml ISBN info
    private void isbnInfo() {
        info = findViewById(R.id.info_button);
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
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonIn:
                if (checked)
                    out = cur+1;
                    BookRef.child("/Books/").child(currentIsbn).child("NumCopys").setValue(in);
                    break;
            case R.id.radioButtonOut:
                if (checked)
                    out = cur-1;
                    BookRef.child("/Books/").child(currentIsbn).child("NumCopys").setValue(out);
                    break;
        }
    }
    //Open Barcode Scanner
    private void scan () {
        scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
    }

}

