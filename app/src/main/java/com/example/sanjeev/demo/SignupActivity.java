package com.example.sanjeev.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {
    EditText Email,Password;
    Button SignupButton, AlreadyHaveAccountButton;
    private DatabaseReference Users;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loading=new ProgressDialog(this);
        InitalizeFields();
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString();
                String password=Password.getText().toString();
                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password))
                {
                    loading.setTitle("Signing Up");
                    loading.setMessage("Please Wait");
                    loading.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                                Users.child(mAuth.getCurrentUser().getUid()).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            loading.dismiss();
                                            Toast.makeText(SignupActivity.this,"Account Created Successfully",Toast.LENGTH_LONG).show();
                                            Intent ques1Intent=new Intent(SignupActivity.this,ques1_activity.class);
                                            startActivity(ques1Intent);
                                        }
                                    }
                                });
                            }
                            else {
                                loading.dismiss();
                                Toast.makeText(SignupActivity.this,"Failed To Create Account",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(SignupActivity.this,"Enter Email And Password",Toast.LENGTH_LONG).show();
                }

            }
        });
        AlreadyHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    private void InitalizeFields() {
        Email=findViewById(R.id.user_id);
        Password=findViewById(R.id.password_id);
        SignupButton=findViewById(R.id.signup_id);
        AlreadyHaveAccountButton=findViewById(R.id.already_account_id);
        mAuth=FirebaseAuth.getInstance();
        Users=FirebaseDatabase.getInstance().getReference().child("Users");

    }
}
