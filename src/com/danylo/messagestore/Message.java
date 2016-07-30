package com.danylo.messagestore;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private int id;
    private Date date;
    private String content;

    public Message(int id, Date date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
