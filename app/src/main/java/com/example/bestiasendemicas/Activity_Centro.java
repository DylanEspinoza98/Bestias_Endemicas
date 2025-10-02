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

    private RecyclerView recyclerViewAnimales;
    private AnimalAdapter animalAdapter;
    private AnimalCrud animalCrud;
    private FloatingActionButton fabAgregarAnimal;
    private Button botonVolver;
    private List<Animal> listaAnimales;

    private static final int REGION_CENTRO_ID = 2; // ID de la región Centro
    private static final int REQUEST_CODE_AGREGAR_EDITAR = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_centro);

        inicializarVistas();
        inicializarCrud();
        configurarRecyclerView();
        configurarBotones();
        cargarAnimales();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCentro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void inicializarVistas() {
        recyclerViewAnimales = findViewById(R.id.recycler_view_animales_centro);
        fabAgregarAnimal = findViewById(R.id.fab_agregar_animal);
        botonVolver = findViewById(R.id.btnVolverC);
    }

    private void inicializarCrud() {
        animalCrud = new AnimalCrud(this);
        animalCrud.open();
    }

    private void configurarRecyclerView() {
        listaAnimales = new ArrayList<>();
        animalAdapter = new AnimalAdapter(this, listaAnimales, this);
        recyclerViewAnimales.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnimales.setAdapter(animalAdapter);
    }

    private void configurarBotones() {
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabAgregarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityAgregarAnimal();
            }
        });
    }

    private void cargarAnimales() {
        listaAnimales = animalCrud.obtenerAnimalesPorRegion(REGION_CENTRO_ID);
        animalAdapter.actualizarAnimales(listaAnimales);

        if (listaAnimales.isEmpty()) {
            Toast.makeText(this, "No hay animales registrados en esta región", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirActivityAgregarAnimal() {
        Intent intent = new Intent(this, AgregarEditarAnimalActivity.class);
        intent.putExtra(AgregarEditarAnimalActivity.EXTRA_REGION_ID, REGION_CENTRO_ID);
        startActivityForResult(intent, REQUEST_CODE_AGREGAR_EDITAR);
    }

    // Implementación de OnAnimalActionListener

    @Override
    public void onEditarAnimal(Animal animal) {
        Intent intent = new Intent(this, AgregarEditarAnimalActivity.class);
        intent.putExtra(AgregarEditarAnimalActivity.EXTRA_ANIMAL_ID, animal.getId());
        startActivityForResult(intent, REQUEST_CODE_AGREGAR_EDITAR);
    }

    @Override
    public void onEliminarAnimal(Animal animal) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que quieres eliminar a " + animal.getNombre() + "?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarAnimal(animal);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onVerDetalles(Animal animal) {
        // Mostrar detalles usando el BottomSheetFragment existente
        int imageResourceId = obtenerRecursoImagen(animal.getFoto_url());
        AnimalBottomSheetFragment bottomSheet = AnimalBottomSheetFragment.newInstance(
                animal.getNombre(),
                animal.getDescripcion(),
                imageResourceId
        );
        bottomSheet.show(getSupportFragmentManager(), "AnimalBottomSheet");
    }

    private void eliminarAnimal(Animal animal) {
        int resultado = animalCrud.eliminarAnimal(animal.getId());
        if (resultado > 0) {
            Toast.makeText(this, "Animal eliminado correctamente", Toast.LENGTH_SHORT).show();
            cargarAnimales(); // Recargar lista
        } else {
            Toast.makeText(this, "Error al eliminar el animal", Toast.LENGTH_SHORT).show();
        }
    }

    private int obtenerRecursoImagen(String fotoUrl) {
        // Mapear URL a recurso local (simplificado)
        if (fotoUrl != null && !fotoUrl.isEmpty()) {
            // Aquí podrías implementar lógica más compleja
            // Por ejemplo, mapear nombres específicos a recursos
            if (fotoUrl.toLowerCase().contains("abejorro")) return R.drawable.abejorronativo;
            if (fotoUrl.toLowerCase().contains("loica")) return R.drawable.loica;
            if (fotoUrl.toLowerCase().contains("monito")) return R.drawable.monito_del_monte_768x786;
            if (fotoUrl.toLowerCase().contains("degu")) return R.drawable.degu_comun;
            if (fotoUrl.toLowerCase().contains("loro")) return R.drawable.loro2;
        }
        return R.drawable.ic_animal_placeholder.xml; // Imagen por defecto
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AGREGAR_EDITAR && resultCode == RESULT_OK) {
            cargarAnimales(); // Recargar lista cuando se añade o edita un animal
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