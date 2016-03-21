package com.klein.todo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.klein.todo.R;
import com.klein.todo.UserManagementActivity;
import com.klein.todo.database.DataSource;
import com.klein.todo.model.User;

import java.util.List;

/**
 * Created by Sebastian on 19.03.2016.
 */
public class UserManagementLVAdapter extends ArrayAdapter<User> {

    private Activity context;
    private static int resourceID = R.layout.usermanagement_entry;
    private DataSource dataSource;

    private List<User> userList;

    public UserManagementLVAdapter(Activity context, List<User> userList){
        super(context, resourceID);

        this.context = context;
        this.userList = userList;
        this.dataSource = new DataSource(context);
        }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent){
        if(convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(resourceID, parent, false);

        final User rowItem = userList.get(position);

        //Name
        TextView tvName = (TextView) convertView.findViewById(R.id.tvUserName);
        tvName.setText(rowItem.getName());

        //Delete Button
        Button bDelete = (Button) convertView.findViewById(R.id.bDeleteUser);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.open();
                dataSource.deleteUser(rowItem);
                dataSource.close();

                ((UserManagementActivity)context).notifyDataSetChanged();
            }
        });
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

