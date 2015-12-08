package com.pmul.league.lolapp;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmul.league.lolapp.model.BD_LOLUniversity;
import com.pmul.league.lolapp.model.Champion;
import com.pmul.league.lolapp.model.Skill;
import com.pmul.league.lolapp.model.Skin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Elements championList;
    TextView tvLore;
    Bitmap imgBitmap;
    ProgressDialog progressDialog;
    int progress=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLore = (TextView) findViewById(R.id.textViewLore);

        new getAllChampionsData().execute();

    }

    private class getAllChampionsData extends AsyncTask<Void, String, String>
    {
        String content;

        @Override
        protected void onProgressUpdate(String... msg)
        {
            super.onProgressUpdate(msg);
            progressDialog.setMessage("Invocando a " + msg[0]);

        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progress = 0;
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setIndeterminate(false);
            progressDialog.setTitle("Descargando Datos...");
            progressDialog.setMessage("");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(progress);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params)
        {
            String title = "";

            Elements championList = getChampionList();

            content = String.valueOf(championList.size());

            progressDialog.setMax(championList.size());

            // Reset Database
            BD_LOLUniversity bd_lolUniversity = new BD_LOLUniversity(getApplicationContext());
            bd_lolUniversity.resetDatabase();

            for (Element e:championList)
            {
                publishProgress(e.text().toString());
                getChampionData(e.text(), "br" , "pt");
                progress++;
                progressDialog.setProgress(progress);
            }



            return title;
        }

        @Override
        protected void onPostExecute(String result) {
            ((TextView)findViewById (R.id.textViewLore)).setText(content);
            ((ImageView)findViewById (R.id.imageViewAhri)).setImageBitmap(imgBitmap);
            progressDialog.dismiss();
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

        private void getChampionData(String championName, String region, String idioma)
        {
            Document doc;
            BD_LOLUniversity bd_lolUniversity = new BD_LOLUniversity(getApplicationContext());

            String champName = championName.replaceAll("\\W", "");
            champName = champName.toLowerCase();

            if (champName.equals("wukong"))
            {
                champName = "monkeyking"; // La mayor tocada de pelotas de la historia
            }

            //String url = "http://gameinfo.euw.leagueoflegends.com/es/game-info/champions/" + champName + "/";
            String url = "http://gameinfo."+region+".leagueoflegends.com/"+idioma+"/game-info/champions/" + champName + "/";

            try
            {
                Champion champion = new Champion();
                Elements elements;
                Element element;
                Bitmap imgChamp;
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

                // Imagen Campeon
                Element img = doc.select("img").first();
                String src = img.attr("src");
                InputStream input = new URL(src).openStream();
                imgChamp = BitmapFactory.decodeStream(input);

                champion.setImg(bitmapToByteArray(imgChamp));


                bd_lolUniversity.addChampion(champion);
                Log.w("ChampionInstert", "Campeon Insertado: " + championName);

                if (champName.equals("monkeyking"))
                {
                    champName = "wukong"; // La mayor tocada de pelotas de la historia
                }

                getChampionSkills(doc, champName);
                getChampionsSkins(doc, champName);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void getChampionSkills(Document doc, String champName)
        {
            BD_LOLUniversity bd_lolUniversity = new BD_LOLUniversity(getApplicationContext());

            Elements elements;
            Element element_aux;
            Skill[] skills = new Skill[9];

            for (int i = 0; i < 9; i++)
            {
                skills[i] = new Skill();
            }

            elements=doc.select("div[id^=spell]");

            int skillCount=0;
            for (Element e :elements)
            {
                element_aux=e.select("p").first();

                String cadena=element_aux.html();
                cadena = cadena.replaceAll("<b>", "");
                cadena = cadena.replaceAll("</b>", "");

                String []costeRango = cadena.split("<br>");

                if (skillCount > 0) // Skills
                {
                    skills[skillCount].setSkill_cost(costeRango[0]); // COSTE
                    skills[skillCount].setSkill_range(costeRango[1]); // RANGO

                    skills[skillCount].setDescription(e.getElementsByClass("spell-description").first().text()); // DESCRIPCION SKILL
                    skills[skillCount].setDetail(e.getElementsByClass("spell-tooltip").first().text()); // TOOLTIP SKILL
                }
                else // Pasiva
                {
                    skills[skillCount].setDescription(e.getElementsByClass("spell-description").first().text()); // DESCRIPCION PASIVA
                }

                skills[skillCount].setSkill_name(e.select("h3").first().text()); // NOMBRE SKILL

                skills[skillCount].setChamp_name(champName);

                switch (skillCount)
                {
                    case 0:
                        skills[skillCount].setCast_char("P");
                        break;
                    case 1:
                        skills[skillCount].setCast_char("Q");
                        break;
                    case 2:
                        skills[skillCount].setCast_char("W");
                        break;
                    case 3:
                        skills[skillCount].setCast_char("E");
                        break;
                    case 4:
                        skills[skillCount].setCast_char("R");
                        break;
                    case 5:
                        skills[skillCount].setCast_char("Q");
                        break;
                    case 6:
                        skills[skillCount].setCast_char("W");
                        break;
                    case 7:
                        skills[skillCount].setCast_char("E");
                        break;
                    case 8:
                        skills[skillCount].setCast_char("R");
                        break;

                }

                // Imagen Skill
                try
                {
                    Bitmap imgSkill;
                    Element img = e.select("img").first();
                    String src = img.attr("src");
                    src = src.replaceAll(" ","%20");
                    InputStream input = null;
                    input = new URL(src).openStream();
                    imgSkill = BitmapFactory.decodeStream(input);
                    skills[skillCount].setSkill_img(bitmapToByteArray(imgSkill));
                    Log.e("SKILLIMG", "IMG: "+src);

                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }


                skillCount++;
            }

            for (Skill s:skills)
            {
                if (s.getSkill_name() != null)
                {
                    bd_lolUniversity.addSkill(s);
                    Log.w("SkillInstert", "SkillInsertada: " + s.getSkill_name());
                }
            }
        }

        private void getChampionsSkins(Document doc, String champName)
        {
            BD_LOLUniversity bd_lolUniversity = new BD_LOLUniversity(getApplicationContext());

            Elements elementsSkins = doc.select("a[class^=skins]");

            for (Element e: elementsSkins)
            {
                Skin skin = new Skin();
                skin.setChamp_name(champName);

                skin.setSkin_name(e.attr("title"));

                // IMG SKIN URL
                String src = "";
                src = e.attr("href");
                src = src.replaceAll(" ", "%20");
                skin.setSkin_url(src);

                Log.e("SKINIMG", "IMGSKIN: " + src);
                bd_lolUniversity.addSkin(skin);
            }
        }
    }




    private Bitmap byteArrayToBitmap(byte[] byteArray)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return bitmap;
    }

    private byte[] bitmapToByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, blob);
        byte[] byteArray = blob.toByteArray();

        return byteArray;
    }
}
