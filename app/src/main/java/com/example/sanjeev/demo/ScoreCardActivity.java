package com.example.sanjeev.demo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreCardActivity extends AppCompatActivity {
    private DatabaseReference ScoreRef;
    private FirebaseAuth mAuth;
    private RecyclerView scoreList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);
        toolbar=findViewById(R.id.score_card_bar_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ScoreCard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAuth=FirebaseAuth.getInstance();
        ScoreRef=FirebaseDatabase.getInstance().getReference().child("Score").child(mAuth.getCurrentUser().getUid());
        scoreList=findViewById(R.id.score_list);
        scoreList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        FirebaseRecyclerOptions<Score> options=new FirebaseRecyclerOptions.Builder<Score>()
                .setQuery(ScoreRef,Score.class)
                .build();


        FirebaseRecyclerAdapter<Score,ScoreViewHolder> adapter=new FirebaseRecyclerAdapter<Score, ScoreViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ScoreViewHolder holder, int position, @NonNull final Score model) {



                ScoreRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            holder.score.setText(model.getResult());
                            holder.date.setText(model.getDate());
                            holder.time.setText(model.getTime());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @NonNull
            @Override
            public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.score_display_layout,parent,false);
                ScoreViewHolder holder=new ScoreViewHolder(view);
                return holder;
            }
        };
        scoreList.setAdapter(adapter);
        adapter.startListening();

    }
    public static class ScoreViewHolder extends RecyclerView.ViewHolder {

        TextView score,time,date;
        public ScoreViewHolder(View itemView) {
            super(itemView);

            score=itemView.findViewById(R.id.score);
            time=itemView.findViewById(R.id.time_id);
            date=itemView.findViewById(R.id.date);
        }
    }

}
