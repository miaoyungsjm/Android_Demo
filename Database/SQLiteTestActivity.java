package com.example.sqlitetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SQLiteTestActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlitetest);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);

        Button button_createDatabase = (Button) findViewById(R.id.button_createDatabase);
        button_createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
//                dbHelper.getReadableDatabase();
            }
        });


        Button button_addData = (Button) findViewById(R.id.button_addData);
        button_addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "Book1");
                contentValues.put("author", "A");
                contentValues.put("price", 50.50);

                db.insert("Book", null, contentValues);
                contentValues.clear();

                contentValues.put("name", "Book2");
                contentValues.put("author", "B");
                contentValues.put("price", 60);

                db.insert("Book", null, contentValues);
                contentValues.clear();
                Toast.makeText(SQLiteTestActivity.this, "Add Data succeeded", Toast.LENGTH_SHORT).show();


//                db.execSQL("insert into Book (name, author, price) values(?, ?, ?)", new String[]{"Book3", "C", "16.50"});
            }
        });


        Button button_updateData = (Button) findViewById(R.id.button_updateData);
        button_updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put("price", 55);

                db.update("Book", contentValues, "id = 4 AND name = ?", new String[]{"Book2"});    // ？为占位符
                contentValues.clear();
                Toast.makeText(SQLiteTestActivity.this, "Update Data succeeded", Toast.LENGTH_SHORT).show();


//                db.execSQL("update Book set price = ? where name = ?", new String[]{"12.5", "Book3"});
            }
        });


        Button button_deleteData = (Button) findViewById(R.id.button_deleteData);
        button_deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.delete("Book", "id = ?", new String[]{"4"});
                Toast.makeText(SQLiteTestActivity.this, "Delete Data succeeded", Toast.LENGTH_SHORT).show();


//                db.execSQL("delete from Book where id = ?", new String[]{"4"});
            }
        });


        Button button_queryData = (Button) findViewById(R.id.button_queryData);
        button_queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

//                Cursor cursor = db.rawQuery("select * from Book", null);

                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d("SQLiteTestActivity", "name : " + name);
                        Log.d("SQLiteTestActivity", "author : " + author);
                        Log.d("SQLiteTestActivity", "price : " + price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
