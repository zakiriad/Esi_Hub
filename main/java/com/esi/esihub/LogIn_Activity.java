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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn_Activity extends AppCompatActivity {
    private EditText email, password;
    private Button Login, Inscription;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_);

        this.email = (EditText) findViewById(R.id.Email_Login);
        this.password = (EditText) findViewById(R.id.MotDePasse_Login);
        this.Login = (Button) findViewById(R.id.LogIn_Button);
        this.Inscription = (Button) findViewById(R.id.SignUp_Button);

        Inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp_Activity.class));
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Home_Activity.class));
        }else{
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String mail = email.getText().toString();
                    final String MotDePasse = password.getText().toString();

                    if(TextUtils.isEmpty(mail)){
                        Toast.makeText(getApplicationContext(), "Saisissez votre Email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(MotDePasse)){
                        Toast.makeText(getApplicationContext(), "Saisissez votre mot de passe", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    auth.signInWithEmailAndPassword(mail, MotDePasse)
                            .addOnCompleteListener(LogIn_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (MotDePasse.length() < 6) {
                                            password.setError("too short");
                                        }
                                        else if(! mail.contains("@esi-sba.dz")){
                                            email.setError("Not an Esi Mail");
                                        }else {
                                            Toast.makeText(LogIn_Activity.this, "Wrong Entries", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(LogIn_Activity.this, Home_Activity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            });
        }
    }
}