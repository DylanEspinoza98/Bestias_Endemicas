package com.example.bestiasendemicas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bestiasendemicas.adapter.AnimalAdapter;
import com.example.bestiasendemicas.database.AnimalCrud;
import com.example.bestiasendemicas.model.Animal;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class Activity_Centro extends AppCompatActivity implements AnimalAdapter.OnAnimalActionListener {

    //Botones originales
    private Button btnVerMasAbejorro, btnVerMasLoica, btnVerMasMonitoDelMonte;
    private Button btnVerMasDeguComun, btnVerMasLoroTricahue, botonVolver;

    //Elementos CRUD
    private RecyclerView recyclerViewAnimales;
    private AnimalAdapter animalAdapter;
    private AnimalCrud animalCrud;
    private FloatingActionButton fabAgregarAnimal;
    private List<Animal> listaAnimales;
    private List<Animal> listaAnimalesCompleta; // Lista sin filtrar

    //Elementos para filtros
    private ChipGroup chipGroupFiltros;
    private LinearLayout contenedorAnimales;

    //Constantes
    private static final int REGION_CENTRO_ID = 2;
    private static final int REQUEST_CODE_AGREGAR_EDITAR = 1001;

    //Constantes para filtros
    private static final String TAG_TODOS = "todos";
    private static final String TAG_TERRESTRE = "terrestre";
    private static final String TAG_VOLADOR = "volador";
    private static final String TAG_ACUATICO = "acuatico";
    private static final String TAG_FAVORITOS = "favoritos";

    private String filtroActual = TAG_TODOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_centro);


        inicializarVistas();
        inicializarCrud();
        configurarBotonesOriginales();
        configurarBotonesCrud();
        configurarRecyclerView();
        configurarFiltros();
        cargarAnimales();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCentro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarVistas() {
        //Botones originales
        btnVerMasAbejorro = findViewById(R.id.btn_ver_mas_Abejorro);
        btnVerMasLoica = findViewById(R.id.btn_ver_mas_Loica);
        btnVerMasMonitoDelMonte = findViewById(R.id.btn_ver_mas_MonitoDelMonte);
        btnVerMasDeguComun = findViewById(R.id.btn_ver_mas_Degu_Comun);
        btnVerMasLoroTricahue = findViewById(R.id.btn_ver_mas_Loro_Tricahue);
        botonVolver = findViewById(R.id.btnVolverC);

        //Nuevas vistas crud
        recyclerViewAnimales = findViewById(R.id.recycler_view_animales_centro);
        fabAgregarAnimal = findViewById(R.id.fab_agregar_animal);

        //Vistas para los filtros
        chipGroupFiltros = findViewById(R.id.chipGrupoFiltros);
        contenedorAnimales = findViewById(R.id.contenedorAnimalesC);
    }

    private void inicializarCrud() {
        animalCrud = new AnimalCrud(this);
        animalCrud.open();
    }

    private void configurarBotonesOriginales() {
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Botones "Ver más" originales
        btnVerMasAbejorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(
                        getString(R.string.abejorronativo),
                        getString(R.string.inf_Abejorro),
                        R.drawable.abejorronativo
                );
                bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
            }
        });

        btnVerMasLoica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(
                        getString(R.string.loica),
                        getString(R.string.inf_Loica),
                        R.drawable.loica);
                bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
            }
        });

        btnVerMasMonitoDelMonte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(
                        getString(R.string.MonitodelMonte),
                        getString(R.string.inf_MdelMonte),
                        R.drawable.monito_del_monte_768x786
                );
                bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
            }
        });

        btnVerMasDeguComun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(
                        getString(R.string.Degu_Comun),
                        getString(R.string.inf_Degu),
                        R.drawable.degu_comun
                );
                bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
            }
        });

        btnVerMasLoroTricahue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(
                        getString(R.string.Loro_Tricahue),
                        getString(R.string.inf_lTricahue),
                        R.drawable.loro2
                );
                bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
            }
        });
    }

    private void configurarBotonesCrud() {
        fabAgregarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityAgregarAnimal();
            }
        });
    }

    private void configurarRecyclerView() {
        listaAnimales = new ArrayList<>();
        listaAnimalesCompleta = new ArrayList<>();
        animalAdapter = new AnimalAdapter(this, listaAnimales, this);
        recyclerViewAnimales.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnimales.setAdapter(animalAdapter);
    }

    private void configurarFiltros() {
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
                        } else if (selectedChip.getId() == R.id.TagFavoritos) {
                            filtroAplicar = TAG_FAVORITOS;
                        } else {
                            filtroAplicar = TAG_TODOS;
                        }
                        aplicarFiltro(filtroAplicar);
                    }
                }
            });
        }

        //Aplicar filtro inicial
        aplicarFiltro(TAG_TODOS);
    }

    private void aplicarFiltro(String tipoFiltro) {
        filtroActual = tipoFiltro;
        Log.d("Activity_Centro", "Aplicando filtro: " + tipoFiltro);

        //Filtrar animales hardcodeados (estáticos)
        if (contenedorAnimales != null) {
            for (int i = 0; i < contenedorAnimales.getChildCount(); i++) {
                View vistaAnimal = contenedorAnimales.getChildAt(i);
                if (vistaAnimal instanceof LinearLayout && vistaAnimal.getTag() != null) {
                    String tagAnimal = vistaAnimal.getTag().toString();
                    boolean mostrar = false;

                    switch (tipoFiltro) {
                        case TAG_TODOS:
                            mostrar = true;
                            break;
                        case TAG_FAVORITOS:
                            mostrar = false; //Los hardcodeados no son favoritos
                            break;
                        case TAG_TERRESTRE:
                        case TAG_VOLADOR:
                        case TAG_ACUATICO:
                            mostrar = tagAnimal.equals(tipoFiltro);
                            break;
                    }

                    vistaAnimal.setVisibility(mostrar ? View.VISIBLE : View.GONE);
                }
            }
        }

        //Filtrar animales dinámicos (RecyclerView)
        filtrarAnimalesDinamicos();
    }

    private void filtrarAnimalesDinamicos() {
        if (animalAdapter != null && listaAnimalesCompleta != null) {
            List<Animal> animalesFiltrados = new ArrayList<>();

            for (Animal animal : listaAnimalesCompleta) {
                boolean mostrar = false;

                switch (filtroActual) {
                    case TAG_TODOS:
                        mostrar = true;
                        break;
                    case TAG_FAVORITOS:
                        mostrar = animal.isEsFavorito();
                        break;
                    case TAG_TERRESTRE:
                    case TAG_VOLADOR:
                    case TAG_ACUATICO:
                        //Comparar con animal.getTipo()
                        mostrar = filtroActual.equals(animal.getTipo());
                        break;
                }

                if (mostrar) {
                    animalesFiltrados.add(animal);
                }
            }

            animalAdapter.actualizarAnimales(animalesFiltrados);
        }
    }


    private void cargarAnimales() {
        listaAnimalesCompleta = animalCrud.obtenerAnimalesPorRegion(REGION_CENTRO_ID);
        Log.d("Activity_Centro", "Animales cargados: " + listaAnimalesCompleta.size());
        filtrarAnimalesDinamicos(); //Aplicar filtro actual
    }

    private void abrirActivityAgregarAnimal() {
        Intent intent = new Intent(this, AddEditAnimal.class);
        intent.putExtra(AddEditAnimal.EXTRA_REGION_ID, REGION_CENTRO_ID);
        startActivityForResult(intent, REQUEST_CODE_AGREGAR_EDITAR);
    }

    @Override
    public void onEditarAnimal(Animal animal) {
        Intent intent = new Intent(this, AddEditAnimal.class);
        intent.putExtra(AddEditAnimal.EXTRA_ANIMAL_ID, animal.getId());
        startActivityForResult(intent, REQUEST_CODE_AGREGAR_EDITAR);
    }

    @Override
    public void onEliminarAnimal(Animal animal) {
        new AlertDialog.Builder(this)
                .setTitle("⚠️ Confirmar eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar a '" + animal.getNombre() + "'?\n\nEsta acción no se puede deshacer.")
                .setPositiveButton("🗑️ Sí, Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarAnimal(animal);
                    }
                })
                .setNegativeButton("❌ Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onVerDetalles(Animal animal) {
        Log.d("Activity_Centro", "Ver detalles de: " + animal.getNombre());
        Log.d("Activity_Centro", "Ruta imagen: " + animal.getRutaImagen());

        //Crear un BottomSheet personalizado que cargue la imagen desde URI
        AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(
                animal.getNombre(),
                animal.getDescripcion(),
                animal.getRutaImagen() // ← PASAR la ruta de imagen
        );
        bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
    }

    private void eliminarAnimal(Animal animal) {
        int resultado = animalCrud.eliminarAnimal(animal.getId());
        if (resultado > 0) {
            Toast.makeText(this, "✅ " + animal.getNombre() + " eliminado correctamente",
                    Toast.LENGTH_SHORT).show();
            cargarAnimales();
        } else {
            Toast.makeText(this, "❌ Error al eliminar el animal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AGREGAR_EDITAR && resultCode == RESULT_OK) {
            cargarAnimales();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animalCrud != null) {
            animalCrud.close();
        }
    }
}
