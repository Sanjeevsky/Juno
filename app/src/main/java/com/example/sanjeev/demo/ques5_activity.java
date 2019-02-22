package com.example.sanjeev.demo;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ques5_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    AppCompatButton b1;
    AppCompatButton  b2;
    AppCompatButton  b3;
    AppCompatButton  b4;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    String result;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private CircleImageView headerImage;
    private TextView headerTitle;

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
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
                        Intent mainIntent = new Intent(ques5_activity.this, MainActivity.class);
                        startActivity(mainIntent);

                    }
                    if (which == 1) {
                        Toast.makeText(ques5_activity.this,"Back Button Pressed",Toast.LENGTH_LONG).show();

                    }
                }
            });

            builder.show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer5);
        b1=(AppCompatButton )findViewById(R.id.btn1);
        b2=(AppCompatButton )findViewById(R.id.btn2);
        b3=(AppCompatButton )findViewById(R.id.btn3);
        b4=(AppCompatButton )findViewById(R.id.btn4);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        final Intent intent=new Intent(ques5_activity.this,ques6_activity.class);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_id);
        navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_closed);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        View navHeader=navigationView.getHeaderView(0);
        headerImage=navHeader.findViewById(R.id.userImage_id);
        headerTitle=navHeader.findViewById(R.id.userName_id);
        Resources res = getResources();
        Bundle bundle=getIntent().getExtras();
        result=bundle.getString("ques4");
        final int res_int=Integer.parseInt(result);
        final Drawable green = res. getDrawable(R.drawable.green_button);
        final Drawable red=res.getDrawable(R.drawable.red_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                b1.setBackground(green);
                intent.putExtra("ques5",""+(res_int+1));
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                b2.setBackground(red);
                b1.setBackground(green);
                intent.putExtra("ques5",""+(res_int));
                startActivity(intent);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                b3.setBackground(red);
                b1.setBackground(green);
                intent.putExtra("ques5",""+(res_int));
                startActivity(intent);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                b4.setBackground(red);
                b1.setBackground(green);
                intent.putExtra("ques5",""+(res_int));
                startActivity(intent);
            }
        });
        mAuth=FirebaseAuth.getInstance();
        RootRef=FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.hasChild("image"))
                    {
                        Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(headerImage);
                    }
                    if(dataSnapshot.hasChild("name"))
                    {
                        headerTitle.setText(dataSnapshot.child("name").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        switch(id)
        {
            case R.id.logout:
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent mainIntent=new Intent(ques5_activity.this,MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.settings:
                Intent settingIntent=new Intent(ques5_activity.this,ScoreCardActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.scorecard:
                Toast.makeText(getApplicationContext(),"Indox",Toast.LENGTH_SHORT).show();
                Intent scoreIntent=new Intent(ques5_activity.this,ScoreCardActivity.class);
                startActivity(scoreIntent);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}
