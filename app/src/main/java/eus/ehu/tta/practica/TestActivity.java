package eus.ehu.tta.practica;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import eus.ehu.tta.practica.modelo.Choice;
import eus.ehu.tta.practica.modelo.Test;
import eus.ehu.tta.practica.presentacion.AudioPlayer;
import eus.ehu.tta.practica.presentacion.Data;

import static android.widget.Toast.LENGTH_SHORT;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    int correct;
    int selected;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Data data=new Data();
        Test test=data.getTest();
        TextView pregunta= findViewById(R.id.testQuestion);
        pregunta.setText(test.getPregunta());
        RadioGroup choices= findViewById(R.id.testChoices);
        int i=0;
        for(Choice choice : test.getChoices())
        {
            RadioButton opcion=new RadioButton(this);
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

        Data data=new Data();
        Test test=data.getTest();


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
        }
        else
        {
            Toast.makeText(getApplicationContext(),R.string.toastAcierto, LENGTH_SHORT).show();
        }
        if(test.getChoices().get(selected).getAyuda()!=null)
        {
            findViewById(R.id.botonAyuda).setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onClick(View v) {
        findViewById(R.id.botonEnviar).setVisibility(View.VISIBLE);
    }

    public void verAyuda(View view) {
        Data data=new Data();
        Test test=data.getTest();
        String mimeType=test.getChoices().get(selected).getMimeType();
        String ayuda=test.getChoices().get(selected).getAyuda();

        if(mimeType=="text/html")
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
           // AudioPlayer player=new AudioPlayer(view, onStop());
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
