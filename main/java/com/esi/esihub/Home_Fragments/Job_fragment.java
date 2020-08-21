package com.esi.esihub.Home_Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esi.esihub.Helper_classes.Actualite;
import com.esi.esihub.Helper_classes.Offre_formation;
import com.esi.esihub.Helper_classes.Offre_job_adapter;
import com.esi.esihub.Helper_classes.User;
import com.esi.esihub.Helper_classes.offre_job;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Job_fragment extends Fragment {

    public Job_fragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();


        final DatabaseReference Offres_Reference = FirebaseDatabase.getInstance().getReference("Offres").child("Jobs");
        final DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());



        Offres_Reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                try{
                    List<offre_job> offres_job = new ArrayList<>();
                    final ListView listView = (ListView) getActivity().findViewById(R.id.Job_mainlayout);
                    for(final DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        final offre_job offre = postSnapshot.getValue(offre_job.class);
                        offres_job.add(offre);
                    }
                    final Offre_job_adapter adapter = new Offre_job_adapter(getContext(), (ArrayList<offre_job>) offres_job);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final offre_job item = (offre_job) adapter.getItem(position);
                            AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                            PopUp.setTitle(item.getNom());
                            PopUp.setMessage("Voulez-vous s'inscrire à cet offre?");
                            PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            PopUp.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    userReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            User user = dataSnapshot.getValue(User.class);
                                            UserReference.child(item.getNom()).child("Interesses").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user.getEmail());

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            });
                            PopUp.show();
                        }
                    });

                        //Pour s'inscrire.



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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_fragment, container, false);
    }
}