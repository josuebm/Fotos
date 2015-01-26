package com.example.josu.fotos;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Transformation;

/**
 * Created by Josué on 25/01/2015.
 */
public class Adaptador extends CursorAdapter {

    Context contexto;

    public Adaptador(Context context, Cursor c) {
        super(context, c, true);
        contexto = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.detalle, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView iv;
        iv = (ImageView) view.findViewById(R.id.ivMiniatura);
        Foto foto = GestorFoto.getRow(cursor);
        iv.setImageBitmap(miniatura(foto));
    }

    public Bitmap miniatura(Foto foto){
        BitmapFactory.Options options = new BitmapFactory.Options();
        long tamano = Long.parseLong(foto.getTamano());
        if(tamano < 100000)
            options.inSampleSize = 5;
        else if(tamano < 200000)
            options.inSampleSize = 10;
        else if(tamano < 300000)
            options.inSampleSize = 15;
        else if(tamano < 4000000)
            options.inSampleSize = 20;
        else if(tamano < 5000000)
            options.inSampleSize = 25;
        else if(tamano < 6000000)
            options.inSampleSize = 30;
        else
            options.inSampleSize = 40;

        Bitmap img = BitmapFactory.decodeFile(foto.getRuta(), options);
        CropSquareTransformation crop = new CropSquareTransformation();
        return crop.transform(img);
    }

    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }

    }
}
