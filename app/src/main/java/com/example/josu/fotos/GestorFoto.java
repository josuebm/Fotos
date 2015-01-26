package com.example.josu.fotos;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

/**
 * Created by Josué on 25/01/2015.
 */
public class GestorFoto {

    Context contexto;


    public GestorFoto(Context c){
        contexto = c;
    }


    public static Foto getRow(Cursor c) {
        Foto objeto = new Foto();
        objeto.setId(c.getString(0));
        objeto.setNombre(c.getString(1));
        objeto.setRuta(c.getString(2));
        objeto.setMime(c.getString(3));
        objeto.setFecha(c.getString(4));
        objeto.setTamano(c.getString(5));
        objeto.setMiniatura(c.getString(6));
        return objeto;
    }

    public Cursor getCursor() {
        String[] columnas = {MediaStore.Images.Media._ID, MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DATA, MediaStore.Images.Media.MIME_TYPE, MediaStore.Images.Media.DATE_MODIFIED, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.MINI_THUMB_MAGIC};
        Cursor cursor = contexto.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columnas, null, null, MediaStore.Images.Media.DATE_MODIFIED + " desc");
        return cursor;
    }
}
