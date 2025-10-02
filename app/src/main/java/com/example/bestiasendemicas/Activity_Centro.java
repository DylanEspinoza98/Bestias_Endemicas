package com.example.bestiasendemicas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class Activity_Centro extends AppCompatActivity implements AnimalAdapter.OnAnimalActionListener {


    private Button btnVerMasAbejorro, btnVerMasLoica, btnVerMasMonitoDelMonte;
    private Button btnVerMasDeguComun, btnVerMasLoroTricahue, botonVolver;


    private RecyclerView recyclerViewAnimales;
    private AnimalAdapter animalAdapter;
    private AnimalCrud animalCrud;
    private FloatingActionButton fabAgregarAnimal;
    private List<Animal> listaAnimales;

    private static final int REGION_CENTRO_ID = 2;
    private static final int REQUEST_CODE_AGREGAR_EDITAR = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_centro);

        inicializarVistas();
        inicializarCrud();
        configurarBotonesOriginales(); // MANTENER funcionalidad original
        configurarBotonesCrud(); // AÑADIR funcionalidad CRUD
        configurarRecyclerView();
        cargarAnimales();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCentro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarVistas() {
        // BOTONES ORIGINALES (mantener todos)
        btnVerMasAbejorro = findViewById(R.id.btn_ver_mas_Abejorro);
        btnVerMasLoica = findViewById(R.id.btn_ver_mas_Loica);
        btnVerMasMonitoDelMonte = findViewById(R.id.btn_ver_mas_MonitoDelMonte);
        btnVerMasDeguComun = findViewById(R.id.btn_ver_mas_Degu_Comun);
        btnVerMasLoroTricahue = findViewById(R.id.btn_ver_mas_Loro_Tricahue);
        botonVolver = findViewById(R.id.btnVolverC);

        // NUEVAS VISTAS CRUD
        recyclerViewAnimales = findViewById(R.id.recycler_view_animales_centro);
        fabAgregarAnimal = findViewById(R.id.fab_agregar_animal);
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

        // Botones "Ver más" originales - MANTENER tu lógica existente exacta
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
        animalAdapter = new AnimalAdapter(this, listaAnimales, this);
        recyclerViewAnimales.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnimales.setAdapter(animalAdapter);
    }

    private void cargarAnimales() {
        listaAnimales = animalCrud.obtenerAnimalesPorRegion(REGION_CENTRO_ID);
        animalAdapter.actualizarAnimales(listaAnimales);
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
        // Crear un BottomSheet personalizado que cargue la imagen desde URI
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
