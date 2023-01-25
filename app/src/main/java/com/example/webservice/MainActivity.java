package com.example.webservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import models.AgentesMunicipales;

public class MainActivity extends AppCompatActivity  {
    EditText cedula , clave;
    JsonArrayRequest peticion;
    RequestQueue colaPeticiones;
    String url="https://patentes-gad-gda.shop/api/agentes-municipales";
    ArrayList<AgentesMunicipales> listaAgentesMunicipales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cedula = (EditText) findViewById(R.id.idLoginCedula);
        clave = (EditText) findViewById(R.id.passwordLoginCedula);
        colaPeticiones = Volley.newRequestQueue(this.getBaseContext());

        cargar();

    }

    private void cargar(){

        peticion=new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                listaAgentesMunicipales=new ArrayList<>();
                for (int i=0;i<response.length();i++){
                   AgentesMunicipales agentesMunicipales= new AgentesMunicipales();
                    try {
                        JSONObject agente= response.getJSONObject(i);
                        agentesMunicipales.setId(agente.optString("id"));
                        agentesMunicipales.setNombre(agente.optString("nombre"));
                        agentesMunicipales.setClave(agente.optString("clave"));
                        //Toast.makeText(getBaseContext(),"",Toast.LENGTH_LONG).show();
                        listaAgentesMunicipales.add(agentesMunicipales);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),error.toString(),Toast.LENGTH_LONG).show();
            }

        });
        colaPeticiones.add(peticion);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void comparar(View view) throws UnsupportedEncodingException {

        String cedulacontent=cedula.getText().toString();
        String clavecontent=clave.getText().toString();
        AgentesMunicipales agenteRes = null;
        int c = listaAgentesMunicipales.size();
       for(int i=0;i<c;i++){
           AgentesMunicipales agente = listaAgentesMunicipales.get(i);
            if(agente.getId().equals(cedulacontent.trim())){
               agenteRes = agente;
            }
        }
       if(agenteRes != null){
           byte [] bytes = Base64.getDecoder().decode(agenteRes.getClave().getBytes(StandardCharsets.UTF_8));
           String contraa = new String(bytes,StandardCharsets.UTF_8);
           if(contraa.equals(clavecontent.trim())){
               Intent n=new Intent(MainActivity.this, Home.class);
               n.putExtra("nombre",agenteRes.getNombre());
               n.putExtra("clave", contraa);
               n.putExtra("id",agenteRes.getId());
               startActivity(n);

           }else {
               Toast.makeText(getBaseContext(),"Usuario o contraseña incorrectas",Toast.LENGTH_LONG).show();
           }
       }else {
           Toast.makeText(getBaseContext(),"Usuario o contraseña incorrectas",Toast.LENGTH_LONG).show();
       }




    }


}