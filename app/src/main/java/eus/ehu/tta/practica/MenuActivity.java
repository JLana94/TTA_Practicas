package eus.ehu.tta.practica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import eus.ehu.tta.practica.modelo.Exercise;
import eus.ehu.tta.practica.modelo.Test;
import eus.ehu.tta.practica.modelo.User;
import eus.ehu.tta.practica.modelo.Server;
import eus.ehu.tta.practica.presentacion.NetworkChecker;
import eus.ehu.tta.practica.presentacion.ProgressTask;

public class MenuActivity extends AppCompatActivity {

    public final static String LOGIN="loginUsuario";
    public final static String PREFERENCES="preferenciasGlobales";
    public final static String TEST="test";
    public final static String EXERCISE="ejercicio";
    public final static String USER_ID="userID";
    private String login;
    private String pass;
    private String nomLeccion;
    private int numLeccion;
    private String nombre;
    private int userID;
    private int nextTest;
    private int nextExercise;
    private Server server = new Server();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent=getIntent();
        User user=(User)intent.getSerializableExtra(LoginActivity.USER);
        pass=intent.getStringExtra(LoginActivity.PASS);
        login=intent.getStringExtra(LoginActivity.LOGIN);
        nombre=user.getNombre();
        nomLeccion=user.getLessonTitle();
        numLeccion=user.getLessonNumber();
        nextTest=user.getNextTest();
        nextExercise=user.getNextExercise();
        userID=user.getId();
        TextView titulo=(TextView)findViewById(R.id.tituloLogin);
        TextView titulo2=(TextView)findViewById(R.id.titulo2Login);
        titulo.setText("Bienvenido "+nombre);
        titulo2.setText("Leccion "+String.valueOf(numLeccion)+": "+nomLeccion);
    }

    public void botonTest(View view) {
        NetworkChecker networkChecker=new NetworkChecker(this);
        if (networkChecker.checkConexion())
        {
            new ProgressTask<Test>(this){
                @Override
                protected Test work() throws Exception{

                    return server.getTest(nextTest,login,pass);
                }

                @Override
                protected void onFinish(Test result)
                {
                    startTestActivity(result);

                }
            }.execute();
        }
        else
            Toast.makeText(this,R.string.noConexion,Toast.LENGTH_SHORT).show();

    }



    public void botonEjer(View view) {

        NetworkChecker networkChecker=new NetworkChecker(this);
        if (networkChecker.checkConexion()) {
            new ProgressTask<Exercise>(this) {
                @Override
                protected Exercise work() throws Exception {

                    return server.getExercise(nextExercise, login, pass);
                }

                @Override
                protected void onFinish(Exercise result) {
                    startExerciseActivity(result);

                }
            }.execute();
        }
        else
            Toast.makeText(this,R.string.noConexion,Toast.LENGTH_SHORT).show();

    }

    private void startExerciseActivity(Exercise result) {
        Intent intent=new Intent(this,EjercicioActivity.class);
        intent.putExtra(EXERCISE,result);
        intent.putExtra(LoginActivity.LOGIN,login);
        intent.putExtra(LoginActivity.PASS,pass);
        intent.putExtra(LoginActivity.USER_ID,userID);
        startActivity(intent);
    }

    public void botonSeg(View view) {
        //
    }

    private void startTestActivity( Test result) {
        Intent intent=new Intent(this,TestActivity.class);
        intent.putExtra(TEST,result);
        intent.putExtra(LoginActivity.LOGIN,login);
        intent.putExtra(LoginActivity.PASS,pass);
        intent.putExtra(LoginActivity.USER_ID,userID);
        startActivity(intent);
    }

}
