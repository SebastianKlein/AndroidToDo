package com.klein.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.klein.todo.adapter.UserManagementLVAdapter;
import com.klein.todo.database.DataSource;
import com.klein.todo.model.User;

import java.util.List;

/**
 * Created by Sebastian on 19.03.2016.
 */
public class UserManagementActivity extends AppCompatActivity {

    //Database
    DataSource dataSource;

    //Layout
    Button bAddUser;
    private ListView lvUserList;

    //Userlist
    private UserManagementLVAdapter lvAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        dataSource = new DataSource(this);

        //add user button
        bAddUser = (Button) findViewById(R.id.bAddUser);
        bAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        fillUserlist();
    }

    private void addUser() {
        Intent manageUser_intent = new Intent(this, AddUserActivity.class);
        startActivity(manageUser_intent);
    }

    private void fillUserlist(){
        dataSource.open();
        userList = dataSource.getAllNotesFromUserByUser();
        dataSource.close();

        lvAdapter = new UserManagementLVAdapter(this);
        lvUserList.setAdapter(lvAdapter);
    }

}
