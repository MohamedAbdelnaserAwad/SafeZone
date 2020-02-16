package com.arabic.quotes.safezone;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.arabic.quotes.safezone.App.CHANNEL_1_ID;
import static com.arabic.quotes.safezone.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    DatabaseReference reff;
    Button button;
    Button button1;
    TextView textView;
    ImageView imageView;
    private NotificationManagerCompat notificationManager;

    private MediaSessionCompat mediaSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        mediaSession = new MediaSessionCompat(this , "tag");

        button = findViewById(R.id.btnaction);
        button1 = findViewById(R.id.btnaction2);
        textView = findViewById(R.id.txt);
        imageView = findViewById(R.id.safeimage);
        reff = FirebaseDatabase.getInstance().getReference().child("sensor");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String number = dataSnapshot.child("laser").getValue().toString();
                if (number.equals("0")){
                    textView.setText("Your building Safe and Secure ");
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_security_black_24dp,0);
                    sendonChannel2();

                }else {
                    button.setVisibility(View.VISIBLE);
                    textView.setText("Your base is under Attack ");
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sentiment_very_dissatisfied_black_24dp,0);
                    sendonChannel1();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this , "fail" , Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Call.class);
                startActivity(intent);

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CamActivity.class);
                startActivity(intent);
            }
        });


    }


    public void sendonChannel1(){

        Intent activityIntent = new Intent(this , MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this ,
                0 , activityIntent , 0);

        Intent activityIntent1 = new Intent(this , Call.class);
        PendingIntent contentIntent1 = PendingIntent.getActivity(this ,
                0 , activityIntent1 , 0);

        Intent activityIntent2 = new Intent(this , CamActivity.class);
        PendingIntent contentIntent2 = PendingIntent.getActivity(this ,
                0 , activityIntent2 , 0);

        Bitmap artWork = BitmapFactory.decodeResource(getResources() , R.drawable.safe);

        Notification notification = new NotificationCompat.Builder(this , CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                .setContentTitle("SafeZone")
                .setContentText("Under Attack")
                .setLargeIcon(artWork)
                .addAction(R.drawable.ic_call_black_24dp , "" , contentIntent1)
                .addAction(R.drawable.ic_videocam_black_24dp , "" , contentIntent2)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)

                .build();

        notificationManager.notify(1 , notification);

    }
    public void sendonChannel2(){

        Notification notification = new NotificationCompat.Builder(this , CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_security_black_24dp)
                .setContentTitle("SafeZone")
                .setContentText("Safe and Secure")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(2 , notification);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
