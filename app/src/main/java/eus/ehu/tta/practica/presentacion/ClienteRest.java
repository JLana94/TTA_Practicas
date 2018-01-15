package eus.ehu.tta.practica.presentacion;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by josu on 15/01/18.
 */

public class ClienteRest {
    private final static String AUTH="Authorization";
    private final String baseURL;
    private final Map<String,String> propiedades=new HashMap<>(); //Esto me va a permitir crear una serie de key->value que es ideal para hacer referencia a las propiedades
    public ClienteRest(String url)
    {
        baseURL=url;
    }
    public void setHttpBasicAuth(String user, String pass)
    {
        String basicAuth= Base64.encodeToString(String.format("%s:%s",user,pass).getBytes(),Base64.DEFAULT);
        propiedades.put(AUTH,String.format("Basic %s",basicAuth));
    }
    public String getAuth()
    {
        return propiedades.get(AUTH);
    }
    public void setAuth(String auth)
    {
        propiedades.put(AUTH,auth);
    }
    public void setPropiedades(String key, String value)
    {
        propiedades.put(key,value);
    }
    private HttpURLConnection getConnection(String path) throws IOException{
        URL url=new URL(baseURL+"/"+path);
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        for(Map.Entry<String,String > propiedad : propiedades.entrySet()) {
            connection.setRequestProperty(propiedad.getKey(), propiedad.getValue());
        }
        connection.setUseCaches(false);
        return connection;
    }
    public String getJson (String path) throws IOException, JSONException
    {
        HttpURLConnection conn=null;
        try {
            conn=getConnection(path);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                return br.readLine();

            }finally {
                if (conn!=null)
                {
                    conn.disconnect();
                }
            }
        }finally {
            if (conn!=null)
            {
                conn.disconnect();
            }
        }
    }


}
