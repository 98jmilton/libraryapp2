package com.example.ezmilja.libraryapp;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.example.ezmilja.libraryapp.BookRow.getName;
import static com.example.ezmilja.libraryapp.BookRow.name;
import static com.example.ezmilja.libraryapp.BooksArray.books;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.example.ezmilja.libraryapp.BooksArray.i;
import static com.example.ezmilja.libraryapp.SplashScreen.j;

public class RecyclerActivity extends AppCompatActivity implements SearchAdapter.BooksAdapterListener {
    private static final String TAG = RecyclerActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<BookRow> bookList;
    private SearchAdapter mAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        bookList = new ArrayList<>();
        mAdapter = new SearchAdapter(this, bookList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getName();
        System.out.println("XXXXXX\n"+name);
        //fetchBooks();
    }

    //fill bookList from database
    private void fetchBooks() {


        // TODO: Add database items to bookList
        List<BookRow> bookItems = new List<BookRow>() {

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<BookRow> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(BookRow bookRow) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends BookRow> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends BookRow> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public BookRow get(int i) {
                return null;
            }

            @Override
            public BookRow set(int i, BookRow bookRow) {
                return null;
            }

            @Override
            public void add(int i, BookRow bookRow) {

            }

            @Override
            public BookRow remove(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<BookRow> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<BookRow> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<BookRow> subList(int i, int i1) {
                return null;
            }
        };
        // adding contacts to book list
        bookList.clear();
        bookList.addAll(bookItems);
        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
        //VolleyInitialiser.getInstance().addToRequestQueue(request);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    @Override
    public void onBookSelected(BookRow bookRow) {
        Toast.makeText(getApplicationContext(), "Selected: " + getName() , Toast.LENGTH_LONG).show();
    }
}