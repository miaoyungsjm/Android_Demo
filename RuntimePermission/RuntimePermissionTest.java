package com.example.runtimepermissiontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RuntimePermissionTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_callPhone = (Button) findViewById(R.id.button_callPhone);
        button_callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  判断 Android 版本是否大于23 （Android 6.0）
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //  请求系统读写权限
                    requestPermission();
                }else {
                    callPhone();
                }
            }
        });
    }

    private void callPhone(){
        try{
            Intent intent = new Intent(Intent.ACTION_CALL);//ACTION_DIAL（无需权限）
            intent.setData(Uri.parse("tel:10010"));
            startActivity(intent);
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
    }

    private void requestPermission(){
        int checkCallPhonePermission = ContextCompat.checkSelfPermission(RuntimePermissionTest.this,
                Manifest.permission.CALL_PHONE);
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RuntimePermissionTest.this,
                    new String[]{ Manifest.permission.CALL_PHONE }, 1);
        }else {
            callPhone();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    callPhone();
                }else {
                    Toast.makeText(RuntimePermissionTest.this, "拒绝 CALL_PHONE 权限申请", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:

                break;
            default:
        }
    }
}
