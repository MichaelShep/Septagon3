package com.kroy.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

abstract public class Character extends Sprite {
    protected int maxHealth, health;
    protected int damage;
    protected int range;
    protected boolean disabled;
    protected Tile location;


    public Character(int health, int damage, int range, Tile spawn, String spriteTex) {
        //super(Constants.getManager().get(spriteTex, Texture.class),Constants.getTileSize(),Constants.getTileSize());
        super(Constants.getManager().get(Constants.getResourceRoot() + spriteTex, Texture.class), 0, 0, Constants.getTileSize(), Constants.getTileSize());
        setSize(Constants.getTileSize(), Constants.getTileSize());
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.range = range;
        this.location = spawn;
        this.disabled = false;
    }


    protected void death() {
        if (this.health == 0) {
            this.disabled = true;
        }
    }

    protected void takeDamage(int damageTaken) {
        this.health = this.health - damageTaken;
        if (this.health < 0) {
            this.health = 0;
        }

        if (health == 0) {
            System.out.println("DIED");
            disabled = true;
        }

    }

    protected void transferTo(Tile newLocation) {
        location.setInhabitant(null);
        location = newLocation;
        newLocation.setInhabitant(this);
    }

    public void shootTarget(Character target) {
        target.takeDamage(damage);
        System.out.println("Character at: " + this.location.getMapX() + ", " + this.location.getMapY() + " shot target at: " + target.getLocation().getMapX() + ", " + target.getLocation().getMapY());
        if (this instanceof FireEngine) {
            ((FireEngine) this).decrementWaterAmount();
        }

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

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isDisabled() {
        return disabled;
    }
}


