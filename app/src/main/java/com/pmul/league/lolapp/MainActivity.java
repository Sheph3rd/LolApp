package com.pmul.league.lolapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.pmul.league.lolapp.model.BD_LOLUniversity;
import com.pmul.league.lolapp.model.Champion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Console;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    Elements championList;
    TextView tvLore;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLore = (TextView) findViewById(R.id.textViewLore);

        new getAllChampionsData().execute();

    }

    private class getAllChampionsData extends AsyncTask<Void, Void, String>
    {
        String content;

        @Override
        protected String doInBackground(Void... params) {
            String title ="";

            Elements championList = getChampionList();

            content = String.valueOf(championList.size());

            for (Element e:championList)
            {
               getChampionData(e.text());
            }
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

    private void getChampionData(String championName)
    {
        Document doc;
        BD_LOLUniversity bd_lolUniversity = new BD_LOLUniversity(getApplicationContext());
        String champName = championName.replaceAll("\\W", "");
        champName = champName.toLowerCase();
        String url = "http://gameinfo.euw.leagueoflegends.com/es/game-info/champions/" + champName + "/";

        try
        {
            Champion champion = new Champion();
            Elements elements;
            Element element;
            doc = Jsoup.connect(url).get();


            element = doc.getElementById("champion-lore");
            champion.setLore(element.text());

            champion.setName(championName);

            elements = doc.select("em");
            champion.setTitle(elements.first().text());

            elements = doc.select("div[class^=faction]");
            champion.setRegion(elements.first().text());

            champion.setImg(null);

            elements = doc.select("span[class*=stat]");

            if (elements.size() > 16)
            {
                champion.setHp(elements.get(1).text());
                champion.setMp(elements.get(3).text());
                champion.setAd(elements.get(5).text());
                champion.setAspd(elements.get(7).text());
                champion.setMovspeed(elements.get(9).text());
                champion.setHpregen(elements.get(11).text());
                champion.setMpregen(elements.get(13).text());
                champion.setArmor(elements.get(15).text());
                champion.setMr(elements.get(17).text());
            }
            else
            {
                champion.setHp(elements.get(1).text());
                champion.setAd(elements.get(3).text());
                champion.setAspd(elements.get(5).text());
                champion.setMovspeed(elements.get(7).text());
                champion.setHpregen(elements.get(9).text());
                champion.setArmor(elements.get(11).text());
                champion.setMr(elements.get(13).text());
            }



            bd_lolUniversity.addChampion(champion);

        } catch (IOException e) {
            e.printStackTrace();
        }





        // Comentario 2 Cambio de ramabich

    }


}
