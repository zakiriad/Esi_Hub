package com.esi.esihub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword_activity extends AppCompatActivity {
    private EditText email;
    private Button Annuler, Envoyer;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        this.email = findViewById(R.id.Email_ResetPassword);
        this.Annuler = findViewById(R.id.Annuler_Button);
        this.Envoyer = findViewById(R.id.Send_Button);
        auth = FirebaseAuth.getInstance();


        Annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resetPassword_activity.this, LogIn_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        Envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Entrer votre email");
                }
                try {
                auth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Email Envoyé", Toast.LENGTH_LONG).show();
                                    Envoyer.setVisibility(View.GONE);
                                    email.setVisibility(View.GONE);
                                    Annuler.setText("Retour");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}