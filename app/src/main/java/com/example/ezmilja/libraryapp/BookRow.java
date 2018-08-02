package com.example.ezmilja.libraryapp;

import static com.example.ezmilja.libraryapp.BooksArray.books;

public class BookRow {

    String name;
    String image;

    public BookRow(String name, String image) {

        this.name=name;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

}