package com.klein.todo.database;

import com.klein.todo.Utils.AppConstants;
import com.klein.todo.model.Note;
import com.klein.todo.model.User;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 16.03.2016.
 */
public class DataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    public Context context;


    public DataSource(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean isOpen(){
        return database.isOpen();
    }

    ///////////
    //  USER
    ///////////
    private User userCurserToEntry(Cursor cursor){
        User entry = new User(cursor.getLong(0),    //ID
                            cursor.getString(1),    //Name
                            cursor.getString(2),    //Password
                            (cursor.getInt(3) == 1),//ShowDone
                            cursor.getInt((4)));    //sort
        return entry;
    }

    public void insertUser(User entry){
        ContentValues values = new ContentValues();
        values.put("NAME", entry.getName());
        values.put("PASSWORD", entry.getPassword());
        values.put("SHOWDONE", entry.getShowDone());
        values.put("SORT", entry.getSort());

         database.insert("USER", null, values);
    }

    public void updateUser(User entry){
        ContentValues values = new ContentValues();
        values.put("NAME", entry.getName());
        values.put("PASSWORD", entry.getPassword());
        values.put("SHOWDONE", entry.getShowDone());
        values.put("SORT", entry.getSort());

        database.update("USER", values, "ID=" + entry.getId(), null);
    }

    public void deleteUser(User entry){
        database.delete("USER", "ID=" + entry.getId(), null);
    }

    public List<User> getAllUser(){
        Cursor cursor = database.query("USER", null, null, null, null, null, null);
        cursor.moveToFirst();

        //Get user
        List<User> userList = new ArrayList<User>();
        while (!cursor.isAfterLast()){
            User entry = userCurserToEntry(cursor);
            userList.add(entry);
            cursor.moveToNext();
        }
        return userList;
    }

    public List<User> getAllUserNoAdmin(){
        Cursor cursor = database.query("USER", null, "ID!=1", null, null, null, null);
        cursor.moveToFirst();

        //Get user
        List<User> userList = new ArrayList<User>();
        while (!cursor.isAfterLast()){
            User entry = userCurserToEntry(cursor);
            userList.add(entry);
            cursor.moveToNext();
        }
        return userList;
    }

    ///////////
    //  NOTE
    ///////////
    private Note noteCurserToEntry(Cursor cursor){
        Note entry = new Note(cursor.getLong(0),    //ID
                cursor.getString(1),                //Name
                cursor.getString(2),                //Description
                cursor.getString(3),                //Timestamp
                (cursor.getInt(4) == 1),            //Important
                (cursor.getInt(5) == 1),            //Done
                cursor.getLong(6));                 //UserID
        return entry;
    }

    public void insertNote(Note entry){
        ContentValues values = new ContentValues();
        values.put("NAME", entry.getName());
        values.put("DESCRIPTION", entry.getDescription());
        values.put("TIMESTAMP", entry.getTimestamp());
        values.put("IMPORTANT", entry.getImportant());
        values.put("DONE", entry.getDone());
        values.put("USER_ID", entry.getUserId());

        database.insert("NOTE", null, values);
    }

    public void deleteNote(Note entry){
        database.delete("NOTE", "ID=" + entry.getId(), null);
    }

    public void deleteAllNotesFromUser(User currentUser){
        database.delete("NOTE", "USER_ID=" + currentUser.getId(), null);
    }

    public void updateNote(Note entry){
        ContentValues values = new ContentValues();
        values.put("NAME", entry.getName());
        values.put("DESCRIPTION", entry.getDescription());
        values.put("TIMESTAMP", entry.getTimestamp());
        values.put("IMPORTANT", entry.getImportant());
        values.put("DONE", entry.getDone());

        database.update("NOTE", values, "ID=" + entry.getId(), null);
    }

    public List<Note> getAllNotesFromUserByUser(User currentUser){
        String whereStatment = null;
        String orderStatment = null;


        if(currentUser.getShowDone() == false) {
            whereStatment = "USER_ID = '" + currentUser.getId() + "'" + " AND DONE = '0'";
        }

        switch(currentUser.getSort()){
            case AppConstants.ASC_DATE:
                orderStatment = "TIMESTAMP ASC";
                break;

            case AppConstants.ASC_IMPORTANT:
                orderStatment = "IMPORTANT DESC, TIMESTAMP ASC";
                break;
        }

        Cursor cursor = database.query("NOTE", null, whereStatment , null, null, null, orderStatment );


        cursor.moveToFirst();


        //Get entries
        List<Note> noteList = new ArrayList<Note>();
        while (!cursor.isAfterLast()){
            Note entry = noteCurserToEntry(cursor);
            noteList.add(entry);
            cursor.moveToNext();
        }
        return noteList;
    }

    public List<Note> getAllNotesFromUserByUserIdWithoutDone(User currentUser){
        String whereStatment = "USER_ID = '" + currentUser.getId() + "'" + " AND "
                                + "DONE = '0'";

        Cursor cursor = database.query("NOTE", null, whereStatment , null, null, null, null );
        cursor.moveToFirst();

        //Get entries
        List<Note> noteList = new ArrayList<Note>();
        while (!cursor.isAfterLast()){
            Note entry = noteCurserToEntry(cursor);
            noteList.add(entry);
            cursor.moveToNext();
        }
        return noteList;
    }
}
