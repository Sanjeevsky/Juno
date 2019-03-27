package com.example.sanjeev.demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class final_activity extends AppCompatActivity {
String result;
TextView t1;
private DatabaseReference Rootref;
private FirebaseAuth mAuth;
private String currentDate,currentTime;
private Button ReplayButton,ExitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_activity);
        Bundle bundle=getIntent().getExtras();
        result=bundle.getString("ques10");
        final int res_int=Integer.parseInt(result);
        t1=(TextView)findViewById(R.id.result_id);
        t1.setText(result);

        Rootref=FirebaseDatabase.getInstance().getReference().child("Score");
        mAuth=FirebaseAuth.getInstance();

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDateFormat=new SimpleDateFormat("MMM dd, yyyy");
        currentDate=currentDateFormat.format(calForDate.getTime());

        Calendar calForTime= Calendar.getInstance();
        SimpleDateFormat currentTimeFormat=new SimpleDateFormat("hh:mm a");
        currentTime=currentTimeFormat.format(calForTime.getTime());

        DatabaseReference userMessageKeyRef=Rootref.child(mAuth.getCurrentUser().getUid()).push();
        String messagePushID=userMessageKeyRef.getKey();
        Map messageTextBody=new HashMap();
        messageTextBody.put("result",result);
        messageTextBody.put("date",currentDate);
        messageTextBody.put("time",currentTime);

        userMessageKeyRef.updateChildren(messageTextBody).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(final_activity.this,"Score Updated",Toast.LENGTH_LONG).show();
                }
            }
        });
        ReplayButton=findViewById(R.id.replay_btn);
        ExitButton=findViewById(R.id.exit_btn);
        ReplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ques1Intent=new Intent(final_activity.this,ques1_activity.class);
                startActivity(ques1Intent);

            }
        });

        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent mainIntent=new Intent(final_activity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });



    }
    @Override
    public void onBackPressed() {

        {
            CharSequence options[]=new CharSequence[]
                    {
                            "Exit",
                            "Cancel"
                    };
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Want To Exit App?");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        mAuth.signOut();
//                        Intent mainIntent = new Intent(final_activity.this, MainActivity.class);
//                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(mainIntent);
                            onDestroy();
                    }
                    if (which == 1) {
                        Toast.makeText(final_activity.this,"Back Button Pressed",Toast.LENGTH_LONG).show();

                    }
                }
            });

            builder.show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
