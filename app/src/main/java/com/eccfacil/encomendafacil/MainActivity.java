package com.eccfacil.encomendafacil;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.eccfacil.classControl.Encomenda;
import com.eccfacil.classControl.SharedPreferenceControler;
import com.eccfacil.classControl.WS_Controlador;
import com.eccfacil.gcm.RegistrationIntentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    SharedPreferenceControler sharedPreferenceControler;
    List<Encomenda> enc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        sharedPreferenceControler = new SharedPreferenceControler(MainActivity.this);

        Intent igcm = new Intent(this, RegistrationIntentService.class);
        startService(igcm);

        //Carregar informações relacionadas aos pacotes do usuário informado
        carregarListaPacotes(sharedPreferenceControler.loadPreferences("idUsr"));

        expListView = (ExpandableListView) findViewById(R.id.ExpListViewPacotes);
        listAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                // 1. Criar a nova tela
                Intent intent = new Intent(MainActivity.this,HistPacote.class);
                // 2. Colocar a Chave e Valor
                intent.putExtra("codigoEncomenda", listDataHeader.get(groupPosition));
                // 3. Iniciar Atividade
                startActivity(intent);

                return false;
            }
        });

        CountDownTimer countDownTimer = new CountDownTimer(300000,5000){

            @Override
            public void onTick(long millisUntilFinished) {
                // Do something on a tick.
            }

            @Override
            public void onFinish() {
                carregarListaPacotes(sharedPreferenceControler.loadPreferences("idUsr"));
                listAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);

                this.start();
            }
        };

        countDownTimer.start();



    }

    private void carregarListaPacotes(String idUsr){
       new carregarListaPacotes().execute(idUsr);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cadastrarEncomenda) {
            startActivity(new Intent(MainActivity.this, CadastroPacotes.class));

        }else if (id == R.id.nav_atualizarEncomenda) {
            carregarListaPacotes(sharedPreferenceControler.loadPreferences("idUsr"));
            listAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);
        }else if (id == R.id.nav_editarPerfil) {
            startActivity(new Intent(MainActivity.this, Perfil.class));
        }else if (id == R.id.nav_logout) {
            if(sharedPreferenceControler.deleteLoginKeys()) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Area destinada apenas a execução de tarefas Async
     * Comunicação com a internet deve sempre ser verificada por aqui
     */
    private class carregarListaPacotes extends AsyncTask<String,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            Log.e("Result" , params[0]);
            enc = new WS_Controlador().carregarPacotesPorUsuario(params[0]);

            if (enc != null) {
                for (Encomenda encomenda : enc) {

                    Log.e("Result", encomenda.getCodRastreio());

                    new WS_Controlador().chamadaCrawler(encomenda.getCodRastreio());

                    List<String> stringHeader = new ArrayList<String>();
                    listDataHeader.add(encomenda.getCodRastreio());

                    stringHeader.add("Nome : " + encomenda.getNome() +
                                    " \nData de Atualização :  " + new SimpleDateFormat("dd/MM/yyyy").format(encomenda.getDataAtualizacao()) +
                                    " \nData de Cadastro : " + new SimpleDateFormat("dd/MM/yyyy").format(encomenda.getDataCadastro()) +
                                    " \nStatus : " + "Entregue ao Destinatario");

                    listDataChild.put(listDataHeader.get(listDataHeader.size() - 1), stringHeader);
                }
            }

           return listDataHeader != null && listDataHeader.size() > 0;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            listAdapter.notifyDataSetChanged();
            for (int i = 0; i < listAdapter.getGroupCount(); i++){
                expListView.expandGroup(i);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Recarregando", "Recarregando Tela Principal");
        //carregarListaPacotes(sharedPreferenceControler.loadPreferences("idUsr"));
    }
}
