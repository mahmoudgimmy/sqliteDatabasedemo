package com.example.mahmo.sqlitedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskDetails extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private Task task;
    private TodoDbHelper todoDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        todoDbHelper = new TodoDbHelper(this);
        editText = findViewById(R.id.ed_edit_task);
        button = findViewById(R.id.bt_edit);

        task = (Task) getIntent().getSerializableExtra("task");
        editText.setText(task.getName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTaskName=editText.getText().toString();
                task.setName(newTaskName);
                //update
                todoDbHelper.updateTask(task);
                finish();
            }
        });
    }
}
