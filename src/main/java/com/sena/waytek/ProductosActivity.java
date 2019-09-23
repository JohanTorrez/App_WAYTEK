package com.sena.waytek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProductosActivity extends AppCompatActivity implements ProductsAdapter.onProductListener{
    private static final String PRODUCT_URL = "http://waytek.000webhostapp.com/app_waytek/api_productos.php";

    List<Producto> productList;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    Activity activity;
    Context context;

    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos_activity);
        progressBar = findViewById(R.id.progressBarProducts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inicializar la productList
        productList =new ArrayList<>();

        //Organizar las vistas y layout manager
        adapter = new ProductsAdapter(ProductosActivity.this, productList,this);
        recyclerView = findViewById(R.id.recyclerView_Products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        loadProducts();
}

@Override
protected void onResume(){
        super.onResume();
        productList.clear();
        adapter.notifyDataSetChanged();
        loadProducts();
}
    private void loadProducts(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    productList.clear();
                    progressBar.setVisibility(View.GONE);
                    //convertir string a objeto json
                    JSONArray array  = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject productObject = array.getJSONObject(i);

                    //Añadir productos a la lista
                    productList.add(new Producto(
                            productObject.getInt("id_producto"),
                            productObject.getString("nombre_tienda"),
                            productObject.getString("tipo_producto"),
                            productObject.getString("nombre_portatil"),
                            productObject.getString("nombre_pc_escritorio"),
                            productObject.getString("nombre_presupuesto"),
                            productObject.getString("nombre_accesorio"),
                            productObject.getString("foto_producto"),
                            productObject.getString("descripcion_portatil"),
                            productObject.getString("descripcion_pc_escritorio"),
                            productObject.getString("descripcion_accesorio"),
                            productObject.getString("descripcion_presupuesto"),
                            productObject.getString("telefono_tienda"),
                            productObject.getInt("precio_producto"),
                            productObject.getInt("descuento_producto"),
                            productObject.getInt("precio_total_producto"),
                            productObject.getString("fecha_publicacion_producto")));
                }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(ProductosActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                adapter.notifyDataSetChanged();
            }

            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductosActivity.this, error.getMessage() + error.networkResponse, Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void detalleproductos(View view){
        Intent detalleproductos = new Intent(this, DetalleActivity.class);
        startActivity(detalleproductos);

    }

    @Override
    public void onProductClick(int position) {
        Log.d("ALGO", "onProductClick:clicked."+position);
        productList.get(position);
        Intent intent =  new Intent(this, DetalleActivity.class);
        intent.putExtra("Producto seleccionado", productList.get(position));
        startActivity(intent);
    }
}

