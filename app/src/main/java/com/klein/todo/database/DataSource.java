package com.klein.todo.database;

import com.klein.todo.model.Note;
import com.klein.todo.model.User;


import android.content.ContentValues;
import android.content.Context;
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
        List<User> userList = new ArrayList<User>();
        Cursor cursor = database.query("USER", null, null, null, null, null, null, null);
        cursor.moveToFirst();

        //User available?
        if(cursor.getCount() == 0) {
            cursor.close();
            return userList;
        }

        //Get user
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
                cursor.getString(3),                //Timestampe
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
        values.put("USERID", entry.getUserId());

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

    public List<Note> getAllNotesFromUserByUserId(int id){
        List<Note> noteList = new ArrayList<Note>();
        //TODO


        return noteList;
    }
}
