package com.eccfacil.encomendafacil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.eccfacil.classControl.Usuario;
import com.eccfacil.classControl.WS_Controlador;

public class Perfil extends AppCompatActivity {

    Button btnCancelar;
    Button btnAtualizar;

    EditText edtEmail;
    EditText edtNome;

    EditText edtSenhaAntiga;
    EditText edtSenhaNova;
    Usuario usuario;
    WS_Controlador ws_controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

    }
}
