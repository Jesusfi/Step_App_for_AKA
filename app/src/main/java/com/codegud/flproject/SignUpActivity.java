package com.codegud.flproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        Button signUpButton = findViewById(R.id.sign_up_button);


        final EditText emailView = findViewById(R.id.email_address_sign_up_editText);
        final EditText passwordView = findViewById(R.id.password_sign_up_editText);
        EditText passswordConfirm = findViewById(R.id.password_confirm_sign_up_editText);
        final EditText firstNameView = findViewById(R.id.first_name_sign_up_editText);
        final EditText lastNameView = findViewById(R.id.last_name_sign_up_editText);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailView.getText().toString().trim();
                String password = passwordView.getText().toString();
                String firstName = firstNameView.getText().toString().trim();
                String lastName = lastNameView.getText().toString().trim();
                signUpUser(email, password, firstName, lastName);
            }
        });


    }

    private void signUpUser(String email, String password, final String firstName, final String lastName) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, firstName, lastName);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, null, null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user, String firstName, String lastName) {
        if (user != null) {
            //User newUser = new User(user.getEmail(), firstName, lastName);
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("Users").add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SignUpActivity.this, NewUserActivity.class));
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getApplicationContext(), "try again",Toast.LENGTH_SHORT).show();
//
//                }
//            });

            Intent intent = new Intent(SignUpActivity.this, NewUserActivity.class);
            intent.putExtra("firstName", firstName);
            intent.putExtra("lastName",lastName);
            startActivity(intent);
            finish();

        }
    }
}
