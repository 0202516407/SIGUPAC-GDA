package com.example.webservice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import models.LocalComercial;

public class BusquedaIndividual extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText nombrePropietarioBI,cedulaPropietarioBI,rucBI,tipoBI,nombreComercioBI,busquedaBI, numeroPropietarioBi, contabilidadComercioBi;
    ImageView imagenCI;
    JsonObjectRequest peticionJson;
    RequestQueue colaPeticiones;
    String url = "https://patentes-gad-gda.shop/api/locales-comerciales?id=";
    LocalComercial contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_invidual);

        cedulaPropietarioBI= (EditText)findViewById(R.id.nombrePropietarioBI);
        nombrePropietarioBI=(EditText)findViewById(R.id.cedulaPropietarioBI);
        numeroPropietarioBi = (EditText) findViewById( R.id.numeroPropietarioBi );
        rucBI=(EditText)findViewById(R.id.rucBI);
        tipoBI = (EditText) findViewById(R.id.tipoBI);
        nombreComercioBI = (EditText) findViewById(R.id.nombreComercioBI);
        contabilidadComercioBi = (EditText) findViewById( R.id.contabilidadComercioBi );
        busquedaBI =(EditText) findViewById(R.id.busquedaBI);
        imagenCI = (ImageView) findViewById(R.id.imagenCI);
        colaPeticiones = Volley.newRequestQueue(this.getBaseContext());
    }

    public void consulta(View view){
        String nombre = busquedaBI.getText().toString().trim();
        peticionJson = new JsonObjectRequest(Request.Method.GET,url+nombre.trim(),new JSONObject(),this,this);
        colaPeticiones.add(peticionJson);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(JSONObject response) {
        contacto = new LocalComercial();
        contacto.setId( response.optString( "id" ) );
        contacto.setNombre(response.optString( "nombre_local" ) );
        contacto.setImagen( response.optString( "imagen" ) );
        contacto.setTipo(response.optString( "tipo" ) );
        contacto.setCedula_propietario( response.optString( "cedula" ) );
        contacto.setNombre_propietario( response.optString( "nombre_propietario" ) );
        contacto.setCelular( response.optString( "celular" ) );
        contacto.setContabilidad(   response.optString( "contabilidad" ) );
        contacto.setRuc( response.optString( "ruc" ) );
        presentar();

    }

    private void presentar() {

        tipoBI.setText(contacto.getTipo());
        nombreComercioBI.setText(contacto.getNombre());
        cedulaPropietarioBI.setText( contacto.getCedula_propietario() );
        nombrePropietarioBI.setText( contacto.getNombre_propietario() );
        numeroPropietarioBi.setText( contacto.getCelular() );
        contabilidadComercioBi.setText( contacto.getContabilidad() );
        rucBI.setText( contacto.getRuc() );

        if(contacto.getImagen() != null){
            imagenCI.setImageBitmap(contacto.getImagen());
        }else {
            imagenCI.setImageResource(R.drawable.icono);
        }

    }
}