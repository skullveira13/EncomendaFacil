package com.eccfacil.classControl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.List;

/**
 * Created by skullveira on 24/04/2016.
 */
public class WS_Controlador {

    private final String usradd = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/usuario/add";
    private final String usrlogin = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/usuario/login/email/senha";
    private final String usrUpdate = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/usuario/update";

    private final String encfindbyCodigoRastreio = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/findByCodigoRastreio/codigoRastreio";
    private final String encfindByNome = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/findByNome/nome";
    private final String encrastrearEncomendaCrawler = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/rastrearEncomenda/{codigoRastreio}";
    private final String encrastrearEncomendas = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/rastrearEncomendas/codigosRastreio";
    private final String encadd = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/add";
    private final String encupdate = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/update";
    private final String encfindbyidUsuario = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/findEncomendasByUsuario/{idUsuario}";
    private final String encfindHistbyEncomenda = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/findHistoricoEncomenda/{idEncomenda}";
    private final String encdelete = "http://25.5.105.206:8080/EncomendaFacilTCC/rest/encomenda/delete";


    Gson gson;
    HttpPost post;
    GsonBuilder gsonBuilder;

    public WS_Controlador(){
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
    }

    /**
     * Area destinada a
     * @param usuario
     * @return
     */
    public Boolean cadastrarPerfil(Usuario usuario){

        gson = gsonBuilder.create();
        post = new HttpPost();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;

        try {

            post.setURI(new URI(usradd));
            post.setEntity(new StringEntity(new Gson().toJson(usuario)));
            post.setHeader("Content-type", "application/json");
            Log.e("JSON", new Gson().toJson(usuario));

            try {
                httpResponse = httpClient.execute(post);
            } catch (IOException e) {
                Log.e("Error_Exception", httpResponse.getStatusLine().toString());
            }
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.e("Result", httpResponse.getStatusLine().toString());
                return true;
            }
            else{
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return false;
            }
        }
        catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return false;
        }
    }

    public Usuario logar(Usuario usuario) {

        Gson gson = gsonBuilder.create();

        InputStream inputStream = null;

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;

        Log.e("JSON", new Gson().toJson(usuario));


            try {
                httpResponse = httpClient.execute(new HttpGet(usrlogin.replace("email", usuario.getEmail()).replace("senha", usuario.getSenha())));
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null) {
                    Reader reader = new InputStreamReader(inputStream);
                    usuario = gson.fromJson(reader, Usuario.class);
                }

                if (usuario != null && usuario.getIdUsuario() != "")
                    Log.e("Executou", usuario.getIdUsuario());

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.e("Result", httpResponse.getStatusLine().toString());
                return usuario;
            } else {
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return null;
            }
        }
        catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return null;
        }
    }

    public Usuario carregarPerfil(Usuario usuario){

        Gson gson = gsonBuilder.create();
        post = new HttpPost();



        return null;
    }

    public Boolean atualizarPerfil(Usuario usuario){

        Gson gson = gsonBuilder.create();
        post = new HttpPost();

        try {

            post.setURI(new URI(usrUpdate));
            post.setEntity(new StringEntity(new Gson().toJson(usuario)));
            post.setHeader("Content-type", "application/json");
            Log.e("JSON", new Gson().toJson(usuario));

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.e("Result", httpResponse.getStatusLine().toString());
                return true;
            }
            else{
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return false;
            }
        }
        catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return false;
        }
    }

    /**
     * Area destinada ao controle de pacotes
     * @return
     */
    public List<Encomenda> carregarPacotesPorUsuario(String idUsr){

        Gson gson = gsonBuilder.create();
        InputStream inputStream = null;

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;

        Log.e("JSON", idUsr);


        try {
            httpResponse = httpClient.execute(new HttpGet(encfindbyidUsuario.replace("{idUsuario}", idUsr)));
            inputStream = httpResponse.getEntity().getContent();
            Reader reader = new InputStreamReader(inputStream);
            List<Encomenda> listEncomendas = gson.fromJson(reader, new TypeToken<List<Encomenda>>(){}.getType());

            Log.e("Result", "Get Encomendas Success");

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.e("Result", httpResponse.getStatusLine().toString());
                return listEncomendas;
            } else {
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return null;
            }
        }catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return null;
        }
    }

    public Encomenda carregarPacotePorcodigoRastreio(String codigoRastreio){

        Encomenda encomenda = new Encomenda();
        Gson gson = gsonBuilder.create();

        InputStream inputStream = null;

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;

        Log.e("JSON", new Gson().toJson(encomenda));

        try {
            httpResponse = httpClient.execute(new HttpGet(encfindbyCodigoRastreio.replace("codigoRastreio", codigoRastreio)));
            inputStream = httpResponse.getEntity().getContent();
            Reader reader = new InputStreamReader(inputStream);
            encomenda = gson.fromJson(reader, Encomenda.class);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.e("Result", httpResponse.getStatusLine().toString());
                return encomenda;
            } else {
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return encomenda;
            }
        }
        catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return null;
        }
    }



    public List<Encomenda> carregarHistoricoPacote(String idEncomenda){

        gson = gsonBuilder.create();

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        InputStream inputStream = null;

        Log.e("JSON", idEncomenda);


        try {
            httpResponse = httpClient.execute(new HttpGet(encfindHistbyEncomenda.replace("{idEncomenda}", idEncomenda)));

            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                inputStream = httpResponse.getEntity().getContent();
                Reader reader = new InputStreamReader(inputStream);
                List<Encomenda> listEncomendas = gson.fromJson(reader, new TypeToken<List<Encomenda>>(){}.getType());
                Log.e("Result", httpResponse.getStatusLine().toString());
                Log.e("Result", "Total Histórico : " + listEncomendas.size());

                return listEncomendas;
            } else {
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return null;
            }
        }catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return null;
        }
    }

    public Encomenda chamadaCrawler(String codigoRastreio){

        gson = gsonBuilder.create();

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        InputStream inputStream = null;

        Log.e("Codigo Encomenda", codigoRastreio);


        try {
            httpResponse = httpClient.execute(new HttpGet(encrastrearEncomendaCrawler.replace("{codigoRastreio}", codigoRastreio)));

            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                inputStream = httpResponse.getEntity().getContent();
                Reader reader = new InputStreamReader(inputStream);
                Encomenda encomenda = gson.fromJson(reader, Encomenda.class);
                Log.e("Result", "Total Histórico : " + encomenda.getIdEncomenda());

                return encomenda;
            } else {
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return null;
            }
        }catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return null;
        }
    }

    public Boolean cadastrarEncomenda(Encomenda encomenda) {
        gson = gsonBuilder.create();
        post = new HttpPost();

        try {

            post.setURI(new URI(encadd));
            post.setEntity(new StringEntity(new Gson().toJson(encomenda)));
            post.setHeader("Content-type", "application/json");
            Log.e("JSON", new Gson().toJson(encomenda));

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.e("Result", httpResponse.getStatusLine().toString());
                return true;
            } else {
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return false;
            }
        } catch (Exception ex) {
            Log.e("Error_Exception", ex.toString());
            return false;
        }
    }

    public Boolean atualizarEncomenda(Encomenda encomenda){
        try {
            gson = gsonBuilder.create();
            post = new HttpPost();
            post.setURI(new URI(encupdate));

            post.setEntity(new StringEntity(new Gson().toJson(encomenda)));
            post.setHeader("Content-type", "application/json");
            Log.e("JSON", new Gson().toJson(encomenda));

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Log.e("Result", httpResponse.getStatusLine().toString());
                return true;
            }
            else{
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return false;
            }
        }
        catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return false;
        }
    }

    public Boolean deletarEncomenda(Encomenda encomenda){
        try {
            gson = gsonBuilder.create();
            post = new HttpPost();
            post.setURI(new URI(encdelete));
            post.setEntity(new StringEntity(new Gson().toJson(encomenda)));
            post.setHeader("Content-type", "application/json");
            Log.e("JSON", new Gson().toJson(encomenda));

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;
            httpResponse = httpClient.execute(post);

            if (httpResponse.getStatusLine().getStatusCode() == 200 || httpResponse.getStatusLine().getStatusCode() == 204 ) {
                Log.e("Result", httpResponse.getStatusLine().toString());
                return true;
            }
            else{
                Log.e("Error_Bad", httpResponse.getStatusLine().toString());
                return false;
            }
        }
        catch (Exception ex){
            Log.e("Error_Exception",ex.toString());
            return false;
        }
    }

}
