package eus.ehu.tta.practica.presentacion;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
                opcion.setId(opcionJSON.getInt("id"));
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
            ejercicio.setId(jsonExercise.getInt("id"));

        } catch (IOException e) {
            Log.d("error",e.getMessage());
        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }



        return ejercicio;

    }

    public int uploadChoice(String DNI, int choiceID) throws JSONException, IOException {
        JSONObject json=new JSONObject();
        int userID=getIDbyDNI(DNI);
        json.put("userId",userID);
        json.put("choiceId",choiceID);
        ClienteRest conexionServer=new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(SERVER_USER,SERVER_PASS);
        Log.d("Enviar respuesta","Usuario: "+userID+" Respuesta: "+choiceID);
        return conexionServer.postChoice(json,"postChoice");

    }

    private int getIDbyDNI(String dni) {
        ClienteRest conexionServer = new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(SERVER_USER, SERVER_PASS);
        int userID=0;
        try {
            String status = conexionServer.getJson(PATH_STATUS + dni);
            JSONObject jsonStatus = new JSONObject(status);
            userID= jsonStatus.getInt("id");

        } catch (IOException e) {
            Log.d("error", e.getMessage());
        } catch (JSONException e) {
            Log.d("error", e.getMessage());
        }
        return userID;
    }

    public int enviarFichero(String login, int exerciseID, InputStream inputStream, String filename)
    {
        int responseCode=0;
        ClienteRest conexionServer = new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(SERVER_USER, SERVER_PASS);
        int userID=getIDbyDNI(login);
        String path="postExercise?user="+userID+"&id="+exerciseID;
        try {
            responseCode=conexionServer.postFile(path,inputStream,filename);
        } catch (IOException e) {
            Log.d("Error",e.getMessage());
        }
        return responseCode;

    }



}
