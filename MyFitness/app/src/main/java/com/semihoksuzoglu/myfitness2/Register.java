package com.semihoksuzoglu.myfitness2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText emailText, isimSoyisimText, boyText, kiloText, sifreText, sifreTekrarText;
    private TextView dogumTarihiText;
    String email;
    String sifre;
    String sifreTekrar;
    String isimSoyisim;
    String boy;
    String kilo;
    String dogumGunu;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailTxt);
        isimSoyisimText = findViewById(R.id.isimSoyisimText);
        dogumTarihiText = findViewById(R.id.dogumTarihiTxt);
        boyText = findViewById(R.id.boyTxt);
        kiloText = findViewById(R.id.kiloTxt);
        sifreText = findViewById(R.id.sifreTxt);
        sifreTekrarText = findViewById(R.id.sifreTekrarTxt);
        dogumTarihiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int yil = calendar.get(Calendar.YEAR);
                int ay = calendar.get(Calendar.MONTH);
                int gun = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, yil, ay, gun);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
    }

    public void girisYap(View view) {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void KayitOlClicked(View view) {
        email = emailText.getText().toString();
        sifre = sifreText.getText().toString();
        sifreTekrar = sifreTekrarText.getText().toString();
        isimSoyisim = isimSoyisimText.getText().toString();
        boy = boyText.getText().toString();
        kilo = kiloText.getText().toString();
        dogumGunu = dogumTarihiText.getText().toString();


        if (!isimSoyisim.equals("")) {
            if (!dogumGunu.equals("")) {
                if (!kilo.equals("")) {
                    if (!boy.equals("")) {
                        if (!email.equals("")) {
                            if (!sifre.equals("")) {
                                if (!sifreTekrar.equals("")) {
                                    if (sifre.equals(sifreTekrar)) {

                                        firebaseAuth.createUserWithEmailAndPassword(email, sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {


                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                HashMap<String, Object> postData = new HashMap<>();
                                                postData.put("İsim Soyisim", isimSoyisim);
                                                postData.put("Email", email);
                                                postData.put("Şifre", sifre);
                                                postData.put("Kilo", kilo);
                                                postData.put("Boy", boy);
                                                postData.put("Doğum Günü", dogumGunu);

                                                firebaseFirestore.collection("Kullanıcılar").document(email).set(postData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Register.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                                Intent intent = new Intent(Register.this, GirisEkrani.class);
                                                startActivity(intent);
                                                finish();
                                                Toast.makeText(Register.this, "Kayıt olundu", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Register.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    } else {
                                        Toast.makeText(Register.this, "Sifreler uyuşmamakta..", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Register.this, "Şifre Tekrar Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Register.this, "Şifre Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Register.this, "Email Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Register.this, "Boy Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Kilo Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Register.this, "Doğum Günü Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Register.this, "İsim Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
        }
    }
}
