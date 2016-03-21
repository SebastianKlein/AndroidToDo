package com.klein.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.klein.todo.database.DataSource;
import com.klein.todo.model.Note;
import com.klein.todo.model.User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {
    private DataSource dataSource;
    private EditText etName;
    private EditText etNote;
    private Button bCreate;
    private Button bCancel;
    private CheckBox cbImportant;
    private CheckBox cbDone;
    private Button bSelectTime;
    private boolean important = false;
    private boolean done = false;

    private User currentUser;
    private Note noteEntry;
    private String time;

    public void onCheckboxClicked(View view) {
        done = cbDone.isChecked();
        important = cbImportant.isChecked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        dataSource = new DataSource(this);
        time = Long.toString(System.currentTimeMillis());

        Intent getIntent = getIntent();
        currentUser = (User) getIntent.getParcelableExtra("CurrentUser");
        noteEntry = (Note) getIntent.getParcelableExtra("CurrentNote");

        etName = (EditText) findViewById(R.id.etName);
        etNote = (EditText) findViewById(R.id.etNote);
        bSelectTime = (Button) findViewById(R.id.bSelectTime);
        cbImportant = (CheckBox) findViewById(R.id.cbImportant);
        cbDone = (CheckBox) findViewById(R.id.cbDone);
        bCreate = (Button) findViewById(R.id.bCreate);
        bCancel = (Button) findViewById(R.id.bCancel);


        if (noteEntry != null) {
            etName.setText(noteEntry.getName());
            etNote.setText(noteEntry.getDescription());
            cbImportant.setChecked(noteEntry.getImportant());
            cbDone.setChecked(noteEntry.getImportant());
            time = noteEntry.getTimestamp();
        }


        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });


        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataSource.open();

                //Insert new note
                if (noteEntry == null) {
                    List<Note> noteList = dataSource.getAllNotesFromUserByUser(currentUser);
                    noteEntry = new Note(new Long(noteList.size()),
                            etName.getText().toString().trim(),
                            etNote.getText().toString().trim(),
                            time,
                            important,
                            done,
                            currentUser.getId()
                    );
                    dataSource.insertNote(noteEntry);
                    noteList.add(noteEntry);
                }
                //Update note
                else{
                    noteEntry.update(
                            etName.getText().toString().trim(),
                            etNote.getText().toString().trim(),
                            time,
                            important,
                            done
                    );
                    dataSource.updateNote(noteEntry);
                }

                dataSource.close();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private void selectTime(){
        final View dialogView = View.inflate(this, R.layout.activity_date_and_time, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        if(!time.equals("")) {
            DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
            TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(Long.valueOf(noteEntry.getTimestamp()));


            datePicker.updateDate(calendar.YEAR, calendar.MONTH, calendar.DAY_OF_MONTH);
            timePicker.setCurrentHour(calendar.HOUR_OF_DAY);
            timePicker.setCurrentMinute(calendar.MINUTE);
        }

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                time = Long.toString(calendar.getTimeInMillis());
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }
}