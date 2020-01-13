package com.kroy.game;

public class FireEngine extends Character {

    private int speed;
    private int waterAmount;
    private int waterCapacity;
    private boolean disabled;

    public FireEngine(int health, int damage, int range, Tile location, int speed, int waterCapacity, String charTex) {
        super(health, damage, range, location, charTex);
        this.speed = speed;
        this.waterCapacity = waterCapacity;
        this.waterAmount = waterCapacity;
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

    /*

    private int canShoot(Fortress target) {

        if (this.waterAmount == 0) {
            return 0;
        }


        if (inRange(target.getLocation())) {
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

     */

    @Override
    public boolean getDisabled() {
        return super.getDisabled();
    }


    @Override
    public int getDamage() {
        return super.getDamage();
    }

    @Override
    public void setDamage(int newDamage) {
        super.setDamage(newDamage);
    }

    @Override
    public int getHealth() {
        return super.getHealth();
    }

    @Override
    public void setHealth(int newHealth) {
        super.setHealth(newHealth);
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
    public void setLocation(Tile newLocation) {
        super.setLocation(newLocation);
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

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


