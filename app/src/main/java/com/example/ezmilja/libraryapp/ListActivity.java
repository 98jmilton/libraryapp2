
package com.example.ezmilja.libraryapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.ezmilja.libraryapp.BooksArray.books;


public class ListActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        System.out.println(books[0].isbn);
        System.out.println(books[0].author);
        System.out.println(books[0].imageAddress);
        System.out.println(books[0].description);
        System.out.println(books[0].bookName);
        System.out.println(books[0].publisher);
        System.out.println(books[0].max_copys);
        System.out.println(books[0].numberOfCopys);
        System.out.println(books[0].page);
        System.out.println(books[0].rating);
        System.out.println(books[0].num_rating);
        System.out.println(books[1].isbn);
        System.out.println(books[1].author);
        System.out.println(books[1].imageAddress);
        System.out.println(books[1].description);
        System.out.println(books[1].bookName);
        System.out.println(books[1].publisher);
        System.out.println(books[1].max_copys);
        System.out.println(books[1].numberOfCopys);
        System.out.println(books[1].page);
        System.out.println(books[1].rating);
        System.out.println(books[1].num_rating);
        System.out.println(books[2].isbn);
        System.out.println(books[2].author);
        System.out.println(books[2].imageAddress);
        System.out.println(books[2].description);
        System.out.println(books[2].bookName);
        System.out.println(books[2].publisher);
        System.out.println(books[2].max_copys);
        System.out.println(books[2].numberOfCopys);
        System.out.println(books[2].page);
        System.out.println(books[2].rating);
        System.out.println(books[2].num_rating);
        System.out.println(books[3].isbn);
        System.out.println(books[3].author);
        System.out.println(books[3].imageAddress);
        System.out.println(books[3].description);
        System.out.println(books[3].bookName);
        System.out.println(books[3].publisher);
        System.out.println(books[3].max_copys);
        System.out.println(books[3].numberOfCopys);
        System.out.println(books[3].page);
        System.out.println(books[3].rating);
        System.out.println(books[3].num_rating);
        System.out.println(books[4].isbn);
        System.out.println(books[4].author);
        System.out.println(books[4].imageAddress);
        System.out.println(books[4].description);
        System.out.println(books[4].bookName);
        System.out.println(books[4].publisher);
        System.out.println(books[4].max_copys);
        System.out.println(books[4].numberOfCopys);
        System.out.println(books[4].page);
        System.out.println(books[4].rating);
        System.out.println(books[4].num_rating);

        }
}