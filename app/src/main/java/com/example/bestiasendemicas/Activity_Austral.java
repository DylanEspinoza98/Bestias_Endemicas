package com.example.bestiasendemicas;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.List;

public class Activity_Austral extends AppCompatActivity {

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
        setContentView(R.layout.activity_austral);

        chipGroupFiltros = findViewById(R.id.chipGrupoFiltros);
        contenedorAnimales = findViewById(R.id.contenedorAnimalesA);

        Button botonVolver4 = findViewById(R.id.btnVolverS);
        botonVolver4.setOnClickListener(new View.OnClickListener() {
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainAustral), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupVerMasButtons(){
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
                        "Delfín Chileno",
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
                        "Cóndor Andino",
                        getString(R.string.inf_CondorAndino) +"\n\n"+ getString(R.string.inf_CondorAndinoMas),
                        R.drawable.condor_andino
                );
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


