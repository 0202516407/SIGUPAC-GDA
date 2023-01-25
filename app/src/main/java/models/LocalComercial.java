package models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class LocalComercial {
    public String id;
    public  String nombre;
    public String tipo;
    public  Bitmap imagen;
    public String id_localisacion;
    public  String id_propietario;
    public  String id_usuario;
    public  String nombre_propietario;
    public  String cedula_propietario;
    public  String celular;
    public  String contabilidad;
    public  String ruc;

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContabilidad() {
        return contabilidad;
    }

    public void setContabilidad(String contabilidad) {
        this.contabilidad = contabilidad;
    }

    public String getNombre_propietario() {
        return nombre_propietario;
    }

    public void setNombre_propietario(String nombre_propietario) {
        this.nombre_propietario = nombre_propietario;
    }

    public String getCedula_propietario() {
        return cedula_propietario;
    }

    public void setCedula_propietario(String cedula_propietario) {
        this.cedula_propietario = cedula_propietario;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setImagen(String imagen) {
        try{
            byte[] byteDatos = Base64.getDecoder().decode(imagen);
            this.imagen = BitmapFactory.decodeByteArray(byteDatos,0,byteDatos.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getId_localisacion() {
        return id_localisacion;
    }

    public void setId_localisacion(String id_localisacion) {
        this.id_localisacion = id_localisacion;
    }

    public String getId_propietario() {
        return id_propietario;
    }

    public void setId_propietario(String id_propietario) {
        this.id_propietario = id_propietario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }


}
