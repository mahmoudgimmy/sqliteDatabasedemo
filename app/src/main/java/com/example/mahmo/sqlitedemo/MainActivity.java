package com.example.mahmo.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ListView listView;
    private ArrayList<Task> tasks;
    private ArrayList<String>tasks_name;
    private ArrayAdapter arrayAdapter;
    private TodoDbHelper todoDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.ed_todo);
        button = findViewById(R.id.bt_add);
        listView = findViewById(R.id.lv_todo);

        // initialize th DbHelper Object
        todoDbHelper = new TodoDbHelper(this);
        //contains all tasks_name extracted from tasks
        tasks_name=new ArrayList<>();
        tasks=new ArrayList<>();

        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, tasks_name);

        listView.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Task task;
                task = new Task();
                task.setName(editText.getText().toString());

                //insert
                todoDbHelper.addTask(task);

                tasks.add(task);
                tasks_name.add(task.getName());
                arrayAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);
                Intent intent = new Intent(getApplicationContext(), TaskDetails.class);
                intent.putExtra("task", task);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);
                //delete
                todoDbHelper.deleteTask(task);

                tasks.remove(i);
                tasks_name.remove(i);
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
//to read all updated values from Database in MainActivity launching(onResume)
    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String>temp;
        temp=new ArrayList<>();
        tasks.clear();
        tasks_name.clear();
        //Select
        tasks.addAll(todoDbHelper.retrieveTasks());
        //extracting tasks names from tasks
        for(int i=0;i<tasks.size();i++)
            temp.add(tasks.get(i).getName());
        tasks_name.addAll(temp);
        arrayAdapter.notifyDataSetChanged();
    }
}
