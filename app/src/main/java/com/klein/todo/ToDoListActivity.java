package com.klein.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.klein.todo.model.User;

public class ToDoListActivity extends AppCompatActivity {
    private Button bSettings;
    private FloatingActionButton fbAdd;
    private int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        bSettings = (Button) findViewById(R.id.bSettings);
        fbAdd = (FloatingActionButton) findViewById(R.id.fbAdd);


        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountSettings();
            }
        });
    }

    public void accountSettings(){
        Intent accountSettings_intent = new Intent(this, AccountSettingsActivity.class);
        startActivity(accountSettings_intent);
    }

    public void addNote() {
        Intent addNote_intent = new Intent(this, AddNoteActivity.class);
        startActivity(addNote_intent);
    }
}
