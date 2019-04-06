package com.codegud.flproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NewUserActivity extends AppCompatActivity {

    private FirestoreRecyclerAdapter<Team, MyViewHolder> adapter; //Firebase UI Firestore Adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        final String firstName = getIntent().getStringExtra("firstName");
        final String lastName = getIntent().getStringExtra("lastName");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText newTeamNameView = findViewById(R.id.new_team_name_editText);
        Button createNewTeamButton = findViewById(R.id.create_new_team_button);
        createNewTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String teamName = newTeamNameView.getText().toString();
                User newUser = new User(auth.getCurrentUser().getEmail(), firstName, lastName);
                newUser.setMembership(teamName);

                Team newTeam = new Team(teamName);
                newTeam.addNewMember(newUser);

                firebaseFirestore.collection("users").document(auth.getCurrentUser().getUid()).set(newUser);
                firebaseFirestore.collection("teams").document(teamName).set(newTeam);

                startActivity(new Intent(NewUserActivity.this, DashboardActivity.class));
                finish();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.teams_to_pick_rv_layout);
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        Query query = rootRef.collection("teams");
        FirestoreRecyclerOptions<Team> options = new FirestoreRecyclerOptions.Builder<Team>()
                .setQuery(query, Team.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Team, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, int position, @NonNull final Team model) {
                holder.teamNameView.setText(model.getTeamName());

                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        final User newUser = new User(auth.getCurrentUser().getEmail(), firstName, lastName);
                        newUser.setMembership(model.getTeamName());
                        db.collection("users").document(auth.getCurrentUser().getUid()).set(newUser);

                        db.collection("teams").document(model.getTeamName()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Team temp = documentSnapshot.toObject(Team.class);
                                temp.addNewMember(new User(auth.getCurrentUser().getEmail(), firstName, lastName));

                                db.collection("teams").document(model.getTeamName()).set(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(NewUserActivity.this, DashboardActivity.class));
                                        finish();
                                    }
                                });
                            }
                        });

                    }
                });
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_card_rv, parent, false);
                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


    }


    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView teamNameView;
        private CardView layout;

        MyViewHolder(View itemView) {
            super(itemView);
            teamNameView = itemView.findViewById(R.id.team_name_rv_textView);
            layout =  itemView.findViewById(R.id.team_card_layout);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();//stop listening to Firestore db
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening(); //start listening Firestore db to fill recycleView

    }
}
