package com.example.ezmilja.libraryapp;

import android.widget.TextView;

import static com.example.ezmilja.libraryapp.BooksArray.books;
import static com.example.ezmilja.libraryapp.SplashScreen.j;

public class BookRow {
    String name;
    String image;
    TextView bookDetails;
    int position;
    public BookRow() {

    }

    public String getName() {
//        for (position = 0; position < j; position++) {
//            name = getName().getClass(BooksArray).setText(books[position].bookName + "\n \n" + books[position].author);
//        }
        return name;
    }

    public String getImage() {

        return image;

    }
}