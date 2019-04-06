package com.codegud.flproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MainFragment extends Fragment {
    private FirestoreRecyclerAdapter<Transactions, MyViewHolder> adapter; //Firebase UI Firestore Adapter


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        Query query = rootRef.collection("transactions").whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()).orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Transactions> options = new FirestoreRecyclerOptions.Builder<Transactions>()
                .setQuery(query, Transactions.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Transactions, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder holder, int position, @NonNull final Transactions model) {
                holder.numberOfStepsView.setText( model.getNumberOfSteps() + " steps added steps on " + model.getDate());

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_card_rv, parent, false);
                return new MyViewHolder(view);
            }
        };

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView totalStepView = getView().findViewById(R.id.total_steps_textView);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User currentUser = documentSnapshot.toObject(User.class);
                Toast.makeText(getContext(),currentUser.getEmail(),Toast.LENGTH_SHORT).show();
                totalStepView.setText(currentUser.getTotalStepsTaken() + "");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        Button button = getView().findViewById(R.id.add_more_steps_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UpdateSteps.class));
            }
        });


        RecyclerView recyclerView = getView().findViewById(R.id.transaction_history_current_user_rv_layout);


        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numberOfStepsView;

        MyViewHolder(View itemView) {
            super(itemView);
            numberOfStepsView = itemView.findViewById(R.id.temp_step_transactions_textView);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

}
