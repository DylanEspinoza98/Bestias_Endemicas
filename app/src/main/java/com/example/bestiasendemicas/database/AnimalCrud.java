package com.example.bestiasendemicas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.bestiasendemicas.database.AnimalContract.AnimalEntry;
import com.example.bestiasendemicas.model.Animal;
import java.util.ArrayList;
import java.util.List;
import com.example.bestiasendemicas.database.AnimalContract.RegionEntry;
import com.example.bestiasendemicas.model.Region;
public class AnimalCrud {

    private SQLiteDatabase database;
    private final AnimalDBHelper dbHelper;
    private final String[] allColumns = {
            AnimalEntry._ID,
            AnimalEntry.COLUMN_NOMBRE,
            AnimalEntry.COLUMN_DESCRIPCION,
            AnimalEntry.COLUMN_FOTO_URL,
            AnimalEntry.COLUMN_REGION_ID,
            AnimalEntry.COLUMN_ES_FAVORITO,
            AnimalEntry.COLUMN_TIPO
    };

    public AnimalCrud(Context context) {
        dbHelper = new AnimalDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //Insertar nuevo animal
    public long insertarAnimal(Animal animal) {
        ContentValues values = new ContentValues();
        values.put(AnimalEntry.COLUMN_NOMBRE, animal.getNombre());
        values.put(AnimalEntry.COLUMN_DESCRIPCION, animal.getDescripcion());
        values.put(AnimalEntry.COLUMN_FOTO_URL, animal.getRutaImagen());
        values.put(AnimalEntry.COLUMN_REGION_ID, animal.getRegionId());
        values.put(AnimalEntry.COLUMN_ES_FAVORITO, animal.isEsFavorito() ? 1 : 0);
        values.put(AnimalEntry.COLUMN_TIPO, animal.getTipo());  // ← Nuevo campo
        return database.insert(AnimalEntry.TABLE_NAME, null, values);
    }

    //Actualizar animal existente
    public int actualizarAnimal(Animal animal) {
        ContentValues values = new ContentValues();
        values.put(AnimalEntry.COLUMN_NOMBRE, animal.getNombre());
        values.put(AnimalEntry.COLUMN_DESCRIPCION, animal.getDescripcion());
        values.put(AnimalEntry.COLUMN_FOTO_URL, animal.getRutaImagen());
        values.put(AnimalEntry.COLUMN_REGION_ID, animal.getRegionId());
        values.put(AnimalEntry.COLUMN_ES_FAVORITO, animal.isEsFavorito() ? 1 : 0);
        values.put(AnimalEntry.COLUMN_TIPO, animal.getTipo());

        return database.update(
                AnimalEntry.TABLE_NAME,
                values,
                AnimalEntry._ID + " = ?",
                new String[]{ String.valueOf(animal.getId()) }
        );
    }

    //Eliminar animal
    public int eliminarAnimal(int animalId) {
        return database.delete(
                AnimalEntry.TABLE_NAME,
                AnimalEntry._ID + " = ?",
                new String[]{ String.valueOf(animalId) }
        );
    }

    //Obtener un animal por ID
    public Animal obtenerAnimalPorId(int animalId) {
        Cursor cursor = database.query(
                AnimalEntry.TABLE_NAME,
                allColumns,
                AnimalEntry._ID + " = ?",
                new String[]{ String.valueOf(animalId) },
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Animal animal = cursorToAnimal(cursor);
            cursor.close();
            return animal;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    //Obtener todos los animales de una región
    public List<Animal> obtenerAnimalesPorRegion(int regionId) {
        List<Animal> animales = new ArrayList<>();
        Cursor cursor = database.query(
                AnimalEntry.TABLE_NAME,
                allColumns,
                AnimalEntry.COLUMN_REGION_ID + " = ?",
                new String[]{ String.valueOf(regionId) },
                null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                animales.add(cursorToAnimal(cursor));
            }
            cursor.close();
        }
        return animales;
    }

    public List<Region> obtenerTodasLasRegiones() {
        List<Region> regiones = new ArrayList<>();
        //Columnas de RegionEntry
        String[] columnas = {
                RegionEntry._ID,
                RegionEntry.COLUMN_NOMBRE
        };
        Cursor cursor = database.query(
                RegionEntry.TABLE_NAME,
                columnas,
                null, null, null, null, null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(RegionEntry._ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(RegionEntry.COLUMN_NOMBRE));
                regiones.add(new Region(id, nombre));
            }
            cursor.close();
        }
        return regiones;
    }




    //Convertir cursor a objeto Animal
    private Animal cursorToAnimal(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry._ID));
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_NOMBRE));
        String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_DESCRIPCION));
        String rutaImagen = cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_FOTO_URL));
        int regionId = cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_REGION_ID));
        boolean esFavorito = cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_ES_FAVORITO)) == 1;
        String tipo = cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_TIPO));  // ← Nuevo campo

        Animal animal = new Animal(id, nombre, descripcion, rutaImagen, regionId, esFavorito, tipo);
        return animal;
    }
}
