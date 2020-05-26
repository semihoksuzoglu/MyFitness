package com.semihoksuzoglu.myfitness2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileFragment extends Fragment {

    private Spinner hedeflerSpinner;
    private Button cikisYap;
    private Button guncelle;
    private TextView dogumTarihiProfile;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private TextView  dogumGunu;
    private EditText isimSoyisim, boy, kilo, sifre, sifreTekrar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        hedeflerSpinner = view.findViewById(R.id.hedefDegisSpinner);

        cikisYap = view.findViewById(R.id.CikisYapBtn);
        guncelle = view.findViewById(R.id.GuncelleButton);
        isimSoyisim = view.findViewById(R.id.isimSoyisimProfile);
        dogumGunu = view.findViewById(R.id.dogumTarihiProfile);
        boy = view.findViewById(R.id.boyProfile);
        kilo = view.findViewById(R.id.kiloProfile);
        sifre = view.findViewById(R.id.sifreProfile);
        sifreTekrar = view.findViewById(R.id.sifreTekrarProfile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, getResources().getStringArray(R.array.Hedefler));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        hedeflerSpinner.setAdapter(adapter);

        dogumTarihiProfile = view.findViewById(R.id.dogumTarihiProfile);
        dogumTarihiProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int yil = calendar.get(Calendar.YEAR);
                int ay = calendar.get(Calendar.MONTH);
                int gun = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(v.getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,yil,ay,gun);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yil, int ay, int gun) {
                ay = ay+1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: "+ ay + "/" + gun + "/" + yil);
                String tarih = gun + "/" + ay + "/" + yil;
                dogumTarihiProfile.setText(tarih);
            }
        };

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                final DocumentReference guncelle = firebaseFirestore.collection("Kullanıcılar").document(firebaseUser.getEmail());

                final String sifre_ = sifre.getText().toString();
                final String sifreTekrar_ = sifreTekrar.getText().toString();
                final String isimSoyisim_ = isimSoyisim.getText().toString();
                final String dogumGunu_ = dogumGunu.getText().toString();
                final String kilo_ = kilo.getText().toString();
                final String boy_ = boy.getText().toString();
                String hedefBelirle = hedeflerSpinner.getSelectedItem().toString();

                if (!isimSoyisim_.equals("")) {
                    if (!dogumGunu_.equals("")) {
                        if (!kilo_.equals("")) {
                            if (!boy_.equals("")) {
                                guncelle.update(
                                        "Kilo", kilo.getText().toString(),
                                        "Boy", boy.getText().toString(),
                                        "İsim Soyisim", isimSoyisim.getText().toString(),
                                        "Doğum Günü", dogumGunu.getText().toString(),
                                        "Kullanıcı Hedefi", hedefBelirle
                                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        if (!sifre_.equals("")) {
                                            if (sifre_.length() > 5) {
                                                if (sifre_.equals(sifreTekrar_)) {

                                                    guncelle.update("Şifre", sifre.getText().toString());
                                                    Toast.makeText(getContext(), "Güncellendi", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(getContext(), "Şifreler Uyuşmamakta", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(getContext(), "Şifre Uzunluğu 6 dan az olamaz", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(getContext(), "Güncellendi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "Boy Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Kilo Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Doğum Günü Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "İsim Boş Bırakılamaz..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        getDataFromFirestore();

        return view;
    }


    public void getDataFromFirestore() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        CollectionReference collectionReference = firebaseFirestore.collection("Kullanıcılar");
        collectionReference.whereEqualTo("Email", firebaseUser.getEmail()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getActivity(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                if (queryDocumentSnapshots != null) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();

                        String i̇sim_soyisim = (String) data.get("İsim Soyisim");
                        String boy_ = (String) data.get("Boy");
                        String dogumGunu_ = (String) data.get("Doğum Günü");
                        String kilo_ = (String) data.get("Kilo");
                        String kullaniciHedefi_ = (String) data.get("Kullanıcı Hedefi");

                        if (kullaniciHedefi_.equals("Kilo Vermek")) {
                            hedeflerSpinner.setSelection(0);
                        } else if (kullaniciHedefi_.equals("Kilo Almak")) {
                            hedeflerSpinner.setSelection(1);
                        } else if (kullaniciHedefi_.equals("Kilo Korumak")) {
                            hedeflerSpinner.setSelection(2);
                        }
                        isimSoyisim.setText(i̇sim_soyisim);
                        boy.setText(boy_);
                        dogumGunu.setText(dogumGunu_);
                        kilo.setText(kilo_);


                    }
                }
            }
        });
    }

}


//public class ProfileFragment extends Fragment {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_profile,container,false);
//    }
//}
//
