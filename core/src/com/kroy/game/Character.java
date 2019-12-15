package com.kroy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.lang.Math;

abstract public class Character extends Sprite
{
    protected int health;
    protected int damage;
    protected int range;
    protected boolean disabled;
    protected Tile location;


    public Character(int health, int damage, int range, Tile spawn, String spriteTex)
    {
        //super(Constants.getManager().get(spriteTex, Texture.class),Constants.getTileSize(),Constants.getTileSize());
        super(Constants.getManager().get(spriteTex,Texture.class),0,0,Constants.getTileSize(),Constants.getTileSize());
        setSize(Constants.getTileSize(),Constants.getTileSize());
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.location = spawn;
        this.disabled = false;
    }

    protected boolean inRange(Tile target)
    {
        /* NEEDS IMPLEMENTING

        if (Math.sqrt(Math.pow((this.location.MapX - target.MapX), 2) + (Math.pow((this.location.MapY - target.MapY), 2))) <= this.range) {
            return true;
        } else {
            return false;
        }
         */

        return false;

    }

    protected void death() {
        if(this.health == 0) {
            this.disabled = true;
        }
    }

    protected void takeDamage(int damageTaken) {
        this.health = this.health - damageTaken;
        if(this.health < 0) {
            this.health = 0;
        }
    }

    protected void transferTo(Tile newLocation)
    {
        location.setInhabitant(null);
        location = newLocation;
        newLocation.setInhabitant(this);
    }


    public int getHealth() {
        return this.health;
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public int getRange() {
        return this.range;

    }
    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int newDamage) {
        this.damage = newDamage;
    }

    public Tile getLocation() {
        return this.location;
    }

    public void setLocation(Tile newLocation) {
        this.location = newLocation;
    }


    public boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean state) {
        this.disabled = state;
    }
}
