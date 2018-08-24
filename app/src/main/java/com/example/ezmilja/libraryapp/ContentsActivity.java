package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.ezmilja.libraryapp.SplashScreen.j;


public class ContentsActivity extends AppCompatActivity {
    public static String[] isbns = new String[j];
    public static Book[] books = new Book[j];

    public static boolean listcurrentPage=false;
    public static boolean detailscurrentPage=false;
    public static boolean requestcurrentPage=false;

    static String currentIsbn="";

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        createButton();
    }

    private void createButton(){

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");

        //List
        Button btn_list = findViewById(R.id.btn_list);
        btn_list.setTypeface(myTypeFace1);

        //Requests
        Button btn_rqst = findViewById(R.id.btn_rqst);
        btn_rqst.setTypeface(myTypeFace1);

        //Check In/Out
        Button btn_check = findViewById(R.id.btn_check);
        btn_check.setTypeface(myTypeFace1);


        TextView textView2 = findViewById(R.id.textView2);
        textView2.setTypeface(myTypeFace1);

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentsActivity.this, BookList.class);
                listcurrentPage = true;

                startActivity(intent);
                finish();
            }
        });

        btn_rqst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentsActivity.this, RequestList.class);
                startActivity(intent);
                requestcurrentPage = true;

                finish();
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentsActivity.this, CheckoutActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(ContentsActivity.this, LoginActivity.class));
                return true;

            case R.id.changepassword_menu:

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseAuth auth = FirebaseAuth.getInstance();

                String emailAddress = user.getEmail();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // do something when mail was sent successfully.
                                    Toast.makeText(ContentsActivity.this, "An password reset has been sent to your email", Toast.LENGTH_SHORT).show(); return;

                                } else {
                                    Toast.makeText(ContentsActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show(); return;
                                    }
                            }
                        });
                return true;

            default:


                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}