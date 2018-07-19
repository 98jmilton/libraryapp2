
package com.example.ezmilja.libraryapp;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.firebase.client.ValueEventListener;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    Query ref;
    ArrayList<Book> books= new ArrayList<Book>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Firebase.setAndroidContext(this);

        /*ref = new Firebase("https://testsongs-a571a.firebaseio.com/Books/").limitToLast(5);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                for (com.firebase.client.DataSnapshot child : dataSnapshot.getChildren()) {
                    String isbn = (String) child.child("ISBN").getValue();
                    String author = (String) child.child("Author").getValue();
                    String imageaddress = (String) child.child("img").getValue();
                    String Description = (String) child.child("Description").getValue();
                    String name = (String) child.child("Name").getValue();
                    String publisher = (String) child.child("Publisher").getValue();
                    int maxCopys = (int) child.child("MaxCopys").getValue();
                    int numCopys = (int) child.child("NumCopys").getValue();
                    int page = (int) child.child("Page").getValue();
                    int totrating = (int) child.child("Rating").getValue();
                    int numrating = (int) child.child("rating").getValue();

                    books.add(new Book(isbn, name, imageaddress, author, Description, page, publisher, totrating, numCopys, maxCopys, numrating));

                }

        }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }});}}
*/}}