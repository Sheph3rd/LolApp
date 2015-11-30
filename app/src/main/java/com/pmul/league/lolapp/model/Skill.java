package com.pmul.league.lolapp.model;

import android.graphics.Bitmap;

/**
 * Created by Rodi on 30/11/2015.
 */
public class Skill extends Champion
{
    private int skill_id;
    private String description;
    private String detail;
    private String skill_name;
    private String skill_video;
    private String cast_char;
    private Bitmap skill_img;



    public Skill() { }


    // GETTERS AND SETTERS

    public int getId() { return skill_id; }

    public void setId(int skill_id) {
        this.skill_id = skill_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public String getSkill_video() {
        return skill_video;
    }

    public void setSkill_video(String skill_video) {
        this.skill_video = skill_video;
    }

    public String getCast_char() {
        return cast_char;
    }

    public void setCast_char(String cast_char) {
        this.cast_char = cast_char;
    }

    public Bitmap getSkill_img() {
        return skill_img;
    }

    public void setSkill_img(Bitmap skill_img) {
        this.skill_img = skill_img;
    }
}
