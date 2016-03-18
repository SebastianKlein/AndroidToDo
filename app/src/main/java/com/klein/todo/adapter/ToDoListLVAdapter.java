package com.klein.todo.adapter;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.klein.todo.AddNoteActivity;
import com.klein.todo.R;
import com.klein.todo.database.DataSource;
import com.klein.todo.model.Note;

import java.util.List;

/**
 * Created by Sebastian on 17.03.2016.
 */
public class ToDoListLVAdapter extends ArrayAdapter<Note> {

    private Context parent;
    private static int resourceID = R.layout.todo_entry;
    private DataSource dataSource;

    private List<Note> toDoList;

    public ToDoListLVAdapter(Activity context,  List<Note> toDoList){
        super(context, resourceID);

        this.parent = context;
        this.toDoList = toDoList;
        this.dataSource = new DataSource(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Note rowItem = toDoList.get(position);

        if(convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(resourceID, parent, false);

        //Change Backgroundcolor / Check Timestamp
        /*(System.currentTimeMillis() > Long.valueOf(rowItem.getTimestamp()) ) {
            convertView.setBackgroundColor(0xFF00FF00);
        }*/

        //Name
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(rowItem.getName());

        //Checkbox
        CheckBox cbDone = (CheckBox) convertView.findViewById(R.id.cbDone);
        if(rowItem.getDone() == true) {
            cbDone.setChecked(true);
        }
        cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowItem.swapDone();
                dataSource.open();
                dataSource.updateNote(rowItem);
                dataSource.close();
            }
        });

        //Important
        if(rowItem.getImportant() == true) {
            ImageView ivImportant = (ImageView) convertView.findViewById(R.id.iVImportant);
            ivImportant.setImageResource(R.drawable.important);
        }

        //Click on rowItem
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO edit note öffnen
            }
        });


        //Long Click on rowItem
        convertView.setOnLongClickListener( new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
               //TODO
                return false;
            }
        });

        return convertView;
    }

    @Override
    public int getCount(){
        return toDoList.size();
    }

    @Override
    public Note getItem(int position){
        return toDoList.get(position);
    }
}