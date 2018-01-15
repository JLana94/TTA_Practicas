package eus.ehu.tta.practica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final String loginCte ="12345678A";
    private final String passCte="tta";

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
            saveLogin(login);
            startActivity(intent);
        }
        else
            Toast.makeText(this,R.string.wrongPassword,Toast.LENGTH_SHORT).show();
    }
     private boolean autenticar(String login, String pass)
     {
         boolean check=false;
         if(login.equals(loginCte)&& pass.equals(passCte))
            check=true;
         return check;
     }
     private void saveLogin(String login)
     {
         SharedPreferences preferences=getSharedPreferences(MenuActivity.PREFERENCES,MODE_PRIVATE);
         SharedPreferences.Editor editor=preferences.edit();
         editor.putString(MenuActivity.LOGIN,login);
         editor.commit();
     }
}
