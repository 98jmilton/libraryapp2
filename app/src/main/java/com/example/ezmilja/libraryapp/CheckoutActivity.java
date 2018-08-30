package com.example.ezmilja.libraryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.ezmilja.libraryapp.ContentsActivity.currentIsbn;
import static com.example.ezmilja.libraryapp.SplashScreen.BookRef;
import static com.example.ezmilja.libraryapp.SplashScreen.bookCount;

public class CheckoutActivity extends AppCompatActivity {
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private boolean radioButtonsChecked;
    public static EditText editText;
    boolean isIsbn=false;
    boolean existingIsbn=false;
    private String numberOfCopies;
    private String isbn;
    private int intNumberOfCopies;
    private String isbnsArray[] = new String[bookCount];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        editText = findViewById(R.id.ISBNTextView);
        if(currentIsbn!=null) editText.setText(currentIsbn);
        createButton();
        isbnInfo();
        scan();

        BookRef.child("/Books/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int index = 0;
                for (DataSnapshot BookSnapshot : dataSnapshot.getChildren()) {

                    isbn = (String) BookSnapshot.child("ISBN").getValue();
                    isbnsArray[index] = isbn;
                    index++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));;

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
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setTypeface(myTypeFace1);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonsChecked = false;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                radioButtonsChecked =true;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIsbn = editText.getText().toString().trim();
                int selected_id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selected_id);

                if (radioButtonsChecked) {
                    checkIfIsbn();
                    isInDataBase();

                    if (!isIsbn){
                        Toast.makeText(CheckoutActivity.this, "Please enter a valid ISBN", Toast.LENGTH_SHORT).show();
                    }

                    if (!existingIsbn){
                        Toast.makeText(CheckoutActivity.this, "This book is not in the database", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        BookRef.child("/Books/").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                numberOfCopies = (String) dataSnapshot.child(currentIsbn).child("NumCopys").getValue();
                                try {
                                    if(numberOfCopies!=null) {
                                        intNumberOfCopies = Integer.valueOf(numberOfCopies);
                                        radioCheck();
                                        existingIsbn = false;
                                        isIsbn = false;
                                        Intent intent = new Intent(CheckoutActivity.this, ContentsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (ArrayIndexOutOfBoundsException e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
    }

    private void checkIfIsbn(){
        isIsbn = currentIsbn.length() == 13;
    }

    private void isInDataBase() {
        for (int d = 0; d < bookCount; d++) {
            if (isbnsArray[d].equals(currentIsbn)) {
                d = bookCount;
                existingIsbn=true;
            }
        }
    }

    private void radioCheck(){
        if (radioButton.getText().equals("Check IN")) {
            String in = String.valueOf(intNumberOfCopies + 1);
            BookRef.child("/Books/").child(currentIsbn).child("NumCopys").setValue(in);
            Toast.makeText(CheckoutActivity.this, "Book Checked IN", Toast.LENGTH_SHORT).show();
        }
        else if (radioButton.getText().equals("Check OUT")){
            String out = String.valueOf(intNumberOfCopies - 1);
            BookRef.child("/Books/").child(currentIsbn).child("NumCopys").setValue(out);
            Toast.makeText(CheckoutActivity.this, "Book Checked OUT", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(CheckoutActivity.this, "Book not found", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent intent = new Intent( this, ContentsActivity.class);
        startActivity(intent);
    }
}

