package eus.ehu.tta.practica;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import eus.ehu.tta.practica.modelo.Exercise;
import eus.ehu.tta.practica.modelo.Server;
import eus.ehu.tta.practica.presentacion.ProgressTask;

public class EjercicioActivity extends AppCompatActivity {

    private Uri pictureUri;
    private String login;
    private String pass;
    private int userID;
    private int exerciseID;
    private final int PICTURE_REQUEST_CODE=1;
    private final int VIDEO_REQUEST_CODE=2;
    private final int AUDIO_REQUEST_CODE=3;
    private final int READ_REQUEST_CODE=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        Intent intent=getIntent();
        Exercise ejer=(Exercise)intent.getSerializableExtra(MenuActivity.EXERCISE);
        userID=intent.getIntExtra(LoginActivity.USER_ID,0);
        login=intent.getStringExtra(LoginActivity.LOGIN);
        pass=intent.getStringExtra(LoginActivity.PASS);
        exerciseID=ejer.getId();
        TextView preguntaEjer=findViewById(R.id.preguntaEjer);
        preguntaEjer.setText(ejer.getPregunta());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public void subirFoto(View view){
        checkPermission();
    }

    public void subirFichero(View view){
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,READ_REQUEST_CODE);

    }
    public void subirAudio(View view){
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
        {
            Toast.makeText(this,R.string.sinMicro,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent=new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if(intent.resolveActivity(getPackageManager())!=null)
                startActivityForResult(intent,AUDIO_REQUEST_CODE);
            else
                Toast.makeText(this,R.string.sinAppMicro,Toast.LENGTH_SHORT).show();

        }

    }
    public void subirVideo(View view){
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Toast.makeText(this,R.string.sinCamara,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null)
                startActivityForResult(intent,VIDEO_REQUEST_CODE);
            else
                Toast.makeText(this,R.string.sinAppCamara,Toast.LENGTH_SHORT).show();

        }
    }

    public void sacarFoto(){
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Toast.makeText(this,R.string.sinCamara,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager())!=null)
            {
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                try
                {
                    File file=File.createTempFile("tta",".jpg",dir);
                    pictureUri= Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                    startActivityForResult(intent,PICTURE_REQUEST_CODE);
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

    private void checkPermission()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {


            sacarFoto();

        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICTURE_REQUEST_CODE);

        }
    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            sacarFoto();

        } else {
            Toast.makeText(this,R.string.permisoDenegado,Toast.LENGTH_SHORT).show();// Permission was denied. Display an error message.
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        if (resultCode!= Activity.RESULT_OK)
        {
            return;
        }
        switch (requestCode){
            case READ_REQUEST_CODE:
                mostrarDatos(data.getData());
                sendFile(data.getData());
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
                sendFile(data.getData());
                break;//Esto luego quitar
            case PICTURE_REQUEST_CODE:
                sendPhoto(pictureUri);
                break;

        }
    }

    private void sendPhoto(Uri uri) {
        final Server server =new Server();
        String uriAcotada=uri.toString().substring(7);
        File file = new File(uriAcotada);
        String filename=file.getName();


        InputStream is=null;
        try {
            is=new FileInputStream(file);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final InputStream finalIs = is;
        final String finalFilename = filename;
        new ProgressTask<Integer>(this){
            @Override
            protected Integer work() throws Exception{

                return server.enviarFichero(userID,exerciseID, finalIs, finalFilename,login,pass);
            }

            @Override
            protected void onFinish(Integer result)
            {
                if(result==204)
                {
                    Toast.makeText(context,"Subido correctamente",Toast.LENGTH_SHORT).show();

                }

            }
        }.execute();
    }

    private void sendFile(Uri uri) {

        final Server data =new Server();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null, null);
        String filename=null;
        InputStream is=null;
        try {
            is=getContentResolver().openInputStream(uri);

            if (cursor != null && cursor.moveToFirst()) {

                filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            cursor.close();

        }


        final InputStream finalIs = is;
        final String finalFilename = filename;
        new ProgressTask<Integer>(this){
            @Override
            protected Integer work() throws Exception{

                return data.enviarFichero(userID,exerciseID, finalIs, finalFilename,login,pass);
            }

            @Override
            protected void onFinish(Integer result)
            {
                if(result==204)
                {
                    Toast.makeText(context,"Subido correctamente",Toast.LENGTH_SHORT).show();

                }
            }
        }.execute();
    }

    private void mostrarDatos(Uri uri)
     {
         String nombre="ERROR!";
         String tamano="0";

         Cursor cursor = getContentResolver().query(uri, null, null, null, null, null);

         try {

             if (cursor != null && cursor.moveToFirst()) {

                 nombre = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                 int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                 tamano = null;
                 if (!cursor.isNull(sizeIndex)) {
                     tamano = cursor.getString(sizeIndex);
                 } else {
                     tamano = "Unknown";
                 }
             }
         } finally {
             cursor.close();

         }
         Toast.makeText(this,"Fichero: "+nombre+"\nTamaño: "+tamano+" bytes",Toast.LENGTH_SHORT).show();


     }

}
