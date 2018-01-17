package eus.ehu.tta.practica.modelo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josu on 26/12/17.
 */

public class Server {

    private Test test=new Test();
    private Exercise ejercicio=new Exercise();

    private final static String baseURL="http://u017633.ehu.eus:28080/ServidorTta/rest/tta";
    private final static String PATH_STATUS="getStatus?dni=";
    private final static String PATH_TEST="getTest?id=";
    private final static String PATH_EXERCISE="getExercise?id=";

    public void setEjercicio(Exercise ejercicio) {
        this.ejercicio = ejercicio;
    }



    public Test getTest(int id, String login, String pass) {
        String testString;
        JSONObject jsonTest;
        List<Choice> choices=new ArrayList<Choice>();


        ClienteRest conexionServer=new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(login,pass);
        try {
            testString=conexionServer.getJson(PATH_TEST+String.valueOf(id));
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

    public User getUser(String dni, String pass) {
        JSONObject jsonUser;
        String userString="Fail";
        User user=new User();

        ClienteRest conexionServer=new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(dni,pass);
        try {
            userString=conexionServer.getJson(PATH_STATUS+dni);
            jsonUser=new JSONObject(userString);
            user.setId(jsonUser.getInt("id"));
            user.setNombre(jsonUser.getString("user"));
            user.setLessonNumber(jsonUser.getInt("lessonNumber"));
            user.setLessonTitle(jsonUser.getString("lessonTitle"));
            user.setNextExercise(jsonUser.getInt("nextExercise"));
            user.setNextTest(jsonUser.getInt("nextTest"));


        } catch (IOException e) {
            Log.d("error",e.getMessage());
        } catch (JSONException e) {
            Log.d("error",e.getMessage());
        }


        if(userString.equals("Fail"))
        {
            return null;
        }
        else
            return user;



    }

    public void setTest(Test test) {
        this.test = test;
    }


    public Exercise getExercise(int id,String login,String pass) {

        String exerciseString;
        JSONObject jsonExercise;

        ClienteRest conexionServer=new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(login,pass);
        try {
            exerciseString=conexionServer.getJson(PATH_EXERCISE+String.valueOf(id));
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

    public int uploadChoice(int userID, int choiceID,String login, String pass) throws JSONException, IOException {
        JSONObject json=new JSONObject();
        json.put("userId",userID);
        json.put("choiceId",choiceID);
        ClienteRest conexionServer=new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(login, pass);
        return conexionServer.postJSON(json,"postChoice");

    }


    public int enviarFichero(int userID, int exerciseID, InputStream inputStream, String filename, String login, String pass)
    {
        int responseCode=0;
        ClienteRest conexionServer = new ClienteRest(baseURL);
        conexionServer.setHttpBasicAuth(login, pass);
        String path="postExercise?user="+userID+"&id="+exerciseID;
        try {
            responseCode=conexionServer.postFile(path,inputStream,filename);
        } catch (IOException e) {
            Log.d("Error",e.getMessage());
        }
        return responseCode;

    }



}
