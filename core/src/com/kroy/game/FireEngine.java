package com.kroy.game;

import com.badlogic.gdx.graphics.Texture;

public class FireEngine extends Character {

    private int speed;
    private int waterAmount;
    private int waterCapacity;
    private boolean disabled;

    public FireEngine(int health, int damage, int range, Tile location, int speed, int waterCapacity, String charTex) {
        super(health, damage, range, location, charTex);
        this.speed = speed;
        this.waterCapacity = waterCapacity;
        this.disabled = false;

        // this.team.add(this);
    }

    public void refillAmount(int newAmount) {
        if ((this.waterAmount + newAmount) > this.waterCapacity) {
            this.waterAmount = this.waterCapacity;
        } else {
            this.waterAmount += newAmount;
        }
    }

    private int canShoot(Fortress target) {

        if(this.waterAmount == 0) {
            return 0;
        }


        if(inRange(target.getLocation())) {
            if (this.waterAmount >= 20) {
                this.waterAmount -= 20;
                return this.getDamage();
            } else if (this.waterAmount < 20) {
                this.waterAmount = 0;
                return (Math.round((this.waterAmount / 20) * this.getDamage()));
            }


        } else {
            return 0;
        }
        return 0;
    }

    public void shoot(Fortress target) throws Exception {
        if (canShoot(target) == 0) {
            throw new Exception("Cannot shoot, don't waste turn");
        } else {
            target.takeDamage(canShoot(target));
        }
    }





    @Override
    public boolean getDisabled() {
        return super.getDisabled();
    }


    @Override
    public int getDamage() {
        return super.getDamage();
    }

    @Override
    public int getHealth() {
        return super.getHealth();
    }

    @Override
    public int getRange() {
        return super.getRange();
    }

    @Override
    public Tile getLocation() {
        return super.getLocation();
    }

    @Override
    public void setDamage(int newDamage) {
        super.setDamage(newDamage);
    }

    @Override
    public void setHealth(int newHealth) {
        super.setHealth(newHealth);
    }

    @Override
    public void setLocation(Tile newLocation) {
        super.setLocation(newLocation);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public int getWaterCapacity() {
        return waterCapacity;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}


