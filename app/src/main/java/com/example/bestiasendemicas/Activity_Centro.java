package com.example.bestiasendemicas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Centro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_centro);

        Button botonVolver1 = findViewById(R.id.btnVolverS);
        botonVolver1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Abejorro Nativo

        Button btnVerMasAbejorroNativo = findViewById(R.id.btn_ver_mas_Abejorro);
        btnVerMasAbejorroNativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Abejorro Nativo",
                        getString(R.string.inf_Abejorro) +"\n\n"+ getString(R.string.info_AbejorroMas),
                        R.drawable.abejorronativo
                );
            }
        });


        //Loica Comun

        Button btnVerMasLoica = findViewById(R.id.btn_ver_mas_Loica);
        btnVerMasLoica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Loica Comun",
                        getString(R.string.inf_Loica) +"\n\n"+  getString(R.string.inf_LoicaMas),
                        R.drawable.loica
                );
            }
        });

        //Monito del Monte

        Button btnVerMasMonito = findViewById(R.id.btn_ver_mas_MonitoDelMonte);
        btnVerMasMonito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Monito del Monte",
                        getString(R.string.inf_MdelMonte) +"\n\n"+  getString(R.string.inf_MdelMonteMas) ,
                        R.drawable.monito_del_monte_768x786
                );
            }
        });

        // Degu Comun

        Button btnVerMasDeguComun = findViewById(R.id.btn_ver_mas_Degu_Comun);
        btnVerMasDeguComun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Degu Comun",
                        getString(R.string.inf_Degu) +"\n\n"+  getString(R.string.inf_DeguMas),
                        R.drawable.degu_comun
                );
            }
        });


        // loro tricahue

        Button btnVerMasLoroTricahue = findViewById(R.id.btn_ver_mas_Loro_Tricahue);
        btnVerMasLoroTricahue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Loro Tricahue",
                        getString(R.string.inf_lTricahue) +"\n\n"+  getString(R.string.inf_lTricahueMas),
                        R.drawable.loro2
                );
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCentro), (v, insets) -> {
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