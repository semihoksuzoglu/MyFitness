package com.semihoksuzoglu.myfitness2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllFoodFragment extends Fragment {

    ArrayList<String> TumYemekler;
    ArrayList<String> TumYemekOlcek;
    ArrayList<String> TumYemekKal;
    AllFoodRecyclerAdapter allFoodRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_food, container, false);
        TumYemekler = new ArrayList<>();
        TumYemekKal = new ArrayList<>();
        TumYemekOlcek = new ArrayList<>();

        TumYemekler.add("Yoğurt");
        TumYemekKal.add("149 Kcal");
        TumYemekOlcek.add("1 Fincan (245g)");

        TumYemekler.add("Turuncu Domates");
        TumYemekKal.add("18 Kcal");
        TumYemekOlcek.add("1 Tam (111g)");

        TumYemekler.add("Kaşar");
        TumYemekKal.add("70 Kcal");
        TumYemekOlcek.add("1 Dilim (70g)");

        RecyclerView recyclerView = view.findViewById(R.id.AllFoodRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        /*
        allFoodRecyclerAdapter = new AllFoodRecyclerAdapter(TumYemekler,TumYemekOlcek,TumYemekKal);
        recyclerView.setAdapter(allFoodRecyclerAdapter);
         */
        return view;
    }
}
