package com.pmul.league.lolapp.model;

import android.graphics.Bitmap;

/**
 * Created by Rodi on 30/11/2015.
 */
public class Skin extends Champion
{
    private int skin_id;
    private String skin_name;
    private String skin_video;
    private Bitmap skin_img;

    public Skin() { }





    // GETTERS AND SETTERS

    public int getSkin_id() {
        return skin_id;
    }

    public void setSkin_id(int skin_id) {
        this.skin_id = skin_id;
    }

    public String getSkin_name() {
        return skin_name;
    }

    public void setSkin_name(String skin_name) {
        this.skin_name = skin_name;
    }

    public String getSkin_video() {
        return skin_video;
    }

    public void setSkin_video(String skin_video) {
        this.skin_video = skin_video;
    }

    public Bitmap getSkin_img() {
        return skin_img;
    }

    public void setSkin_img(Bitmap skin_img) {
        this.skin_img = skin_img;
    }
}
