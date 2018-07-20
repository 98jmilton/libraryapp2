package com.example.ezmilja.libraryapp;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ImageViewHolder> {

    private int[] images;

    public  BookAdapter(int[] images){

        this.images = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        int image_id = images[position];
        holder.BookImage.setImageResource(image_id);
        holder.BookDetails.setText("Author:"+position);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

    ImageView BookImage;
    TextView BookDetails;

        public ImageViewHolder(View itemView) {
            super(itemView);
            BookImage = itemView.findViewById(R.id.imageViewCustom);
            BookDetails = itemView.findViewById(R.id.bookDetails);
        }
    }
}
