package com.example.bestiasendemicas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Sur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sur);

        Button botonVolver3 = findViewById(R.id.btnVolverS);
        botonVolver3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btnVerMasHuillin = findViewById(R.id.btn_ver_mas_Hullin);
        btnVerMasHuillin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Huillin", getString(R.string.inf_Huillin) +"\n\n"+  getString(R.string.inf_HuillinMas) ,
                        R.drawable.huill_n);
            }
        });

        Button btnVerMasRanitaDarwin = findViewById(R.id.btn_ver_mas_RanitaD);
        btnVerMasRanitaDarwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Ranita de Darwin", getString(R.string.inf_RanitaDarwin) +"\n\n"+  getString(R.string.inf_RanitaDarwinMas) ,
                        R.drawable.rana_de_darwin);
            }
        });

        Button btnVerMasFocaCangrejera = findViewById(R.id.btn_ver_mas_FocaCangrejera);
        btnVerMasFocaCangrejera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Foca Cangrejera", getString(R.string.inf_FocaCangrejera) +"\n\n"+  getString(R.string.inf_FocaCangrejeraMas) ,
                        R.drawable.foca_cangrejera_caracteristicas);
            }
        });

        Button btnVerMasTucuquere = findViewById(R.id.btn_ver_mas_Tucuquere);
        btnVerMasTucuquere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Tucuquere", getString(R.string.inf_Tucuquere) +"\n\n"+  getString(R.string.inf_TucuquereMas) ,
                        R.drawable.tucu);
            }
        });

        Button btnVerMasConcon = findViewById(R.id.btn_ver_mas_Concon);
        btnVerMasConcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Concón", getString(R.string.inf_Concón) +"\n\n"+  getString(R.string.inf_ConconMas) ,
                        R.drawable.concon);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainSur), (v, insets) -> {
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