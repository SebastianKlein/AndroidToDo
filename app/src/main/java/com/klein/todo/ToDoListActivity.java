package com.klein.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.klein.todo.adapter.ToDoListLVAdapter;
import com.klein.todo.database.DataSource;
import com.klein.todo.model.Note;
import com.klein.todo.model.User;

import java.util.List;

public class ToDoListActivity extends AppCompatActivity {
    //Database
    private DataSource dataSource;

    //Layout
    private Button bSettings;
    private FloatingActionButton fbAdd;
    private ListView lvToDoList;

    //ToDo_List
    private ToDoListLVAdapter lvAdapter;
    private List<Note> toDoList;


    private int ID = 0;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        dataSource = new DataSource(this);

        //ToDo_List
        lvToDoList = (ListView) findViewById(R.id.lvToDoList);

        //Add entry
        fbAdd = (FloatingActionButton) findViewById(R.id.fbAdd);
        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        //Accoutsettings
        bSettings = (Button) findViewById(R.id.bSettings);
        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountSettings();
            }
        });

        currentUser = new User(0, "lala", "lala");
        fillToDoList();

        lvToDoList.setAdapter(lvAdapter);
    }

    public void fillToDoList(){
        dataSource.open();
        toDoList = dataSource.getAllNotesFromUserByUserId(currentUser.getId());
        dataSource.close();

        lvAdapter = new ToDoListLVAdapter(this, toDoList);
    }

    public void accountSettings(){
        Intent accountSettings_intent = new Intent(this, AccountSettingsActivity.class);
        accountSettings_intent.putExtra("User", currentUser);
        startActivity(accountSettings_intent);
    }

    public void addNote() {
        Intent addNote_intent = new Intent(this, AddNoteActivity.class);
        addNote_intent.putExtra("User", currentUser);
        startActivity(addNote_intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fillToDoList();
    }
}
