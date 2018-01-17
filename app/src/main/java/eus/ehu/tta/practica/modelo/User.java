package eus.ehu.tta.practica.modelo;

import java.io.Serializable;

/**
 * Created by josu on 17/01/18.
 */

public class User implements Serializable{
    private int id;
    private String nombre;
    private int lessonNumber;
    private String lessonTitle;
    private int nextTest;
    private int nextExercise;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(int lessonNumbre) {
        this.lessonNumber = lessonNumbre;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public int getNextTest() {
        return nextTest;
    }

    public void setNextTest(int nextTest) {
        this.nextTest = nextTest;
    }


    public int getNextExercise() {
        return nextExercise;
    }

    public void setNextExercise(int nextExercise) {
        this.nextExercise = nextExercise;
    }
}
