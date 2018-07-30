package com.example.ezmilja.libraryapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

private Button scanButton;
private ImageButton info;
private static RadioButton radioButton;
private static Button button;
private static RadioGroup radioGroup;
private boolean on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


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

        Button close = dialog.findViewById(R.id.close);

        close.setTypeface(myTypeFace1);

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
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

