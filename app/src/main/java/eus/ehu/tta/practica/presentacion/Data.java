package eus.ehu.tta.practica.presentacion;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import eus.ehu.tta.practica.modelo.Choice;
import eus.ehu.tta.practica.modelo.Exercise;
import eus.ehu.tta.practica.modelo.Test;

/**
 * Created by josu on 26/12/17.
 */

public class Data {

    private Test test=new Test();
    private Exercise ejercicio=new Exercise();
    private final static String baseURL="http://u017633.ehu.eus:28080/ServidorTta/rest/tta";
    private final static String PATH_STATUS="getStatus?dni=";
    private final static String PATH_TEST="getTest?id=";
    private final static String PATH_EXERCISE="getExercise?id=";
    private final static String SERVER_USER="12345678A";
    private final static String SERVER_PASS="tta";

    public void setEjercicio(Exercise ejercicio) {
        this.ejercicio = ejercicio;
    }



    public Test getTest(String dni) {

        int nextTest=0;
        String status="No funciona";
        String testString;
        JSONObject jsonTest;
        List<Choice> choices=new ArrayList<Choice>();


        ClienteRest conexionServer=new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(SERVER_USER,SERVER_PASS);
        try {
            status=conexionServer.getJson(PATH_STATUS+dni);
            JSONObject jsonStatus=new JSONObject(status);
            nextTest=jsonStatus.getInt("nextTest");
            testString=conexionServer.getJson(PATH_TEST+String.valueOf(nextTest));
            jsonTest=new JSONObject(testString);
            test.setPregunta(jsonTest.getString("wording"));

            JSONArray opciones=jsonTest.getJSONArray("choices");
            for (int i=0;i<opciones.length();i++)
            {
                Choice opcion=new Choice();
                JSONObject opcionJSON=opciones.getJSONObject(i);
                opcion.setTexto(opcionJSON.getString("answer"));
                opcion.setAyuda(opcionJSON.optString("advise",null));
                JSONObject resourceType=opcionJSON.optJSONObject("resourceType");
                if (resourceType!=null)
                {
                    opcion.setMimeType(resourceType.optString("mime",null));
                }
                else
                    opcion.setMimeType(null);

                opcion.setCorrect(opcionJSON.getBoolean("correct"));
                choices.add(opcion);
            }
            test.setChoices(choices);


        } catch (IOException e) {
            Log.d("error",e.getMessage());
        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }



        return test;



    }

    public void setTest(Test test) {
        this.test = test;
    }


    public Exercise getExercise(String dni) {

        int nextExercise=0;
        String status="No funciona";
        String exerciseString;
        JSONObject jsonExercise;

        ClienteRest conexionServer=new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(SERVER_USER,SERVER_PASS);
        try {
            status=conexionServer.getJson(PATH_STATUS+dni);
            JSONObject jsonStatus=new JSONObject(status);
            nextExercise=jsonStatus.getInt("nextTest");
            exerciseString=conexionServer.getJson(PATH_EXERCISE+String.valueOf(nextExercise));
            jsonExercise=new JSONObject(exerciseString);
            ejercicio.setPregunta(jsonExercise.getString("wording"));

        } catch (IOException e) {
            Log.d("error",e.getMessage());
        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }



        return ejercicio;

    }
}
