package com.sena.waytek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tapadoo.alerter.Alerter;

import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener, View.OnClickListener {

    private RequestQueue rq;
    JsonRequest jrq;
    EditText cajaNombre;
    EditText cajaApellido;
    RadioGroup radioSexo;
    RadioButton resulSexo;
    EditText cajaCiudad;
    EditText cajaDepartamento;
    EditText cajaDireccion;
    EditText cajaDocumento;
    EditText cajaTelefono;
    EditText cajaEmail;
    EditText cajaCrearContra;
    EditText cajaConfirmContra;
    Button registrar;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        rq = Volley.newRequestQueue(getApplicationContext());
        registrar = findViewById(R.id.btn_registro);

        cajaNombre = findViewById(R.id.input_nombre);
        cajaApellido = findViewById(R.id.input_apellido);
        cajaCiudad = findViewById(R.id.input_ciudad);
        cajaDepartamento = findViewById(R.id.input_departamento);
        cajaDireccion = findViewById(R.id.input_direccion);
        cajaDocumento = findViewById(R.id.input_documento);
        cajaTelefono = findViewById(R.id.input_telefono);
        cajaEmail = findViewById(R.id.input_email);
        cajaCrearContra = findViewById(R.id.input_crear_contra);
        cajaConfirmContra = findViewById(R.id.input_confirm_contra);

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

        registrar.setOnClickListener(this);

    }

    //Link para ir a login
    public void volverLogin (View view) {
        onBackPressed();
    }
    //Link para ir a inicio/home
    private void InicioMain() {
        Intent iniciarMain = new Intent(this, MainActivity.class);
        Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
        startActivity(iniciarMain);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Bien "+response,
                Toast.LENGTH_SHORT).show();
        limpiar_cajas();
    }
    //Limpiamos cajas cuando cuando el registro sale bien
    void limpiar_cajas(){
        cajaNombre.setText("");
        cajaApellido.setText("");
        cajaCiudad.setText("");
        cajaDepartamento.setText("");
        cajaDireccion.setText("");
        cajaDocumento.setText("");
        cajaTelefono.setText("");
        cajaEmail.setText("");
        cajaCrearContra.setText("");
        cajaCrearContra.setText("");
    }

    //Método para registrar usuario
    private void registrarusuario(String email, String pass) {
        if (!validarCamposReg()) {
            return;
        }
        if (!validarContraseña()) {
            return;
        }
        if (!confirContra()) {
            return;
        }
        //Crear usuario en FireBase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("SESION", "Usuario creado correctamente");
                    regisOnDataBase();
                    InicioMain();
                } else {
                    Log.e("SESION", task.getException().getMessage()+"");
                    String errorCreateUser = task.getException().getMessage()+"";
                    if (errorCreateUser.equals("The email address is already in use by another account.")){
                        errorCreateUser=getString(R.string.errorCreateUserEmail);
                    }
                    Alerter.create(RegistroActivity.this)
                            .setTitle(getString(R.string.errorFirebase))
                            .setText(errorCreateUser)
                            .setBackgroundColorRes(R.color.alert_default_error_background)
                            .show();
                }
            }
        });

    }

    private void regisOnDataBase() {
        radioSexo = findViewById(R.id.input_sexo);
        int radioButtonId = radioSexo.getCheckedRadioButtonId();
        View radioIdSexo = radioSexo.findViewById(radioButtonId);
        int indiceSexo = radioSexo.indexOfChild(radioIdSexo);
        resulSexo = (RadioButton) radioSexo.getChildAt(indiceSexo);
        String textoSexo = resulSexo.getText().toString();
            //Toast.makeText(RegistroActivity.this, textoSexo, Toast.LENGTH_SHORT).show();

            String url = "https://waytek.000webhostapp.com/app_waytek/registro.php?input_nombre="+cajaNombre.getText().toString()+
                    "&input_apellido="+cajaApellido.getText().toString()+"&input_sexo="+textoSexo+
                    "&input_documento="+cajaDocumento.getText().toString()+"&input_telefono"+cajaTelefono.getText().toString()+
                    "&input_email="+cajaEmail.getText().toString()+"&input_crear_contra="+cajaCrearContra.getText().toString()+
                    "&input_ciudad="+cajaCiudad.getText().toString()+"&input_departamento="+cajaDepartamento.getText().toString()+
                    "&input_direccion="+cajaDireccion.getText().toString();
            jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            rq.add(jrq);
    }

    //Condicionando si hay algun campo vacío
    private boolean validarCamposReg() {
        boolean validReg = true;

        String setTitleAlerter = getString(R.string.titleCamReque);
        String setTextAlerter = getString(R.string.textCamReque);
        String errorInputEmpty = getString(R.string.erroInputEmpty);

        if (cajaNombre.getText().toString().isEmpty()) {
            Alerter.create(RegistroActivity.this)
                .setTitle(setTitleAlerter)
                .setText(setTextAlerter)
                .setBackgroundColorRes(R.color.alert_default_error_background)
                .show();
            cajaNombre.setError(errorInputEmpty);
            validReg = false;
        }else {
            cajaNombre.setError(null);
        }

        if (cajaApellido.getText().toString().isEmpty()) {
            Alerter.create(RegistroActivity.this).setTitle(setTitleAlerter)
                    .setText(setTextAlerter)
                    .setBackgroundColorRes(R.color.alert_default_error_background)
                    .show();
            cajaApellido.setError(errorInputEmpty);
            validReg = false;
        }else {
            cajaApellido.setError(null);
        }

        if (cajaCiudad.getText().toString().isEmpty()) {
            Alerter.create(RegistroActivity.this)
                .setTitle(setTitleAlerter)
                .setText(setTextAlerter)
                .setBackgroundColorRes(R.color.alert_default_error_background)
                .show();
            cajaCiudad.setError(errorInputEmpty);
            validReg = false;
        }else {
            cajaCiudad.setError(null);
        }

        if (cajaDepartamento.getText().toString().isEmpty()) {
            Alerter.create(RegistroActivity.this)
                .setTitle(setTitleAlerter)
                .setText(setTextAlerter)
                .setBackgroundColorRes(R.color.alert_default_error_background)
                .show();
            cajaDepartamento.setError(errorInputEmpty);
            validReg = false;
        }else {
            cajaDepartamento.setError(null);
        }

        if (cajaTelefono.getText().toString().isEmpty()) {
            Alerter.create(RegistroActivity.this)
                .setTitle(setTitleAlerter)
                .setText(setTextAlerter)
                .setBackgroundColorRes(R.color.alert_default_error_background)
                .show();
            cajaTelefono.setError(errorInputEmpty);
            validReg = false;
        }else {
            cajaTelefono.setError(null);
        }

        if (cajaEmail.getText().toString().isEmpty()) {
            Alerter.create(RegistroActivity.this)
                .setTitle(setTitleAlerter)
                .setText(setTextAlerter)
                .setBackgroundColorRes(R.color.alert_default_error_background)
                .show();
            cajaEmail.setError(errorInputEmpty);
            validReg = false;
        }else {
            cajaEmail.setError(null);
        }

        if (cajaCrearContra.getText().toString().isEmpty()) {
            Alerter.create(RegistroActivity.this)
                    .setTitle(setTitleAlerter)
                    .setText(setTextAlerter)
                    .setBackgroundColorRes(R.color.alert_default_error_background)
                    .show();
            cajaCrearContra.setError(errorInputEmpty);
            validReg = false;
        } else {
            cajaCrearContra.setError(null);
        }

        if (cajaConfirmContra.getText().toString().isEmpty()) {
            Alerter.create(RegistroActivity.this)
                    .setTitle(setTitleAlerter)
                    .setText(setTextAlerter)
                    .setBackgroundColorRes(R.color.alert_default_error_background)
                    .show();
            cajaConfirmContra.setError(errorInputEmpty);
            validReg = false;
        } else {
            cajaConfirmContra.setError(null);
        }

        return validReg;

    }

    public boolean validarContraseña() {
        boolean validCon = true;

        String caracteresContra = cajaCrearContra.getText().toString();
        if (caracteresContra.length()>=8) {
            cajaCrearContra.setError(null);
        } else {
            cajaCrearContra.setError("Mínimo 8 caracteres.");
            validCon = false;
        }

        return validCon;
    }

    public boolean confirContra() {
        String contra1=cajaCrearContra.getText().toString();
        String contra2=cajaConfirmContra.getText().toString();
        boolean validConfi = true;
        if (contra1.equals(contra2)) {
            cajaConfirmContra.setError(null);
        } else {
            cajaConfirmContra.setError("Las contraseñas no coinciden");
            validConfi = false;
        }
        return validConfi;
    }

    @Override
    public void onClick(View view) {
        String email = cajaEmail.getText().toString();
        String pass = cajaCrearContra.getText().toString();
        registrarusuario(email,pass);
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