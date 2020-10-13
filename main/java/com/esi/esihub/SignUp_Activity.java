package com.esi.esihub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esi.esihub.Helper_classes.Resume;
import com.esi.esihub.Helper_classes.User;
import com.esi.esihub.Helper_classes.Verification_Dossier;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUp_Activity extends AppCompatActivity {

    private EditText nom, prenom, email, MotDePasse;
    private Spinner Niveau, Specialite, genre;
    private Button Confirmer;
    private TextView connexion_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        final DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants");
        final DatabaseReference ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes");

        final DatabaseReference AlumniReference = FirebaseDatabase.getInstance().getReference("Alumni");

        this.nom = (EditText)findViewById(R.id.Nom_SignUp);
        this.prenom = (EditText)findViewById(R.id.Prenom_SignUp);
        this.email = (EditText)findViewById(R.id.Email_Esi_SignUp);
        this.MotDePasse = (EditText)findViewById(R.id.MotDePasse_SignUp);
        this.Niveau = (Spinner)findViewById(R.id.Niveau_SignUp);
        this.Specialite = (Spinner)findViewById(R.id.Specialite_SignUp);
        this.Confirmer = (Button)findViewById(R.id.confirm_button);
        this.genre = findViewById(R.id.Genre_SignUp);
        this.connexion_txt = findViewById(R.id.connexion_text_Signup);


        connexion_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_Activity.this, LogIn_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        Confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nom_content = nom.getText().toString();
                final String prenom_content = prenom.getText().toString ();
                final String email_content = email.getText().toString();
                final String MotDePasse_content = MotDePasse.getText().toString();
                final int Niveau_selected = Niveau.getSelectedItemPosition();
                final String Specialite_selected = (String) Specialite.getSelectedItem();
                final String Genre = (String) genre.getSelectedItem();

                if(TextUtils.isEmpty(nom_content)){
                    nom.setError("veuillez indiquer votre nom");
                    return;
                }
                if (TextUtils.isEmpty(prenom_content)){
                    prenom.setError("veuillez indiquer votre prenom");
                    return;
                }
                if(TextUtils.isEmpty(email_content)){
                    email.setError("veuillez indiquer votre email");
                    return;
                }
                if(TextUtils.isEmpty(MotDePasse_content)){
                    MotDePasse.setError("veuillez indiquer votre Mot de passe");
                    return;
                }else if(!email_content.contains("esi-sba.dz")){
                    email.setError("veuillez saisir votre email fourni par l'etablissement");
                }
                if(Niveau_selected == 0){
                    Toast.makeText(getApplicationContext(), "Selectionner un niveau", Toast.LENGTH_LONG).show();
                    return;
                }else if((Niveau_selected >= 4)&&(Specialite.getSelectedItemPosition() == 0)){
                    Toast.makeText(getApplicationContext(), "Selectionner une specialite", Toast.LENGTH_LONG).show();
                    return;
                }
                if(genre.getSelectedItemPosition() == 0){
                    Toast.makeText(getApplicationContext(), "Selectionner un genre", Toast.LENGTH_LONG).show();
                    return;
                }

                DatabaseReference Liste_Emails;
                switch (Niveau_selected){
                    case 1:
                        Liste_Emails = FirebaseDatabase.getInstance().getReference("1CPI");
                        break;
                    case 2:
                        Liste_Emails = FirebaseDatabase.getInstance().getReference("2CPI");
                        break;
                    case 3:
                        Liste_Emails = FirebaseDatabase.getInstance().getReference("1CS");
                        break;
                    case 4:
                        Liste_Emails = FirebaseDatabase.getInstance().getReference("2CS");
                        break;
                    case 5:
                        Liste_Emails = FirebaseDatabase.getInstance().getReference("3CS");
                        break;
                    default:
                        Liste_Emails = FirebaseDatabase.getInstance().getReference("3CS");
                        break;
                }


                final boolean[] found = {false};
                Liste_Emails.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try{

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String email_item = snapshot.getValue(String.class);
                                if (email_item.contains(email_content)) found[0] = true;
                            }

                        }catch (Exception e){
                            Log.e("error_Query", e.toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (found[0]) {
                            //Create the user
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_content, MotDePasse_content)
                                    .addOnCompleteListener(SignUp_Activity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "vérifier vos données", Toast.LENGTH_LONG).show();
                                            } else {

                                                Resume resume = new Resume(nom_content, prenom_content, email_content, Niveau_selected, false, "", Genre);
                                                User user = new User(nom_content, prenom_content, email_content, Niveau_selected, Genre, false);
                                                Verification_Dossier verificationDossier = new Verification_Dossier();

                                                if ((Niveau_selected >= 4)&&(Niveau_selected != 6)) {
                                                    user.setSpecialite(Specialite_selected);
                                                    resume.setSpecialite(Specialite_selected);
                                                }
                                                if(Niveau_selected == 6){
                                                    user.setSpecialite(Specialite_selected);
                                                    resume.setSpecialite(Specialite_selected);
                                                    user.setAlumni(true);
                                                    resume.setAlumni(true);
                                                    user.setNiveau(0);
                                                    resume.setNiveau(0);
                                                }
                                                if(Niveau_selected != 6) {
                                                    UserReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                                                }
                                                else {
                                                    AlumniReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                                                }
                                                ResumeReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(resume);


                                                try {
                                                    ResumeReference.child("Langues").push();
                                                    ResumeReference.child("Experiences").push();
                                                    ResumeReference.child("Competences").push();
                                                    ResumeReference.child("Formations").push();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                startActivity(new Intent(getApplicationContext(), LogIn_Activity.class));
                                                finish();

                                            }
                                        }
                                    });
                            //
                        } else {
                            Toast.makeText(getApplicationContext(), "cette adresse est introuvable", Toast.LENGTH_LONG).show();
                        }

                    }
                },5000);







            }
        });




    }
}