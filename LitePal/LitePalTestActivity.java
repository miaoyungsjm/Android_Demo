package com.example.ggz.litepaltest;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class LitePalTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepaltest);


        Button button_createDatabase = (Button) findViewById(R.id.button_createDatabase);
        button_createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.getDatabase();
                Toast.makeText(LitePalTestActivity.this, "Create database", Toast.LENGTH_SHORT).show();
            }
        });


        Button button_addData = (Button) findViewById(R.id.button_addData);
        button_addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Book book_1 = new Book();
//                book_1.setName("book 1");
//                book_1.setAuthor("first");
//                book_1.setPrice(25.55);
//                book_1.save();

                Book book_3 = new Book();
                book_3.setName("book 3");
                book_3.setAuthor("second");
                book_3.setPrice(35.06);
                if(book_3.save()){
                    Toast.makeText(LitePalTestActivity.this, "Add Data succeeded", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button button_updateData = (Button) findViewById(R.id.button_updateData);
        button_updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book book = new Book();
                book.setPrice(50.10);
//                book.setToDefault("price");
                book.updateAll("name = ? AND author = ?", "book 1", "first");
                Toast.makeText(LitePalTestActivity.this, "Update Data succeeded", Toast.LENGTH_SHORT).show();
            }
        });


        Button button_deleteData = (Button) findViewById(R.id.button_deleteData);
        button_deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataSupport.deleteAll(Book.class, "name = ?", "book 3");
                Toast.makeText(LitePalTestActivity.this, "Delete Data succeeded", Toast.LENGTH_SHORT).show();
            }
        });


        Button button_queryData = (Button) findViewById(R.id.button_queryData);
        button_queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Book> books = DataSupport.findAll(Book.class);
                for (Book book: books){
                    Log.d("LitePalTestActivity", "id : " + book.getId());
                    Log.d("LitePalTestActivity", "name : " + book.getName());
                    Log.d("LitePalTestActivity", "author : " + book.getAuthor());
                    Log.d("LitePalTestActivity", "price : " + book.getPrice());
                }

                List<Book> bookss = DataSupport.select("id", "name", "author")
                                               .where("id > ?", "0")
                                               .order("id")
                                               .limit(10)
                                               .offset(0)
                                               .find(Book.class);
                for (Book book: bookss){
                    Log.d("LitePalTestActivity", "id : " + book.getId());
                    Log.d("LitePalTestActivity", "name : " + book.getName());
                    Log.d("LitePalTestActivity", "author : " + book.getAuthor());
                }

//                Cursor cursor = DataSupport.findBySQL("select * from Book where price > ?", "10");
            }
        });
    }
}
