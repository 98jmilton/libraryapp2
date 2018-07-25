package com.example.ezmilja.libraryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import static com.example.ezmilja.libraryapp.BooksArray.books;
import static com.example.ezmilja.libraryapp.BooksArray.i;
import static com.example.ezmilja.libraryapp.SplashScreen.j;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> myBookNames;

     static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView BookDetails;

        SearchViewHolder(View itemView) {
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
        for(k = 0; k < j; k++) {
            String nameArray = books[k].bookName;
            myBookNames.add(nameArray);
        }
        View view = inflater.inflate(R.layout.custom_layout, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        holder.BookDetails.setText((CharSequence) myBookNames);

    }

    @Override
    public int getItemCount() {
        return i;
    }
}