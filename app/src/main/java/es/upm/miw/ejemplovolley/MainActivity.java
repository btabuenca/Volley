package es.upm.miw.ejemplovolley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;

import es.upm.miw.ejemplovolley.models.Centro;


public class MainActivity extends Activity {

    static final String URL_RECURSO = "https://www.upm.es/wapi_upm/academico/comun/index.upm/v2/centro.json";
    static final String TAG = "MiW" ;
    private RequestQueue colaPeticiones;
    private TextView tvResultado;
    Gson gson;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SingletonVolley volley;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Acceder instancia volley y obtener cola peticiones
        volley = SingletonVolley.getInstance(getApplicationContext());
        colaPeticiones = volley.getRequestQueue();

        tvResultado = (TextView) findViewById(R.id.tvResultado);
        gson = new Gson();
        performRequest(URL_RECURSO);
    }


    /**
     * Genera la petición y la encola
     * @param urlRecurso URL del recurso solicitado
     */
    public void performRequest(String urlRecurso){
        JsonArrayRequest peticion = new JsonArrayRequest(
                urlRecurso,                             // URL to fetch the JSON from
                new Response.Listener<JSONArray>() {    // Listener to receive the JSON response
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        Log.i(TAG, "#centros UPM=" + Integer.toString(jsonArray.length()));

                        // btb split into items
                        StringBuilder sb = new StringBuilder();
                        for (int c = 0; c < jsonArray.length(); c++) {
                            try {
                                Centro centro = gson.fromJson(
                                        jsonArray.getJSONObject(c).toString(),
                                        Centro.class
                                );
                                Log.i(TAG, '\t' + centro.toString());

                                sb.append("- ["+centro.getCodigo()+"] "+centro.getNombre()+"\n");
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                        tvResultado.setText(sb.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.toString());
                    }
                }
        );
        encolarPeticion(peticion);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (colaPeticiones != null) {
            colaPeticiones.cancelAll(TAG);
        }
        Log.i(TAG, "onStop() - Peticiones canceladas");
    }

    /**
     * Encola la petición
     * @param peticion petición HTTP
     */
    public void encolarPeticion(Request peticion) {
        if (peticion != null) {
            peticion.setTag(TAG);  // Tag for this request. Can be used to cancel all requests with this tag
            peticion.setRetryPolicy(
                    new DefaultRetryPolicy(
                            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,  // initial timeout for the policy.
                            3,      // maximum number of retries
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )
            );
            colaPeticiones.add(peticion);
        }
    }
}
