package com.example.androidcontinuity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {

    EditText et_msg,et_number;
    ImageButton callButton;
    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        et_msg = findViewById(R.id.et_text_message);
        et_number = findViewById(R.id.et_number);
        callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+91"+et_number.getText().toString().trim();
                String msg = et_msg.getText().toString();
                onSendClick(number,msg);
            }
        });
    }
    SmsManager smsManager = SmsManager.getDefault();
    public void onSendClick(String number,String msg) {

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            getPermissionToReadSMS();
        } else {
            try {
                smsManager.sendTextMessage(number, null, msg, null, null);
                Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
            }catch (Exception e)
            {
                Log.e("mytag",e.getMessage());
            }
        }
    }
    public void getPermissionToReadSMS() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)
                    || shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)
                    || shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if ((grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    && (grantResults.length > 1 && grantResults[2] == PackageManager.PERMISSION_GRANTED) ){
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
