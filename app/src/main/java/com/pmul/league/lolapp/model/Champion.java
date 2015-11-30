package com.pmul.league.lolapp.model;

import android.graphics.Bitmap;

/**
 * Created by Rodi on 30/11/2015.
 */
public class Champion
{
    // DESCRIPTION
    protected int id;
    protected String lore; // champion history
    protected String name;
    protected String tips;
    protected Bitmap img;

    // STATS
    protected double hp; // health
    protected double mp; // mana
    protected double ad; // attack damage
    protected double ap; // ability power
    protected double aspd; // attack speed
    protected double movspeed; // movement speed
    protected double hpregen; // health regeneration
    protected double mpregen; // mana regenration
    protected double armor; // armor
    protected double mr; // magic resist
    protected double critstrike; // critical strike
    protected double ls; // life steal

    public Champion (){}




    // GETTERS AND SETTERS
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getLore() {return lore;}

    public void setLore(String lore) {this.lore = lore;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getTips() {return tips;}

    public void setTips(String tips) {this.tips = tips;}

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getMp() {
        return mp;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public double getAd() {
        return ad;
    }

    public void setAd(double ad) {
        this.ad = ad;
    }

    public double getAp() {
        return ap;
    }

    public void setAp(double ap) {
        this.ap = ap;
    }

    public double getAspd() {
        return aspd;
    }

    public void setAspd(double aspd) {
        this.aspd = aspd;
    }

    public double getMovspeed() {
        return movspeed;
    }

    public void setMovspeed(double movspeed) {
        this.movspeed = movspeed;
    }

    public double getHpregen() {
        return hpregen;
    }

    public void setHpregen(double hpregen) {
        this.hpregen = hpregen;
    }

    public double getMpregen() {
        return mpregen;
    }

    public void setMpregen(double mpregen) {
        this.mpregen = mpregen;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getMr() {
        return mr;
    }

    public void setMr(double mr) {
        this.mr = mr;
    }

    public double getCritstrike() {
        return critstrike;
    }

    public void setCritstrike(double critstrike) {
        this.critstrike = critstrike;
    }

    public double getLs() {
        return ls;
    }

    public void setLs(double ls) {
        this.ls = ls;
    }
}
