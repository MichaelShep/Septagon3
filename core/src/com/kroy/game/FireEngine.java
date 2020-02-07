package com.kroy.game;

/***
 * Used to handle all the info about fire engines in the game
 */

public class FireEngine extends Patrol {

    private int speed;
    private int waterAmount;
    private int waterCapacity;
    private boolean disabled;

    /**
     * Constructs a FireEngine which is the character that a human can control
     * Extends Character
     *
     * @param health        the starting and max health
     * @param damage        the amount health it removes when shooting
     * @param range         how many tiles away it can shoot
     * @param location      the starting location
     * @param speed         how many tiles it can move at once
     * @param waterCapacity the max amount of water this engine can shoot
     * @param charTex       the sprite root that is rendered for this character
     */
    public FireEngine(int health, int damage, int range, Tile location, int speed, int waterCapacity, String charTex) {
        super(health, damage, range, location, charTex);
        this.speed = speed;
        this.waterCapacity = waterCapacity;
        this.waterAmount = waterCapacity;
        this.disabled = false;
        this.type = Type.ENGINE;
    }


    /**
     * Increase the stored amount of water, capped by the water capacity
     *
     * @param newAmount the amount of water you want to add
     */


    public void refillAmount(int newAmount) {
        if ((this.waterAmount + newAmount) > this.waterCapacity) {
            this.waterAmount = this.waterCapacity;
        } else {
            this.waterAmount += newAmount;
        }
    }

    //Getters and Setters
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    public int getWaterCapacity() {
        return waterCapacity;
    }

    public void setWaterCapacity(int waterCapacity) {
        this.waterCapacity = waterCapacity;
    }

    public void decrementWaterAmount() {
        waterAmount = Math.max(0, waterAmount - 1);
    }
}


