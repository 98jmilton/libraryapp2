package com.example.ezmilja.libraryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.ezmilja.libraryapp.ContentsActivity.books;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import static com.example.ezmilja.libraryapp.ContentsActivity.currentIsbn;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ImageViewHolder> {

    private Context context;

    BookAdapter(Context context_) {

        this.context = context_;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder,final int position) {

        try {
            if (books[position].imageAddressX != null) {
                Glide.with(this.context)
                        .load(books[position].imageAddressX)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.getImage());

                holder.BookDetails.setText(books[position].bookNameX+ "\n" + books[position].authorX + "\n" + books[position].genreX);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentIsbn=books[position].isbnX;
                        Context context = view.getContext();
                        Intent intent = new Intent(context, BookDetailsPage.class);
                        context.startActivity(intent);
                    }
                });

            }

        }
         catch (NullPointerException e) {
             e.printStackTrace();
             System.out.println("Null Error = " + e );
         }

    }

    private void toDetails() {
        toDetails();
    }

    @Override
    public int getItemCount() {
        return books.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView BookImage;
        TextView BookDetails;

        ImageViewHolder(View itemView) {
            super(itemView);
            BookImage = itemView.findViewById(R.id.imageViewCustom);
            BookDetails = itemView.findViewById(R.id.bookDetails);
        }

        public ImageView getImage(){
            return this.BookImage;
        }
    }
}