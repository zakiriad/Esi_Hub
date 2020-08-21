package com.esi.esihub.CV_Details;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.esi.esihub.Helper_classes.Formation;
import com.esi.esihub.Helper_classes.Langue;
import com.esi.esihub.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Ajouter_langue_fragment extends Fragment {

    private Uri filePath;
    private ProgressDialog progressDialog;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Spinner Nom , Niveau;
    private Button Choisir, Annuler, Ajouter, Consulter;
    private TextView NomFichier;
    private EditText NomCertificat;

    final DatabaseReference ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());



    public Ajouter_langue_fragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart(){
        super.onStart();


        Nom = getActivity().findViewById(R.id.Nom_Spinner_Ajout_langues);
        Niveau = getActivity().findViewById(R.id.Niveau_Spinner_Ajout_langues);
        Choisir = getActivity().findViewById(R.id.Choisir_Button_Ajout_langues);
        Annuler = getActivity().findViewById(R.id.Annuler_Button_Ajout_langues);
        Ajouter = getActivity().findViewById(R.id.Ajouter_Button_Ajout_langues);Consulter = getActivity().findViewById(R.id.Consulter_Button_Ajout_langues);
        NomFichier = getActivity().findViewById(R.id.Fichier_Text_Ajout_langues);
        NomCertificat = getActivity().findViewById(R.id.NomCertificat_Edittext_Ajout_langues);

        //ResumeReference = FirebaseDatabase.getInstance().getReference("Resumes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        Choisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    //File Selecter
                    selectPdf();
                }else{
                    try{
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });


        Consulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLay, new Visualiser_langues());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        Annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NomCertificat.setText("");
            }
        });


        Ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Nom.getSelectedItemPosition() == 0){
                    Toast.makeText(getActivity().getApplicationContext(),"Selectionner une langue",Toast.LENGTH_LONG).show();
                    return;
                }
                if(filePath != null){
                    if(TextUtils.isEmpty(NomCertificat.getText().toString())){
                        NomCertificat.setError("Veuillez indiquer ce champs");
                    }
                    uploadFile(filePath);
                    Langue langue = new Langue(Nom.getSelectedItem().toString(), Niveau.getSelectedItem().toString(),NomCertificat.getText().toString(), "");
                    ResumeReference.child("Langues").child(Nom.getSelectedItem().toString()).setValue(langue);
                }else{
                    Langue langue = new Langue(Nom.getSelectedItem().toString(), Niveau.getSelectedItem().toString(),NomCertificat.getText().toString(), "");
                    ResumeReference.child("Langues").child(Nom.getSelectedItem().toString()).setValue(langue);
                }
            }
        });

    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 86);
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "please provide a permission", Toast.LENGTH_SHORT).show();

        }
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 86 && resultCode == getActivity().RESULT_OK && data != null) {
            filePath = data.getData();
            NomFichier.setText(data.getData().getLastPathSegment().toString());
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "Select a file", Toast.LENGTH_SHORT).show();
        }
    }








    private void uploadFile(Uri filePath) {

        // montre l'evolution de l'upload
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final String fileName = Nom.getSelectedItem().toString();
        StorageReference storageReference = storage.getReference();
        storageReference.child(currentUser.getUid()).child(fileName).putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        ResumeReference.child("Langues").child(Nom.getSelectedItem().toString()).child("Nom").setValue(Nom.getSelectedItem().toString());
                        ResumeReference.child("Langues").child(Nom.getSelectedItem().toString()).child("lien_Certificat").setValue(url);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Task fails", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        // Creating a progress tracker
                        int currentProgress = (int)(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);
                        if(currentProgress == 100){
                            progressDialog.dismiss();
                        }
                    }
                });


    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ajouter_langue_fragment, container, false);
    }
}