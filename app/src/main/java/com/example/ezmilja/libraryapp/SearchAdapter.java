package com.example.ezmilja.libraryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<BookRow> bookList;
    private List<BookRow> bookListFiltered;
    private BooksAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView thumbnail;

         MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.bookDetails);
            thumbnail = view.findViewById(R.id.imageViewCustom);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected book row in callback
                    listener.onBookSelected(bookListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    SearchAdapter(Context context, List<BookRow> bookList, BooksAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.bookList = bookList;
        this.bookListFiltered = bookList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BookRow bookRow = bookListFiltered.get(position);

        holder.name.setText(bookRow.getName());

        Glide.with(context)
                .load(bookRow.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return bookListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    bookListFiltered = bookList;
                } else {
                    List<BookRow> filteredList = new ArrayList<>();
                    for (BookRow row : bookList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    bookListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = bookListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
               bookListFiltered = (ArrayList<BookRow>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface BooksAdapterListener {
        void onBookSelected(BookRow bookRow);
    }
}