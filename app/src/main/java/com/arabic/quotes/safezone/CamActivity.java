package com.arabic.quotes.safezone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CamActivity extends AppCompatActivity {

    DatabaseReference reff;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        webView = (WebView)findViewById(R.id.webView);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://192.168.43.143");

        webView.setWebViewClient(new WebViewClient());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reff = firebaseDatabase.getReference();
        reff.child("camIp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String number = dataSnapshot.getValue().toString();

                webView.loadUrl("http://"+number);

                webView.setWebViewClient(new WebViewClient());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    }

