package com.sena.waytek;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import c




















om.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ProductosFragment extends Fragment implements ProductsAdapter.onProductListener{
    private static final String PRODUCT_URL = "http://waytek.000webhostapp.com/app_waytek/api_productos.php";

    List<Producto> productList;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    //OnCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View vista = inflater.inflate(R.layout.productos_fragment, container, false);
        progressBar = vista.findViewById(R.id.progressBarProducts);
        FrameLayout l_main = vista.findViewById(R.id.main_fragment_placeholder);
        swipeRefreshLayout = vista.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.Way_Primary_Deg);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.Way_White);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        //inicializar la productList
        productList =new ArrayList<>();
        productList.clear();

        //Organizar las vistas y layout manager
        adapter = new ProductsAdapter(getActivity(), productList,this);
        recyclerView = vista.findViewById(R.id.recyclerView_Products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        loadProducts(getActivity());
        return vista;
}

@Override
public void onResume(){
        super.onResume();
        refresh();
}
    private void loadProducts(Activity activity){
        progressBar.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
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
                            productObject.getString("procesador"),
                            productObject.getString("almacenamiento"),
                            productObject.getString("ram"),
                            productObject.getString("tarjeta_video"),
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
                    Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();

                }
                adapter.notifyDataSetChanged();
            }

            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                progressBar.setVisibility(View.GONE);
                View v = getLayoutInflater().inflate(R.layout.network_error,null);
                TextView volleyerrortext = v.findViewById(R.id.textViewVolleyError);
                volleyerrortext.setText(error.getMessage());
            }
        });
        Volley.newRequestQueue(activity).add(stringRequest);
    }
    public void detalleproductos(View view){
        Intent detalleproductos = new Intent(getActivity(), DetalleActivity.class);
        startActivity(detalleproductos);
    }

    @Override
    public void onProductClick(int position) {
        Log.d("ALGO", "onProductClick:clicked."+position);
        productList.get(position);
        Intent intent =  new Intent(getActivity(), DetalleActivity.class);
        intent.putExtra("Producto seleccionado", productList.get(position));
        startActivity(intent);
    }

    public void refresh(){
        productList.clear();
        adapter.notifyDataSetChanged();
        loadProducts(getActivity());
    }
}

z<x