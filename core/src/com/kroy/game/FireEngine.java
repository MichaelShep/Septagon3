package com.kroy.game;

public class FireEngine extends Character {

    private int speed;
    private int waterAmount;
    private int waterCapacity;
    private boolean disabled;

    public FireEngine(int health, int damage, int range, Tile location, int speed, int waterCapacity) {
        super(health, damage, range, location, "badlogic.jpg");
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

    private void shoot(Fortress target) throws Exception {
        if (canShoot(target) == 0) {
            throw new Exception("Cannot shoot, don't waste turn");
        } else {
            target.takeDamage(canShoot(target));
        }
    }
}


