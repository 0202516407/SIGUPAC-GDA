package com.example.webservice;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ActualizarContacto extends AppCompatActivity {
    EditText claveactual, clavenueva, repetirclave;
    StringRequest peticionString;
    RequestQueue colaPeticiones;
    String url = "https://patentes-gad-gda.shop/api/actualizar/agente";
    String clave,id,clave64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_contacto);
        claveactual = (EditText) findViewById(R.id.idPasswordActual);
        clavenueva = (EditText) findViewById(R.id.idPasswordNueva);
        repetirclave =(EditText) findViewById(R.id.idPasswordRepetir);
        colaPeticiones = Volley.newRequestQueue(this.getBaseContext());
        clave= getIntent().getStringExtra( "clave" );
        id=getIntent().getStringExtra( "id" );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cambiar(View view) {
        if (claveactual.getText().toString().trim().equals( clave )){
            if (clavenueva.getText().toString().trim().equals( repetirclave.getText().toString().trim())){
                byte[] data=Base64.getEncoder().encode( clavenueva.getText().toString().trim().getBytes( StandardCharsets.UTF_8 ));
                clave64 = new String(data,StandardCharsets.UTF_8);
                actualizar();
            }else {
                Toast.makeText( getBaseContext(),"Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText( getBaseContext(),"La contraseña es incorrecta", Toast.LENGTH_LONG).show();
        }

    }

    private void actualizar (){
        peticionString = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respuesta= new JSONObject(response);
                    int r=respuesta.optInt("ident");
                    String resultado=respuesta.optString("error");
                    if(r!=0) {
                        Toast.makeText(getBaseContext(), respuesta.optString( "result" ), Toast.LENGTH_LONG).show();
                        Intent i=new Intent(ActualizarContacto.this, MainActivity.class);
                        startActivity( i );
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

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id",id);
                parametros.put("password",clave64);
                return parametros;
            }
        };
        colaPeticiones.add(peticionString);
    }

}

