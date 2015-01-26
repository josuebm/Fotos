package com.example.josu.fotos;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class PantallaCompleta extends Activity implements View.OnTouchListener{

    ImageView iv;
    float punto = 0;
    Cursor cursor;
    GestorFoto gf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_completa);
        gf = new GestorFoto(this);
        cursor = gf.getCursor();
        cursor.moveToPosition((int)getIntent().getExtras().get("posicion"));
        iv = (ImageView)findViewById(R.id.ivGrande);
        cargarFoto();
        iv.setOnTouchListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("posicion", cursor.getPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cursor.moveToPosition(savedInstanceState.getInt("posicion"));
        cargarFoto();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            punto = event.getX();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            float distancia = punto - event.getX();
            if (distancia > 0) {
                if(!cursor.isLast()){
                    cursor.moveToNext();
                    cargarFoto();
                }
                else
                    Toast.makeText(PantallaCompleta.this, getResources().getString(R.string.ultima_foto), Toast.LENGTH_SHORT).show();
            }else if(distancia < 0){
                if(!cursor.isFirst()){
                    cursor.moveToPrevious();
                    cargarFoto();
                }
                else
                    Toast.makeText(PantallaCompleta.this, getResources().getString(R.string.primera_foto), Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    public void cargarFoto(){
        Foto f = GestorFoto.getRow(cursor);
        Uri uri = Uri.parse(f.getRuta());
        iv.setImageURI(uri);
    }
}
