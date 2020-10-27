package es.upm.miw.ejemplovolley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

@SuppressWarnings("unused")
class SingletonVolley {

    private static SingletonVolley instanciaVolley = null;

    // cola de peticiones
    private RequestQueue colaPeticiones;

    private SingletonVolley(Context context) {
        colaPeticiones = Volley.newRequestQueue(context);
    }

    public static SingletonVolley getInstance(Context context) {
        if (instanciaVolley == null) {
            instanciaVolley = new SingletonVolley(context);
        }

        return instanciaVolley;
    }

    public RequestQueue getRequestQueue() {
        return colaPeticiones;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
