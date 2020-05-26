package com.semihoksuzoglu.myfitness2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;


public class SportFragment extends Fragment {
    public Button gun1;
    public Button gun2;
    public Button gun3;
    public Button gun4;
    public Button gun5;
    public Button gun6;
    public Button gun7;

    private FirebaseAuth firebaseAuth;
    FirebaseUser curUser;
    private FirebaseFirestore firebaseFirestore;

    TextView kaloritv;
    TextView dakikatv;
    TextView egzersiztv;


    int kalori = 0;
    int egzersiz = 0;
    int dakika = 0;

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_sport,container,false);
        kaloritv = rootView.findViewById(R.id.kaloritv);
        dakikatv = rootView.findViewById(R.id.dakikatv);
        egzersiztv = rootView.findViewById(R.id.egzersiztv);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        curUser = firebaseAuth.getCurrentUser();

        getData();

        gun1=(Button)rootView.findViewById(R.id.gun1);
        gun2=(Button)rootView.findViewById(R.id.gun2);
        gun3=(Button)rootView.findViewById(R.id.gun3);
        gun4=(Button)rootView.findViewById(R.id.gun4);
        gun5=(Button) rootView.findViewById(R.id.gun5);
        gun6=(Button) rootView.findViewById(R.id.gun6);
        gun7=(Button) rootView.findViewById(R.id.gun7);
        gun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeni=new Intent(v.getContext(),Video.class);
                String ifade="gun1";
                yeni.putExtra("ifade",ifade);
                yeni.putExtra("egzersiz",egzersiz);
                yeni.putExtra("dakika",dakika);
                yeni.putExtra("kalori",kalori);
                startActivity(yeni);
            }
        });


        gun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeni=new Intent(v.getContext(),Video.class);
                String ifade="gun2";
                yeni.putExtra("ifade",ifade);
                yeni.putExtra("egzersiz",egzersiz);
                yeni.putExtra("dakika",dakika);
                yeni.putExtra("kalori",kalori);
                startActivity(yeni);

            }
        });


        gun3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent yeni=new Intent(v.getContext(),Video.class);
                String ifade="gun3";
                yeni.putExtra("ifade",ifade);
                yeni.putExtra("egzersiz",egzersiz);
                yeni.putExtra("dakika",dakika);
                yeni.putExtra("kalori",kalori);
                startActivity(yeni);

            }
        });

        gun4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeni=new Intent(v.getContext(),Video.class);
                String ifade="gun4";
                yeni.putExtra("ifade",ifade);
                yeni.putExtra("egzersiz",egzersiz);
                yeni.putExtra("dakika",dakika);
                yeni.putExtra("kalori",kalori);
                startActivity(yeni);


            }
        });

        gun5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeni=new Intent(v.getContext(),Video.class);
                String ifade="gun5";
                yeni.putExtra("ifade",ifade);
                yeni.putExtra("egzersiz",egzersiz);
                yeni.putExtra("dakika",dakika);
                yeni.putExtra("kalori",kalori);
                startActivity(yeni);

            }
        });


        gun6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yeni=new Intent(v.getContext(),Video.class);
                String ifade="gun6";
                yeni.putExtra("ifade",ifade);
                yeni.putExtra("egzersiz",egzersiz);
                yeni.putExtra("dakika",dakika);
                yeni.putExtra("kalori",kalori);
                startActivity(yeni);
            }
        });


        gun7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Bu gun idman yok",Toast.LENGTH_LONG).show();


            }
        });



        return rootView;
    }


void getData(){
    DocumentReference documentReference = firebaseFirestore.collection("Egzersiz").document(curUser.getUid());
    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

            if (documentSnapshot.getData() != null) {
                Map<String, Object> map = documentSnapshot.getData();

                kalori = Integer.parseInt(map.get("kalori").toString());
                egzersiz = Integer.parseInt(map.get("egzersiz").toString());
                dakika = Integer.parseInt(map.get("dakika").toString());

                egzersiztv.setText("" + egzersiz);
                kaloritv.setText("" + kalori);
                dakikatv.setText("" + dakika);

            }

        }

    });
}

}
