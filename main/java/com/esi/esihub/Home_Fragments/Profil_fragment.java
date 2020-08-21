package com.esi.esihub.Home_Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esi.esihub.Etapes.Import_Documents_fragment;
import com.esi.esihub.Helper_classes.User;
import com.esi.esihub.Home_Activity;
import com.esi.esihub.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class Profil_fragment extends Fragment {

    private Uri filePath;
    private ImageView photo_profil;
    private ProgressDialog progressDialog;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference("Liste_Etudiants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    public Profil_fragment() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onStart(){
        super.onStart();

        final EditText Nom, Prenom, Esi_mail, other_mail, phone_number, birth_day, birth_place, wilaya;
        Button Edit_button = getActivity().findViewById(R.id.Edit_Button_profil),
                Cancel_button = getActivity().findViewById(R.id.Cancel_Button_profil);

        photo_profil = getActivity().findViewById(R.id.photo_profil);
        Nom = getActivity().findViewById(R.id.Nom_Edittext_profil);
        Prenom = getActivity().findViewById(R.id.Prenom_Edittext_profil);
        Esi_mail = getActivity().findViewById(R.id.Email_Edittext_profil);
        other_mail = getActivity().findViewById(R.id.OtherEmail_Edittext_profil);
        phone_number = getActivity().findViewById(R.id.Telephone_Edittext_profil);
        birth_day = getActivity().findViewById(R.id.BirthDay_Edittext_profil);
        birth_place = getActivity().findViewById(R.id.BirthPlace_Edittext_profil);
        wilaya = getActivity().findViewById(R.id.Wilaya_Edittext_profil);
        photo_profil = getActivity().findViewById(R.id.photo_profil);

        photo_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    try {

                        AlertDialog.Builder PopUp = new AlertDialog.Builder(getActivity());
                        PopUp.setPositiveButton("Importer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectPicture();
                                dialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setPositiveButton("Sauvegarder", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Picasso.get().load(filePath).into((ImageView) getActivity().findViewById(R.id.photo_profil));
                                        dialog.dismiss();
                                    }
                                });
                                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                final AlertDialog Preview = builder.create();
                                LayoutInflater inflater = getLayoutInflater();
                                View dialogLayout = inflater.inflate(R.layout.photo_profil_preview, null);

                                Preview.setView(dialogLayout);
                                Preview.show();



                            }
                        });
                        PopUp.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        PopUp.show();



                        //Fin
                    }catch (Exception e){
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    try{
                        ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        UserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    User user_db  = dataSnapshot.getValue(User.class);
                    Nom.setText(user_db.getNom());
                    Prenom.setText(user_db.getPrenom());
                    Esi_mail.setText(user_db.getEmail());
                    other_mail.setText(user_db.getOther_email());
                    phone_number.setText(user_db.getTelephone());
                    birth_day.setText(user_db.getDate_de_naissance());
                    birth_place.setText(user_db.getLieu_de_naissance());
                    wilaya.setText(user_db.getWilaya());

                    if (user_db.getLien_photo_profil() != null){
                        StorageReference Uri_Photo_profil = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Photo_profil");
                        Uri_Photo_profil.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ImageView photo_profil = getActivity().findViewById(R.id.photo_profil);
                                Glide.with(getContext()).load(uri).into(photo_profil);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        Edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(Nom.getText().toString())){
                    Nom.setError("Ce champs ne doit pas etre vide");
                    return;
                }
                if(TextUtils.isEmpty(Prenom.getText().toString())){
                    Prenom.setError("Ce champs ne doit pas etre vide");
                    return;
                }
                if(TextUtils.isEmpty(Esi_mail.getText().toString())){
                    Esi_mail.setError("Ce champs ne doit pas etre vide");
                    return;
                }

                UserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {

                            User user= dataSnapshot.getValue(User.class);
                            try{
                                if((filePath != null) && !(TextUtils.isEmpty(filePath.toString()))){
                                    uploadFile(filePath);
                                }
                                user.setNom(Nom.getText().toString());
                                user.setPrenom(Prenom.getText().toString());
                                user.setEmail(Esi_mail.getText().toString());
                                user.setOther_email(other_mail.getText().toString());
                                user.setDate_de_naissance(birth_day.getText().toString());
                                user.setLieu_de_naissance(birth_place.getText().toString());
                                user.setWilaya(wilaya.getText().toString());
                                user.setTelephone(phone_number.getText().toString());

                                UserReference.setValue(user);
                            }catch(Exception e){
                                e.printStackTrace();
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
        });


        Cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentLay, new Actualite_fragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }






    private void uploadFile(Uri filePath) {

        // Show
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading...");
        progressDialog.setProgress(0);
        progressDialog.show();


        StorageReference storageReference = storage.getReference();

        storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Photo_profil").putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        //To store the download url
                        UserReference.child("lien_photo_profil").setValue(url);
                        Toast.makeText(getContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Task fails", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            // Creating a progress tracker
                            int currentProgress = (int)(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setProgress(currentProgress);



                    }
                });


    }

    private void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
       // Picasso.get().load(filePath).into((ImageView) getActivity().findViewById(R.id.Preview_profil_profil_dialog));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPicture();
        }else{
            Toast.makeText(getContext(), "please provide a permission", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 86 && resultCode == getActivity().RESULT_OK && data != null) {
            filePath = data.getData();
        }else{
            Toast.makeText(getContext(), "Select a file", Toast.LENGTH_SHORT).show();
        }
    }
    
    
    
    
    
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_fragment, container, false);
    }
}