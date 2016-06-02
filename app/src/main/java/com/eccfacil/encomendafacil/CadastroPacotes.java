package com.eccfacil.encomendafacil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eccfacil.classControl.Encomenda;
import com.eccfacil.classControl.SharedPreferenceControler;
import com.eccfacil.classControl.Usuario;
import com.eccfacil.classControl.WS_Controlador;

public class CadastroPacotes extends AppCompatActivity {

    EditText txt_CodigoRastreio;
    EditText txt_Descricao;
    Button btnCadastrar;
    Button btnCancelar;
    SharedPreferenceControler sharedPreferenceControler;
    Usuario usuario;
    Encomenda encomenda;
    WS_Controlador ws_controlador;
    String codigoRastreio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pacotes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario = new Usuario();
        ws_controlador = new WS_Controlador();
        sharedPreferenceControler = new SharedPreferenceControler(CadastroPacotes.this);
        usuario.setIdUsuario(sharedPreferenceControler.loadPreferences("idUsr"));

        txt_CodigoRastreio = (EditText) findViewById(R.id.txt_CodigoRastreio);
        txt_Descricao = (EditText) findViewById(R.id.txtDescricao);

        Bundle extras = getIntent().getExtras();
        btnCadastrar = (Button) findViewById(R.id.btnCadastrarPacote);

        if (extras != null) {
            codigoRastreio = extras.getString("codigoEncomenda");
            new carregarEncomendaTask().execute(codigoRastreio);

            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.e("MetodoAtualizar", "AtualizarPacote");
                    if (validarPacote())
                        registrarPacote(encomenda);
                }
            });
        }else {
            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (validarPacote())
                        registrarPacote(null);
                }
            });
        }

        btnCancelar = (Button) findViewById(R.id.btnCancelarPacote);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private Boolean validarPacote(){
        if(txt_CodigoRastreio.getText() == null || txt_CodigoRastreio.getText().length() < 13 ) {
            Toast.makeText(CadastroPacotes.this, "Codigo de Encomenda Inválido", Toast.LENGTH_SHORT).show();
            return false;
        }else if(usuario.getIdUsuario() == null || usuario.getIdUsuario().isEmpty()){
            Toast.makeText(CadastroPacotes.this, "Não há usuário conectado, feche o sistema e abra-o novamente", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void registrarPacote(Encomenda enc){
        if(enc != null){

            encomenda = new Encomenda();
            encomenda.setIdEncomenda(enc.getIdEncomenda());
            encomenda.setIdUsuario(enc.getIdUsuario());
            encomenda.setCodRastreio(txt_CodigoRastreio.getText().toString());
            encomenda.setNome(txt_Descricao.getText().toString());
            encomenda.setStatus(" ");

            new atualizarPacoteTask().execute(encomenda);

        }else {
            encomenda = new Encomenda();
            encomenda.setIdUsuario(usuario.getIdUsuario());
            encomenda.setNome(txt_Descricao.getText().toString());
            encomenda.setCodRastreio(txt_CodigoRastreio.getText().toString());
            encomenda.setStatus(" ");

            new cadastrarPacoteTask().execute(encomenda);
        }
    }

    public class atualizarPacoteTask extends  AsyncTask<Encomenda,Boolean,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Encomenda... params) {
            return ws_controlador.atualizarEncomenda(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean)
                finish();
        }
    }

    public class cadastrarPacoteTask extends AsyncTask<Encomenda, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Encomenda... params) {

            if(params[0].getIdEncomenda() != null && (!params[0].getIdEncomenda().isEmpty())){
                return ws_controlador.atualizarEncomenda(params[0]);
            }else {
                return ws_controlador.cadastrarEncomenda(params[0]);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean) {
                Toast.makeText(getApplicationContext(),"Encomenda Cadastrada com Sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(CadastroPacotes.this, "Ocorreu um erro ao tentar realizar o cadastro, por favor, tente novamente ou verifique os dados informados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class carregarEncomendaTask extends  AsyncTask<String,Void,Encomenda>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Encomenda doInBackground(String... params) {
            return ws_controlador.carregarPacotePorcodigoRastreio(codigoRastreio);
        }

        @Override
        protected void onPostExecute(Encomenda encomendaTask) {
            super.onPostExecute(encomendaTask);
            encomenda = encomendaTask;
            txt_CodigoRastreio.setText(encomenda.getCodRastreio());
            txt_Descricao.setText(encomenda.getNome());
        }
    }
}
