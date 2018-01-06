package eus.ehu.tta.practica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        Intent intent=new Intent(this,MenuActivity.class);
        String login=((EditText)findViewById(R.id.login)).getText().toString();
        String pass=((EditText)findViewById(R.id.pass)).getText().toString();
        if (autenticar(login,pass))
        {
            intent.putExtra(MenuActivity.LOGIN,login);
            startActivity(intent);
        }
    }
     private boolean autenticar(String login, String pass)
     {
         boolean check;
         check=true;//Aqui tengo que llamar a una clase de negocio que se ocupa de autenticar
         return check;
     }
}
