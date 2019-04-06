package com.codegud.flproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateSteps extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_steps);


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        final EditText numberOfStepsView = findViewById(R.id.user_inputted_steps_editText);
        Button changeDateView = findViewById(R.id.change_date_button);
        final TextView dateView = findViewById(R.id.date_for_transaction_textView);
        Button updateStepButton = findViewById(R.id.commit_transaction_button);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseFirestore.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        changeDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });
        updateStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String steps = numberOfStepsView.getText().toString();
                final int numberOfSteps = Integer.valueOf(steps);
                String date = dateView.getText().toString();

                Transactions transactions = new Transactions(numberOfSteps, user.getEmail(), date, user.getFirstName()+ " " + user.getLastName());
                transactions.setTeamName(user.getMembership());


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("transactions").add(transactions);
                long totalusersteps = user.getTotalStepsTaken() + numberOfSteps;
                db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("totalStepsTaken", totalusersteps);

                final DocumentReference documentReference = db.collection("teams").document(user.getMembership());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Team team = documentSnapshot.toObject(Team.class);
                        long oldAmount = team.getTotalNumberOfSteps();
                        team.setTotalNumberOfSteps(oldAmount + numberOfSteps);

                        documentReference.update("totalNumberOfSteps", oldAmount + numberOfSteps).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                finish();
                            }
                        });
                    }
                });

            }
        });


    }
}
