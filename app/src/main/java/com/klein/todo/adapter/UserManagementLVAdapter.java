package com.klein.todo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.klein.todo.R;
import com.klein.todo.model.User;

import java.util.List;

/**
 * Created by Sebastian on 19.03.2016.
 */
public class UserManagementLVAdapter extends ArrayAdapter<User> {

    private Activity context;
    private static int resourceID = R.layout.usermanagement_entry;

    private List<User> userList;

    public UserManagementLVAdapter(Activity context){
            super(context, resourceID);

            }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        return convertView;
    }

    @Override
    public int getCount(){
            return userList.size();
            }

    @Override
    public User getItem(int position){
            return userList.get(position);
            }

}

