package com.example.ezmilja.libraryapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import static com.example.ezmilja.libraryapp.SplashScreen.j;


public class BooksArray {

    static int i = (int) j;
    public static Book[] books = new Book[i];
    public static ArrayList<Book> createBooksList(int numBooks) {
        ArrayList<Book> booklist = new ArrayList<>();

        for (int i = 0; i < books.length; i++) {
            booklist.add(new Book(books[i].isbn, books[i].author, books[i].imageAddress, books[i].description, books[i].bookName, books[i].publisher, books[i].max_copies, books[i].numberOfCopies, books[i].page, books[i].rating, books[i].num_rating));
        }
        return booklist;
    }
}
