package com.semihoksuzoglu.myfitness2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    Context context;
    SharedPreferences sharedPreferences;

    private String customTarge = "";

    private TextInputLayout customTargetEdit;

    WaterFragment fragment;
    Button btnUpdate;

    FirebaseUser curUser;
    FirebaseFirestore firebaseFirestore;

    BottomSheetFragment(Context ctx, WaterFragment fragment, FirebaseUser curUser, FirebaseFirestore firebaseFirestore){
        context = ctx;
        this.fragment = fragment;
        this.firebaseFirestore = firebaseFirestore;
        this.curUser = curUser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        customTargetEdit = view.findViewById(R.id.etTarget);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> MyWater = new HashMap<>();
                MyWater.put("total", Integer.parseInt(customTargetEdit.getEditText().getText().toString()));

                firebaseFirestore.collection("Su").document(curUser.getUid()).update(MyWater);

                fragment.updateValues();
                dismiss();

            }
        });

    }


}
