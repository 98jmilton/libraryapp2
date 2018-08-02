package com.example.ezmilja.libraryapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RecyclerActivity extends AppCompatActivity implements SearchAdapter.BooksAdapterListener {
    private static final String TAG = RecyclerActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<BookRow> bookList;
    private SearchAdapter mAdapter;
    private SearchView searchView;
    FirebaseRecyclerAdapter<BookRow, SearchAdapter.MyViewHolder> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        bookList = new ArrayList<> ();
        mAdapter = new SearchAdapter(this, bookList, this);

        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("/Books/");
        Query booksQuery = booksRef.orderByKey();

        fetchBooks();

        FirebaseRecyclerOptions booksOptions = new FirebaseRecyclerOptions.Builder<BookRow>().setQuery(booksQuery, BookRow.class).build();

        recyclerView.setAdapter(mAdapter);

        System.out.println("hhhhhhhhhhh\n" + booksOptions + "\nhhhhhhhhhh");
    }

    //TODO: Make this add name, image etc. to bookList as items and we are finished thanks
    //fill bookList with items from database
    private void fetchBooks() {



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.searchlist)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
           automatically handle clicks on the Home/Up button, so long
           as you specify a parent activity in AndroidManifest.xml.     */
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.searchlist) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //close search view on back button pressed
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    //When Row is clicked
    @Override
    public void onBookSelected(BookRow bookRow) {
        Toast.makeText(getApplicationContext(), "Selected: " + bookRow.getName() , Toast.LENGTH_LONG).show();
    }
}