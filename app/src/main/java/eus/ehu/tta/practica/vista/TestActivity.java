package eus.ehu.tta.practica.vista;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

import eus.ehu.tta.practica.R;
import eus.ehu.tta.practica.modelo.Choice;
import eus.ehu.tta.practica.modelo.Test;
import eus.ehu.tta.practica.presentacion.AudioPlayer;
import eus.ehu.tta.practica.modelo.Server;
import eus.ehu.tta.practica.presentacion.NetworkChecker;
import eus.ehu.tta.practica.presentacion.ProgressTask;

import static android.widget.Toast.LENGTH_SHORT;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    int correct;
    int selected;
    LinearLayout layout;
    private String login;
    private String pass;
    private Test test;
    private int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent=getIntent();
        test=(Test)intent.getSerializableExtra(MenuActivity.TEST);
        TextView pregunta= findViewById(R.id.testQuestion);
        pregunta.setText(test.getPregunta());

        userID=intent.getIntExtra(LoginActivity.USER_ID,0);
        login=intent.getStringExtra(LoginActivity.LOGIN);
        pass=intent.getStringExtra(LoginActivity.PASS);


        RadioGroup choices= findViewById(R.id.testChoices);
        int i=0;
        for(Choice choice : test.getChoices())
        {
            RadioButton opcion=new RadioButton(this);
            opcion.setId(choice.getId());
            opcion.setText(choice.getTexto());
            opcion.setOnClickListener(this);
            choices.addView(opcion);
            if(choice.isCorrect())
                correct=i;
            i++;
        }
        layout= findViewById(R.id.activity_test);
    }



    public void enviar(View view) {

        RadioGroup choices= findViewById(R.id.testChoices);

        findViewById(R.id.botonEnviar).setVisibility(View.GONE);
        int numOpciones=choices.getChildCount();
        for (int i=0;i<numOpciones;i++)
        {
            if(choices.getChildAt(i).getId()==choices.getCheckedRadioButtonId())
            {
                selected=i;
            }
            choices.getChildAt(i).setEnabled(false);
        }

        choices.getChildAt(correct).setBackgroundColor(Color.GREEN);
        if(selected!=correct)
        {
            choices.getChildAt(selected).setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(),R.string.toastFallo, LENGTH_SHORT).show();
            findViewById(R.id.botonAyuda).setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(getApplicationContext(),R.string.toastAcierto, LENGTH_SHORT).show();

        }

        uploadChoice(login,choices.getChildAt(selected).getId());



    }

    private void uploadChoice(final String login, final int selected) {

        final Server server=new Server();

        NetworkChecker networkChecker=new NetworkChecker(this);
        if (networkChecker.checkConexion()) {
            new ProgressTask<Integer>(this) {
                @Override
                protected Integer work() throws Exception {

                    return server.uploadChoice(userID, selected, login, pass);
                }

                @Override
                protected void onFinish(Integer result) {
                    if (result == 204) {
                        Toast.makeText(context, R.string.respuestaToServer, Toast.LENGTH_SHORT).show();

                    }
                }
            }.execute();
        }
        else
            Toast.makeText(this,R.string.noConexionSubir,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View v) {
        findViewById(R.id.botonEnviar).setVisibility(View.VISIBLE);
    }

    public void verAyuda(View view) throws IOException {
        Button botonAyuda=findViewById(R.id.botonAyuda);
        botonAyuda.setEnabled(false);
        String mimeType=test.getChoices().get(selected).getMimeType();
        String ayuda=test.getChoices().get(selected).getAyuda();

        if(mimeType.equals("text/html"))
        {

            if (ayuda.substring(0,10).contains("://"))
            {
                Uri uri= Uri.parse(ayuda);
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
            else
            {
                WebView web=new WebView(this);
                web.loadData(ayuda,"text/html",null);
                web.setBackgroundColor(Color.TRANSPARENT);
                web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
                layout.addView(web);
            }

        }
        if(mimeType.contains("audio"))
        {

           AudioPlayer player=new AudioPlayer(layout, new Runnable(){
               @Override
               public void run() {
                   finish();
               }
           });
           player.setAudioUri(Uri.parse(ayuda));

        }
        if(mimeType.contains("video"))
        {
            showVideo(ayuda);

        }




    }

    private void showVideo(String ayuda) {
        VideoView video = new VideoView(this);
        video.setVideoURI(Uri.parse(ayuda));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        video.setLayoutParams(params);

        MediaController controller=new MediaController(this){
            @Override
            public void hide()
            {

            }
            @Override
            public boolean dispatchKeyEvent(KeyEvent event)
            {
                if (event.getKeyCode()==KeyEvent.KEYCODE_BACK)
                {
                    finish();
                }
                return super.dispatchKeyEvent(event);
            }
        };
        controller.setAnchorView(video);
        video.setMediaController(controller);

        layout.addView(video);
        video.start();

    }
}
