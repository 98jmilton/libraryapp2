package com.example.ezmilja.libraryapp;

import android.provider.Settings;

import java.net.URL;

public class Book {
    public String isbn;
    public String bookName;
    public String  imageAddress;
    public String author;
    public String description;
    public String page;
    public String publisher;
    public String rating;
    public String num_rating;
    public String numberOfCopies;
    public String max_copies;



    public Book(String isbn, String bookName, String imageAddress, String author, String description,
                String page, String publisher, String rating, String numberOfCopys, String max_copys, String num_rating){

        this.isbn = isbn;
        this.bookName =bookName;
        this.imageAddress = imageAddress;
        this.author = author;
        this.description = description;
        this.page = page;
        this.publisher = publisher;
        this.rating = rating;
        this.max_copies = max_copys;
        this.num_rating = num_rating;
        this.numberOfCopies = numberOfCopys;

    }

    public String getIsbn() {return isbn;}

    public String getBookName(){return bookName;}

    public String getAuthor(){return author;}

    public String getImageId(){return imageAddress;}

    public String getDescription(){return  description;}

    public String getPage(){return  page;}

    public String getPublisher(){return  publisher;}

    public String getRating() {return rating;}

    public String getNumberOfCopys(){return numberOfCopies;}

    public String getMax_copys(){return max_copies;}

    public String getNum_rating(){return num_rating;}

    public void addToNumberOfCopys(int added){
        numberOfCopies = numberOfCopies + added;
    }

    public void setBookInfo(String isbn, String bookName, String imageAddress, String author, String description,
                            String page, String publisher, String rating, String numberOfCopies, String max_copies, String num_rating){


    }


}