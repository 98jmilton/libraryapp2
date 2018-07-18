
package com.example.ezmilja.libraryapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;

public class ListActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        ListView listView1 = (ListView) findViewById(R.id.listView1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference BookRef = database.getReference("Books");


        BookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                    int isbn = (int) messageSnapshot.child("ISBN").getValue();
                    String author = (String) messageSnapshot.child("Author").getValue();
                    URL imageaddress = (URL) messageSnapshot.child("img").getValue();
                    String Description = (String) messageSnapshot.child("Description").getValue();
                    String name= (String) messageSnapshot.child("Name").getValue();
                    String publisher= (String) messageSnapshot.child("Publisher").getValue();
                    int maxCopys= (int) messageSnapshot.child("MaxCopys").getValue();
                    int numCopys= (int) messageSnapshot.child("NumCopys").getValue();
                    int page= (int) messageSnapshot.child("Page").getValue();
                    int totrating= (int) messageSnapshot.child("Rating").getValue();
                    int numrating= (int) messageSnapshot.child("rating").getValue();


                    Book[] book = {
                            new Book(isbn, name, imageaddress, author, Description, page, publisher, totrating, numCopys, maxCopys, numrating),
                    };
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       ArrayAdapter<Book> adapter = new ArrayAdapter<Book>(this, android.R.layout.simple_list_item_1, book);
       listView1.setAdapter(adapter);
    }
}