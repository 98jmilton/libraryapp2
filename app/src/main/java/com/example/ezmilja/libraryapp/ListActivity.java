
package com.example.ezmilja.libraryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        /*


        ListView listView1 = (ListView) findViewById(R.id.listView1);

        Product[] items = {
                new Product(1, "Milk", 21.50),
                new Product(2, "Butter", 15.99),
                new Product(3, "Yogurt", 14.90),
                new Product(4, "Toothpaste", 7.99),
                new Product(5, "Ice Cream", 10.00),
        };

        */

       // ArrayAdapter<Book> adapter = new ArrayAdapter<Book>(this,
        //        android.R.layout.simple_list_item_1, book);

       // listView1.setAdapter(adapter);
    }
}