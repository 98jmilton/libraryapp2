package com.example.ezmilja.libraryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.ezmilja.libraryapp.BooksArray.books;
import static com.example.ezmilja.libraryapp.BooksArray.i;
import static com.example.ezmilja.libraryapp.SplashScreen.j;

public class ListActivity extends AppCompatActivity {

    ArrayAdapter<String> searchArrayAdapter;
    private RecyclerView Rv;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager layoutManager;

    private BookAdapter adapter;

    private SearchAdapter searchAdapter;

    ArrayList<String> myBookNames = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Rv = (RecyclerView) findViewById(R.id.bookRecycler);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(layoutManager);

        adapter = new BookAdapter(books, this);
        Rv.setAdapter(adapter);

        int k;
        for(k =0; k < j; k++) {
            String nameArray = books[k].bookName;
            myBookNames.add(nameArray);
        }
    }

    //Search to filter results
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Search Bar to filter results of books
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.searchmenu, menu);
        MenuItem item = menu.findItem(R.id.searchlist);
        final SearchView searchView = (SearchView)item.getActionView();

        //suggestion list for searching
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myBookNames);
//        Rv.setAdapter(searchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //using the enter key in search bar
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            //typing/deleting in search bar
            @Override
            public boolean onQueryTextChange(String s) {

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}