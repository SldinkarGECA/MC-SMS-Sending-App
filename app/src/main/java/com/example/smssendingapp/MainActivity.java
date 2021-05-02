package com.example.smssendingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 111;
    private EditText getMobileNo, getMessage;
    private Button sendButton;
    String setMobileNo;
    String setMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMobileNo = findViewById(R.id.getMobileNumber);
        getMessage = findViewById(R.id.getMessage);
        sendButton = findViewById(R.id.sendButton);
        sendButton.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)) sendButton.setEnabled(true);
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMsg = getMessage.getText().toString();
                setMobileNo = getMobileNo.getText().toString();
                if (!TextUtils.isEmpty(setMsg) && !TextUtils.isEmpty(setMobileNo)) {
                    if (checkPermission(Manifest.permission.SEND_SMS)) {
                        SmsManager smsmgr = SmsManager.getDefault();
                        smsmgr.sendTextMessage(setMobileNo, null, setMsg, null, null);
                        Toast.makeText(MainActivity.this, "SMS Sent..", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Mobile no. and Message should no empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    sendButton.setEnabled(true);
                break;
        }
    }
}