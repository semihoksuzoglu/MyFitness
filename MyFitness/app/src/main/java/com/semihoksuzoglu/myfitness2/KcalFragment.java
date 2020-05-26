package com.semihoksuzoglu.myfitness2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.itangqi.waveloadingview.WaveLoadingView;

public class KcalFragment extends Fragment {
    ArrayList<String> YemekListesi;
    ArrayList<String> YemekCalDegeri;

    int MaxCal=2000,NowCal=500;

    WaveLoadingView waveLoadingView;

    FoodRecyclerAdapter foodRecyclerAdapter;
    FloatingActionButton addFood;
    Button FoodUpdate;

    FragmentManager fragmentManager;
    KcalFragment fragment;
    Context context;

    private FirebaseAuth firebaseAuth;
    FirebaseUser curUser;
    private FirebaseFirestore firebaseFirestore;
    DocumentReference reference;
    SharedPreferences sharedPreferences;

    public KcalFragment(Context context, FragmentManager fragmentManager){
        this.context = context;
        this.fragmentManager = fragmentManager;
        fragment = this;
        sharedPreferences = context.getSharedPreferences("Shared",0);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        curUser = firebaseAuth.getCurrentUser();


        View view = inflater.inflate(R.layout.fragment_kcal,container,false);
        addFood = view.findViewById(R.id.FoodAdd);
        FoodUpdate = view.findViewById(R.id.FoodUpdate);
        waveLoadingView = view.findViewById(R.id.foodLevel);

        YemekListesi = new ArrayList<>();
        YemekCalDegeri = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.EklenmisYemekler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        foodRecyclerAdapter = new FoodRecyclerAdapter(YemekListesi,YemekCalDegeri,context,fragment);
        getData();
        recyclerView.setAdapter(foodRecyclerAdapter);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NewFood",false);
        editor.commit();

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomFoodFragment bottomFoodFragment = new BottomFoodFragment(context, fragment);
                bottomFoodFragment.show(fragmentManager, bottomFoodFragment.getTag());
            }
        });
        FoodUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("NewFood",false);
                editor.commit();
                //getData();
                NowCal=0;
                for (String var : YemekCalDegeri) {
                    NowCal+= Integer.parseInt(var);
                }
                setWaterLevel(NowCal,MaxCal);


                HashMap<String,Object> MyFood = new HashMap<>();
                for (int i=0;i<YemekListesi.size();i++){
                    MyFood.put(""+(i+1),YemekListesi.get(i)+"/"+YemekCalDegeri.get(i));
                }

                firebaseFirestore.collection("Yiyecekler").document(curUser.getUid()).set(MyFood);

            }
        });


        return view;
    }
    public void getData(){


        DocumentReference documentReference = firebaseFirestore.collection("Yiyecekler").document(curUser.getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.getData() != null){
                    Map<String,Object> map = documentSnapshot.getData();
                    String[] AllFood = new String[map.size()];
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        AllFood[(Integer.parseInt(key) - 1)]=value.toString();
                    }
                    YemekListesi.clear();
                    YemekCalDegeri.clear();
                    for (String s: AllFood) {
                        String[] temp = s.split("/");
                        YemekListesi.add(temp[0]);
                        YemekCalDegeri.add(temp[1]);
                    }
                }

                NowCal=0;
                for (String var : YemekCalDegeri) {
                    NowCal+= Integer.parseInt(var);
                }
                setWaterLevel(NowCal,MaxCal);
                foodRecyclerAdapter.notifyDataSetChanged();
            }


        });
    }
    private void setWaterLevel(int inTook, int totalIntake) {

        int progress = (int) ((inTook / (float) totalIntake) * 100);
        waveLoadingView.setProgressValue(progress);
        waveLoadingView.setCenterTitle("%" + progress);
    }
}
