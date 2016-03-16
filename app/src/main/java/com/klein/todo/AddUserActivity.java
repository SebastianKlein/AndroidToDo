package com.klein.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.klein.todo.database.DataSource;
import com.klein.todo.model.User;

/**
 * Created by Sebastian on 16.03.2016.
 */
public class AddUserActivity extends AppCompatActivity{

    private DataSource dataSource;
    private EditText etName;
    private EditText etPassword;
    private EditText etPasswordVerify;
    private Button bCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        dataSource = new DataSource(this);


        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordVerify = (EditText) findViewById(R.id.etPasswordVerify);
        bCreate = (Button) findViewById(R.id.bCreate);


        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_values();

                dataSource.open();
                User entry = new User(0,                                         //ID
                                        etName.getText().toString().trim(),      //Name
                                        etPassword.getText().toString().trim()); //Password
                dataSource.insertUser(entry);
                dataSource.close();
                onBackPressed();
            }
        });
    }


    private boolean check_values(){
        //TODO
        return true;
    }


    @Override
    public void onBackPressed() {
        finish();//finishing activity
    }
}
