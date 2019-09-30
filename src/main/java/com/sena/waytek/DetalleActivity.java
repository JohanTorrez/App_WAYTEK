package com.sena.waytek;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;


public class DetalleActivity extends AppCompatActivity {
    private static final String TAG = "DetalleActivity";
    private ProgressBar progressBar;
    private Context mContext;
    TextView nombreProductoDetalle, precioProductoDetalle,textodescuento, descripcionProductoDetalle, tipoproductotexto, tipoProductoDetalle, procesadorProducto, almacenamientoProducto, ramProducto, tarjetavideoProducto, textovendedor,nombreTienda;
    ImageView imagenDetalle, iconTypeProduct, iconProcesador, iconAlmacenamiento, iconRam, iconTarjetadeVideo;
    Button contactarWhatsapp;
    Producto producto;
    CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_productos_layout);
        ConstraintLayout constraint=findViewById(R.id.constraintLayout);
        if(getIntent().hasExtra("Producto seleccionado")){
        producto = getIntent().getParcelableExtra("Producto seleccionado");
        Log.d(TAG, "OnCreate:"+producto.toString());

        //Vistas
            imagenDetalle =findViewById(R.id.imageViewProductDetail);
            nombreProductoDetalle = findViewById(R.id.textViewNameProductDetail);
            descripcionProductoDetalle = findViewById(R.id.textViewDescripcionProductDetail);
            precioProductoDetalle = findViewById(R.id.textViewPriceDetail);
            textodescuento = findViewById(R.id.textViewDiscount);
            tipoproductotexto = findViewById(R.id.textViewTypeProduct);
            textovendedor = findViewById(R.id.textViewStore);
            tipoProductoDetalle = findViewById(R.id.textViewContentTypeProduct);
            iconTypeProduct = findViewById(R.id.imageViewIconTypeProduct);
            iconProcesador = findViewById(R.id.imageViewProcesador);
            procesadorProducto = findViewById(R.id.textViewProcesador);
            iconAlmacenamiento = findViewById(R.id.imageViewAlmacenamiento);
            almacenamientoProducto = findViewById(R.id.textViewAlmacenamiento);
            iconRam = findViewById(R.id.imageViewRam);
            ramProducto = findViewById(R.id.textViewRam);
            iconTarjetadeVideo = findViewById(R.id.imageViewTarjetavideo);
            tarjetavideoProducto = findViewById(R.id.textViewTarjetaVideo);
            nombreTienda = findViewById(R.id.textViewContentStore);
            contactarWhatsapp = findViewById(R.id.button);
            //Data binding

            //Request Options
            RequestOptions myOptions= new RequestOptions().fitCenter();
            GlideApp.with(DetalleActivity.this).load("http://waytek.000webhostapp.com/" + producto.getFoto_producto()).apply(myOptions).into(imagenDetalle);
            precioProductoDetalle.setText("$ "+ String.format(Locale.ENGLISH, "%,d", producto.getPrecio_producto()));
            if (producto.getDescuento_producto() > 0) {
                textodescuento.setText("-"+String.valueOf(producto.getDescuento_producto())+"%");
            }
            else{
                textodescuento.setVisibility(View.GONE);
            }
            tipoProductoDetalle.setText(StringUtils.capitalize(producto.getTipo_producto()));
            procesadorProducto.setText(producto.getProcesador());
            almacenamientoProducto.setText(producto.getAlmacenamiento());
            ramProducto.setText(producto.getRam());
            tarjetavideoProducto.setText(producto.getTarjetavideo());}
            nombreTienda.setText(producto.getNombre_tienda());
            //Producto a contactar
            final String productoContacto;
         switch (producto.getTipo_producto()){
             case "portátil":
                 nombreProductoDetalle.setText(producto.getNombre_portatil());
                 descripcionProductoDetalle.setText(producto.getDescripcion_portatil());
                 iconTypeProduct.setImageDrawable(getResources().getDrawable(R.drawable.ic_laptop_solid));
                 productoContacto = producto.getNombre_portatil();
                 break;
             case "pc escritorio":
                 nombreProductoDetalle.setText(producto.getNombre_pc_escritorio());
                 descripcionProductoDetalle.setText(producto.getDescripcion_pc_escritorio());
                 iconTypeProduct.setImageDrawable(getResources().getDrawable(R.drawable.ic_desktop_solid));
                 productoContacto = producto.getNombre_pc_escritorio();
                 break;
             case "accesorio":
                 nombreProductoDetalle.setText(producto.getNombre_accesorio());
                 descripcionProductoDetalle.setText(producto.getDescripcion_accesorio());
                 iconTypeProduct.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_solid));
                 constraint.removeView(procesadorProducto);
                 constraint.removeView(almacenamientoProducto);
                 constraint.removeView(ramProducto);
                 constraint.removeView(tarjetavideoProducto);
                 constraint.removeView(iconProcesador);
                 constraint.removeView(iconAlmacenamiento);
                 constraint.removeView(iconRam);
                 constraint.removeView(iconTarjetadeVideo);

                 ConstraintSet constraintSet = new ConstraintSet();
                 constraintSet.clone(constraint);
                 constraintSet.connect(textovendedor.getId(), ConstraintSet.TOP,tipoproductotexto.getId(), ConstraintSet.BOTTOM, 20);
                 constraintSet.connect(nombreTienda.getId(), ConstraintSet.TOP, tipoProductoDetalle.getId(), ConstraintSet.BOTTOM, 26);
                 constraintSet.applyTo(constraint);
                 productoContacto = producto.getNombre_accesorio();
                 break;
                 default:
                 nombreProductoDetalle.setText(producto.getNombre_presupuesto());
                 descripcionProductoDetalle.setText(producto.getDescripcion_presupuesto());
                     iconTypeProduct.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_alt_solid));
                 productoContacto = producto.getNombre_presupuesto();
                     break;
         }
         contactarWhatsapp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ContactarWhatsapp(contactarWhatsapp, producto.getTelefono_tienda(), productoContacto);
             }
         });

    }

    public void ContactarWhatsapp(View view, String telefono, String producto){
        Intent sendIntent = new Intent();
        String codigopais="57";
        String numero=codigopais+telefono;
        String mensaje= "Hola, estoy interesado en "+producto;
        String uri ="whatsapp://send?phone="+numero+"&text="+mensaje;
        sendIntent.setData(Uri.parse(uri));
        startActivity(sendIntent);
    }
}
