package models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AgentesMunicipales {
    public  String id;
    public  String nombre;
    public  String clave;
    private Base64 password;

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

    public String getClave() {
        return clave;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setClave(String clave) {
        try {
            byte[] data = clave.getBytes(StandardCharsets.UTF_8);
            String calves =new String(data,"UTF-8");
            this.clave = calves;
        } catch (UnsupportedEncodingException e) {
            this.clave = "null";
            e.printStackTrace();
        }
    }
}
