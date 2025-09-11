package com.example.bestiasendemicas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Norte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_norte);

        Button botonVolver1 = findViewById(R.id.btnVolverS);
        botonVolver1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnVerMasGatoAndino = findViewById(R.id.btn_ver_mas_GatoAndino);
        btnVerMasGatoAndino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Gato Andino",
                        getString(R.string.inf_GatoAndino) +"\n\n"+ getString(R.string.inf_GatoAndinoMas),
                        R.drawable.gato_andino_chile
                );
            }
        });

        Button btnVerMasPudu = findViewById(R.id.btn_ver_mas_Pudu);
        btnVerMasPudu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Pudu",
                        getString(R.string.inf_pudu) +"\n\n"+  getString(R.string.inf_puduMas),
                        R.drawable._541_pudu_1
                );
            }
        });

        Button btnVerMasgatoColocolo = findViewById(R.id.btn_ver_mas_GatoColoColo);
        btnVerMasgatoColocolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Gato Colocolo",
                        getString(R.string.inf_GatoColocolo) +"\n\n"+  getString(R.string.inf_GatoColocoloMas) ,
                        R.drawable._3218_colocolo_marcio_motta
                );
            }
        });

        Button btnVerMasFlamencochileno = findViewById(R.id.btn_ver_mas_FlamencoChileno);
        btnVerMasFlamencochileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Flamenco chileno",
                        getString(R.string.inf_Flamencochileno) +"\n\n"+  getString(R.string.inf_FlamencochilenoMas),
                        R.drawable.flamencochileno
                );
            }
        });

        Button btnVerMasCulebraDeColaLarga = findViewById(R.id.btn_ver_mas_CulebraDeColaLarga);
        btnVerMasCulebraDeColaLarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Culebra De Cola Larga",
                        getString(R.string.inf_Culebradecolalarga) +"\n\n"+  getString(R.string.inf_CulebradecolalargaMas),
                        R.drawable.culebra
                );
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainNorte), (v, insets) -> {
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