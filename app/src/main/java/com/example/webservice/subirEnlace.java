package com.example.webservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class subirEnlace extends AppCompatActivity {
    EditText tvLink,tvNombreLocacion,tvidLocacion;
    StringRequest peticionString;
    String url="https://patentes-gad-gda.shop/api/locaciones";
    RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_enlace);
        tvidLocacion=(EditText)findViewById(R.id.idLocacion);
        tvNombreLocacion=(EditText)findViewById(R.id.tvNombreLocacion) ;
        tvLink=(EditText)findViewById(R.id.tvLink);
        colaPeticiones = Volley.newRequestQueue(this.getBaseContext());
    }

    public void Guardar(View view){
        peticionString = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuesta= new JSONObject(response);
                    int r=respuesta.optInt("ident");
                    String resultado=respuesta.optString("error");
                    if(r!=0) {
                        tvidLocacion.setText("");
                        tvNombreLocacion.setText("");
                        tvLink.setText("");

                        Toast.makeText(getBaseContext(), "Se ingreso correctamente el enlace del Mapa", Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getBaseContext(),resultado,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        ){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String link = tvLink.getText().toString();
                String idLocacion= tvidLocacion.getText().toString();
                String nombre=tvNombreLocacion.getText().toString();
                Map<String,String> parametros = new HashMap<>();
                parametros.put("link",link);
                parametros.put("id",idLocacion);
                parametros.put("nombre",nombre);
                return parametros;
            }
        };
        colaPeticiones.add(peticionString);
    }

}