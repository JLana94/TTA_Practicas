package eus.ehu.tta.practica.presentacion;

import java.util.ArrayList;
import java.util.List;

import eus.ehu.tta.practica.modelo.Choice;
import eus.ehu.tta.practica.modelo.Exercise;
import eus.ehu.tta.practica.modelo.Test;

/**
 * Created by josu on 26/12/17.
 */

public class Data {

    private Test test;
    private Exercise ejercicio;

    public Exercise getEjercicio() {
        Exercise ejercicioFalso=new Exercise();
        ejercicioFalso.setPregunta("Explica cómo aplicarías el patrón de diseño MVP en el desarrollo de una app para Android");
        return ejercicioFalso;
        //return ejercicio;
    }

    public void setEjercicio(Exercise ejercicio) {
        this.ejercicio = ejercicio;
    }



    public Test getTest() {
        //Esta parte es un apaño para poder tener contenido en esta parte
        Test testFalso=new Test();
        testFalso.setPregunta("¿Cuál de las siguientes opciones NO se indica en el fichero de manifiesto de la app?");
        //testFalso.setAdvise("<html><body>The manifest describes the <b>components of the application:</b> the activities, services, broadcast receivers, and content providers that the application is composed of. It names the classes that implement each of the component and publishes their capabilities (for example, which Intent messages they can handle). These declarations let the Android system know what the components are and under what conditions they can be launched.</body></html>");
        List<Choice> opcionesFijas= new ArrayList<Choice>(5);
        Choice opcionFija1=new Choice();
        opcionFija1.setTexto("Versión de la aplicación");
        opcionFija1.setCorrect(false);
        opcionFija1.setMimeType("text/html");
        opcionFija1.setAyuda("https://developer.android.com/studio/publish/versioning.html?hl=es-419");
        opcionesFijas.add(opcionFija1);

        Choice opcionFija2=new Choice();
        opcionFija2.setTexto("Listado de componentes de la aplicación");
        opcionFija2.setCorrect(false);
        opcionFija2.setMimeType("text/html");
        opcionFija2.setAyuda("<html><body>The manifest describes the <b>components of the application:</b> the activities, services, broadcast receivers, and content providers that the application is composed of. It names the classes that implement each of the component and publishes their capabilities (for example, which Intent messages they can handle). These declarations let the Android system know what the components are and under what conditions they can be launched.</body></html>");
        opcionesFijas.add(opcionFija2);

        Choice opcionFija3=new Choice();
        opcionFija3.setTexto("Opciones del menú de ajustes");
        opcionFija3.setCorrect(true);
        opcionFija3.setAyuda(null);
        opcionesFijas.add(opcionFija3);

        Choice opcionFija4=new Choice();
        opcionFija4.setTexto("Nivel mínimo de la API Android requerida");
        opcionFija4.setCorrect(false);
        opcionFija4.setMimeType("video/mpeg");
        //opcionFija4.setAyuda("http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4");
        opcionFija4.setAyuda("http://techslides.com/demos/sample-videos/small.mp4");
        opcionesFijas.add(opcionFija4);

        Choice opcionFija5=new Choice();
        opcionFija5.setTexto("Nombre del paquete java de la aplicación");
        opcionFija5.setCorrect(false);
        opcionFija5.setMimeType("audio/mpeg3");
        //opcionFija5.setAyuda("http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4");
        opcionFija5.setAyuda("http://techslides.com/demos/sample-videos/small.mp4");
        opcionesFijas.add(opcionFija5);
        testFalso.setChoices(opcionesFijas);
        return testFalso;
        //return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }


}
