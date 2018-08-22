package com.example.ezmilja.libraryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.example.ezmilja.libraryapp.ContentsActivity.books;

public class Book {

    public String isbnX;
    public String bookNameX;
    public String imageAddressX;
    public String authorX;
    public String genreX;

    Book(String isbn,String bookName, String author, String imageAddress,String genre) {

        this.isbnX=isbn;
        this.bookNameX =bookName;
        this.imageAddressX = imageAddress;
        this.authorX = author;
        this.genreX = genre;
    }

    public String getName() {return bookNameX;}

    public String getAuthor() {return authorX;}

    public String getGenre() {return genreX;}

    public String getIsbn() {return isbnX;}
}