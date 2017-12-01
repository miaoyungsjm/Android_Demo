package com.example.ggz.litepaltest;

import org.litepal.crud.DataSupport;

/**
 * Created by ggz on 2017/11/23.
 */

public class Book extends DataSupport{

    private int id;
    private String name;
    private String author;
    private double price;
//    private int page;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

//    public int getPage(){
//        return page;
//    }
//    public void setPage(int page){
//        this.page = page;
//    }
}
