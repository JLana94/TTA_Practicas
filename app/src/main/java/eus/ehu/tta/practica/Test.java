package eus.ehu.tta.practica;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by josu on 26/12/17.
 */

public class Test {
    private String pregunta;

    public String getPregunta() {
        //return pregunta;
        return "¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?";
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<Choice> getChoices() {
        List <Choice> opcionesFijas= new ArrayList<Choice>(5);
        Choice opcionFija1=new Choice();
        opcionFija1.setTexto("Versión de la aplicación");
        opcionFija1.setCorrect(false);
        opcionesFijas.add(opcionFija1);

        Choice opcionFija2=new Choice();
        opcionFija2.setTexto("Listado de componentes de la aplicación");
        opcionFija2.setCorrect(false);
        opcionesFijas.add(opcionFija2);

        Choice opcionFija3=new Choice();
        opcionFija3.setTexto("Opciones del menú de ajustes");
        opcionFija3.setCorrect(true);
        opcionesFijas.add(opcionFija3);

        Choice opcionFija4=new Choice();
        opcionFija4.setTexto("Nivel mínimo de la API Android requerida");
        opcionFija4.setCorrect(false);
        opcionesFijas.add(opcionFija4);

        Choice opcionFija5=new Choice();
        opcionFija5.setTexto("Nombre del paquete java de la aplicación");
        opcionFija5.setCorrect(false);
        opcionesFijas.add(opcionFija5);

        return opcionesFijas;



        //return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    private List<Choice> choices;
}
