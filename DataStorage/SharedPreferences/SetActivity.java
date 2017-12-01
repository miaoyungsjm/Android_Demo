package com.sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editText_name, editText_password;
    private TextView textView;
    private Button button_save, button_load;

    private List infoList = new ArrayList();//存储用户登录信息的一个list

    SharedPreferences.Editor editor;
    SharedPreferences reader;


    public static final String TAG = "MainActivity";
    public static final String UserInfo = "UserInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_password = (EditText) findViewById(R.id.editText_password);
        textView = (TextView) findViewById(R.id.textView);
        button_save = (Button) findViewById(R.id.button_save);
        button_load = (Button) findViewById(R.id.button_load);

        button_save.setOnClickListener(this);
        button_load.setOnClickListener(this);


        initSPDB();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_save :

                infoList.clear();
                Log.i(TAG, " -- infoList.clear()");

                String account = editText_name.getText().toString();
                String passw = editText_password.getText().toString();

                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(passw)){
                    Log.i(TAG, " -- EditText is null");
                    Toast.makeText(this, "EditText is null", Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    Log.i(TAG, " -- infoList.add()");
                    infoList.add(account);
                    infoList.add(passw);
                }

                write(UserInfo, infoList);

                break;

            case R.id.button_load :

                infoList.clear();
                Log.i(TAG, " -- infoList.clear()");

                infoList = load(UserInfo);
                if (infoList.size() != 0){
                    StringBuilder content = new StringBuilder();
                    content.append("Account :" + infoList.get(0).toString() + "\n");
                    content.append("Password :" + infoList.get(1).toString() + "\n");

                    textView.setText(content);
                }
                break;
        }
    }

    public void initSPDB(){

        editor = getSharedPreferences("SharedPreferences_Name", MODE_PRIVATE).edit();

        reader = getSharedPreferences("SharedPreferences_Name", MODE_PRIVATE);
    }


    public void write(String name, List list){
        Gson gson = new Gson();
        String  str = gson.toJson(list);
        Log.i(TAG, " ----- write(String name, List list) : \n" +
                "    str = gson.toJson(list);");

        editor.clear();
        editor.putString(name, str);
        editor.commit();
        Log.i(TAG, "    editor.putString(name, str);\n" +
                "    editor.commit()");

        Toast.makeText(this, "write", Toast.LENGTH_SHORT).show();
    }


    public List load(String name){
        Log.i(TAG, " ----- load(String name)");

        List recordList = new ArrayList();
        String str = reader.getString(name, null);
        Log.i(TAG, "    str = reader.getString(name, null)");
        if(str == null){
            Log.i(TAG, "    str = null");
            return recordList;
        }

        Gson gson = new Gson();
        recordList = gson.fromJson(str, new TypeToken<List>(){}.getType());
        Log.i(TAG, "    recordList = gson.fromJson(str, new TypeToken<List>(){}.getType())");

        Toast.makeText(this, "load", Toast.LENGTH_SHORT).show();
        return recordList;
    }
}
