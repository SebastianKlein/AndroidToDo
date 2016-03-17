package com.klein.todo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AccountSettingsActivity extends AppCompatActivity {

    private Button bChangePassword;
    private Button bAddAccount;
    private EditText etPassword;
    private EditText etNewPassword;
    private EditText etNewPasswordVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewPasswordVerify = (EditText) findViewById(R.id.etNewPasswordVerify);
        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bAddAccount = (Button) findViewById(R.id.bAddAccount);


    }
}
