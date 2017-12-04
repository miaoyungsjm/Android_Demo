package com.example.databasetest;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private MyDatabaseHelper dbHelper;

    String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);

        Button button_createDatabase = (Button) findViewById(R.id.button_createDatabase);
        button_createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
//                dbHelper.getReadableDatabase();
            }
        });


        Button addDataToDB = (Button) findViewById(R.id.addDataToDB);
        addDataToDB.setOnClickListener(new View.OnClickListener() {
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

                contentValues.put("category_name", "A1");
                contentValues.put("category_code", "1001");
                db.insert("Category", null, contentValues);
                contentValues.clear();

                contentValues.put("category_name", "B1");
                contentValues.put("category_code", "1002");
                db.insert("Category", null, contentValues);
                contentValues.clear();

                Toast.makeText(MainActivity.this, "Add Data succeeded", Toast.LENGTH_SHORT).show();


//                db.execSQL("insert into Book (name, author, price) values(?, ?, ?)", new String[]{"Book3", "C", "16.50"});
            }
        });


        Button updateDataToDB = (Button) findViewById(R.id.updateDataToDB);
        updateDataToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put("price", 55);

                db.update("Book", contentValues, "id = 4 AND name = ?", new String[]{"Book2"});    // ？为占位符
                contentValues.clear();
                Toast.makeText(MainActivity.this, "Update Data succeeded", Toast.LENGTH_SHORT).show();


//                db.execSQL("update Book set price = ? where name = ?", new String[]{"12.5", "Book3"});
            }
        });


        Button deleteDataToDB = (Button) findViewById(R.id.deleteDataToDB);
        deleteDataToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.delete("Book", "id < ?", new String[]{"4"});
                Toast.makeText(MainActivity.this, "Delete Data succeeded", Toast.LENGTH_SHORT).show();


//                db.execSQL("delete from Book where id = ?", new String[]{"4"});
            }
        });


        Button queryDataToDB = (Button) findViewById(R.id.queryDataToDB);
        queryDataToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

//                Cursor cursor = db.rawQuery("select * from Book", null);

                Cursor cursor = null;
                try{
                    cursor = db.query("Book", null, null, null, null, null, null);
                    if (cursor != null){
                        while(cursor.moveToNext()){
                            int id = cursor.getInt(cursor.getColumnIndex("id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            String author = cursor.getString(cursor.getColumnIndex("author"));
                            double price = cursor.getDouble(cursor.getColumnIndex("price"));

                            Log.d("MainActivity", "id : " + id);
                            Log.d("MainActivity", "name : " + name);
                            Log.d("MainActivity", "author : " + author);
                            Log.d("MainActivity", "price : " + price);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (cursor != null)cursor.close();
                }

                try{
                    cursor = db.query("Category", null, null, null, null, null, null);
                    if (cursor != null){
                        while(cursor.moveToNext()){
                            int id = cursor.getInt(cursor.getColumnIndex("id"));
                            String category_name = cursor.getString(cursor.getColumnIndex("category_name"));
                            int category_code = cursor.getInt(cursor.getColumnIndex("category_code"));

                            Log.d("MainActivity", "id : " + id);
                            Log.d("MainActivity", "category_name : " + category_name);
                            Log.d("MainActivity", "category_code : " + category_code);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (cursor != null)cursor.close();
                }
            }
        });




        Button addDataToCP = (Button) findViewById(R.id.addDataToCP);
        addDataToCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/Book");
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "Book3");
                contentValues.put("author", "C");
                contentValues.put("price", 49.96);
                Uri newUri = getContentResolver().insert(uri, contentValues);
                newId = newUri.getPathSegments().get(1);
            }
        });

        Button updateDataToCP = (Button) findViewById(R.id.updateDataToCP);
        updateDataToCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/Book/3");
                ContentValues contentValues = new ContentValues();
                contentValues.put("price", 99.99);
                int updatedRows = getContentResolver().update(uri, contentValues, null, null);
            }
        });

        Button deleteDataToCP = (Button) findViewById(R.id.deleteDataToCP);
        deleteDataToCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/Book/3");
                int deletedRows = getContentResolver().delete(uri, null, null);
            }
        });

        Button queryDataToCP = (Button) findViewById(R.id.queryDataToCP);
        queryDataToCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.databasetest.provider/Book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d("MainActivity", "-------------");
                        Log.d("MainActivity", "id : " + id);
                        Log.d("MainActivity", "name : " + name);
                        Log.d("MainActivity", "author : " + author);
                        Log.d("MainActivity", "price : " + price);
                    }
                    cursor.close();
                }
            }
        });

    }
}
