package eus.ehu.tta.practica.presentacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by josu on 12/01/18.
 */

public abstract class ProgressTask<A> extends AsyncTask<Void, Void, A> {
    protected final Context context;
    private final ProgressDialog dialog;
    private Exception e;

    public ProgressTask(Context context)
    {
        this.context=context;
        dialog=new ProgressDialog(context);
        dialog.setMessage("Cargando...");
    }

    @Override
    protected void onPreExecute(){
        dialog.show();
    }

    @Override
    protected A doInBackground(Void... params)
    {
        A result = null;
        try {
            result=work();
        } catch (Exception e)
        {
            this.e=e;
        }
        return result;
    }

    @Override
    protected void onPostExecute(A result)
    {
        if (dialog.isShowing())
            dialog.dismiss();
        if (e!=null)
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        else
            onFinish(result);
    }
    protected abstract A work() throws Exception;
    protected abstract void onFinish(A result);

}
