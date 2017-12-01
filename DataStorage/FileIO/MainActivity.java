package com.fileio;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editText;
    private TextView textView;
    private Button button_save, button_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button_save = (Button) findViewById(R.id.button_save);
        button_read = (Button) findViewById(R.id.button_read);

        button_save.setOnClickListener(this);
        button_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_save :
                String str = editText.getText().toString();
                saveToFile(str);
                break;

            case R.id.button_read :
                String temp = readToFile();
                if(TextUtils.isEmpty(temp)){
                    Toast.makeText(this, "File content is null", Toast.LENGTH_SHORT).show();
                }else {
                    textView.setText(temp);
                }
                break;
        }
    }


    public void saveToFile(String savedata){
        String data = savedata;

        FileOutputStream out = null;
        BufferedWriter writer = null;

        try{
            //  openFileOutput() 方法获取 FileOutputStream 对象
            out = openFileOutput("filename", Context.MODE_APPEND);// Context.MODE_PRIVATE

            //  借助 FileOutputStream 对象构建 OutputStreamWriter 对象，
            //  接着再使用 OutputStreamWriter 对象构建出 BufferedWriter 对象
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data + "\n");         //  最后通过 BufferedWriter 对象把数据写入文件
            Toast.makeText(this, "Success to save", Toast.LENGTH_SHORT).show();

        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show();

        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public String readToFile(){

        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try{
            in = openFileInput("filename");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line + "\n");
            }
            Toast.makeText(this, "Success to read", Toast.LENGTH_SHORT).show();

        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to read", Toast.LENGTH_SHORT).show();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return content.toString();
    }
}
