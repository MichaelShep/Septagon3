package com.kroy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Character class used as a base class for all moveable objects in the game
 */

abstract public class  Character{
    public enum Type {ENGINE, FORTRESS, PATROL};
    protected float width, height;
    protected int x, y;
    private int maxHealth, health;
    private int damage;
    private int range;
    private boolean disabled;
    protected Tile location;
    protected Type type;

    protected Texture texture;

    /**
     * Constructs a Character object which is an entity that can shoot, move and be killed.
     * Extends from Sprite to allow rendering capability
     * Character is abstract and must be constructed via a child
     *
     * @param health    the starting health of the character
     * @param damage    the amount of damage this deals
     * @param range     how far from its location it can shoot
     * @param spawn     the starting location of this character
     * @param spriteTex the image that the sprite class is loaded with
     */
    protected Character(int health, int damage, int range, Tile spawn, String spriteTex) {
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.range = range;
        this.location = spawn;
        this.disabled = false;
        this.x = 0;
        this.y = 0;
        this.width = Constants.getTileSize();
        this.height = Constants.getTileSize();
        this.texture = new Texture(Gdx.files.internal(spriteTex));

    }

    /**
     * Checks if the character needs to be disabled
     */
    protected void checkDeath() {
        if (this.health == 0) {
            this.disabled = true;
        }
    }

    /**
     * Deal damage to this character
     *
     * @param damageTaken the amount the character needs their health reduced by
     */
    public void takeDamage(int damageTaken) {
        this.health = this.health - damageTaken;
        if (this.health < 0) {
            this.health = 0;
        }

        checkDeath();
    }

    /**
     * Tell this character to shoot at another character
     *
     * @param target the character you want to shoot at
     */
    public void shootTarget(Character target) {
        target.takeDamage(damage);
        System.out.println("Character at: " + this.location.getMapX() + ", " + this.location.getMapY() + " shot target at: " + target.getLocation().getMapX() + ", " + target.getLocation().getMapY());
        if (this instanceof FireEngine) {
            ((FireEngine) this).decrementWaterAmount();
        }

    }

    /***
     * Used to improve fortresses over time
     */
    public void improve() { //Added by Septagon
        setMaxHealth(getMaxHealth() + 2);
        setHealth(getHealth() + 2);
        setDamage(getDamage() + 1);
    }


    //Getters and Setters
    public int getHealth() {
        return this.health;
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
    }

    public int getRange() {
        return this.range;

    }

    public void setRange(int range) {
        this.range = range;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean state) {
        this.disabled = state;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Type getType() { return type; }

    public int getX(){
        return x;
    }

    public int getY(){ return y; }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Texture getTexture(){ return this.texture; }

    public void setTexture(Texture texture){ this.texture = texture;}

    public void draw(Batch batch){
        batch.draw(this.texture, this.x, this.y, this.width, this.height);
    }
}


