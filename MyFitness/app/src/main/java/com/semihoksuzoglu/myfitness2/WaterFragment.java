package com.semihoksuzoglu.myfitness2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.itangqi.waveloadingview.WaveLoadingView;
import params.com.stepprogressview.StepProgressView;

public class WaterFragment extends Fragment {

    Context ctx;
    FragmentManager fragmentManager;

    ConstraintLayout bottomSheet;
    ImageView btnMenu;
    FloatingActionButton addBtn;

    TextView totalTargetV;
    TextView currentLevelV;
    StepProgressView intakeProgress;
    WaveLoadingView waveLoadingView;

    TextView targetTextView;
    TextView remainingTextView;
    TextView tvCustom;
    android.graphics.drawable.Drawable optBackground;

    LinearLayout _50mlBtn;
    LinearLayout _100mlBtn;
    LinearLayout _150mlBtn;
    LinearLayout _200mlBtn;
    LinearLayout _250mlBtn;
    LinearLayout _customBtn;

    int selectedOption = 0;
    int totalTarget = 2000;
    int currentLevel = 0;

    Calendar currentDay;

    WaterFragment fragment;

    private FirebaseAuth firebaseAuth;
    FirebaseUser curUser;
    private FirebaseFirestore firebaseFirestore;

    SharedPreferences sharedPreferences;

    WaterFragment(Context context, FragmentManager fragmentManager) {
        ctx = context;
        this.fragmentManager = fragmentManager;
        fragment = this;
        currentDay = Calendar.getInstance();
    }

    @java.lang.Override
    public void onViewCreated(@NonNull View view, @Nullable android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = ctx.getSharedPreferences("Shared", 0);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        curUser = firebaseAuth.getCurrentUser();



        btnMenu = getView().findViewById(R.id.btnMenu);
        _50mlBtn = getView().findViewById(R.id.op50ml);
        _100mlBtn = getView().findViewById(R.id.op100ml);
        _150mlBtn = getView().findViewById(R.id.op150ml);
        _200mlBtn = getView().findViewById(R.id.op200ml);
        _250mlBtn = getView().findViewById(R.id.op250ml);
        _customBtn = getView().findViewById(R.id.opCustom);
        addBtn = getView().findViewById(R.id.fabAdd);
        tvCustom = getView().findViewById(R.id.tvCustom);

        totalTargetV = getView().findViewById(R.id.tvTotalIntake);
        currentLevelV = getView().findViewById(R.id.tvIntook);
        intakeProgress = getView().findViewById(R.id.intakeProgress);
        waveLoadingView = getView().findViewById(R.id.waterLevelView);
        remainingTextView = getView().findViewById(R.id.remainingIntake);
        targetTextView = getView().findViewById(R.id.targetIntake);


        optBackground = getResources().getDrawable(R.drawable.option_select_bg);

        bottomSheet = getView().findViewById(R.id.bottomSheetParent);

        updateValues();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment(ctx, fragment, curUser, firebaseFirestore);

                bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalTarget > 0) {
                    currentLevel += selectedOption;
                    setWaterLevel(currentLevel, totalTarget);
                    updatecurrentLevel();
                    updateValues();
                }else{
                    Toast.makeText(ctx, "Eklemek için hedef belirlemen gerekiyor.", Toast.LENGTH_LONG).show();
                }
            }
        });

        _50mlBtn.setOnClickListener(optionBtnsListener);
        _100mlBtn.setOnClickListener(optionBtnsListener);
        _150mlBtn.setOnClickListener(optionBtnsListener);
        _200mlBtn.setOnClickListener(optionBtnsListener);
        _250mlBtn.setOnClickListener(optionBtnsListener);
        _customBtn.setOnClickListener(optionBtnsListener);

    }


    View.OnClickListener optionBtnsListener = new View.OnClickListener() {
        @java.lang.Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.op50ml:
                    selectedOption = 50;
                    _50mlBtn.setBackground(optBackground);
                    _100mlBtn.setBackground(null);
                    _150mlBtn.setBackground(null);
                    _200mlBtn.setBackground(null);
                    _250mlBtn.setBackground(null);
                    _customBtn.setBackground(null);
                    break;

                case R.id.op100ml:
                    selectedOption = 100;
                    _50mlBtn.setBackground(null);
                    _100mlBtn.setBackground(optBackground);
                    _150mlBtn.setBackground(null);
                    _200mlBtn.setBackground(null);
                    _250mlBtn.setBackground(null);
                    _customBtn.setBackground(null);
                    break;

                case R.id.op150ml:
                    selectedOption = 150;
                    _50mlBtn.setBackground(null);
                    _100mlBtn.setBackground(null);
                    _150mlBtn.setBackground(optBackground);
                    _200mlBtn.setBackground(null);
                    _250mlBtn.setBackground(null);
                    _customBtn.setBackground(null);
                    break;

                case R.id.op200ml:
                    selectedOption = 200;
                    _50mlBtn.setBackground(null);
                    _100mlBtn.setBackground(null);
                    _150mlBtn.setBackground(null);
                    _200mlBtn.setBackground(optBackground);
                    _250mlBtn.setBackground(null);
                    _customBtn.setBackground(null);
                    break;

                case R.id.op250ml:
                    selectedOption = 250;
                    _50mlBtn.setBackground(null);
                    _100mlBtn.setBackground(null);
                    _150mlBtn.setBackground(null);
                    _200mlBtn.setBackground(null);
                    _250mlBtn.setBackground(optBackground);
                    _customBtn.setBackground(null);
                    break;

                case R.id.opCustom:

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.custom_input_dialog, (ViewGroup) getView(), false);
                    final TextInputLayout input = viewInflated.findViewById(R.id.etCustomInput);
                    builder.setView(viewInflated);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedOption = Integer.parseInt(input.getEditText().getText().toString());
                            tvCustom.setText(selectedOption + " ml");
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                    _50mlBtn.setBackground(null);
                    _100mlBtn.setBackground(null);
                    _150mlBtn.setBackground(null);
                    _200mlBtn.setBackground(null);
                    _250mlBtn.setBackground(null);
                    _customBtn.setBackground(optBackground);
                    break;


            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_water, container, false);
    }


    private void setWaterLevel(int inTook, int totalIntake) {

        currentLevelV.setText(inTook + "");
        totalTargetV.setText("/" + totalIntake);
        int progress = (int) ((inTook / (float) totalIntake) * 100);
        intakeProgress.setCurrentProgress(progress);
        waveLoadingView.setProgressValue(progress);
        waveLoadingView.setCenterTitle("%" + progress);
    }

    void updateValues() {

        Log.d("Test Water : ", totalTarget + "");


        DocumentReference documentReference = firebaseFirestore.collection("Su").document(curUser.getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.getData() != null) {

                    Map<String, Object> map = documentSnapshot.getData();
                    System.out.println("Passed get Data...! : " + map.toString());
                    totalTarget = Integer.parseInt(map.get("total").toString());
                    currentLevel = totalTarget > 0 ? Integer.parseInt(map.get("level").toString()) : 0;
                    int savedDay = Integer.parseInt(map.get("day").toString());
                    int day = currentDay.get(Calendar.DAY_OF_MONTH);
                    if(day != savedDay){
                        currentLevel = 0;
                        updatecurrentLevel();
                    }
                }

                int remaining = totalTarget - currentLevel;
                remainingTextView.setText(remaining + " ml");

                targetTextView.setText(totalTarget + " ml");
                setWaterLevel(currentLevel, totalTarget);

            }

        });


    }

    void updatecurrentLevel() {
        int day = currentDay.get(Calendar.DAY_OF_MONTH);

        HashMap<String,Object> MyWater = new HashMap<>();
        MyWater.put("level", currentLevel);
        MyWater.put("total", totalTarget);
        MyWater.put("day", day);

        firebaseFirestore.collection("Su").document(curUser.getUid()).set(MyWater);
    }
}
