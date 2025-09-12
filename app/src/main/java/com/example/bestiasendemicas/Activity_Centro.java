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
// import com.example.bestiasendemicas.fragments.AnimalBottomSheetFragment; // Si es necesario

public class Activity_Centro extends AppCompatActivity {

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
        setContentView(R.layout.activity_centro);


        chipGroupFiltros = findViewById(R.id.chipGrupoFiltros);
        contenedorAnimales = findViewById(R.id.contenedorAnimalesC);



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


    private void setupVerMasButtons() {
        Button btnVerMasAbejorroNativo = findViewById(R.id.btn_ver_mas_Abejorro);
        btnVerMasAbejorroNativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Abejorro Nativo",
                        getString(R.string.inf_Abejorro) + "\n\n" + getString(R.string.info_AbejorroMas),
                        R.drawable.abejorronativo);
            }
        });

        Button btnVerMasLoica = findViewById(R.id.btn_ver_mas_Loica);
        btnVerMasLoica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Loica Común",
                        getString(R.string.inf_Loica) + "\n\n" + getString(R.string.inf_LoicaMas),
                        R.drawable.loica);
            }
        });

        Button btnVerMasMonito = findViewById(R.id.btn_ver_mas_MonitoDelMonte);
        btnVerMasMonito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Monito del Monte",
                        getString(R.string.inf_MdelMonte) + "\n\n" + getString(R.string.inf_MdelMonteMas),
                        R.drawable.monito_del_monte_768x786);
            }
        });

        Button btnVerMasDeguComun = findViewById(R.id.btn_ver_mas_Degu_Comun);
        btnVerMasDeguComun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Degú Común",
                        getString(R.string.inf_Degu) + "\n\n" + getString(R.string.inf_DeguMas),
                        R.drawable.degu_comun);
            }
        });

        Button btnVerMasLoroTricahue = findViewById(R.id.btn_ver_mas_Loro_Tricahue);
        btnVerMasLoroTricahue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnimalDetail(
                        "Loro Tricahue",
                        getString(R.string.inf_lTricahue) + "\n\n" + getString(R.string.inf_lTricahueMas),
                        R.drawable.loro2);
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


