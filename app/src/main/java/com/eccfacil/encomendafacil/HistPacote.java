package com.eccfacil.encomendafacil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eccfacil.classControl.Encomenda;
import com.eccfacil.classControl.WS_Controlador;

import java.util.List;

public class HistPacote extends AppCompatActivity {

    WS_Controlador ws_controlador;
    Encomenda encomenda;
    ListView lvHist;
    TextView txtCodRastreio;
    DialogInterface.OnClickListener dialogClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist_pacote);
        carregarCompTela();

        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        new deletarEncomendaTask().execute(txtCodRastreio.getText().toString());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
    }

    private void carregarCompTela(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String codigoRastreio = extras.getString("codigoEncomenda");
            txtCodRastreio = (TextView) findViewById(R.id.txtCodRastreioHist);
            txtCodRastreio.setText(codigoRastreio);


            lvHist = (ListView) findViewById(R.id.listViewHistPacote);
            lvHist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                }
            });

            new carregarHistoricoEncomendaTask().execute(codigoRastreio);
        }else{
            Toast.makeText(this, "Nenhum pacote foi recebido, por favor tente novamente", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pacote,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.nav_atualizarHist:
                // Code you want run when activity is clicked
                carregarCompTela();
                return true;
            case R.id.nav_deletarEnc:
                AlertDialog.Builder builder = new AlertDialog.Builder(HistPacote.this);
                builder.setMessage("Deseja realmente deletar este pacote?")
                        .setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener).show();

                return true;
            case R.id.nav_editarEncomenda:
                Intent intent = new Intent(HistPacote.this,CadastroPacotes.class);
                // 2. Colocar a Chave e Valor
                intent.putExtra("codigoEncomenda", txtCodRastreio.getText());
                // 3. Iniciar Atividade
                startActivity(intent);
                return true;
            default:
        return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected class carregarHistoricoEncomendaTask extends AsyncTask<String,Void,List<Encomenda>>{

        @Override
        protected void onPreExecute() {
            ws_controlador = new WS_Controlador();
            encomenda = new Encomenda();
            super.onPreExecute();
        }

        @Override
        protected List<Encomenda> doInBackground(String... params) {
             encomenda = ws_controlador.carregarPacotePorcodigoRastreio(params[0]);

            Log.e("Result","ID ENCOMENDA " +  encomenda.getIdEncomenda());
            List<Encomenda> listEncomenda = ws_controlador.carregarHistoricoPacote(encomenda.getIdEncomenda());

            return listEncomenda != null && listEncomenda.size() > 0 ? listEncomenda : null;
        }

        @Override
        protected void onPostExecute(List<Encomenda> listEncomenda) {
            super.onPostExecute(listEncomenda);
            Log.e("Result", listEncomenda != null && listEncomenda.size() > 0 ? "MAIS DE UMA LINHA" : "MENOS DE UMA LINHA");

            if(listEncomenda != null)
                lvHist.setAdapter(new ListViewHistAdapter(HistPacote.this, listEncomenda));
            else
                Toast.makeText(HistPacote.this,"Não há nenhuma atualização deste pacote, tente novamente mais tarde",Toast.LENGTH_SHORT).show();
        }
    }

    protected class deletarEncomendaTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            ws_controlador = new WS_Controlador();
            encomenda = new Encomenda();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            encomenda = ws_controlador.carregarPacotePorcodigoRastreio(params[0]);
            Encomenda newEncomenda = new Encomenda();

            newEncomenda.setCodRastreio(encomenda.getCodRastreio());
            newEncomenda.setIdEncomenda(encomenda.getIdEncomenda());
            newEncomenda.setIdUsuario(encomenda.getIdUsuario());

            return ws_controlador.deletarEncomenda(newEncomenda);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Toast.makeText(HistPacote.this,"Encomenda Removida com Sucesso!",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(HistPacote.this,"Erro ao Tentar Remover, tente novamente mais tarde",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
