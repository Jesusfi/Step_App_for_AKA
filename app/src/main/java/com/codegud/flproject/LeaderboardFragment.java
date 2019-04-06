package com.codegud.flproject;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {


    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference reference = db.collection("teams");
        Query query = reference;

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    long biggest = 0 ;
                    String teamName = "";
                    for (DocumentSnapshot document : task.getResult()) {
                        Team team = document.toObject(Team.class);
                        if(team.getTotalNumberOfSteps()> biggest){
                            biggest = team.getTotalNumberOfSteps();
                            teamName = team.getTeamName();

                        }

                    }

                    TextView allTimeTeamName = getView().findViewById(R.id.all_time_team_name_textView);
                    TextView allTimeStepCount = getView().findViewById(R.id.all_time_step_count_textView);
                    allTimeTeamName.setText("Team : "+teamName);
                    allTimeStepCount.setText("Step Count :" +biggest );

                    TextView weekTeamName =  getView().findViewById(R.id.week_team_name_textView);
                    TextView weekStepCount = getView().findViewById(R.id.week_step_count_textView);
                    weekTeamName.setText("Team :" + teamName);
                    weekStepCount.setText("Step Count :" +biggest);


                }
            }
        });





        final CollectionReference reference2 = db.collection("transactions");
        Query query2 = reference2;

        query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    long mostSteps = 0;
                    String teamWithMostSteps = "";
                    for(DocumentSnapshot documentSnapshot: task.getResult()){
                        Transactions transaction = documentSnapshot.toObject(Transactions.class);
                        Date current =  returnFormattedDate(transaction.getDate());
                        HashMap<String, Integer> map = new HashMap<>();
                        if(isDateInCurrentWeek(current)){

                        }
                    }
                }
            }
        });


        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }
    public Date returnFormattedDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(), date.toString(), Toast.LENGTH_SHORT).show();
        return date;
    }
    public boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }
}
