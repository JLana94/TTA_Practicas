package eus.ehu.tta.practica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    public final static String LOGIN="loginUsuario";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent=getIntent();
        String login=intent.getStringExtra(LOGIN);
        TextView titulo=(TextView)findViewById(R.id.tituloLogin);
        titulo.setText("Bienvenido "+login);
    }

    public void botonTest(View view) {
        Intent intent=new Intent(this,TestActivity.class);
        startActivity(intent);

    }

    public void botonEjer(View view) {
        Intent intent=new Intent(this,EjercicioActivity.class);
        startActivity(intent);

    }

    public void botonSeg(View view) {
        //Aqui todavia no se ha definido nada para hacer

    }

}
