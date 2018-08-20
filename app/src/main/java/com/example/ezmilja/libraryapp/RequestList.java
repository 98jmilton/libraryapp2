package com.example.ezmilja.libraryapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.ezmilja.libraryapp.SplashScreen.BookRef;

public class RequestList extends AppCompatActivity {

    private Button buttonRequest;
    private ListView listView;
    public static RequestBook requestBook;
    public static ArrayList<RequestBook> originalList=new ArrayList<RequestBook>();
    private SearchView searchView;
    private RequestList.CustomAdapter customAdapter;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String isbn = "Not found", bookName = "Not found", author = "Not found", imageAddress = "Not found", genre ="Not found";
    boolean isUpVoted;
    String[] emails;
    String votedby, curUser;
    int k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_list);
        sortlist(originalList);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        listView = findViewById(R.id.list_view);
        createButton();
        curUser =String.valueOf(user.getEmail());

        BookRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                k = 0;
                originalList.clear();
                int d;


                    for (DataSnapshot BookSnapshotB : dataSnapshot.child("/Requests/").getChildren()) {
                    k= (int) dataSnapshot.child("/Requests/").getChildrenCount();

                    String reqbook      = (String) BookSnapshotB.child("bookName").getValue();
                    String reqauthor    = (String) BookSnapshotB.child("bookAuthor").getValue();
                    String reqvotes     = (String) BookSnapshotB.child("votes").getValue();
                    String email        = (String) BookSnapshotB.child("email").getValue();
                    String votedby      = (String) BookSnapshotB.child("votedBy").getValue();

                    if(votedby!=null) {emails = votedby.split(",");}
                    else{votedby=""; emails[i]="";}
                    System.out.println("poo"+reqbook +reqauthor +reqvotes +email);
                    int votes = Integer.valueOf(reqvotes);
                    int w=0;
                    if(w<=email.length()){
                    if(emails[w].equals(curUser)){isUpVoted=true;}
                    else{isUpVoted=false;}w++;}
                    System.out.println(emails[i]+"AAAAAAAAAAAAAAAA"+String.valueOf(user.getEmail())+"aaaaaaaaaaaaaaaaa"+isUpVoted);

                    try{
                        if(reqbook!=null && reqauthor!=null && reqvotes!=null && email!=null)originalList.add(requestBook= new RequestBook(reqbook,reqauthor, email, votes,isUpVoted));
                        if(i==k-1)makeListView();
                    }
                    catch (ArrayIndexOutOfBoundsException e){

                        return;

                    }
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchView = findViewById(R.id.jeffdasearchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void sortlist(List list){
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o, Object t1) {
                RequestBook b1 = (RequestBook) o;
                RequestBook b2 = (RequestBook) t1;
                return b1.compareTo(b2);
            }
        });
    }
    private void makeListView(){

        listView = findViewById(R.id.leaderbd_list);

        customAdapter = new RequestList.CustomAdapter(RequestList.this, originalList);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Toast.makeText(RequestList.this, "Goodbye Dave! Hello Steve!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createButton(){

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");
        buttonRequest = findViewById(R.id.buttonRequest);
        buttonRequest.setTypeface(myTypeFace1);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequestDialog();
            }
        });
    }

    private void makeRequestDialog(){
        final Dialog dialog = new Dialog(RequestList.this);
        dialog.setContentView(R.layout.activity_request_book);
        dialog.show();

        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");

        Button btn_submitRequest =  dialog.findViewById(R.id.btn_submitrequest);
        Button btn_back = dialog.findViewById(R.id.btn_back);
        btn_submitRequest.setTypeface(myTypeFace1);
        btn_back.setTypeface(myTypeFace1);

        final EditText edt_name = dialog.findViewById(R.id.name);
        final EditText edt_author = dialog.findViewById(R.id.reason);

        btn_submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp_name = edt_name.getText().toString();
                String temp_author = edt_author.getText().toString();
                if (requestCheck(temp_name, temp_author, user.getEmail())) {
                    RequestBook temp = new RequestBook(temp_name, temp_author, user.getEmail(), 0,false);
                    originalList.add(temp);
                    makeListView();
                    dialog.dismiss();
                    BookRef.child("/Requests/").child(temp_name).child("bookName").setValue(temp_name);
                    BookRef.child("/Requests/").child(temp_name).child("bookAuthor").setValue(temp_author);
                    BookRef.child("/Requests/").child(temp_name).child("email").setValue(user.getEmail());
                    BookRef.child("/Requests/").child(temp_name).child("votes").setValue(String.valueOf(temp.getVote()));
                    BookRef.child("/Requests/").child(temp_name).child("votedBy").setValue(user.getEmail());
                }
                else {
                    Toast.makeText(RequestList.this, "Error: Please input correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private boolean requestCheck(String name, String author, String email){
        if (name.length() == 0 || author.length() == 0){
            return false;
        }
        if (email.toLowerCase().endsWith("@ericsson.com")){
            return true;
        }
        return false;
    }

    class CustomAdapter extends BaseAdapter implements Filterable {

        BookFilter bookFilter;
        Context context;
        List <RequestBook> showList;

        public CustomAdapter(Context context,List <RequestBook> items){
            this.context = context;
            this.showList = items;
            sortlist(showList);
        }

        private class ViewHolder{
            TextView bookName;
            TextView bookVote;
            ImageView image;
            boolean upVote;
            Button btn_more;
        }
        @Override
        public int getCount() {
            return showList.size();
        }

        @Override
        public Object getItem(int position) {
            return showList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return showList.get(position).hashCode();
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {


            View vi = view;
            final ViewHolder holder ;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final RequestBook myBook = showList.get(position);

            if(view==null){

                vi = inflater.inflate(R.layout.leaderboard_layout,null);
                holder = new ViewHolder();
                holder.bookName=  vi.findViewById(R.id.tbx_bookName);
                holder.bookVote = vi.findViewById(R.id.tbx_voteCount);
                holder.image =  vi.findViewById(R.id.ibnt_vote);
                holder.image.setTag(position);
                holder.btn_more =  vi.findViewById(R.id.btn_more);
                vi.setTag(holder);
            }

            else{
                holder = (ViewHolder) vi.getTag();
            }

            holder.bookName.setText(myBook.getBookName());
            holder.bookVote.setText(myBook.getVote()+ "");

            if(myBook.getisUpVoted()){
                holder.image.setImageResource(R.drawable.steve);
            }
            else{
                holder.image.setImageResource(R.drawable.dave);
            }

            //send help pls

            holder.btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(RequestList.this)
                            .setTitle(myBook.getBookName())
                            .setMessage("Author: " + "\n" + myBook.getAuthor() + "\n" + "\nRequested by "+myBook.getEmail()).setNeutralButton("Close", null).show();
                }
            });



            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tempVotedby="";
                    if (myBook.getisUpVoted()) {
                        myBook.setisUpVoted(false);
                        holder.image.setImageResource(R.drawable.dave);
                        myBook.addVote(-1);

                        int r = 0;
                        if(r<=emails.length) {
                            if (emails[r] != String.valueOf(user.getEmail())) {
                                System.out.println("AAAAAAAAAAAAAAAAAAAAAA  EMAIL FOUND");
                            } else {
                                if (votedby != null) tempVotedby = votedby +",";
                                else tempVotedby = tempVotedby + votedby +",";
                            }
                        }
                        holder.bookVote.setText(myBook.getVote()+ "");
                        BookRef.child("/Requests/").child(myBook.getBookName()).child("votedBy").setValue(tempVotedby);

                    }
                    else {
                        myBook.setisUpVoted(true);
                        holder.image.setImageResource(R.drawable.steve);
                        myBook.addVote(1);

                        int r = 0;
                        if(r<= emails.length){
                            if(emails[r]!=String.valueOf(user.getEmail())){
                                if(votedby!=null)tempVotedby=votedby+String.valueOf(user.getEmail())+",";
                                else tempVotedby = String.valueOf(user.getEmail())+",";
                            }
                            r++;
                        }

                        holder.bookVote.setText( myBook.getVote() + "");
                        BookRef.child("/Requests/").child(myBook.getBookName()).child("votedBy").setValue(tempVotedby);

                    }

                }
            });

            return vi;
        }


        @Override
        public Filter getFilter() {
            if (bookFilter == null)
                bookFilter = new BookFilter();
            return bookFilter;
        }

        class BookFilter extends Filter{

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // We implement here the filter logic
                if (constraint == null || constraint.length() < 1) {
                    // No filter implemented we return all the list
                    showList = originalList;
                    results.values = showList;
                    results.count = showList.size();
                }
                else {
                    // We perform filtering operation
                    List<RequestBook> nBookList = new ArrayList<RequestBook>();

                    for (RequestBook b : originalList) {
                        if (b.getBookName().toUpperCase()
                                .contains(constraint.toString().toUpperCase())) {
                            nBookList.add(b);
                        }
                        else if (b.getAuthor().toUpperCase()
                                .contains(constraint.toString().toUpperCase())) {
                            nBookList.add(b);

                        }
                    }
                    showList = nBookList;
                    results.values = nBookList;
                    results.count = nBookList.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                // Now we have to inform the adapter about the new list filtered
                if(results.count ==0) {
                    notifyDataSetInvalidated();
                }
                else
                {
                    showList = (List<RequestBook>) results.values;
                    notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent intent = new Intent( this, ContentsActivity.class);
        startActivity(intent);
    }
}