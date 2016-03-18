package com.klein.todo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sebastian on 16.03.2016.
 */
public class Note implements Parcelable {
    private long id;
    private String name;
    private String description;
    private String timestamp;
    private boolean important;
    private boolean done;
    private long userId;

    public Note(long id, String name, String description, String timestamp, boolean important, boolean done, long userId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.important = important;
        this.done = done;
        this.userId = userId;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getTimestamp(){
        return this.timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

    public boolean getImportant(){
        return this.important;
    }

    public void setImportant(boolean important){
        this.important = important;
    }

    public boolean getDone(){
        return this.done;
    }

    public void setDone(boolean done){
        this.done = done;
    }

    public void swapDone(){
        if(done == true)
            done = false;
        else
            done = true;
    }

    public long getUserId(){
        return this.userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeString(this.name);
        out.writeString(this.description);
        out.writeString(this.timestamp);
        out.writeByte((byte) (this.important ? 1 : 0));
        out.writeByte((byte) (this.done ? 1 : 0));
        out.writeLong(this.userId);
    }

    public static final Parcelable.Creator<Note> CREATOR
            = new Parcelable.Creator<Note>() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    private Note(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
        this.timestamp = in.readString();
        this.important = in.readByte() != 0;
        this.done = in.readByte() != 0;
        this.userId = in.readLong();
    }

}
