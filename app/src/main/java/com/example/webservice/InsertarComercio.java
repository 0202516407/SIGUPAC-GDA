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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class InsertarComercio extends AppCompatActivity {
    EditText tvNombrePropietario,tvcedulaPropietario,tvRuc,tvTipo,tvNombreComercio,tvCodigo,tvNumeroPropietario, tvContabilidadComercio;
    String url = "https://patentes-gad-gda.shop/api/locales-comerciales";
    RequestQueue colaPeticiones;
    ImageView imagenIC;
    Bitmap bitmap;
    StringRequest peticionString;
    String id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_contacto);
        tvcedulaPropietario=(EditText)findViewById(R.id.tvcedulaPropietario);
        tvNombrePropietario=(EditText)findViewById(R.id.tvNombrePropietario);
        tvNumeroPropietario=(EditText)findViewById(R.id.tvNumeroPropietario);
        tvRuc=(EditText)findViewById(R.id.tvRuc);
        tvCodigo =(EditText) findViewById(R.id.tvCodigo);
        tvTipo = (EditText) findViewById(R.id.tvTipo);
        tvNombreComercio = (EditText) findViewById(R.id.tvNombreComercio);
        tvContabilidadComercio = (EditText) findViewById(R.id.tvContabilidadComercio);
        colaPeticiones = Volley.newRequestQueue(this.getBaseContext());
        imagenIC =(ImageView) findViewById(R.id.imagenIC);
        id_usuario = getIntent().getStringExtra( "id" );
    }



    public void insertar(View view){
        peticionString = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respuesta= new JSONObject(response);
                    int r=respuesta.optInt("ident");
                    String resultado=respuesta.optString("error");
                    if(r!=0) {
                        tvcedulaPropietario.setText("");
                        tvNombrePropietario.setText("");
                        tvNumeroPropietario.setText("");
                        tvRuc.setText("");
                        tvCodigo.setText("");
                        tvTipo.setText("");
                        tvNombreComercio.setText("");
                        tvContabilidadComercio.setText("");
                        imagenIC.setImageResource(R.drawable.icono);
                        Toast.makeText(getBaseContext(), "Se ingreso correctamente el comercio", Toast.LENGTH_LONG).show();


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
                String nombrePropietario=tvNombrePropietario.getText().toString();
                String cedulaPropietario=tvcedulaPropietario.getText().toString();
                String numeroPropietario=tvNumeroPropietario.getText().toString();
                String ruc=tvRuc.getText().toString();
                String codigo = tvCodigo.getText().toString();
                String tipo = tvTipo.getText().toString();
                String nombreComercio = tvNombreComercio.getText().toString();
                String contabilidadComercio = tvContabilidadComercio.getText().toString();
                String imagen = convertirImg(bitmap);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("cedula_propietario",cedulaPropietario);
                parametros.put("nombre_propietario",nombrePropietario);
                parametros.put("celular",numeroPropietario);
                parametros.put("ruc",ruc);
                parametros.put("id_local",codigo);
                parametros.put("tipo",tipo);
                parametros.put("nombre_local",nombreComercio);
                parametros.put("contabilidad",contabilidadComercio);
                parametros.put("imagen",imagen);
                parametros.put("id_usuario",id_usuario);
                return parametros;
            }
        };
        colaPeticiones.add(peticionString);
    }

    public void onclick(View view) {
        cargarImagen();
    }

    private void cargarImagen() {
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galeria.setType("image/");
        startActivityForResult(galeria.createChooser(galeria,"Seleccione una aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            imagenIC.setImageURI(path);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getBaseContext().getContentResolver(),path);
                imagenIC.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String convertirImg(Bitmap bitmap){
        ByteArrayOutputStream ida = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,ida);
        byte[] imagenBytes = ida.toByteArray();
        String imgString = Base64
                .getEncoder()
                .encodeToString(imagenBytes);
        return  imgString;
    }

}