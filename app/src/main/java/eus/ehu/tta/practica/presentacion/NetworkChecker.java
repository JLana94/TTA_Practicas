package eus.ehu.tta.practica.presentacion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by josu on 17/01/18.
 */

public class NetworkChecker {
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    public NetworkChecker(Context context)
    {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
    }

    public boolean checkConexion()
    {
        if(networkInfo!=null&&networkInfo.isConnected())
            return true;
        else
            return false;

    }
    public String conexionType()
    {
        return networkInfo.getTypeName();

    }
}
