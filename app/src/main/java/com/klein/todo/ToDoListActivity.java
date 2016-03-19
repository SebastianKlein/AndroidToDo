package com.klein.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.klein.todo.Utils.AppConstants;
import com.klein.todo.Utils.AppUtils;
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

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        dataSource = new DataSource(this);

        //ToDo_List
        lvToDoList = (ListView) findViewById(R.id.lvToDoList);
        registerForContextMenu(lvToDoList);

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

        //Todo
        currentUser = new User(0, "lala", "lala");
        fillToDoList();

        lvToDoList.setAdapter(lvAdapter);
    }

    public void fillToDoList(){
        dataSource.open();
        toDoList = dataSource.getAllNotesFromUserByUserId(currentUser.getId());
        dataSource.close();

        lvAdapter = new ToDoListLVAdapter(this, toDoList, currentUser);
        lvToDoList.setAdapter(lvAdapter);
    }

    public void accountSettings(){
        Intent accountSettings_intent = new Intent(this, AccountSettingsActivity.class);
        accountSettings_intent.putExtra("CurrentUser", currentUser);
        startActivity(accountSettings_intent);
    }

    public void addNote() {
        Intent addNote_intent = new Intent(this, AddNoteActivity.class);
        addNote_intent.putExtra("CurrentUser", currentUser);
        startActivityForResult(addNote_intent, AppConstants.RESULT_ADDNOTE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (AppConstants.RESULT_ADDNOTE) :
            case (AppConstants.RESULT_EDITNOTE) :
                if (resultCode == RESULT_OK) {
                    fillToDoList();
                }
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Get the info on which item was selected
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        // Retrieve the item that was clicked on
        Object item = lvAdapter.getItem(info.position);

        // Menu entrys
        menu.add(0, AppConstants.NOTE_EDIT, 0, R.string.context_menu_edit);
        menu.add(0, AppConstants.NOTE_IMPORTANT, 0, R.string.context_menu_mark);
        menu.add(0, AppConstants.NOTE_DELETE, 0, R.string.context_menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Here's how you can get the correct item in onContextItemSelected()
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Note entry = lvAdapter.getItem(info.position);

        switch (item.getItemId()) {
            case AppConstants.NOTE_EDIT:
                Intent editNote = new Intent(this, AddNoteActivity.class);
                editNote.putExtra("CurrentUser", currentUser);
                editNote.putExtra("CurrentNote", entry);
                startActivityForResult(editNote, AppConstants.RESULT_ADDNOTE);
                break;
            case AppConstants.NOTE_IMPORTANT:
                entry.swapImportant();
                dataSource.open();
                dataSource.updateNote(entry);
                dataSource.close();
                break;
            case AppConstants.NOTE_DELETE:
                dataSource.open();
                dataSource.deleteNote(entry);
                dataSource.close();
                AppUtils.showMsg(this, "DELETE: " + entry.getName());
                break;
            default:
                return super.onContextItemSelected(item);
        }
        fillToDoList();
        return true;
    }
}
