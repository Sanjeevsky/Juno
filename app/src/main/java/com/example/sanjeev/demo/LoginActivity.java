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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button LoginButton,NeedNewAccountButton;
    private EditText Email,Password;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loading=new ProgressDialog(this);

        InitializeFields();
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=Email.getText().toString();
                password=Password.getText().toString();
                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password))
                {loading.setTitle("Logging In");
                loading.setMessage("Please Wait");
                loading.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this,"Successful",Toast.LENGTH_LONG).show();
                                Intent ques1Intent=new Intent(LoginActivity.this,ques1_activity.class);
                                startActivity(ques1Intent);
                                loading.dismiss();

                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,""+task.getException(),Toast.LENGTH_LONG).show();
                                loading.dismiss();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Enter Email and Password",Toast.LENGTH_LONG).show();
                }

            }
        });

        NeedNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignupIntent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(SignupIntent);
            }
        });
    }

    private void InitializeFields() {
        LoginButton=findViewById(R.id.login_id);
        NeedNewAccountButton=findViewById(R.id.need_new_account_id);
        Email=findViewById(R.id.user_id);
        Password=findViewById(R.id.password_id);
        mAuth=FirebaseAuth.getInstance();
    }
}
