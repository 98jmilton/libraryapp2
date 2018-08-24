package com.example.ezmilja.libraryapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.ezmilja.libraryapp.ContentsActivity.listcurrentPage;
import static com.example.ezmilja.libraryapp.ContentsActivity.requestcurrentPage;
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
    String bookName;
    boolean isUpVoted;
    String[] emails;
    String votedby;
    String curUser;
    int k;
    Typeface myTypeFace1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        sortlist(originalList);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        listView = findViewById(R.id.list_view);
        createButton();
        curUser =String.valueOf(user.getEmail());
        BookRef.child("/Requests/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                k = 0;
                int i = 0;

                if(requestcurrentPage){

                    originalList.clear();
                    for (DataSnapshot BookSnapshotB : dataSnapshot.getChildren()) {
                        k= (int) dataSnapshot.getChildrenCount();
                        String reqbook      = (String) BookSnapshotB.child("bookName").getValue();
                        String reqauthor    = (String) BookSnapshotB.child("bookAuthor").getValue();
                        String reqvotes     = (String) BookSnapshotB.child("votes").getValue();
                        String email        = (String) BookSnapshotB.child("email").getValue();
                        String votedby      = (String) BookSnapshotB.child("votedBy").getValue();

                        if(votedby!=null) {emails = votedby.split(",");}

                        //Prevents crashes if database requests has been deleted
                        if (reqvotes==null) {reqvotes="0";}
                        if (emails==null) {
                            emails = new String[1];
                            emails[0] = curUser;
                        }

                        int votes = Integer.valueOf(reqvotes);

                        //Checks if user has already voted or originally made the request on create
                        for (String email1 : emails) {
                            boolean found = Arrays.asList(emails).contains(curUser);
                            if (found) {
                                if (email1.equals(curUser)) {isUpVoted = true;}
                            }
                            else {isUpVoted = false;}
                            if (curUser.equals(email) && !isUpVoted) {
                                isUpVoted = true;
                            }
                        }
                        //Add requests to list
                        try{
                            if(reqbook!=null && reqauthor!=null && reqvotes!=null && email!=null)originalList.add(requestBook= new RequestBook(reqbook,reqauthor, email, votes, votedby,isUpVoted));
                            if(i==k-1){
                                makeListView();
                            }
                            i++;                    }
                        catch (ArrayIndexOutOfBoundsException e){
                            return;
                        }

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        searchView = findViewById(R.id.request_search);
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
    //Order request list based on votes
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
    //Add new request
    private void makeRequestDialog(){

        final Dialog dialog = new Dialog(RequestList.this);
        dialog.setContentView(R.layout.activity_request_book);
        dialog.show();
        Typeface myTypeFace1 = Typeface.createFromAsset(getAssets(),"yourfont.ttf");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));;
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

                //Firebase path does not allow these characters
                if (temp_name.contains(".") || temp_name.contains("#") || temp_name.contains("$") || temp_name.contains("[") || temp_name.contains("]")) {
                    Toast.makeText(RequestList.this,"Book name cannot contain '.', '#', '$', '[', or ']'",Toast.LENGTH_LONG).show();
                }

                else if (requestCheck(temp_name, temp_author, user.getEmail())) {
                    RequestBook temp = new RequestBook(temp_name, temp_author, user.getEmail(), 0, user.getEmail(),false);
                    originalList.add(temp);
                    makeListView();
                    dialog.dismiss();
                    BookRef.child("/Requests/").child(temp_name).child("bookName").setValue(temp_name);
                    BookRef.child("/Requests/").child(temp_name).child("bookAuthor").setValue(temp_author);
                    BookRef.child("/Requests/").child(temp_name).child("email").setValue(user.getEmail());
                    BookRef.child("/Requests/").child(temp_name).child("votes").setValue("1");
                    BookRef.child("/Requests/").child(temp_name).child("votedBy").setValue(user.getEmail()+",");
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
        CustomAdapter(Context context,List <RequestBook> items){
            this.context = context;
            this.showList = items;
            sortlist(showList);
        }
        private class ViewHolder{
            TextView bookName;
            TextView bookVote;
            ImageView image;
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
            else {
                holder = (ViewHolder) vi.getTag();
            }
            holder.bookName.setText(myBook.getBookName());
            holder.bookVote.setText(myBook.getVote()+ "");

            if (curUser.equals(myBook.getEmail())) {
                holder.image.setImageResource(R.drawable.delete);
            }

            else if(myBook.getisUpVoted()){
                holder.image.setImageResource(R.drawable.grey_thumb);
            }
            else {
                holder.image.setImageResource(R.drawable.white_thumb);
            }

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
                    votedby = myBook.getVotedby();
                    String tempVotedby = "";

                    //Deleting the user's own request
                    if (curUser.equals(myBook.getEmail())) {
                        final String requestName = myBook.getBookName();

                        final Dialog deletedialog = new Dialog(RequestList.this);
                        deletedialog.setContentView(R.layout.deleterequest);
                        deletedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        TextView title = deletedialog.findViewById(R.id.title);
                        title.setTypeface(myTypeFace1);
                        title.setText("Are you sure you want to delete this request from the database?");

                        Button yes= deletedialog.findViewById(R.id.yes);
                        Button no = deletedialog.findViewById(R.id.no);

                        yes.setTypeface(myTypeFace1);
                        no.setTypeface(myTypeFace1);
                        deletedialog.show();

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String request = requestName;
                                BookRef.child("/Requests/").child(request).removeValue();
                                deletedialog.dismiss();
                                Toast.makeText(RequestList.this,"Request Deleted",Toast.LENGTH_LONG).show();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                deletedialog.dismiss();
                            }
                        });
                    }

                    //Voting for another user's request
                    if (!curUser.equals(myBook.getEmail())) {
                        //Remove vote
                        if (myBook.getisUpVoted()) {
                            myBook.setisUpVoted(false);
                            holder.image.setImageResource(R.drawable.white_thumb);
                            myBook.addVote(-1);

                            tempVotedby = votedby.replace(curUser + ",", "");

                            holder.bookVote.setText(myBook.getVote() + "");
                            BookRef.child("/Requests/").child(myBook.getBookName()).child("votedBy").setValue(tempVotedby);
                        }
                        //Add vote
                        else {
                            myBook.setisUpVoted(true);
                            holder.image.setImageResource(R.drawable.grey_thumb);
                            myBook.addVote(1);

                            if (votedby == null) {
                                votedby = "";
                            }

                            tempVotedby = votedby + curUser + ",";

                            holder.bookVote.setText(myBook.getVote() + "");
                            BookRef.child("/Requests/").child(myBook.getBookName()).child("votedBy").setValue(tempVotedby);
                        }
                    }
                }
            });
            return vi;
        }

        //Search Filter Method
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
        requestcurrentPage = false;
        Intent intent = new Intent( this, ContentsActivity.class);
        startActivity(intent);
    }
}