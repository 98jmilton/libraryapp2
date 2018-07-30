package com.example.ezmilja.libraryapp;

import android.widget.TextView;

import static com.example.ezmilja.libraryapp.BooksArray.books;
import static com.example.ezmilja.libraryapp.SplashScreen.j;

public class BookRow {
    static String name;
    static String image;
    TextView bookDetails;
   static int position;

    public BookRow() {

    }

    public static String getName() {
        for (position = 0; position<j ;position++) {
            name = books[position].bookName;
        }
        return name;
    }

    public String getImage() {

        for (position = 0; position<j ;position++) {
            image = books[position].imageAddress;
        }
        return image;
    }
}