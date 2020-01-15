package com.kroy.game;

import java.util.Random;

public class Human extends Player {


    /**
     * Constucts a Human Player, this will be able to control a team of fire engines
     *
     * @param name     the name of the player
     * @param myTurn   if this player is active on instantiation
     * @param teamSize the number of characters he can control
     */
    public Human(String name, boolean myTurn, int teamSize) {
        super(name, myTurn, teamSize);
        for (int members = 0; members < teamSize; members++) {
            team[members] = createFireEngine();
        }


    }


    /**
     * constructs and returns a fire engine with random stats
     *
     * @return a constructed fire engine object
     */
    public FireEngine createFireEngine() {
        Random r = new Random();

        //encapsulates the balance
        int health = r.nextInt((9 - 5) + 1) + 5;
        int damage = r.nextInt((7 - 2) + 1) + 2;
        int range = r.nextInt((6 - 3) + 1) + 3;
        int speed = r.nextInt((6 - 3) + 1) + 3;
        int waterCapacity = r.nextInt((5 - 2) + 1) + 2;

        return new FireEngine(health, damage, range, null, speed, waterCapacity, "fireEngineSprite.png");
    }


}
