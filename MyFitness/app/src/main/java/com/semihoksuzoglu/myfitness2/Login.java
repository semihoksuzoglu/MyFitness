package com.semihoksuzoglu.myfitness2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Login extends AppCompatActivity {


    EditText password, emailTxt;
    CheckBox show;

    FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.etPassword);
        emailTxt = findViewById(R.id.etEmail);
        show = findViewById(R.id.show);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        if (firebaseUser != null){


            CollectionReference collectionReference = firebaseFirestore.collection("Kullanıcılar");
            collectionReference.whereEqualTo("Email", firebaseUser.getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Toast.makeText(Login.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    if (queryDocumentSnapshots != null) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                            Map<String, Object> data = snapshot.getData();
                            String kullaniciHedefi_ = (String) data.get("Kullanıcı Hedefi");

                            if (kullaniciHedefi_ == null){
                                Intent intent = new Intent(Login.this, GirisEkrani.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                     }
                    }
                }
            });


        }

        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    public void kayitOl(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();

    }

    public void girisYap(View view) {
        String email = emailTxt.getText().toString();
        String sifre = password.getText().toString();

        if (!email.equals("") & !sifre.equals("")){
            firebaseAuth.signInWithEmailAndPassword(email, sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                      finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Login.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
