package com.example.ezmilja.libraryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText ReEditTextPassword;
    private Button buttonSignup;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    public static String email;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ContentsActivity.class));
        }

        //initializing Textviews and buttons
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        ReEditTextPassword = (EditText) findViewById(R.id.ReEditTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        email = editTextEmail.getText().toString().toLowerCase().trim();
        final String password  = editTextPassword.getText().toString().toLowerCase().trim();



        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog



            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                finish();
                                //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                sendVerificationEmail();
                            } else
                                {
                                //display some message here
                                Toast.makeText(SignUp.this, "Registration Error account may already exist", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });



    }

    @Override
    public void onClick(View view) {

        // When the Signup button is clicked
        if(view == buttonSignup)
        {

            if (editTextPassword.getText().toString().trim().matches("^(?=.*\\d)(?=.*[a-zA-Z]).{6,70}$"))
            {
                // checks the text from editTextPassword and compares it to ReEditTextPassword
                if (editTextPassword.getText().toString().trim().equals(ReEditTextPassword.getText().toString().trim()) ) {
                    // Check if the email field ends with "@ericsson.com"
                    if (!editTextEmail.getText().toString().trim().endsWith("@ericsson.com"))
                    {
                        Toast.makeText(SignUp.this, "Must be an Ericsson email to sign up", Toast.LENGTH_LONG).show();
                    } else
                        {
                        registerUser();
                    }
                }
                else
                    {
                    Toast.makeText(SignUp.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(SignUp.this, "Password must be at least 6 characters long and contain numbers and letters", Toast.LENGTH_LONG).show();
            }
        }

        // If the Sign in Here text is clicked
        if(view == textViewSignin){
            //open login activity when user taps on the already registered textview
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    private void sendVerificationEmail()
    {
        // Gets the current user from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent

                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignUp.this, LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            startActivity(new Intent(SignUp.this, LoginActivity.class));
                            finish();

                        }
                    }
                });
    }
}
