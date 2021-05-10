package com.android.fetchdataparsing.model;


import java.util.Comparator;

public class fetchItem {
    public int setNameNum;
    private int id;
    private int listId;
    private String name;
    int nameNum;

    public  fetchItem(){}

    public fetchItem(int id, int listId, String name, int nameNum) {
        this.id = id;
        this.listId = listId;
        this.name = name;
        this.nameNum = nameNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNameNum() {
        return nameNum;
    }

    public void setNameNum(int nameNum) {
        this.nameNum = nameNum;
    }



}