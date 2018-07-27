//package com.example.ezmilja.libraryapp;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.media.Image;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//
//import java.util.ArrayList;
//
//import static com.example.ezmilja.libraryapp.BooksArray.books;
//import static com.example.ezmilja.libraryapp.SplashScreen.j;
//
//public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ImageViewHolder> {
//
//    private LayoutInflater inflater;
//    private Book[] images;
//    Context context;
//    private ArrayList<String> myBookNames;
//
//    public  BookAdapter(Book[] books, Context context_, ArrayList myBookNames) {
//
//        this.images = books;
//        this.context = context_;
//        this.myBookNames =myBookNames;
//    }
//
//    @NonNull
//    @Override
//    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
//        return new ImageViewHolder(view);
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(ImageViewHolder holder, int position) {
//
//        Glide.with(this.context)
//                .load(books[position].imageAddress)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.getImage());
//
//        holder.BookDetails.setText( books[position].bookName +"\n \n"+ books[position].author);
//    }
//
//    @NonNull
//    public SearchAdapter.SearchViewHolder onCreateSearchViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        int k;
//        for(k = 0; k < j; k++) {
//            String nameArray = books[k].bookName;
//            myBookNames.add(nameArray);
//        }
//        View view = inflater.inflate(R.layout.custom_layout, parent, false);
//        return new SearchAdapter.SearchViewHolder(view);
//    }
//
//    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
//
//        holder.BookDetails.setText((CharSequence) myBookNames);
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return images.length;
//    }
//
//    public static class ImageViewHolder extends RecyclerView.ViewHolder{
//
//        ImageView BookImage;
//        TextView BookDetails;
//
//        ImageViewHolder(View itemView) {
//            super(itemView);
//            BookImage = itemView.findViewById(R.id.imageViewCustom);
//            BookDetails = itemView.findViewById(R.id.bookDetails);
//
//        }
//        public ImageView getImage(){ return this.BookImage;}
//    }
//}
