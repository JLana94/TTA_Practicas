package eus.ehu.tta.practica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import eus.ehu.tta.practica.modelo.Exercise;
import eus.ehu.tta.practica.presentacion.Data;

public class EjercicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        Data data=new Data();
        Exercise ejer=data.getEjercicio();
        TextView preguntaEjer=findViewById(R.id.preguntaEjer);
        preguntaEjer.setText(ejer.getPregunta());
    }

    public void sacarFoto(View view){

    }
    public void subirFichero(View view){

    }
    public void grabarAudio(View view){

    }
    public void grabarVideo(View view){

    }
}
