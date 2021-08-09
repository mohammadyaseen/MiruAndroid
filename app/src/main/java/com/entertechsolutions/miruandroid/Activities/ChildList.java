package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.entertechsolutions.miruandroid.Models.ChildListAdapter;
import com.entertechsolutions.miruandroid.Models.Task_Model;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChildList extends AppCompatActivity {

    List<Task_Model> rowListItem;
    private LinearLayoutManager lLayout;
    Button back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);
        RecyclerView recyclerView = findViewById(R.id.recycleChild);
        back_btn = findViewById(R.id.backBtnChild);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(ChildList.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(lLayout);

        ChildListAdapter adapter = new ChildListAdapter(ChildList.this, rowListItem, new ChildListAdapter.OnItemClickListener() {
            @Override
            public void onClick(Task_Model main_task_model) {

            }
        });


        recyclerView.setAdapter(adapter);

        FloatingActionButton fab =  findViewById(R.id.addChild);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(ChildList.this, RegisterChild.class);
               // intent.putExtra("token",userToken);
                startActivity(intent);
            }
        });
    }


    private List<Task_Model> getAllItemList(){

        List<Task_Model> allItems = new ArrayList<>();
        allItems.add(new Task_Model("Muhammad yaseen"));
        allItems.add(new Task_Model("Muhammad muzammil"));
        allItems.add(new Task_Model("Muhammad haris"));
        return allItems;
    }

}
