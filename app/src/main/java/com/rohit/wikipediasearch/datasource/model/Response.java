package com.rohit.wikipediasearch.datasource.model;


import com.google.gson.annotations.SerializedName;

public class Response {

    public String topicOffSetKey = "";

    @SerializedName("continue")
    public Paginate paginate = new Paginate();

    public Query query = new Query();


}