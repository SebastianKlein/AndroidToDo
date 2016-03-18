package com.klein.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.klein.todo.database.DataSource;
import com.klein.todo.model.Note;
import com.klein.todo.model.User;

import java.util.List;

public class AddNoteActivity extends AppCompatActivity {
    private DataSource dataSource;
    private EditText etName;
    private EditText etNote;
    private EditText etDeadline;
    private Button bCreate;
    private Button bCancel;
    private CheckBox cbImportant;
    private CheckBox cbDone;
    private boolean important = false;
    private boolean done = false;

    public void onCheckboxClicked(View view) {
        done = cbDone.isChecked();
        important = cbImportant.isChecked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        dataSource = new DataSource(this);

        etName = (EditText) findViewById(R.id.etName);
        etNote = (EditText) findViewById(R.id.etNote);
        etDeadline = (EditText) findViewById(R.id.etDeadline);
        cbImportant = (CheckBox) findViewById(R.id.cbImportant);
        cbDone = (CheckBox) findViewById(R.id.cbDone);
        bCreate = (Button) findViewById(R.id.bCreate);
        bCancel = (Button) findViewById(R.id.bCancel);

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNote_Intent = getIntent();
                int userID = addNote_Intent.getIntExtra("user_id", -1);
                dataSource.open();
                List<Note> noteList = dataSource.getAllNotesFromUserByUserId(userID);  // id <<
                Note noteEntry = new Note(noteList.size(),
                        etName.getText().toString().trim(),
                        etNote.getText().toString().trim(),
                        etDeadline.getText().toString().trim(),
                        done,
                        important,
                        userID
                );
                dataSource.insertNote(noteEntry);
                dataSource.close();
                finish();
            }
        });
    }
}