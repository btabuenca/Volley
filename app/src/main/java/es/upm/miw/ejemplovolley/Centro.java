package es.upm.miw.ejemplovolley;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Centro {

    @SerializedName("codigo")
    private String mCodigo;

    @SerializedName("nombre")
    private String mNombre;

    public String getCodigo() {
        return mCodigo;
    }

    public void setCodigo(String codigo) {
        mCodigo = codigo;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    @Override
    public String toString() {
        return "Centro{" +
                "codigo='" + mCodigo + '\'' +
                ", nombre='" + mNombre + '\'' +
                '}';
    }
}
