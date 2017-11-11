package com.example.sharedpreferencestest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText_account, editText_password;
    private TextView textView;
    private Button button_login, button_load, button_reset;
    private CheckBox checkBox_remember;


    SharedPreferences.Editor editor;
    SharedPreferences pref;


    public static final String TAG = "LoginActivity";
    public static final String UserInfo = "UserInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        pref = getSharedPreferences("spname", MODE_PRIVATE);

        boolean isRemember = pref.getBoolean("remember", false);
        if (isRemember){
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            editText_account.setText(account);
            editText_password.setText(password);
            checkBox_remember.setChecked(true);
        }
    }

    private void initView(){
        editText_account = (EditText) findViewById(R.id.editText_account);
        editText_password = (EditText) findViewById(R.id.editText_password);
        checkBox_remember = (CheckBox) findViewById(R.id.checkBox_remember);
        textView = (TextView) findViewById(R.id.textView);
        button_login = (Button) findViewById(R.id.button_login);
        button_load = (Button) findViewById(R.id.button_load);
        button_reset = (Button) findViewById(R.id.button_reset);

        button_login.setOnClickListener(this);
        button_load.setOnClickListener(this);
        button_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_login:

                String account = editText_account.getText().toString();
                String password = editText_password.getText().toString();

                if (account.equals("admin") && password.equals("admin")){

                    editor = pref.edit();           //

                    if (checkBox_remember.isChecked()){
                        editor.putString("account", account);
                        editor.putString("password", password);
                        editor.putBoolean("remember", true);
                    }else {
                        editor.clear();
                    }

                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, EmptyActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.button_load :
                String account_1 = pref.getString("account", "");
                String password_1 = pref.getString("password", "");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Account:" + account_1 + "\n");
                stringBuilder.append("Password:" + password_1 + "\n");
                textView.setText(stringBuilder.toString());
                break;

            case R.id.button_reset:
                editText_account.setText("");
                editText_password.setText("");
                textView.setText("");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(LoginActivity.this, "LoginActivity - finish()", Toast.LENGTH_SHORT).show();
    }

}
