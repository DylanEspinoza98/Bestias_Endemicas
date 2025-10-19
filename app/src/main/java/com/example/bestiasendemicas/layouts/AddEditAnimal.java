package com.example.bestiasendemicas.layouts;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bestiasendemicas.R;
import com.example.bestiasendemicas.audio.AudioPicker;
import com.example.bestiasendemicas.database.AnimalCrud;
import com.example.bestiasendemicas.model.Animal;
import com.example.bestiasendemicas.model.Region;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class AddEditAnimal extends AppCompatActivity implements AudioPicker.AudioPickerListener {

    public static final String EXTRA_ANIMAL_ID = "animal_id";
    public static final String EXTRA_REGION_ID = "region_id";

    private EditText etNombre, etDescripcion;
    private Spinner spinnerRegion, spinnerTipo;
    private CheckBox cbEsFavorito;
    private Button btnGuardar, btnCancelar, btnSeleccionarImagen, btnSeleccionarAudio;
    private ImageView ivPreviewImagen;
    private TextView tvAudioFilename;

    private AnimalCrud animalCrud;
    private List<Region> regiones;
    private Animal animalAEditar = null;
    private Uri imagenSeleccionadaUri = null;
    private Uri audioSeleccionadoUri = null;
    private int animalId = -1;

    private AudioPicker audioPicker;

    private final androidx.activity.result.ActivityResultLauncher<PickVisualMediaRequest> pickImagen =
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
        btnSeleccionarAudio = findViewById(R.id.btn_select_audio);
        tvAudioFilename = findViewById(R.id.tv_audio_filename);
        ivPreviewImagen = findViewById(R.id.iv_preview_imagen);

        spinnerRegion.setEnabled(true);
        spinnerRegion.setAlpha(1f);
    }

    private void inicializarCrud() {
        animalCrud = new AnimalCrud(this);
        animalCrud.open();
        audioPicker = new AudioPicker(this, this);
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
            cargarDatosAnimal();
        } else {
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
            int posTipo = ((ArrayAdapter) spinnerTipo.getAdapter())
                    .getPosition(animalAEditar.getTipo());
            spinnerTipo.setSelection(posTipo);

            // Mostrar imagen
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

            // Mostrar nombre del audio si existe
            if (animalAEditar.getSoundUri() != null && !animalAEditar.getSoundUri().isEmpty()) {
                tvAudioFilename.setText(new File(animalAEditar.getSoundUri()).getName());
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
                pickImagen.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build())
        );

        btnSeleccionarAudio.setOnClickListener(v -> audioPicker.selectAudio());
        btnCancelar.setOnClickListener(v -> finish());
        btnGuardar.setOnClickListener(v -> guardarAnimal());
    }

    @Override
    public void onAudioSelected(Uri audioUri, String fileName) {
        audioSeleccionadoUri = audioUri;
        tvAudioFilename.setText(fileName);
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

        // ✅ Conservar imagen/audio previos si no se reemplazan
        String rutaImagen = (animalAEditar != null) ? animalAEditar.getRutaImagen() : "";
        if (imagenSeleccionadaUri != null) {
            rutaImagen = copiarArchivoAAlmacenamientoInterno(imagenSeleccionadaUri, "imagen");
        }

        String rutaAudio = (animalAEditar != null) ? animalAEditar.getSoundUri() : "";
        if (audioSeleccionadoUri != null) {
            rutaAudio = copiarArchivoAAlmacenamientoInterno(audioSeleccionadoUri, "audio");
        }

        if (animalAEditar == null) {
            Animal nuevo = new Animal(nombre, descripcion, rutaImagen,
                    regionIdSeleccionada, esFavorito, tipoSeleccionado, rutaAudio);
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
            animalAEditar.setRutaImagen(rutaImagen);
            animalAEditar.setSoundUri(rutaAudio);
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

    private String copiarArchivoAAlmacenamientoInterno(Uri uri, String tipo) {
        try (InputStream in = getContentResolver().openInputStream(uri)) {
            if (in != null) {
                String extension = tipo.equals("audio") ? ".mp3" : ".jpg";
                String nombre = tipo + "_" + System.currentTimeMillis() + extension;
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
            Log.e("AddEditAnimal", "Error copiando archivo: " + e.getMessage());
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
