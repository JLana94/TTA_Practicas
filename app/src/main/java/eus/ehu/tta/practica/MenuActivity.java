package eus.ehu.tta.practica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import eus.ehu.tta.practica.modelo.Test;
import eus.ehu.tta.practica.presentacion.Data;
import eus.ehu.tta.practica.presentacion.ProgressTask;

public class MenuActivity extends AppCompatActivity {

    public final static String LOGIN="loginUsuario";
    public final static String PREFERENCES="preferenciasGlobales";
    public final static String TEST="test";
    private String login;
    private Data data = new Data();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences preferences=getSharedPreferences(PREFERENCES,MODE_PRIVATE);
        login=preferences.getString(LOGIN,null);
        TextView titulo=(TextView)findViewById(R.id.tituloLogin);
        titulo.setText("Bienvenido "+login);
    }

    public void botonTest(View view) {

        new ProgressTask<Test>(this){
            @Override
            protected Test work() throws Exception{

                return data.getTest(login);
            }

            @Override
            protected void onFinish(Test result)
            {
                startTestActivity(result);

            }
        }.execute();

    }

    private void startTestActivity( Test result) {
        Intent intent=new Intent(this,TestActivity.class);
        intent.putExtra(TEST,result);
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
