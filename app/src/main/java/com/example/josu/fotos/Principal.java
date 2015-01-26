package com.example.josu.fotos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


public class Principal extends Activity {

    GridView gv;
    GestorFoto gf;
    Adaptador ad;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        gv = (GridView)findViewById(R.id.gridview);
        gf = new GestorFoto(this);

        cursor = gf.getCursor();
        ad = new Adaptador(this, cursor);

        gv.setAdapter(ad);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (cursor.moveToPosition(position)) {
                    Foto f = GestorFoto.getRow(cursor);
                    maximizar(f, position);
                }
            }
        });
    }

    public void maximizar(Foto f, int position){
        Intent intent = new Intent(this, PantallaCompleta.class);
        Uri data = Uri.parse(f.getRuta());
        intent.putExtra("ruta", data);
        intent.putExtra("posicion", position);
        startActivity(intent);
    }
}
