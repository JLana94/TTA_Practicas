package eus.ehu.tta.practica.presentacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import eus.ehu.tta.practica.R;
import eus.ehu.tta.practica.modelo.User;
import eus.ehu.tta.practica.modelo.Server;

public class LoginActivity extends AppCompatActivity {

    public static final String USER="user";
    public static final String PASS="pass";
    public static final String LOGIN="login";
    public static final String USER_ID="userID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        final Intent intent=new Intent(this,MenuActivity.class);
        final String login=((EditText)findViewById(R.id.login)).getText().toString();
        final String pass=((EditText)findViewById(R.id.pass)).getText().toString();
        final Server server=new Server();

        NetworkChecker networkChecker=new NetworkChecker(this);
        if (networkChecker.checkConexion())
        {
            new ProgressTask<User>(this){
                @Override
                protected User work() throws Exception{

                    return server.getUser(login,pass);
                }

                @Override
                protected void onFinish(User result)
                {
                    if (result!=null)
                    {
                        intent.putExtra(USER,result);
                        intent.putExtra(PASS,pass);
                        intent.putExtra(LOGIN,login);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(context,R.string.wrongPassword,Toast.LENGTH_SHORT).show();


                }
            }.execute();

        }
        else
            Toast.makeText(this,R.string.noConexion,Toast.LENGTH_SHORT).show();



     }

}
