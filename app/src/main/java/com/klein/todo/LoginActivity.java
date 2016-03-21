package com.klein.todo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
public class LoginActivity extends AppCompatActivity {

    private DataSource dataSource;
    private EditText etName;
    private EditText etPassword;
    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dataSource = new DataSource(this);

        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);

        dataSource.open();
        List<User> userList = dataSource.getAllUser();
        dataSource.close();

        //First user?
        if (userList.isEmpty()) {
            Intent addUser_intent = new Intent(this, AddUserActivity.class);
            startActivity(addUser_intent);
        }

        //Loginbutton
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.open();
                List<User> userList = dataSource.getAllUser();
                dataSource.close();
                for (int i = 0; i <= userList.size() - 1; i++) { // i = id
                    if (userList.get(i).getName().equals(etName.getText().toString().trim()) && userList.get(i).getPassword().equals(etPassword.getText().toString().trim())) {
                        //login..
                        Intent resultLogin = new Intent();
                        resultLogin.putExtra("CurrentUser", userList.get(i));
                        setResult(RESULT_OK, resultLogin);
                        finish();
                    }
                }

            }
        });
    }
}
