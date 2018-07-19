
package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ezmilja.libraryapp.BooksArray.books;
import static com.example.ezmilja.libraryapp.SplashScreen.j;


public class ListActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView lv = (ListView) findViewById(R.id.bookRecycler);


        final List<String> your_array_list = new ArrayList<String>();
        int i;
        for (i=0;i<j;i++) {
            your_array_list.add( "Book Name: "+books[i].bookName + "\n" + "Author: " + books[i].author);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,your_array_list );

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

               // Intent androidsolved_intent = new Intent(getApplicationContext(), MessageScreen.class);
               // androidsolved_intent.putExtra("partner", your_array_list.get(position));
              // startActivity(androidsolved_intent);
            }
        });

        int k=0;
                while (k<j){
        System.out.println(books[k].isbn);
        System.out.println(books[k].author);
        System.out.println(books[k].imageAddress);
        System.out.println(books[k].description);
        System.out.println(books[k].bookName);
        System.out.println(books[k].publisher);
        System.out.println(books[k].max_copys);
        System.out.println(books[k].numberOfCopys);
        System.out.println(books[k].page);
        System.out.println(books[k].rating);
        System.out.println(books[k].num_rating);
        k++;}
    }
}