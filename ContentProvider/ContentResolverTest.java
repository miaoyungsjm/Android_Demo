package com.example.contentresolvertest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContentResolverTest extends AppCompatActivity {

    ArrayAdapter<String> mArrayAdapter;

    List<String> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 ListView ，使用默认布局
        ListView contactsView = (ListView) findViewById(R.id.listView_contacts);
        mArrayAdapter = new ArrayAdapter<String>(ContentResolverTest.this, android.R.layout.simple_list_item_1, contactsList);
        contactsView.setAdapter(mArrayAdapter);

        // 权限申请
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermission();

        }else {
            Toast.makeText(ContentResolverTest.this, "READ_CONTACTS Permission Allow", Toast.LENGTH_SHORT).show();
            readContacts();
        }
    }

    // 访问内容提供器，获取联系人资料
    private void readContacts(){
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    String displayName = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName + "\n" + number);
                }
                mArrayAdapter.notifyDataSetChanged();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (cursor != null)cursor.close();
        }
    }

    private void requestPermission(){
        int checkPermission = ContextCompat.checkSelfPermission(ContentResolverTest.this, Manifest.permission.READ_CONTACTS);
        if (checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContentResolverTest.this, new String[]{ Manifest.permission.READ_CONTACTS }, 1);
        }else {
            Toast.makeText(ContentResolverTest.this, "READ_CONTACTS Permission Allow", Toast.LENGTH_SHORT).show();
            readContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(ContentResolverTest.this, "READ_CONTACTS Permission Allow", Toast.LENGTH_SHORT).show();
                    readContacts();

                }else {
                    Toast.makeText(ContentResolverTest.this, "READ_CONTACTS Permission Deny", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
