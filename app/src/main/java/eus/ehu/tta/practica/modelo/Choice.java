package eus.ehu.tta.practica.modelo;

import java.io.Serializable;

/**
 * Created by josu on 26/12/17.
 */

public class Choice implements Serializable{
    private boolean correct;
    private String texto;
    private String mimeType;
    private String ayuda;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getAyuda() {
        return ayuda;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }


    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


}
