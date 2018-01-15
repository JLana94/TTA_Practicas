package eus.ehu.tta.practica.modelo;

import java.io.Serializable;

/**
 * Created by josu on 26/12/17.
 */

public class Exercise implements Serializable{
    private String pregunta;
    private int id;

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
