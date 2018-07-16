package com.example.ezmilja.libraryapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardList extends AppCompatActivity {

    private Cursor cursor;
    private Button buttonRequest;
    private ListView listView;
    private Button btn_more;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        while (cursor.moveToNext()) {
            String t_name = cursor.getString(1);
            String t_author = cursor.getString(2);
            String t_email = cursor.getString(3);
            String t_vote = cursor.getString(4);
            String id = cursor.getString(0);
        }

        createButton();
        makeListView();

        searchView = (SearchView) findViewById(R.id.jeffdasearchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }


    private void makeListView(){

        listView = (ListView) findViewById(R.id.leaderbd_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Toast.makeText(LeaderboardList.this, "Goodbye Dave! Hello Steve!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createButton(){

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");
        buttonRequest = (Button) findViewById(R.id.buttonRequest);
        buttonRequest.setTypeface(myTypeFace1);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequestDialog();
            }
        });
    }

    private void makeRequestDialog(){
        final Dialog dialog = new Dialog(LeaderboardList.this);
        dialog.setContentView(R.layout.request);
        dialog.show();

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");

        Button btn_submitRequest = (Button) dialog.findViewById(R.id.btn_submitrequest);
        Button btn_back = (Button)dialog.findViewById(R.id.btn_back);
        btn_submitRequest.setTypeface(myTypeFace1);
        btn_back.setTypeface(myTypeFace1);

        final EditText edt_name = dialog.findViewById(R.id.name);
        final EditText edt_author = dialog.findViewById(R.id.reason);
        final EditText edt_email = dialog.findViewById(R.id.email);

        btn_submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_name = edt_name.getText().toString();
                String temp_author = edt_author.getText().toString();
                String temp_email = edt_email.getText().toString();
                if (requestCheck(temp_name, temp_author, temp_email)) {
                    makeListView();

                    dialog.dismiss();
                }
                else {
                    Toast.makeText(LeaderboardList.this, "Error: Please input correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private boolean requestCheck(String name, String author, String email){
        if (name.length() == 0 || author.length() == 0){
            return false;
        }
        if (email.toLowerCase().endsWith("@ericsson.com")){
            return true;
        }
        return false;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



