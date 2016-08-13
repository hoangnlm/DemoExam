package vn.t3h.demoexam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Email {
    @SerializedName("Id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("date")
    @Expose
    private long date;

    public final static String TABLE_NAME = "Email";
    public final static String COL_ID = "id";
    public final static String COL_TITLE = "title";
    public final static String COL_DESCRIPTION= "description";
    public final static String COL_DATE = "date";

    public final static String CREATE_TABLE = "create table if not exists " +TABLE_NAME+"("
            + COL_ID + " integer primary key, "
            + COL_TITLE  + " text,"
            + COL_DESCRIPTION + " text,"
            + COL_DATE + " interger)";
    public final static String DROP_TABLE = "drop table if exists " + TABLE_NAME;

    public Email() {
    }

    public Email(int id, String title, String description, long date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}