package com.example.bestiasendemicas;

import android.os.Bundle;
import androidx.activity.EdgeToEdge; // Para EdgeToEdge
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout; //Necesario para LinearLayout
import androidx.annotation.NonNull; //Necesario para @NonNull
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.chip.Chip; // Necesario para Chip
import com.google.android.material.chip.ChipGroup; // Necesario para ChipGroup
import java.util.List; //para List


public class Activity_Norte extends AppCompatActivity {


    private ChipGroup chipGroupFiltros;
    private LinearLayout contenedorAnimales;


    private static final String TAG_TODOS = "todos";
    private static final String TAG_TERRESTRE = "terrestre";
    private static final String TAG_VOLADOR = "volador";
    private static final String TAG_ACUATICO = "acuatico";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_norte);


        chipGroupFiltros = findViewById(R.id.chipGrupoFiltros);
        contenedorAnimales = findViewById(R.id.contenedorAnimalesN);


        Button botonVolver1 = findViewById(R.id.btnVolverS);
        botonVolver1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupVerMasButtons();


        if (chipGroupFiltros != null) {
            chipGroupFiltros.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                    if (checkedIds.isEmpty()) {
                        aplicarFiltro(TAG_TODOS);
                        return;
                    }

                    int selectedChipId = checkedIds.get(0);
                    Chip selectedChip = group.findViewById(selectedChipId);

                    if (selectedChip != null) {
                        String filtroAplicar;
                        if (selectedChip.getId() == R.id.chipTodos) {
                            filtroAplicar = TAG_TODOS;
                        } else if (selectedChip.getId() == R.id.TagTerrestre) {
                            filtroAplicar = TAG_TERRESTRE;
                        } else if (selectedChip.getId() == R.id.TagVolador) {
                            filtroAplicar = TAG_VOLADOR;
                        } else if (selectedChip.getId() == R.id.TagAcuatico) {
                            filtroAplicar = TAG_ACUATICO;
                        } else {
                            filtroAplicar = TAG_TODOS;
                        }
                        aplicarFiltro(filtroAplicar);
                    }
                }
            });
        }
        aplicarFiltro(TAG_TODOS);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainNorte), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupVerMasButtons() {
        Button btnVerMasGatoAndino = findViewById(R.id.btn_ver_mas_GatoAndino);
        btnVerMasGatoAndino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Gato Andino",
                        getString(R.string.inf_GatoAndino) + "\n\n" + getString(R.string.inf_GatoAndinoMas),
                        R.drawable.gato_andino_chile);
            }
        });

        Button btnVerMasPudu = findViewById(R.id.btn_ver_mas_Pudu);
        btnVerMasPudu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Pudú", // Corregido el nombre si es necesario
                        getString(R.string.inf_pudu) + "\n\n" + getString(R.string.inf_puduMas),
                        R.drawable._541_pudu_1);
            }
        });

        Button btnVerMasgatoColocolo = findViewById(R.id.btn_ver_mas_GatoColoColo);
        btnVerMasgatoColocolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Gato Colocolo",
                        getString(R.string.inf_GatoColocolo) + "\n\n" + getString(R.string.inf_GatoColocoloMas),
                        R.drawable._3218_colocolo_marcio_motta);
            }
        });

        Button btnVerMasFlamencochileno = findViewById(R.id.btn_ver_mas_FlamencoChileno);
        btnVerMasFlamencochileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Flamenco chileno",
                        getString(R.string.inf_Flamencochileno) + "\n\n" + getString(R.string.inf_FlamencochilenoMas),
                        R.drawable.flamencochileno);
            }
        });

        Button btnVerMasCulebraDeColaLarga = findViewById(R.id.btn_ver_mas_CulebraDeColaLarga);
        btnVerMasCulebraDeColaLarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Culebra De Cola Larga",
                        getString(R.string.inf_Culebradecolalarga) + "\n\n" + getString(R.string.inf_CulebradecolalargaMas),
                        R.drawable.culebra);
            }
        });
    }

    private void aplicarFiltro(String tipoFiltro) {
        if (contenedorAnimales == null) {
            return;
        }

        for (int i = 0; i < contenedorAnimales.getChildCount(); i++) {
            View vistaAnimal = contenedorAnimales.getChildAt(i);
            if (vistaAnimal instanceof LinearLayout && vistaAnimal.getTag() != null) {
                String tagAnimal = vistaAnimal.getTag().toString();

                if (tipoFiltro.equals(TAG_TODOS)) {
                    vistaAnimal.setVisibility(View.VISIBLE);
                } else {
                    vistaAnimal.setVisibility(tagAnimal.equals(tipoFiltro) ? View.VISIBLE : View.GONE);
                }
            }
        }
    }

    private void showAnimalDetail(String animalName, String description, int imageResourceId) {
        AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(animalName, description, imageResourceId);
        bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
    }
}
