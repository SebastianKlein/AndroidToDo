package com.klein.todo.model;

/**
 * Created by Sebastian on 16.03.2016.
 */
public class Note {
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
        this.description = timestamp;
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

    public long getUserId(){
        return this.userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }
}
