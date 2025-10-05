package com.example.bestiasendemicas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bestiasendemicas.database.AnimalCrud;
import com.example.bestiasendemicas.model.Animal;
import com.example.bestiasendemicas.model.Region;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class AddEditAnimal extends AppCompatActivity {
    public static final String EXTRA_ANIMAL_ID = "animal_id";
    public static final String EXTRA_REGION_ID = "region_id";

    private EditText etNombre, etDescripcion;
    private Spinner spinnerRegion, spinnerTipo;
    private CheckBox cbEsFavorito;
    private Button btnGuardar, btnCancelar, btnSeleccionarImagen;
    private ImageView ivPreviewImagen;

    private AnimalCrud animalCrud;
    private List<Region> regiones;
    private Animal animalAEditar = null;
    private Uri imagenSeleccionadaUri = null;
    private int animalId = -1;

    private final androidx.activity.result.ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
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
        spinnerTipo = findViewById(R.id.spinner_tipo);
        cbEsFavorito = findViewById(R.id.cb_es_favorito);
        btnGuardar = findViewById(R.id.btn_guardar_animal);
        btnCancelar = findViewById(R.id.btn_cancelar_animal);
        btnSeleccionarImagen = findViewById(R.id.btn_seleccionar_imagen);
        ivPreviewImagen = findViewById(R.id.iv_preview_imagen);

        spinnerRegion.setEnabled(false);
        spinnerRegion.setAlpha(0.5f);
    }

    private void inicializarCrud() {
        animalCrud = new AnimalCrud(this);
        animalCrud.open();
    }

    private void cargarRegiones() {
        regiones = animalCrud.obtenerTodasLasRegiones();
        ArrayAdapter<String> adapterRegiones = new ArrayAdapter<>(
                this, R.layout.spinner_item_black_text);
        for (Region r : regiones) {
            adapterRegiones.add(r.getNombre());
        }
        adapterRegiones.setDropDownViewResource(R.layout.spinner_item_black_text);
        spinnerRegion.setAdapter(adapterRegiones);


        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(
                this, R.array.animal_tipos, R.layout.spinner_item_black_text);
        adapterTipos.setDropDownViewResource(R.layout.spinner_item_black_text);
        spinnerTipo.setAdapter(adapterTipos);
    }

    private void verificarModoEdicion() {
        Intent intent = getIntent();
        animalId = intent.getIntExtra(EXTRA_ANIMAL_ID, -1);
        if (animalId != -1) {
            setTitle("Editar Animal");
            btnGuardar.setText("Actualizar");
            spinnerRegion.setEnabled(true);
            spinnerRegion.setAlpha(1f);
            spinnerTipo.setEnabled(true);
            spinnerTipo.setAlpha(1f);
            cargarDatosAnimal();
        } else {
            setTitle("Añadir Animal");
            btnGuardar.setText("Guardar");
            int regionIdSeleccionada = intent.getIntExtra(EXTRA_REGION_ID, -1);
            if (regionIdSeleccionada != -1) {
                seleccionarRegionEnSpinner(regionIdSeleccionada);
            }
            spinnerTipo.setEnabled(true);
            spinnerTipo.setAlpha(1f);
        }
    }

    private void cargarDatosAnimal() {
        animalAEditar = animalCrud.obtenerAnimalPorId(animalId);
        if (animalAEditar != null) {
            etNombre.setText(animalAEditar.getNombre());
            etDescripcion.setText(animalAEditar.getDescripcion());
            cbEsFavorito.setChecked(animalAEditar.isEsFavorito());
            seleccionarRegionEnSpinner(animalAEditar.getRegionId());
            int posTipo = ((ArrayAdapter) spinnerTipo.getAdapter())
                    .getPosition(animalAEditar.getTipo());
            spinnerTipo.setSelection(posTipo);

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
        btnSeleccionarImagen.setOnClickListener(v ->
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build())
        );

        btnCancelar.setOnClickListener(v -> finish());
        btnGuardar.setOnClickListener(v -> guardarAnimal());
    }

    private void guardarAnimal() {
        String nombre = etNombre.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        boolean esFavorito = cbEsFavorito.isChecked();
        String tipoSeleccionado = spinnerTipo.getSelectedItem().toString();

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

        int regionIdSeleccionada;
        if (animalAEditar != null) {
            regionIdSeleccionada = regiones
                    .get(spinnerRegion.getSelectedItemPosition()).getId();
        } else {
            regionIdSeleccionada = getIntent().getIntExtra(EXTRA_REGION_ID, -1);
            if (regionIdSeleccionada == -1) {
                Toast.makeText(this, "Error al determinar región", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String rutaImagen = "";
        if (imagenSeleccionadaUri != null) {
            rutaImagen = copiarImagenAAlmacenamientoInterno(imagenSeleccionadaUri);
            if (rutaImagen.isEmpty()) {
                Toast.makeText(this, "⚠️ Error al procesar imagen", Toast.LENGTH_SHORT).show();
            }
        }

        if (animalAEditar == null) {
            Animal nuevo = new Animal(nombre, descripcion, rutaImagen,
                    regionIdSeleccionada, esFavorito, tipoSeleccionado);
            long res = animalCrud.insertarAnimal(nuevo);
            if (res > 0) {
                Toast.makeText(this, "✅ Añadido", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "❌ Error al añadir", Toast.LENGTH_SHORT).show();
            }
        } else {
            animalAEditar.setNombre(nombre);
            animalAEditar.setDescripcion(descripcion);
            if (!rutaImagen.isEmpty()) {
                animalAEditar.setRutaImagen(rutaImagen);
            }
            animalAEditar.setRegionId(regionIdSeleccionada);
            animalAEditar.setEsFavorito(esFavorito);
            animalAEditar.setTipo(tipoSeleccionado);
            int res = animalCrud.actualizarAnimal(animalAEditar);
            if (res > 0) {
                Toast.makeText(this, "✅ Actualizado", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "❌ Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String copiarImagenAAlmacenamientoInterno(Uri uri) {
        try (InputStream in = getContentResolver().openInputStream(uri)) {
            if (in != null) {
                String nombre = "animal_" + System.currentTimeMillis() + ".jpg";
                try (FileOutputStream out = openFileOutput(nombre, Context.MODE_PRIVATE)) {
                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                }
                File f = new File(getFilesDir(), nombre);
                return "file://" + f.getAbsolutePath();
            }
        } catch (Exception e) {
            Log.e("AddEditAnimal", "Error copiando imagen: " + e.getMessage());
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animalCrud != null) {
            animalCrud.close();
        }
    }
}
