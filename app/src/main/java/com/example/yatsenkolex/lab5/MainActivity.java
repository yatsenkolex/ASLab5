package com.example.yatsenkolex.lab5;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "Logs";
    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in myTable: ---");

                cv.put("name", name);
                cv.put("email", email);

                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                etName.setText(null);
                etEmail.setText(null);
                Toast.makeText(getApplicationContext(), "Row inserted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable ---");
                Intent intent = new Intent(this, ActivityOut.class);
                Cursor c = db.query("mytable", null, null, null, null, null, null);
                List<DbRow> dbValues = new ArrayList<>();
                DbRow dbRow;

                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex("_id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");

                    do {
                        dbRow = new DbRow(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(emailColIndex));
                        dbValues.add(dbRow);
                        Log.d(LOG_TAG, "ID = " + dbRow.getId() +
                                ", name = " + dbRow.getName() +
                                ", email = " + dbRow.getEmail());
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();

                intent.putExtra("dbValues", (Serializable) dbValues);
                startActivity(intent);
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable ---");
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);
                Toast.makeText(getApplicationContext(), "Table cleared", Toast.LENGTH_SHORT).show();
                break;
        }
        dbHelper.close();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    static class DBHelper extends SQLiteOpenHelper {
        private String LOG_TAG = "Logs";

        DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- OnCreate database ---");
            db.execSQL(String.format("create table mytable (_id integer primary key autoincrement,name text,email text);"));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
