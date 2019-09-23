package com.sena.waytek;

import android.os.Parcel;
import android.os.Parcelable;


public class Producto implements Parcelable {
    private int id_producto;
    private String  nombre_tienda;
    private String tipo_producto;
    private String nombre_portatil;
    private String nombre_pc_escritorio;
    private String nombre_presupuesto;
    private String nombre_accesorio;
    private String foto_producto;
    private String descripcion_portatil;
    private String descripcion_pc_escritorio;
    private String descripcion_accesorio;
    private String descripcion_presupuesto;
    private String telefono_tienda;
    private int precio_producto;
    private int descuento_producto;
    private int precio_total_producto;
    private String fecha_publicacion_producto;

    public Producto(int id_producto, String nombre_tienda, String tipo_producto, String nombre_portatil, String nombre_pc_escritorio, String nombre_presupuesto, String nombre_accesorio, String foto_producto, String descripcion_portatil,String descripcion_pc_escritorio, String descripcion_accesorio, String descripcion_presupuesto, String telefono_tienda, int precio_producto, int descuento_producto, int precio_total_producto, String fecha_publicacion_producto){
        this.id_producto = id_producto;
        this.nombre_tienda = nombre_tienda;
        this.tipo_producto = tipo_producto;
        this.nombre_portatil = nombre_portatil;
        this.nombre_pc_escritorio=nombre_pc_escritorio;
        this.nombre_presupuesto= nombre_presupuesto;
        this.nombre_accesorio = nombre_accesorio;
        this.foto_producto = foto_producto;
        this.descripcion_portatil = descripcion_portatil;
        this.descripcion_pc_escritorio = descripcion_pc_escritorio;
        this.descripcion_accesorio = descripcion_accesorio;
        this.descripcion_presupuesto = descripcion_presupuesto;
        this.telefono_tienda =telefono_tienda;
        this.precio_producto = precio_producto;
        this.descuento_producto = descuento_producto;
        this.precio_total_producto = precio_total_producto;
        this.fecha_publicacion_producto = fecha_publicacion_producto;
    }

    protected Producto(Parcel in) {
        id_producto = in.readInt();
        nombre_tienda = in.readString();
        tipo_producto = in.readString();
        nombre_portatil = in.readString();
        nombre_pc_escritorio = in.readString();
        nombre_presupuesto = in.readString();
        nombre_accesorio = in.readString();
        foto_producto = in.readString();
        descripcion_portatil= in.readString();
        descripcion_pc_escritorio=in.readString();
        descripcion_accesorio = in.readString();
        descripcion_presupuesto = in.readString();
        telefono_tienda =in.readString();
        precio_producto = in.readInt();
        descuento_producto = in.readInt();
        precio_total_producto = in.readInt();
        fecha_publicacion_producto = in.readString();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public int getId_producto(){return id_producto;}

    public String getNombre_tienda(){return nombre_tienda;}
    public String getTipo_producto(){return tipo_producto;}
    public String getNombre_portatil(){return nombre_portatil;}
    public String getNombre_pc_escritorio() { return nombre_pc_escritorio; }
    public String getNombre_presupuesto() { return nombre_presupuesto;}
    public String getNombre_accesorio() { return nombre_accesorio; }
    public String getFoto_producto(){return foto_producto;}
    public String getDescripcion_portatil(){return descripcion_portatil;}
    public String getDescripcion_pc_escritorio(){return descripcion_pc_escritorio;}
    public String getDescripcion_accesorio(){return descripcion_accesorio;}
    public String getDescripcion_presupuesto(){return descripcion_presupuesto;}
    public String getTelefono_tienda(){return telefono_tienda;}
    public int getPrecio_producto(){return precio_producto;}
    public int getDescuento_producto(){return descuento_producto;}
    public int getPrecio_total_producto(){return precio_total_producto;}
    public String getFecha_publicacion_producto(){return fecha_publicacion_producto;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_producto);
        parcel.writeString(nombre_tienda);
        parcel.writeString(tipo_producto);
        parcel.writeString(nombre_portatil);
        parcel.writeString(nombre_pc_escritorio);
        parcel.writeString(nombre_presupuesto);
        parcel.writeString(nombre_accesorio);
        parcel.writeString(foto_producto);
        parcel.writeString(descripcion_portatil);
        parcel.writeString(descripcion_pc_escritorio);
        parcel.writeString(descripcion_accesorio);
        parcel.writeString(descripcion_presupuesto);
        parcel.writeString(telefono_tienda);
        parcel.writeInt(precio_producto);
        parcel.writeInt(descuento_producto);
        parcel.writeInt(precio_total_producto);
        parcel.writeString(fecha_publicacion_producto);
    }
}
