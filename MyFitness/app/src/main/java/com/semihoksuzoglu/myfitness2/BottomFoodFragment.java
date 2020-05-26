package com.semihoksuzoglu.myfitness2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomFoodFragment extends BottomSheetDialogFragment {

    Context context;
    SharedPreferences sharedPreferences;


    KcalFragment fragment;


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
        TumYemekKal.add("149");
        TumYemekOlcek.add("1 Fincan (245g)");

        TumYemekler.add("Turuncu Domates");
        TumYemekKal.add("18");
        TumYemekOlcek.add("1 Tam (111g)");

        TumYemekler.add("Kaşar");
        TumYemekKal.add("70");
        TumYemekOlcek.add("1 Dilim (70g)");

        TumYemekler.add("Kaşar");
        TumYemekKal.add("70");
        TumYemekOlcek.add("1 Dilim (70g)");
        TumYemekler.add("Kaşar");
        TumYemekKal.add("70");
        TumYemekOlcek.add("1 Dilim (70g)");
        TumYemekler.add("Kaşar");
        TumYemekKal.add("70");
        TumYemekOlcek.add("1 Dilim (70g)");
        TumYemekler.add("Kaşar");
        TumYemekKal.add("70");
        TumYemekOlcek.add("1 Dilim (70g)");


        RecyclerView recyclerView = view.findViewById(R.id.AllFoodRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        allFoodRecyclerAdapter = new AllFoodRecyclerAdapter(TumYemekler,TumYemekOlcek,TumYemekKal, fragment,context);
        recyclerView.setAdapter(allFoodRecyclerAdapter);
        return view;
    }

    BottomFoodFragment(Context ctx, KcalFragment fragment){
        context = ctx;
        this.fragment = fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = context.getSharedPreferences("Shared", 0);

    }


}
