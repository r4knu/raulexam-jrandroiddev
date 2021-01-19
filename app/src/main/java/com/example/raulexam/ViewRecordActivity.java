package com.example.raulexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewRecordActivity extends AppCompatActivity {
    FloatingActionButton btnAdd;
    RecyclerView recView;
    DatabaseHelper dbHelper;

    @Override


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewadd_layout);
        btnAdd = findViewById(R.id.fabAddProduct);
        recView = findViewById(R.id.recycleView);
        dbHelper = new DatabaseHelper(this);
        showRecord();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecordActivity.this,AddRecordActivity.class);
                intent.putExtra("editMode",false);
                startActivity(intent);
            }
        });
    }
    private void showRecord(){
        Adapter adapter = new Adapter(ViewRecordActivity.this,dbHelper.getAllData(dbHelper.COL_1));
        recView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecord();
    }
}
