package com.example.ezmilja.libraryapp;

import java.net.URL;

public class Book {
    public  int isbn;
    public  String bookName;
    URL  imageAddress;
    public  String author;
    public  String description;
    public  int page;
    public  String publisher;
    public double rating;
    public int num_rating;
    public int numberOfCopys;
    public int max_copys;


    public Book(int isbn, String bookName, URL imageAddress, String author, String description,
                int page, String publisher, double rating, int numberOfCopys, int max_copys, int num_rating){

        this.isbn = isbn;
        this.bookName =bookName;
        this.imageAddress = imageAddress;
        this.author = author;
        this.description = description;
        this.page = page;
        this.publisher = publisher;
        this.rating = rating;
        this.max_copys = max_copys;
        this.num_rating = num_rating;
        this.numberOfCopys = numberOfCopys;
    }
    public int getIsbn() {return isbn;}

    public String getBookName(){return bookName;}

    public String getAuthor(){return author;}

    public URL getImageId(){return imageAddress;}

    public String getDescription(){return  description;}

    public int getPage(){return  page;}

    public String getPublisher(){return  publisher;}

    public  double getRating() {return rating;}


    public int getNumberOfCopys(){return numberOfCopys;}

    public int getMax_copys(){return max_copys;}

    public int getNum_rating(){return num_rating;}

    public void addToNumberOfCopys(int added){
        numberOfCopys = numberOfCopys + added;
    }
}
