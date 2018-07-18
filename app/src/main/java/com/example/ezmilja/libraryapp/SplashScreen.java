package com.example.ezmilja.libraryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;



public class SplashScreen extends AppCompatActivity {

    Book[] book;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(), "yourfont.ttf");
        TextView TextView1 = (TextView) findViewById(R.id.TextView1);
        TextView1.setTypeface(myTypeFace1);



        requestPermission();



    }

    private void requestPermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(SplashScreen.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        else{

            Thread myThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                        Intent intent = new Intent(getApplicationContext(),ContentsActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Permission Granted
                    Toast.makeText(SplashScreen.this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();

                    Thread myThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(2000);
                                Intent intent = new Intent(getApplicationContext(),
                                        ContentsActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    myThread.start();

                }

                else
                {
                    // Permission Denied
                    Toast.makeText(SplashScreen.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();

                    Thread myThread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(2000);
                                Intent intent = new Intent(getApplicationContext(),ContentsActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    myThread.start();

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}


