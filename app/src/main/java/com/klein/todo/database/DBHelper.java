package com.klein.todo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sebastian on 16.03.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ToDo.db";
    private static final int DATABASE_VERSION = 1;

    // USER_TABLE
    private static final String TABLE_USER =
            "create table USER("
                    +   "ID integer primary key autoincrement, "
                    +   "NAME text, "
                    +   "PASSWORD text, "
                    +   "SHOWDONE int, "
                    +   "SORT int)";

    // NOTE_TABLE
    private static final String TABLE_NOTE =
            "create table NOTE("
                    +   "ID integer primary key autoincrement, "
                    +   "NAME text, "
                    +   "DESCRIPTION text,"
                    +   "TIMESTAMP text,"
                    +   "IMPORTANT boolean,"
                    +   "DONE boolean,"
                    +   "USER_ID integer,"
                    + 	"FOREIGN KEY (USER_ID) REFERENCES USER(ID) ON DELETE CASCADE)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_USER);
        database.execSQL(TABLE_NOTE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS SCANITEM");
        //onCreate(db);
    }

}
