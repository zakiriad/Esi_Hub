package com.esi.esihub.Home_Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esi.esihub.Helper_classes.Actualite;
import com.esi.esihub.R;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Actualite_fragment extends Fragment {

    public Actualite_fragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public void onStart(){
        super.onStart();

        final LinearLayout MainLayout = (LinearLayout)getActivity().findViewById(R.id.Actualite_mainlayout);
        final DatabaseReference Actualites_Reference = FirebaseDatabase.getInstance().getReference("Actualites");

        Actualites_Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                        Actualite actualite = postSnapshot.getValue(Actualite.class);

                        LinearLayout nouvelle_actualite = new LinearLayout(getActivity());
                        nouvelle_actualite.setOrientation(LinearLayout.HORIZONTAL);

                        TextView Titre = new TextView(getActivity());
                        Titre.setText(actualite.getTitre());

                        TextView sousTitre = new TextView(getActivity());
                        sousTitre.setText(actualite.getSousTitre());

                        nouvelle_actualite.addView(Titre);
                        nouvelle_actualite.addView(sousTitre);

                        MainLayout.addView(nouvelle_actualite);
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_actualite_fragment, container, false);
    }
}