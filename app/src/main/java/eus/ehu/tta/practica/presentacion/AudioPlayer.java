package eus.ehu.tta.practica.presentacion;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;

import java.io.IOException;

/**
 * Created by josu on 6/01/18.
 */

public class AudioPlayer implements MediaController.MediaPlayerControl, MediaPlayer.OnPreparedListener{

    private View view;
    private MediaPlayer player;
    private MediaController controller;

    /*public AudioPlayer(View view, final Runnable onExit)
    {
        this.view=view;
        player=new MediaPlayer();
        player.setOnPreparedListener(this);
        controller=new MediaController(view.getContext()){
            @Override
            public boolean dispatchKeyEvent(KeyEvent event){
                if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
                    release();
                    onExit.run();
                }
                return super.dispatchKeyEvent(event);
            }
        };
    }*/

    //DUDA: ese contructor que tengo arriba recibe un objeto que implementa el interfaz Runnable que sirve para ejecutar un thread ¿¿porque??

    public void setAudioUri (Uri uri) throws IOException{
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDataSource(view.getContext(),uri);
        player.prepare();
        player.start();
    }

    public void release()
    {
        if (player!=null){
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onPrepared (MediaPlayer mp){
        controller.setMediaPlayer(this);
        controller.setAnchorView(view);
        controller.show(0);
    }

    @Override
    public void start() {
        player.start();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        player.seekTo(pos);

    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }
    //DUDA: a partir de aqui no tengo claro si están bien los metodos que hay que sobreescribir
    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
