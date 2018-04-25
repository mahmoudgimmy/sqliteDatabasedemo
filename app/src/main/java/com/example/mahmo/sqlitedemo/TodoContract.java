package com.example.mahmo.sqlitedemo;

import android.provider.BaseColumns;

public class TodoContract {
    //Basecolumns to handle ID creation
    public static final class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_NAME = "taskName";
    }
}
