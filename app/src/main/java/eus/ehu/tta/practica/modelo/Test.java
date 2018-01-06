package eus.ehu.tta.practica.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josu on 26/12/17.
 */

public class Test {
    private String pregunta;

    private List<Choice> choices;


    public String getPregunta() {
        return pregunta;

    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<Choice> getChoices() {

        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }


}
