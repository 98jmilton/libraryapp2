package com.example.ezmilja.libraryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import static com.example.ezmilja.libraryapp.BooksArray.books;
import static com.example.ezmilja.libraryapp.BooksArray.i;
import static com.example.ezmilja.libraryapp.SplashScreen.j;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> myBookNames;

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public SearchViewHolder(View itemView) {
            super(itemView);


        }
    }

    public SearchAdapter(Context context,ArrayList myBookNames) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.myBookNames =myBookNames;
    }



    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int k;
        for(k =0; k < j; k++) {
            String nameArray = books[k].bookName;
            myBookNames.add(nameArray);
        }

        View view = inflater.inflate(R.layout.custom_layout, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        //holder.serial_number.setText(<your string array[position]>);

    }

    @Override
    public int getItemCount() {
        return i;
    }
}