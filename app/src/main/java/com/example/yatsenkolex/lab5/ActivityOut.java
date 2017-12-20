package com.example.yatsenkolex.lab5;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ActivityOut extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<DbRow> dbRows = (List<DbRow>) getIntent().getSerializableExtra("dbValues");
        setContentView(R.layout.activity_out);
        ListView list = findViewById(android.R.id.list);
        ArrayAdapter<DbRow> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbRows);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        "itemClick: " + parent.getAdapter().getItem(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
