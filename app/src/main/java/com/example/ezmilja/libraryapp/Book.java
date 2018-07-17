package com.example.ezmilja.libraryapp;

public class Book {
    public  int isbn;
    public  String bookName;
    public  String imageAddress;
    public  String author;
    public  String description;
    public  String page;
    public  String publisher;
    public double rating;
    public int num_rating;
    public int numberOfCopys;
    public int MAX_COPYS;


    public Book(int isbn, String bookName, String imageAddress, String author, String description,
                String page, String publisher, double rating, int numberOfCopys, int MAX_COPYS, int num_rating){

        this.isbn = isbn;
        this.bookName =bookName;
        this.imageAddress = imageAddress;
        this.author = author;
        this.description = description;
        this.page = page;
        this.publisher = publisher;
        this.rating = rating;
        this.MAX_COPYS = MAX_COPYS;
        this.num_rating = num_rating;
        this.numberOfCopys = numberOfCopys;
    }
    public int getIsbn() {return isbn;}

    public String getBookName(){ return bookName;}

    public String getAuthor(){ return author;}

    public String getImageId(){return imageAddress;}

    public String getDescription(){return  description;}

    public String getPage(){return  page;}

    public String getPublisher(){return  publisher;}

    public  double getRating() {return rating;}


    public int getNumberOfCopys(){return numberOfCopys;}

    public int getMAX_COPYS(){return MAX_COPYS;}

    public int getNum_rating(){return num_rating;}

    public void addToNumberOfCopys(int added){
        numberOfCopys = numberOfCopys + added;
    }
}
