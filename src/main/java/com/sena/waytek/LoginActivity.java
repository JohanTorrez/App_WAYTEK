package com.sena.waytek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tapadoo.alerter.Alerter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    RequestQueue rq;
    JsonRequest jrq;
    private EditText cajaCorreo;
    private EditText cajaContra;
    Button ingresar;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cajaCorreo = findViewById(R.id.input_correolog);
        cajaContra = findViewById(R.id.input_contrasena);
        ingresar = findViewById(R.id.button_login);
        rq = Volley.newRequestQueue(getApplicationContext());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = firebaseAuth.getCurrentUser();
                if (usuario !=null) {
                    Log.i("SESION","sesion iniciada con Email: " + usuario.getEmail());
                    InicioMain();
                } else {
                    Log.i("SESION", "Sesion cerrada");
                }
            }
        };

        ingresar.setOnClickListener(this);

    }

    public void link_registro (View view) {
        Intent link_registro = new Intent(this, RegistroActivity.class);
        startActivity(link_registro);
    }

    /*@Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"No se encontró el usuario" + error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        usuario usuario = new usuario();
        Toast.makeText(getApplicationContext(), "Se ha encontado al usuario" + cajaCorreo.getText().toString(),
                Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setCorreo(jsonObject.optString("correo"));
            usuario.setContrasena(jsonObject.optString("contrasena"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
    private void iniciarSesion(String email, String pass) {
        if (!validarCampos()) {
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("SESION", "Sesion iniciada");
                } else {
                    Log.e("SESION", task.getException().getMessage()+"");
                }
            }
        });

    }

    private boolean validarCampos() {
        boolean valid = true;

        if(cajaCorreo.getText().toString().isEmpty()){
            Alerter.create(LoginActivity.this)
                    .setTitle("ERROR")
                    .setText("Campo(s) vacío(s).")
                    .setBackgroundColorRes(R.color.alert_default_error_background)
                    .show();
            cajaCorreo.setError("Requerido");

            valid = false;
        } else {
            cajaCorreo.setError(null);
        }
        if (cajaContra.getText().toString().isEmpty()){
            Alerter.create(LoginActivity.this)
                    .setTitle("ERROR")
                    .setText("Campo(s) vacío(s).")
                    .setBackgroundColorRes(R.color.alert_default_error_background)
                    .show();
            cajaContra.setError("Requerido");
            valid = false;
        } else {
            cajaContra.setError(null);
        }
        return valid;
    }

    private void InicioMain() {
        Intent iniciarMain = new Intent(this, MainActivity.class);
        startActivity(iniciarMain);
    }

    @Override
    public void onClick(View view) {
        String email = cajaCorreo.getText().toString();
        String pass = cajaContra.getText().toString();
        iniciarSesion(email,pass);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}