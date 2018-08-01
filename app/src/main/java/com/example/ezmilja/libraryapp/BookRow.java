package com.example.ezmilja.libraryapp;

import static com.example.ezmilja.libraryapp.BooksArray.books;

public class BookRow {

    String name;
    String image;

    public BookRow() {

    }

    public String getName() {
//        int position;
//        for (position = 0; position < books.length ;position++) {
//            name = books[position].bookName;
//        }
        return name;
    }

    public String getImage() {
//        int position;
//        for (position = 0; position<books.length ;position++) {
//            image = books[position].imageAddress;
//        }
        return image;
    }
}