package com.eccfacil.encomendafacil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eccfacil.classControl.Usuario;
import com.eccfacil.classControl.WS_Controlador;

public class CadastroUsuario extends AppCompatActivity {

    EditText edtEmail;
    EditText edtNome;
    EditText edtSenha;
    Button btnCadastrarUsuario;
    Button btnCancelarUsuario;
    Usuario usr;
    WS_Controlador ws_controlador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtEmail = (EditText) findViewById(R.id.txtEmailCadastro);
        edtNome = (EditText) findViewById(R.id.txtNomeCadastro);
        edtSenha = (EditText) findViewById(R.id.txtSenhaCadastro);

        btnCadastrarUsuario = (Button) findViewById(R.id.btnCadastrarUsuario);
        btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AttemptCadastrar()){
                    finish();
                }
            }
        });

        btnCancelarUsuario = (Button) findViewById(R.id.btnCancelarCadastro);
        btnCancelarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Boolean AttemptCadastrar(){

        // Reset errors.
        edtEmail.setError(null);
        edtNome.setError(null);
        edtSenha.setError(null);

        // Store values at the time of the login attempt.
        String email = edtEmail.getText().toString();
        String nome = edtNome.getText().toString();
        String password = edtSenha.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && password.length() < 4) {
            edtSenha.setError(getString(R.string.error_invalid_password));
            focusView = edtSenha;
            cancel = true;
        }

        // Check de validade do nome
        if(!TextUtils.isEmpty(nome) && nome.length() < 4){
            edtNome.setError(getString(R.string.error_field_required));
            focusView = edtNome;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.error_field_required));
            focusView = edtEmail;
            cancel = true;
        } else if (!email.contains("@")) {
            edtEmail.setError(getString(R.string.error_invalid_email));
            focusView = edtEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }else{
            usr = new Usuario();
            ws_controlador = new WS_Controlador();

            usr.setTokenId(null);
            usr.setSenha(edtSenha.getText().toString());
            usr.setNome(edtNome.getText().toString());
            usr.setEmail(edtEmail.getText().toString());

            if(ws_controlador.cadastrarPerfil(usr)){
                return true;
            }
            else{
                Toast.makeText(CadastroUsuario.this,"Não foi possível realizar o cadastro, por favor tente novamente", Toast.LENGTH_SHORT).show();
                return false;
            }


        }
        return false;
    }

}
