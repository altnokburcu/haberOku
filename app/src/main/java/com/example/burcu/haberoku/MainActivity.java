package com.example.burcu.haberoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
//import com.example.scorpion.kitaplar.Adapter.ListAdapterNews;
//import com.example.scorpion.kitaplar.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Adapter.ListAdapterNews;
import Model.NewsMO;

public class MainActivity extends AppCompatActivity {

    private static ListAdapterNews adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        try {
            CheckNews news = new CheckNews();       //CheckNews AsyncTask ile çalışan class.
            news.execute();                         //CheckNews class'ını çalıştırıp API'den verileri çekiyoruz.
            return rootView;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    class CheckNews extends AsyncTask<Object, Object, ArrayList<NewsMO>> {
        @Override
        protected ArrayList<NewsMO> doInBackground(Object... params) {   //AsyncTask'in arkaplanında çalışan kısmı.
            try {

                ArrayList<NewsMO> modelList = new ArrayList<NewsMO>();
                String line = "";
                HttpClient httpClient = new DefaultHttpClient();   //Request için client oluşturuyoruz.
                HttpGet httpGet = new HttpGet("https://api.hurriyet.com.tr/v1/articles?$top=20");
                //Request'i yapılandırıyoruz.
                httpGet.setHeader("accept", "application/json");    //Headerları ekliyoruz.
                httpGet.setHeader("apikey", "6cddd31c73d0471982281610eb26d39a");    //API Key'i buraya kopyalıyoruz.
                HttpResponse httpResponse = httpClient.execute(httpGet);      //Get isteğini çalıştırıp response alıyoruz.

                InputStream is = httpResponse.getEntity().getContent();       //Gelen veriyi okumak için is nesnesine atıyoruz.
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bf = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                while ((line = bf.readLine()) != null) {                     //Veriyi satır satır okuyoruz.
                    sb.append(line).append("\n");                            //Veriyi satır satır sb'ye ekliyoruz.
                }
                isr.close();
                String toString = sb.toString();
                JSONArray haberler = new JSONArray(toString);                //Gelen JSON haberler dizisini değişkene atıyoruz.
                for (int i = 0; i < haberler.length(); i++) {                //JSON verilerini modelList dizisine aktarıyoruz.
                    NewsMO model = new NewsMO();
                    JSONObject haber = haberler.getJSONObject(i);
                    model.setId(haber.getString("Id"));
                    Log.e("idler", String.valueOf(haber.getString("Id")));
                    model.setDescription(haber.getString("Description"));
                    model.setTitle(haber.getString("Title"));
                    model.setUrl(haber.getString("Url"));
                    JSONArray resimler = haber.getJSONArray("Files");
                    if(resimler.length() == 0){
                        model.setImgUrl(null);
                    }
                    else {
                        JSONObject resim = resimler.getJSONObject(0);
                        model.setImgUrl(resim.getString("FileUrl"));
                    }
                    modelList.add(model);
                }
                return modelList;                                    //modelList dizisini onPostExecute metoduna gönderiyor.
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<NewsMO> result ) {       //modelList'i alıyor, ListView'i oluşturuyor.
            super.onPostExecute(result);
            adapter = new ListAdapterNews(result, MainActivity.this);
            ListView listView = (ListView) MainActivity.this.findViewById(R.id.listviewHaberler);
            listView.setAdapter(adapter);                               //Oluşan Adapter'ı ListView'in içine aktarıyoruz.
        }
    }



}

