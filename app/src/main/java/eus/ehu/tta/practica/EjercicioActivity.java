package eus.ehu.tta.practica;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import eus.ehu.tta.practica.modelo.Exercise;
import eus.ehu.tta.practica.presentacion.Data;

public class EjercicioActivity extends AppCompatActivity {

    private Uri pictureUri;
    private int PICTURE_REQUEST_CODE=1;
    private int FOTO=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        Data data=new Data();
        Exercise ejer=data.getEjercicio();
        TextView preguntaEjer=findViewById(R.id.preguntaEjer);
        preguntaEjer.setText(ejer.getPregunta());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public void sacarFoto(){
        Log.d("pruebaFoto","Entra en la funcion");
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Toast.makeText(this,R.string.sinCamara,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.d("pruebaFoto","Ve que tiene cámara");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager())!=null)
            {
                Log.d("pruebaFoto","Ve que tiene app para la cámara");
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                try
                {
                    Log.d("pruebaFoto","Intenta sacar la foto");
                    File file=File.createTempFile("tta",".jpg",dir);
                    Log.d("pruebaFoto","Ha creado el fichero");
                    pictureUri= Uri.fromFile(file);
                    Log.d("pruebaFoto","Encuentra la posición del archivo");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                    Log.d("pruebaFoto","Le pasa el parametro a la llamada de la camara");
                    startActivityForResult(intent,PICTURE_REQUEST_CODE);
                    Log.d("pruebaFoto","Parece que hace todo");
                }
                catch (IOException e){

                }
            }
            else
            {
                Toast.makeText(this,R.string.sinAppCamara,Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void checkPermission(int type)
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
            if(type==FOTO)
            {
                sacarFoto();
            }
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);

            }
    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if(requestCode==FOTO)
            {
                sacarFoto();
            }
        } else {
            Toast.makeText(this,R.string.permisoDenegado,Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
        }
    }

    public void subirFoto(View view){
        checkPermission(FOTO);
    }

    public void subirFichero(View view){

    }
    public void grabarAudio(View view){

    }
    public void grabarVideo(View view){

    }
}
