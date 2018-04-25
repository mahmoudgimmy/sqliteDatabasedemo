package com.example.mahmo.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mahmo.sqlitedemo.Task;
import com.example.mahmo.sqlitedemo.TodoContract.TodoEntry;

import java.util.ArrayList;

public class TodoDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    //every time you change your schema Version++
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase todoDataBase;

    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //CREATE TABLE tasks ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, taskName TEXT NOT NULL );
        final String SQL_CREATE_TODO_SCHEMA = "CREATE TABLE " +
                TodoEntry.TABLE_NAME + " (" +
                TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TodoEntry.COLUMN_TASK_NAME + " TEXT NOT NULL " + ");";

        sqLiteDatabase.execSQL(SQL_CREATE_TODO_SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<Task> retrieveTasks() {
        ArrayList<Task> tasks;
        tasks = new ArrayList<>();

        todoDataBase = getReadableDatabase();
        // SELECT* from tasks;
       Cursor cursor = todoDataBase
                .query(TodoEntry.TABLE_NAME,   // The table to query
                        null,             // The columns to return
                        null,           // The columns for the WHERE clause
                        null,       // The values for the WHERE clause
                        null,           // don't group the rows
                        null,           // don't filter by row groups
                        null);          // The sort order

        if (cursor != null)

        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Task task;
                task = new Task();
                task.setId(cursor.getString(cursor.getColumnIndex(TodoEntry._ID)));
                task.setName(cursor.getString(cursor.getColumnIndex(TodoEntry.COLUMN_TASK_NAME)));
                tasks.add(task);
                cursor.moveToNext();
            }
            //cursor leak
            cursor.close();
            //sqliteDataBase Leak
            todoDataBase.close();

            return tasks;
        }

        return tasks;
    }

    public void addTask(Task task) {
        todoDataBase = getWritableDatabase();
        //contentValue as a row in database
        ContentValues c = new ContentValues();
        c.put(TodoEntry.COLUMN_TASK_NAME, task.getName());
        //INSERT INTO tasks (_ID,taskName) VALUES(id,task.getName());
        todoDataBase.insert(TodoEntry.TABLE_NAME, null, c);
        todoDataBase.close();
    }

    public void updateTask(Task task) {
        todoDataBase = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(TodoEntry.COLUMN_TASK_NAME, task.getName());
        String whereClause = TodoEntry._ID + " = " + task.getId();

        //UPDATE tasks SET taskName = task.getName() WHERE _ID = task.getId();
        todoDataBase.update(TodoEntry.TABLE_NAME, c, whereClause, null);
        todoDataBase.close();
    }

    public void deleteTask(Task task) {
        todoDataBase = getWritableDatabase();
        String whereClause = TodoEntry._ID + " = " + task.getId();
        //DELETE FROM tasks where _ID = task.getId();
        todoDataBase.delete(TodoEntry.TABLE_NAME, whereClause, null);
        todoDataBase.close();
    }
}
