package com.example.webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    String nombreUsuario,clave,id;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nombreUsuario=getIntent().getStringExtra("nombre");
        clave=getIntent().getStringExtra("clave");
        id=getIntent().getStringExtra( "id" );
        tv1 =(TextView) findViewById(R.id.tv1);
        tv1.setText(nombreUsuario);}

    public void Registrar(View view){

        Intent i=new Intent(Home.this, InsertarComercio.class);
        i.putExtra( "id",id );
        startActivity(i);
    }
    public void Consultar(View view){

        Intent i=new Intent(Home.this, BusquedaIndividual.class);
        startActivity(i);
    }

    public void SubirMapa(View view){

        Intent i=new Intent(Home.this, subirEnlace.class);
        startActivity(i);
    }

    public void Configurar(View view){

        Intent i=new Intent(Home.this, ActualizarContacto.class);
        i.putExtra( "clave",clave );
        i.putExtra( "id",id );
        startActivity(i);
    }



}
