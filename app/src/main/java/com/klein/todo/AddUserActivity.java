package com.klein.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.klein.todo.Utils.AppConstants;
import com.klein.todo.database.DataSource;
import com.klein.todo.model.User;

import java.util.List;

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
                if(check_values()) {
                    dataSource.open();
                    List<User> userList = dataSource.getAllUser();
                    User entry = new User(userList.size(),          //ID
                            etName.getText().toString().trim(),     //Name
                            etPassword.getText().toString().trim());//Password
                    dataSource.insertUser(entry);
                    dataSource.close();
                    onBackPressed();
                }
            }
        });
    }


    private boolean check_values(){
        String password=etPassword.getText().toString().trim();
        String passwordVerify=etPasswordVerify.getText().toString().trim();
        if(password.equals(passwordVerify) && password.length() >= 5 && password.length() <=20) {
            int literal = interval(password, 'A', 'z');
            int digit = interval(password, '0', '9');
            if (digit > 0 && literal > 0 && password.length() - digit - literal == 0)
                return true;
        }
        return false;
    }

    private int interval(String pass, char min, char max){
        int count = 0;
        for(char c : pass.toCharArray()) {
            if (c >= min && c <= max)
                count++;
        }
        return count;
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();//finishing activity
    }
}