package com.example.sanjeev.demo;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button login, signup, phoneLogin;
    private FirebaseAuth mAuth;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login_main);
        signup = findViewById(R.id.signup_main);
        phoneLogin = findViewById(R.id.phone_main);
//        mAuth = FirebaseAuth.getInstance();
//        currentUser=mAuth.getCurrentUser().getUid();

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(signupIntent);
                    finish();
                }
            });
            phoneLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent phoneIntent = new Intent(MainActivity.this, PhoneLoginActivity.class);
                    startActivity(phoneIntent);
                    finish();
                }
            });

        }

//    @Override
//    protected void onStart() {
//        if(currentUser!=null){
//            Intent Ques1Intent=new Intent(MainActivity.this,ques1_activity.class);
//            startActivity(Ques1Intent);
//        }
//        super.onStart();
//    }
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
//                        moveTaskToBack(true);
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                        System.exit(1);
                       //finish();
                        finish();
                    }
                    if (which == 1) {
                        Toast.makeText(MainActivity.this,"Back Button Pressed",Toast.LENGTH_LONG).show();

                    }
                }
            });

            builder.show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.runFinalizersOnExit(true);
    }
}