package com.esi.esihub.Home_Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esi.esihub.Helper_classes.Offre_formation;
import com.esi.esihub.Helper_classes.Offre_formation_adapter;
import com.esi.esihub.Helper_classes.User;
import com.esi.esihub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.esi.esihub.R.layout.offre_formations_model;


public class Formation_fragment extends Fragment {



    public Formation_fragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onStart(){
        super.onStart();
        //final LinearLayout MainLayout = (LinearLayout)getActivity().findViewById(R.id.Formation_mainLayout);

        final DatabaseReference Offres_Reference = FirebaseDatabase.getInstance().getReference("Offres").child("Formations");

        Offres_Reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    List<Offre_formation> offres_formation = new ArrayList<>();
                    final ListView listView = (ListView) getActivity().findViewById(R.id.Formation_mainLayout);
                    for(final DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                        final Offre_formation offre = postSnapshot.getValue(Offre_formation.class);
                        offres_formation.add(offre);
                    }
                    final Offre_formation_adapter adapter = new Offre_formation_adapter(getContext(), (ArrayList<Offre_formation>) offres_formation);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> parent, final View view, int position, long id) {
                            final Offre_formation item = (Offre_formation) adapter.getItem(position);
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
                                            Offres_Reference.child(item.getNom()).child("Interesses").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user.getEmail());

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

        return inflater.inflate(R.layout.fragment_formation_fragment, container, false);
    }
}