package com.example.bestiasendemicas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.bestiasendemicas.model.Animal;
import com.example.bestiasendemicas.model.Region;
import com.example.bestiasendemicas.database.AnimalContract.AnimalEntry;
import com.example.bestiasendemicas.database.AnimalContract.RegionEntry;
import java.util.ArrayList;
import java.util.List;

public class AnimalCrud {
    private AnimalDBHelper dbHelper;
    private SQLiteDatabase database;

    public AnimalCrud(Context context) {
        dbHelper = new AnimalDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // ===== OPERACIONES CRUD PARA ANIMALES =====

    // CREATE - Insertar animal
    public long insertarAnimal(Animal animal) {
        ContentValues values = new ContentValues();
        values.put(AnimalEntry.COLUMN_NOMBRE, animal.getNombre());
        values.put(AnimalEntry.COLUMN_DESCRIPCION, animal.getDescripcion());
        values.put(AnimalEntry.COLUMN_FOTO_URL, animal.getFoto_url());
        values.put(AnimalEntry.COLUMN_REGION_ID, animal.getRegionId());
        values.put(AnimalEntry.COLUMN_ES_FAVORITO, animal.isEsFavorito() ? 1 : 0);

        return database.insert(AnimalEntry.TABLE_NAME, null, values);
    }

    // READ - Obtener todos los animales
    public List<Animal> obtenerTodosLosAnimales() {
        List<Animal> animales = new ArrayList<>();

        String query = "SELECT * FROM " + AnimalEntry.TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Animal animal = new Animal();
                animal.setId(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry._ID)));
                animal.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_NOMBRE)));
                animal.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_DESCRIPCION)));
                animal.setFoto_url(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_FOTO_URL)));
                animal.setRegionId(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_REGION_ID)));
                animal.setEsFavorito(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_ES_FAVORITO)) == 1);
                animal.setFechaIngreso(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_FECHA_INGRESO)));

                animales.add(animal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return animales;
    }

    // READ - Obtener animales por región
    public List<Animal> obtenerAnimalesPorRegion(int regionId) {
        List<Animal> animales = new ArrayList<>();

        String selection = AnimalEntry.COLUMN_REGION_ID + " = ?";
        String[] selectionArgs = { String.valueOf(regionId) };

        Cursor cursor = database.query(
                AnimalEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                AnimalEntry.COLUMN_NOMBRE + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                Animal animal = new Animal();
                animal.setId(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry._ID)));
                animal.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_NOMBRE)));
                animal.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_DESCRIPCION)));
                animal.setFoto_url(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_FOTO_URL)));
                animal.setRegionId(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_REGION_ID)));
                animal.setEsFavorito(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_ES_FAVORITO)) == 1);
                animal.setFechaIngreso(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_FECHA_INGRESO)));

                animales.add(animal);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return animales;
    }

    // READ - Obtener animal por ID
    public Animal obtenerAnimalPorId(int id) {
        String selection = AnimalEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = database.query(
                AnimalEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Animal animal = null;
        if (cursor.moveToFirst()) {
            animal = new Animal();
            animal.setId(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry._ID)));
            animal.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_NOMBRE)));
            animal.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_DESCRIPCION)));
            animal.setFoto_url(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_FOTO_URL)));
            animal.setRegionId(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_REGION_ID)));
            animal.setEsFavorito(cursor.getInt(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_ES_FAVORITO)) == 1);
            animal.setFechaIngreso(cursor.getString(cursor.getColumnIndexOrThrow(AnimalEntry.COLUMN_FECHA_INGRESO)));
        }
        cursor.close();
        return animal;
    }

    // UPDATE - Actualizar animal
    public int actualizarAnimal(Animal animal) {
        ContentValues values = new ContentValues();
        values.put(AnimalEntry.COLUMN_NOMBRE, animal.getNombre());
        values.put(AnimalEntry.COLUMN_DESCRIPCION, animal.getDescripcion());
        values.put(AnimalEntry.COLUMN_FOTO_URL, animal.getFoto_url());
        values.put(AnimalEntry.COLUMN_REGION_ID, animal.getRegionId());
        values.put(AnimalEntry.COLUMN_ES_FAVORITO, animal.isEsFavorito() ? 1 : 0);

        String selection = AnimalEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(animal.getId()) };

        return database.update(AnimalEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    // DELETE - Eliminar animal
    public int eliminarAnimal(int id) {
        String selection = AnimalEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        return database.delete(AnimalEntry.TABLE_NAME, selection, selectionArgs);
    }

    // ===== OPERACIONES PARA REGIONES =====

    // READ - Obtener todas las regiones
    public List<Region> obtenerTodasLasRegiones() {
        List<Region> regiones = new ArrayList<>();

        String query = "SELECT * FROM " + RegionEntry.TABLE_NAME + " ORDER BY " + RegionEntry._ID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Region region = new Region();
                region.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RegionEntry._ID)));
                region.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(RegionEntry.COLUMN_NOMBRE)));
                regiones.add(region);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return regiones;
    }

    // READ - Obtener región por ID
    public Region obtenerRegionPorId(int id) {
        String selection = RegionEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = database.query(
                RegionEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Region region = null;
        if (cursor.moveToFirst()) {
            region = new Region();
            region.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RegionEntry._ID)));
            region.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(RegionEntry.COLUMN_NOMBRE)));
        }
        cursor.close();
        return region;
    }
}