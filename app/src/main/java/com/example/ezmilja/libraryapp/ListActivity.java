
package com.example.ezmilja.libraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.ezmilja.libraryapp.BooksArray.books;
import static com.example.ezmilja.libraryapp.SplashScreen.j;


public class ListActivity extends AppCompatActivity {

    private RecyclerView Rv;

    private RecyclerView.LayoutManager layoutManager;

    private BookAdapter adapter;


    private int[] images = {
            R.drawable.ado,R.drawable.advancecobra,R.drawable.agileretrospectives,R.drawable.antinaction,R.drawable.bounce
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Rv = (RecyclerView) findViewById(R.id.bookRecycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutManager);

        adapter=new BookAdapter(books,this);
        Rv.setAdapter(adapter);

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