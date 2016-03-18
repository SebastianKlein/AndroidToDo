package com.klein.todo.database;

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
                            cursor.getString(2));   //Password
        return entry;
    }

    public void insertUser(User entry){
        ContentValues values = new ContentValues();
        values.put("NAME", entry.getName());
        values.put("PASSWORD", entry.getPassword());

         database.insert("USER", null, values);
    }

    public List<User> getAllUser(){
        Cursor cursor = database.query("USER", null, null, null, null, null, null, null);
        cursor.moveToFirst();

        //User available?
        if(cursor.getCount() == 0) {
            cursor.close();
            return null;
        }

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
                cursor.getLong(6));                 //Done
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

    public void deleteNoteById(int id){
        database.delete("NOTE", "ID=" + id, null);
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

    public List<Note> getAllNotesFromUserByUserId(long userId){
        Cursor cursor = database.query("NOTE", null, "USER_ID = '" + userId + "'", null, null, null, null );
        cursor.moveToFirst();

        //Entries available?
       /* if(cursor.getCount() == 0) {
            cursor.close();
        }*/

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
