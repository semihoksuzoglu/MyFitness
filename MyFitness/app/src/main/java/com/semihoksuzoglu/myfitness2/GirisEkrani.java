package com.semihoksuzoglu.myfitness2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class GirisEkrani extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_ekrani);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void kiloVerme(View view) {
        hedefBelirleFonk("Kilo Vermek");
        Intent intent = new Intent(GirisEkrani.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void kiloAlma(View view) {
        hedefBelirleFonk("Kilo Almak");
        Intent intent = new Intent(GirisEkrani.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void kiloKoruma(View view) {
        hedefBelirleFonk("Kilo Korumak");
        Intent intent = new Intent(GirisEkrani.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void hedefBelirleFonk(String hedef) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
         DocumentReference guncelle = firebaseFirestore.collection("Kullanıcılar").document(firebaseUser.getEmail());

        guncelle.update(
                "Kullanıcı Hedefi", hedef
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GirisEkrani.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
