package com.example.bestiasendemicas;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
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

public class Activity_Sur extends AppCompatActivity {

    // Variables para los filtros
    private ChipGroup chipGroupFiltros;
    private LinearLayout contenedorAnimales;

    // Constantes para los tags de los filtros
    private static final String TAG_TODOS = "todos";
    private static final String TAG_TERRESTRE = "terrestre";
    private static final String TAG_VOLADOR = "volador";
    private static final String TAG_ACUATICO = "acuatico";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sur);


        chipGroupFiltros = findViewById(R.id.chipGrupoFiltros);
        contenedorAnimales = findViewById(R.id.contenedorAnimalesS);


        Button botonVolver3 = findViewById(R.id.btnVolverS);
        botonVolver3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        setupVerMasButtons();


        if (chipGroupFiltros != null) { //Aqui evitamos que el código se ejecute si chipGroupFiltros es null//
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainSur), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupVerMasButtons() {
        Button btnVerMasHuillin = findViewById(R.id.btn_ver_mas_Hullin);
        btnVerMasHuillin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Huillín", getString(R.string.inf_Huillin) + "\n\n" + getString(R.string.inf_HuillinMas),
                        R.drawable.huillin); // CAMBIADO de huill_n a huillin (asegúrate que el drawable exista)
            }
        });

        Button btnVerMasRanitaDarwin = findViewById(R.id.btn_ver_mas_RanitaD);
        btnVerMasRanitaDarwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Ranita de Darwin", getString(R.string.inf_RanitaDarwin) + "\n\n" + getString(R.string.inf_RanitaDarwinMas),
                        R.drawable.rana_de_darwin);
            }
        });

        Button btnVerMasFocaCangrejera = findViewById(R.id.btn_ver_mas_FocaCangrejera);
        btnVerMasFocaCangrejera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Foca Cangrejera", getString(R.string.inf_FocaCangrejera) + "\n\n" + getString(R.string.inf_FocaCangrejeraMas),
                        R.drawable.foca_cangrejera_caracteristicas);
            }
        });

        Button btnVerMasTucuquere = findViewById(R.id.btn_ver_mas_Tucuquere);
        btnVerMasTucuquere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Tucúquere", getString(R.string.inf_Tucuquere) + "\n\n" + getString(R.string.inf_TucuquereMas),
                        R.drawable.tucu);
            }
        });

        Button btnVerMasConcon = findViewById(R.id.btn_ver_mas_Concon);
        btnVerMasConcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Concón", getString(R.string.inf_Concón) + "\n\n" + getString(R.string.inf_ConconMas),
                        R.drawable.concon);
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

    // Tu método showAnimalDetail existente
    private void showAnimalDetail(String animalName, String description, int imageResourceId) {
        AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(animalName, description, imageResourceId);
        bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
    }
}

