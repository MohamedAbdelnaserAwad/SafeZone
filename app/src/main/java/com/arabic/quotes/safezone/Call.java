package com.arabic.quotes.safezone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Call extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    Button call;
    TextView textNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        call = findViewById(R.id.btncall);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textNumber = findViewById(R.id.txtnumber);
                String number = (String) textNumber.getText();
                makephoneCall(number);
            }
        });
    }

    private void makephoneCall(String num){

        if (ContextCompat.checkSelfPermission(Call.this , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Call.this ,
                    new String[]{Manifest.permission.CALL_PHONE} , REQUEST_CALL);
        }else {

            String dail = "tel:" + num;
            startActivity(new Intent(Intent.ACTION_CALL , Uri.parse(dail)));
        }

    }


}
