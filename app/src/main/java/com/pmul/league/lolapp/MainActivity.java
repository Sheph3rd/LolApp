package com.pmul.league.lolapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    TextView tvLore;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLore = (TextView) findViewById(R.id.textViewLore);

        // Comentario de prueba

        new getLore().execute();


    }

    private class getLore extends AsyncTask<Void, Void, String>
    {
        String content;

        @Override
        protected String doInBackground(Void... params) {
            String title ="";
            Document doc;
            try
            {
                doc = Jsoup.connect("http://gameinfo.euw.leagueoflegends.com/es/game-info/champions/ahri/").get();

                content = doc.getElementById("champion-lore").text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }


        @Override
        protected void onPostExecute(String result) {
            //if you had a ui element, you could display the title
            ((TextView)findViewById (R.id.textViewLore)).setText (content);
        }
    }


}
