package com.esi.esihub.CV_Details;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.esi.esihub.Helper_classes.Competence;
import com.esi.esihub.Helper_classes.Experience;
import com.esi.esihub.Helper_classes.Formation;
import com.esi.esihub.Helper_classes.Langue;
import com.esi.esihub.Helper_classes.Resume;

import com.esi.esihub.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Resume_fragment extends Fragment {
    private TextView apropos, Nom;
    private LinearLayout Contact, Skills, Langue, Education, Experience;
    private ImageView photo;



    public Resume_fragment() {}


    @Override
    public  void onStart(){
        super.onStart();

        try{


        Nom = getActivity().findViewById(R.id.Nom_resume);
        photo = getActivity().findViewById(R.id.photo_profil_resume);
        apropos = getActivity().findViewById(R.id.Apropos_section_resume);
        Contact = getActivity().findViewById(R.id.Contact_section_resume);
        Skills = getActivity().findViewById(R.id.Skills_section_resume);
        Langue = getActivity().findViewById(R.id.Langues_section_resume);
        Education = getActivity().findViewById(R.id.Education_section_resume);
        Experience = getActivity().findViewById(R.id.Experience_section_resume);


        DatabaseReference UserResumeReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DatabaseReference LanguesReference = UserResumeReference.child("Langues");
        DatabaseReference EducationReference = UserResumeReference.child("Formations");
        DatabaseReference ExcperiencesReference = UserResumeReference.child("Experiences");
        DatabaseReference SkillsReference = UserResumeReference.child("Competences");

        DatabaseReference AproposReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("apropos");
        AproposReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apropos.setText(dataSnapshot.getValue(String.class));
                apropos.setTextColor(getActivity().getColor(R.color.Black));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Chargment du Nom et de la section Apropos SectionApropos

            UserReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Resume userResume = dataSnapshot.getValue(Resume.class);

                        Nom.setText(userResume.getNom()+" "+userResume.getPrenom());
                        Contact.setOrientation(LinearLayout.VERTICAL);

                        if(userResume.getLien_photo_profil() != null){
                            StorageReference PhotoProfil = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Photo_profil");
                            PhotoProfil.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ImageView photoProfil = getActivity().findViewById(R.id.photo_profil_resume);
                                    Glide.with(getContext()).load(uri).into(photoProfil);
                                }
                            });
                        }

                        // Les parametres:
                        LinearLayout.LayoutParams FirstTextViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        FirstTextViewParams.setMargins(15, 60, 20,0);

                        LinearLayout.LayoutParams TextViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        TextViewParams.setMargins(15, 60, 20,0);


                        //Les Informations
                        TextView Esi_Email = new TextView(getActivity());
                        Esi_Email.setText(userResume.getEmail());
                        Esi_Email.setTextColor(getActivity().getColor(R.color.White));
                        Esi_Email.setLayoutParams(FirstTextViewParams);

                        TextView autre_Email = new TextView(getActivity());
                        autre_Email.setTextColor(getActivity().getColor(R.color.White));
                        autre_Email.setText(userResume.getOther_email());
                        autre_Email.setLayoutParams(TextViewParams);

                        TextView Telephone = new TextView(getActivity());
                        Telephone.setTextColor(getActivity().getColor(R.color.White));
                        Telephone.setText(userResume.getTelephone());
                        Telephone.setLayoutParams(TextViewParams);

                        TextView wilaya = new TextView(getActivity());
                        wilaya.setTextColor(getActivity().getColor(R.color.White));
                        wilaya.setText(userResume.getWilaya());
                        wilaya.setLayoutParams(TextViewParams);


                        Contact.addView(Esi_Email);
                        Contact.addView(Telephone);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        //Chargment, modifications et insertion des Langues  SectionLangue

            Langue.setOrientation(LinearLayout.VERTICAL);
            Langue.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                    PopUp.setTitle("Que voulez vous Faire");
                    PopUp.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentLay, new Ajouter_langue_fragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    PopUp.show();
                    return true;
                }
            });


            LanguesReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int cpt = 0;
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.esi.esihub.Helper_classes.Langue langue = snapshot.getValue(Langue.class);




                        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 20,1);
                        layoutParam.setMargins(20, 0, 0,0);
                        LinearLayout Nouvelle_langue = new LinearLayout(getActivity());
                        Nouvelle_langue.setOrientation(LinearLayout.HORIZONTAL);
                        Nouvelle_langue.setLayoutParams(layoutParam);


                        LinearLayout.LayoutParams NomParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        NomParam.setMargins(0, 0, 10, 0);
                        LinearLayout.LayoutParams NiveauParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        NiveauParam.setMargins(0, 0, 0, 0);

                        TextView Nom = new TextView(getActivity());
                        Nom.setText(langue.getNom());
                        Nom.setTextColor(getActivity().getColor(R.color.White));
                        Nom.setLayoutParams(NomParam);

                        TextView Niveau = new TextView(getActivity());
                        Niveau.setText(langue.getNiveau());
                        Niveau.setTextColor(getActivity().getColor(R.color.White));
                        Niveau.setLayoutParams(NiveauParam);

                        Nouvelle_langue.addView(Nom);
                        Nouvelle_langue.addView(Niveau);

                        Langue.addView(Nouvelle_langue);
                        cpt ++;
                        if(cpt == 3){
                            break;
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });







        //Chargement, modifications et insertion des Formations SectionEducation

            Education.setOrientation(LinearLayout.VERTICAL);
            Education.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                    PopUp.setTitle("Que voulez vous Faire");
                    PopUp.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentLay, new Ajout_formation_fragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    PopUp.show();
                    return true;
                }
            });
            EducationReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Formation formation = snapshot.getValue(Formation.class);


                        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        layoutParam.setMargins(5,0,0,0);
                        LinearLayout Nouvelle_formation = new LinearLayout(getActivity());
                        Nouvelle_formation.setOrientation(LinearLayout.HORIZONTAL);
                        Nouvelle_formation.setLayoutParams(layoutParam);



                        LinearLayout.LayoutParams NomParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        NomParam.setMargins(0, 0, 0, 0);
                        LinearLayout.LayoutParams DateParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        DateParam.setMargins(10, 0, 0, 0);

                        TextView Nom = new TextView(getActivity());
                        Nom.setText(formation.getNom());
                        Nom.setTextColor(getActivity().getColor(R.color.Black));
                        Nom.setLayoutParams(NomParam);

                        TextView Date = new TextView(getActivity());
                        Date.setText(formation.getDateFin());
                        Date.setTextColor(getActivity().getColor(R.color.Black));
                        Date.setLayoutParams(DateParam);


                        Nouvelle_formation.addView(Date);
                        Nouvelle_formation.addView(Nom);


                        Education.addView(Nouvelle_formation);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });







        //Chargement, modifications et insertion des Experiences SectionExperiences


            Experience.setOrientation(LinearLayout.VERTICAL);
            Experience.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                    PopUp.setTitle("Que voulez vous Faire");
                    PopUp.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentLay, new Ajout_Experience_fragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    PopUp.show();
                    return true;
                }
            });


            ExcperiencesReference.addValueEventListener(new ValueEventListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        com.esi.esihub.Helper_classes.Experience experience = snapshot.getValue(Experience.class);



                        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        layoutParam.setMargins(5,10,0,0);
                        LinearLayout Nouvelle_experience = new LinearLayout(getActivity());
                        Nouvelle_experience.setLayoutParams(layoutParam);
                        Nouvelle_experience.setOrientation(LinearLayout.HORIZONTAL);

                        LinearLayout.LayoutParams NomParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        NomParam.setMargins(0, 0, 0, 0);
                        LinearLayout.LayoutParams DateParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        DateParam.setMargins(10, 0, 0, 0);

                        TextView Nom = new TextView(getActivity());
                        Nom.setText(experience.getNom());
                        Nom.setTextColor(R.color.Black);
                        Nom.setLayoutParams(NomParam);

                        TextView Date = new TextView(getActivity());
                        Date.setText(experience.getDateDebut());
                        Date.setTextColor(R.color.Black);
                        Date.setLayoutParams(DateParam);


                        Nouvelle_experience.addView(Date);
                        Nouvelle_experience.addView(Nom);


                        Experience.addView(Nouvelle_experience);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            // SectionSkills:
            Skills.setOrientation(LinearLayout.VERTICAL);
            Skills.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                    PopUp.setTitle("Que voulez vous Faire");
                    PopUp.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentLay, new Ajout_Competence_fragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    PopUp.show();

                    return true;
                }
            });

            SkillsReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Competence competence = snapshot.getValue(Competence.class);

                        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        LinearLayout Nouvelle_competence = new LinearLayout(getActivity());
                        Nouvelle_competence.setLayoutParams(layoutParam);
                        Nouvelle_competence.setOrientation(LinearLayout.HORIZONTAL);

                        LinearLayout.LayoutParams NomParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        NomParam.setMargins(0, 10, 0, 10);
                        LinearLayout.LayoutParams NiveauParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
                        NiveauParam.setMargins(10, 10, 10, 10);

                        TextView Nom = new TextView(getActivity());
                        Nom.setText(competence.getNom());
                        Nom.setTextColor(getActivity().getColor(R.color.White));
                        Nom.setLayoutParams(NomParam);

                        TextView Niveau = new TextView(getActivity());
                        Niveau.setText("("+competence.getNiveau()+")");
                        Niveau.setTextColor(getActivity().getColor(R.color.White));
                        Niveau.setLayoutParams(NiveauParam);


                        Nouvelle_competence.addView(Nom);
                        Nouvelle_competence.addView(Niveau);



                        Skills.addView(Nouvelle_competence);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





            apropos.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                    PopUp.setTitle("Que Voulez vous faire");
                    PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    PopUp.setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragmentLay, new Apropos_fragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    PopUp.show();
                    return true;
                }
            });
        }catch (Exception e){
            Log.e("ereur", e.toString());

        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resume_fragment, container, false);
    }
}