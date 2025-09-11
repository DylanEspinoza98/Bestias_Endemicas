package com.example.bestiasendemicas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Austral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_austral);

        Button botonVolver4 = findViewById(R.id.btnVolverS);
        botonVolver4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnVerMasHuemul = findViewById(R.id.btn_ver_mas_huemul);
        btnVerMasHuemul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Huemul",
                        getString(R.string.inf_Huemul) +"\n\n"+ getString(R.string.inf_HuemulMas),
                        R.drawable.huemul
                );
            }
        });

        Button btnVerMasDelfinChileno = findViewById(R.id.btn_ver_mas_DelfinChileno);
        btnVerMasDelfinChileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Delfin Chileno",
                        getString(R.string.inf_DelfinChileno) +"\n\n"+ getString(R.string.inf_DelfinChilenoMas),
                        R.drawable.delfin_chileno
                );
            }
        });

        Button btnVerMasZorroCulpeo = findViewById(R.id.btn_ver_mas_ZorroCulpeo);
        btnVerMasZorroCulpeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Zorro Culpeo",
                        getString(R.string.inf_ZorroCulpeo) +"\n\n"+ getString(R.string.inf_ZorroCulpeoMas),
                        R.drawable.zorro_culpeo
                );
            }
        });

        Button btnVerMasCaranchoCordillerano = findViewById(R.id.btn_ver_mas_CaranchoCordillerano);
        btnVerMasCaranchoCordillerano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Carancho Cordillerano",
                        getString(R.string.inf_CaranchoCordillerano) +"\n\n"+ getString(R.string.inf_CaranchoCordilleranoMas),
                        R.drawable.carancho_cordillerano
                );
            }
        });

        Button btnVerMasCondorAndino = findViewById(R.id.btn_ver_mas_CondorAndino);
        btnVerMasCondorAndino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Condor Andino",
                        getString(R.string.inf_CondorAndino) +"\n\n"+ getString(R.string.inf_CondorAndinoMas),
                        R.drawable.condor_andino
                );
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainAustral), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void showAnimalDetail(String animalName, String description, int imageResourceId) {
        AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(animalName, description, imageResourceId);
        bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
    }
}