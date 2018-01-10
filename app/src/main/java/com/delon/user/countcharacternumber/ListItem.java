package com.delon.user.countcharacternumber;

/**
 * Created by User on 2016/05/31.
 */

public class ListItem {

    private int id;
    private String title;
    private String contents;
    private int number;

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
