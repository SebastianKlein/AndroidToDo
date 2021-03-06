package com.klein.todo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.klein.todo.Utils.AppConstants;

/**
 * Created by Sebastian on 16.03.2016.
 */
public class User implements Parcelable {

    private long id;
    private String name;
    private String password;
    private boolean showDone;
    private int sort;

    public User(long id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
        this.showDone = true;
        this.sort = AppConstants.ASC_DATE;
    }

    public User(long id, String name, String password, boolean showDone, int sort){
        this.id = id;
        this.name = name;
        this.password = password;
        this.showDone = showDone;
        this.sort = sort;
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

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public boolean getShowDone(){
        return showDone;
    }

    public void setShowDone(boolean showDone){
        this.showDone = showDone;
    }

    public void swapShowDone(){
        if(showDone == true)
            showDone = false;
        else
            showDone = true;
    }

    public int getSort(){
        return sort;
    }

    public void setSort(int sort){
        this.sort = sort;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeString(this.name);
        out.writeString(this.password);
        out.writeByte((byte) (this.showDone ? 1 : 0));
        out.writeInt(this.sort);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.password = in.readString();
        this.showDone = in.readByte() != 0;
        this.sort = in.readInt();
    }
}
