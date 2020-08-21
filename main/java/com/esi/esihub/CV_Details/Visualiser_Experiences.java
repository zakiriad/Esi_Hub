package com.esi.esihub.CV_Details;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esi.esihub.Helper_classes.Experience;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Visualiser_Experiences extends Fragment {
    final DatabaseReference ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private LinearLayout MainLayout;
    private Button retour;



    public Visualiser_Experiences() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onStart(){
        super.onStart();
        MainLayout = getActivity().findViewById(R.id.Vis_experiences_mainlayout);
        retour = getActivity().findViewById(R.id.Retour_Button_vis_experiences);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLay, new Resume_fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        try{
            ResumeReference.child("Experiences").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        final Experience experience = postSnapshot.getValue(Experience.class);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                        layoutParams.setMargins(10,0,10,20);
                        LinearLayout new_experience = new LinearLayout(getActivity());
                        new_experience.setOrientation(LinearLayout.HORIZONTAL);
                        new_experience.setLayoutParams(layoutParams);

                        TextView Nom = new TextView(getActivity());
                        Nom.setText(experience.getNom()+" ("+experience.getDateDebut()+"-"+experience.getDateFin()+")");
                        Nom.setTextSize(18);
                        Nom.setTextColor(getActivity().getColor(R.color.Black));

                        Button Supprimer = new Button(getActivity());
                        Supprimer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 3));
                        Supprimer.setText("Supprimer");
                        Supprimer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                postSnapshot.getRef().removeValue();
                            }
                        });

                        new_experience.addView(Nom);
                        new_experience.addView(Supprimer);

                        MainLayout.addView(new_experience);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visualiser__experiences, container, false);
    }
}