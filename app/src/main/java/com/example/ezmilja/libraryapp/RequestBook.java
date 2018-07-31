package com.example.ezmilja.libraryapp;

import android.support.annotation.NonNull;

public class RequestBook implements Comparable{

    private long vote;
    private boolean isUpVoted;
    private final String email;

    public RequestBook( final String email, final long vote){

        this.email = email;
        this.vote = vote;
        this.isUpVoted = false;
    }

    public String getEmail(){return email;}
    public long getVote(){return vote;}

    public void addVote(int add){vote += add;}
    public boolean getisUpVoted(){return isUpVoted;}

    public void setisUpVoted(boolean isUpVoted){this.isUpVoted = isUpVoted;}

    @Override
    public int compareTo(@NonNull Object o) {
        RequestBook b = (RequestBook) o;
        if (b.getVote() > vote) {
            return 1;
        }
        else if (b.getVote() < vote){
            return -1;
        }
        return 0;
    }
}


