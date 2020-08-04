package com.esi.esihub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.esi.esihub.Helper_classes.Resume;
import com.esi.esihub.Helper_classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_Activity extends AppCompatActivity {

    private EditText nom, prenom, email, MotDePasse;
    private Spinner Niveau;
    private Button Confirmer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        final DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants");
        final DatabaseReference ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes");

        this.nom = (EditText)findViewById(R.id.Nom_SignUp);
        this.prenom = (EditText)findViewById(R.id.Preom_SignUp);
        this.email = (EditText)findViewById(R.id.Email_Esi_SignUp);
        this.MotDePasse = (EditText)findViewById(R.id.MotDePasse_SignUp);
        this.Niveau = (Spinner)findViewById(R.id.Niveau_SignUp);
        this.Confirmer = (Button)findViewById(R.id.confirm_button);


        Confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nom_content = nom.getText().toString();
                final String prenom_content = prenom.getText().toString ();
                final String email_content = email.getText().toString();
                final String MotDePasse_content = MotDePasse.getText().toString();
                final int Niveau_selected = Niveau.getSelectedItemPosition();

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
                }


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_content, MotDePasse_content)
                        .addOnCompleteListener(SignUp_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){

                                }else{

                                    Resume resume = new Resume(nom_content, prenom_content, email_content, Niveau_selected, false);
                                    User user = new User(nom_content, prenom_content, email_content, Niveau_selected, false);
                                    UserReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                                    ResumeReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(resume);


                                    startActivity(new Intent(getApplicationContext(), LogIn_Activity.class));
                                    finish();

                                }
                            }
                        });


            }
        });




    }
}