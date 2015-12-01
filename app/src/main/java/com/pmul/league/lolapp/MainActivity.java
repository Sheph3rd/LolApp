package com.pmul.league.lolapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pmul.league.lolapp.model.BD_LOLUniversity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    Elements championList;
    TextView tvLore;
    BD_LOLUniversity bd_lolUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLore = (TextView) findViewById(R.id.textViewLore);

        bd_lolUniversity = new BD_LOLUniversity(getApplicationContext());

        SQLiteDatabase database = bd_lolUniversity.getReadableDatabase();

        new getChampionData().execute();

    }

    private class getChampionData extends AsyncTask<Void, Void, String>
    {
        String content;

        @Override
        protected String doInBackground(Void... params) {
            String title ="";

            Document doc;

            Elements championList = getChampionList();

            //doc = Jsoup.connect("http://gameinfo.euw.leagueoflegends.com/es/game-info/champions/ahri/").get();
            //doc = Jsoup.connect("http://leagueoflegends.wikia.com/wiki/List_of_champions").get();

            // content = doc.getElementById("champion-lore").text();

            //Elements elements = doc.select("div[id^=spell]");

           // Elements campeones = doc.getElementsByClass("character_icon tooltips-init-complete");

            content = String.valueOf(championList.size());

            try
            {
                doc = Jsoup.connect("http://gameinfo.euw.leagueoflegends.com/en/game-info/champions/ahri/").get();
                Elements elem = doc.select("div[class^=faction]");

                content += "\nRegion: " + elem.first().text();

                elem = doc.select("em");
                content += "\nTitle: " + elem.first().text();

                elem = doc.select("span[class*=stat-hp]");

                content += "\nTexto Vida: " + elem.first().text();

                elem = elem.parents().first().children();



                content += "\nValor: " + elem.last().text();


                elem = doc.select("span[class*=stat]");


                content += "\n\n\nFiligrana: \n";
                for (Element e:elem)
                {
                    content +=  "\n" + e.text();
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            for (Element e:championList)
            {
                if (e.text() != "")
                {
                    content += "\n" + e.text();
                }
            }

            */
            return title;


        }

        @Override
        protected void onPostExecute(String result)
        {
            ((TextView)findViewById (R.id.textViewLore)).setText (content);
        }
    }


    private Elements getChampionList()
    {
        Elements chamopionList = new Elements();
        Document doc;

        try
        {
            doc = Jsoup.connect("http://leagueoflegends.wikia.com/wiki/List_of_champions").get();
            Elements campeones = doc.select("span[class^=character]");
            campeones = campeones.select("a[href]");

            for (Element e:campeones)
            {
                if (e.text() != "")
                {
                    chamopionList.add(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chamopionList;

    }




}
