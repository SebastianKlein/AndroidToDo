package com.klein.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.klein.todo.database.DataSource;
import com.klein.todo.model.User;

public class AccountSettingsActivity extends AppCompatActivity {

    private DataSource dataSource;
    private Button bChangePassword;
    private Button bAddAccount;
    private EditText etPassword;
    private EditText etNewPassword;
    private EditText etNewPasswordVerify;
    private User currentUser; // = dataSource.getUser(id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewPasswordVerify = (EditText) findViewById(R.id.etNewPasswordVerify);
        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bAddAccount = (Button) findViewById(R.id.bAddAccount);

        bChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = etPassword.getText().toString().trim();
                String newPassword = etNewPassword.getText().toString().trim();
                String newPasswordVerify = etNewPasswordVerify.getText().toString().trim();

                if (currentUser.getPassword().equals(oldPassword) &&  // old passwords match
                        check_values(newPassword, newPasswordVerify)) { // new password follows rules
                    currentUser.setPassword(newPassword);
                    dataSource.open();
                    //dataSource.updateUser(currentUser);
                    dataSource.close();
                }
            }
        });

        bAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser.getId() == 0) {
                    addUser();
                }
            }
        });

        // TODO: delete user

    }

    private void addUser() {
        Intent addUser_intent = new Intent(this, AddUserActivity.class);
        startActivity(addUser_intent);
    }

    private boolean check_values(String password, String passwordVerify) {
        if (password.equals(passwordVerify) && password.length() >= 5 && password.length() <= 20) {
            int literal = interval(password, 'A', 'z');
            int digit = interval(password, '0', '9');
            if (digit > 0 && literal > 0 && password.length() - digit - literal == 0)
                return true;
        }
        return false;
    }

    private int interval(String pass, char min, char max) {
        int count = 0;
        for (char c : pass.toCharArray()) {
            if (c >= min && c <= max)
                count++;
        }
        return count;
    }
}
