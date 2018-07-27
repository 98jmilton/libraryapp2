package com.example.ezmilja.libraryapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.InputStream;

import static android.Manifest.permission.INTERNET;
import static com.example.ezmilja.libraryapp.BooksArray.books;
import static java.lang.Math.toIntExact;
import static java.lang.Thread.sleep;


public class SplashScreen extends AppCompatActivity {

    public static long j=0;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(), "yourfont.ttf");
        TextView TextView1 = findViewById(R.id.TextView1);
        TextView1.setTypeface(myTypeFace1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference BookRef = database.getReference("/ Books/");

        //Read data from database
        BookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // j= dataSnapshot.getChildrenCount();
                j= dataSnapshot.getChildrenCount();
                int i = 0;

                for (DataSnapshot BookSnapshot : dataSnapshot.getChildren()) {
                    String isbn         = (String) BookSnapshot.child("ISBN").getValue();
                    String name         = (String) BookSnapshot.child("BookName").getValue();
                    String imageAddress = (String) BookSnapshot.child("ImageAddress").getValue();
                    String author       = (String) BookSnapshot.child("Author").getValue();
                    String description  = (String) BookSnapshot.child("Description").getValue();
                    String page         = (String) BookSnapshot.child("Pages").getValue();
                    String publisher    = (String) BookSnapshot.child("Publisher").getValue();
                    String numRating    = (String) BookSnapshot.child("NumRating").getValue();
                    String totRating    = (String) BookSnapshot.child("Rating").getValue();
                    String numCopies    = (String) BookSnapshot.child("NumCopies").getValue();
                    String maxCopies    = (String) BookSnapshot.child("MaxCopies").getValue();

                    books[i] = new Book(isbn, name, imageAddress, author, description, page, publisher,numRating, totRating, numCopies, maxCopies);
                    i++;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Request permissions and move to contents page
        requestCameraPermission();
        networkConnection();

        if (networkConnection()) {
            toContents();
        }

        else {
            //Restart Current Activity
            Toast.makeText(SplashScreen.this, "Please enable Internet connection to continue", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
            startActivity(intent);
        }
    }

    //Request Camera Permission
    private void requestCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    //Move to Contents Activity
    private void toContents(){

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(), ContentsActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    //Camera Permissions Results
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(SplashScreen.this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    // Permission Denied
                    Toast.makeText(SplashScreen.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //Check Internet Connection
    private boolean networkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}