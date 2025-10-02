package com.example.bestiasendemicas;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.PickVisualMediaRequest;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bestiasendemicas.database.AnimalCrud;
import com.example.bestiasendemicas.model.Animal;
import com.example.bestiasendemicas.model.Region;
import java.util.List;

public class AddEditAnimal extends AppCompatActivity {

    private EditText etNombre, etDescripcion;
    private Spinner spinnerRegion;
    private CheckBox cbEsFavorito;
    private Button btnGuardar, btnCancelar, btnSeleccionarImagen;
    private ImageView ivPreviewImagen;
    private AnimalCrud animalCrud;
    private List<Region> regiones;
    private Animal animalAEditar = null;
    private int animalId = -1;
    private Uri imagenSeleccionadaUri = null;

    public static final String EXTRA_ANIMAL_ID = "animal_id";
    public static final String EXTRA_REGION_ID = "region_id";



    // ActivityResultLauncher para seleccionar imagen
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    imagenSeleccionadaUri = uri;
                    ivPreviewImagen.setImageURI(uri);
                    ivPreviewImagen.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "✅ Imagen seleccionada", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_animal);

        inicializarVistas();
        inicializarCrud();
        cargarRegiones();
        verificarModoEdicion();
        configurarBotones();
    }

    private void inicializarVistas() {
        etNombre = findViewById(R.id.et_nombre_animal);
        etDescripcion = findViewById(R.id.et_descripcion_animal);
        spinnerRegion = findViewById(R.id.spinner_region);
        cbEsFavorito = findViewById(R.id.cb_es_favorito);
        btnGuardar = findViewById(R.id.btn_guardar_animal);
        btnCancelar = findViewById(R.id.btn_cancelar_animal);
        btnSeleccionarImagen = findViewById(R.id.btn_seleccionar_imagen);
        ivPreviewImagen = findViewById(R.id.iv_preview_imagen);


    }

    private void inicializarCrud() {
        animalCrud = new AnimalCrud(this);
        animalCrud.open();
    }

    private void cargarRegiones() {
        regiones = animalCrud.obtenerTodasLasRegiones();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item);

        for (Region region : regiones) {
            adapter.add(region.getNombre());
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(adapter);
    }

    private void verificarModoEdicion() {
        Intent intent = getIntent();
        animalId = intent.getIntExtra(EXTRA_ANIMAL_ID, -1);

        if (animalId != -1) {
            // Modo edición
            setTitle("Editar Animal");
            btnGuardar.setText("Actualizar");
            cargarDatosAnimal();
        } else {
            // Modo añadir
            setTitle("Añadir Animal");
            btnGuardar.setText("Guardar");
            int regionIdSeleccionada = intent.getIntExtra(EXTRA_REGION_ID, -1);
            if (regionIdSeleccionada != -1) {
                seleccionarRegionEnSpinner(regionIdSeleccionada);
            }
        }
    }

    private void cargarDatosAnimal() {
        animalAEditar = animalCrud.obtenerAnimalPorId(animalId);
        if (animalAEditar != null) {
            etNombre.setText(animalAEditar.getNombre());
            etDescripcion.setText(animalAEditar.getDescripcion());
            cbEsFavorito.setChecked(animalAEditar.isEsFavorito());
            seleccionarRegionEnSpinner(animalAEditar.getRegionId());

            // Cargar imagen si existe
            String rutaImagen = animalAEditar.getRutaImagen();
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                try {
                    imagenSeleccionadaUri = Uri.parse(rutaImagen);
                    ivPreviewImagen.setImageURI(imagenSeleccionadaUri);
                    ivPreviewImagen.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    ivPreviewImagen.setImageResource(R.drawable.ic_animal_placeholder);
                }
            }
        }
    }

    private void seleccionarRegionEnSpinner(int regionId) {
        for (int i = 0; i < regiones.size(); i++) {
            if (regiones.get(i).getId() == regionId) {
                spinnerRegion.setSelection(i);
                break;
            }
        }
    }

    private void configurarBotones() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarAnimal();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
    }



    private void abrirGaleria() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }





    private void guardarAnimal() {
        String nombre = etNombre.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        boolean esFavorito = cbEsFavorito.isChecked();

        // Validaciones
        if (nombre.isEmpty()) {
            etNombre.setError("El nombre es obligatorio");
            etNombre.requestFocus();
            return;
        }

        if (descripcion.isEmpty()) {
            etDescripcion.setError("La descripción es obligatoria");
            etDescripcion.requestFocus();
            return;
        }

        int posicionRegion = spinnerRegion.getSelectedItemPosition();
        if (posicionRegion < 0 || posicionRegion >= regiones.size()) {
            Toast.makeText(this, "Selecciona una región válida", Toast.LENGTH_SHORT).show();
            return;
        }

        Region regionSeleccionada = regiones.get(posicionRegion);

        // Convertir URI a string (solo si hay imagen seleccionada)
        String rutaImagen = (imagenSeleccionadaUri != null) ? imagenSeleccionadaUri.toString() : "";

        if (animalAEditar == null) {
            // Modo añadir
            Animal nuevoAnimal = new Animal(nombre, descripcion, rutaImagen,
                    regionSeleccionada.getId(), esFavorito);

            long resultado = animalCrud.insertarAnimal(nuevoAnimal);
            if (resultado > 0) {
                Toast.makeText(this, "✅ Animal añadido correctamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "❌ Error al añadir el animal", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Modo editar
            animalAEditar.setNombre(nombre);
            animalAEditar.setDescripcion(descripcion);
            animalAEditar.setRutaImagen(rutaImagen);  // ← Solo ruta de galería
            animalAEditar.setRegionId(regionSeleccionada.getId());
            animalAEditar.setEsFavorito(esFavorito);

            int resultado = animalCrud.actualizarAnimal(animalAEditar);
            if (resultado > 0) {
                Toast.makeText(this, "✅ Animal actualizado correctamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "❌ Error al actualizar el animal", Toast.LENGTH_SHORT).show();
            }
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
