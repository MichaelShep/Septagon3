package com.kroy.game;

import org.junit.Before;

/***
 * Class used to manage the fireEngines and all the users info
 */

public class Human extends Player {

    @Before
    public void init(){

    }

    /**
     * Constucts a Human Player, this will be able to control a team of fire engines
     *
     * @param myTurn   if this player is active on instantiation
     * @param teamSize the number of characters he can control
     */
    public Human(boolean myTurn, int teamSize) {
        super(myTurn, teamSize);
        for (int members = 0; members < teamSize; members++) {
            team.add(createFireEngine(members));
        }


    }


    /**
     * constructs and returns a fire engine with random stats
     *
     * @return a constructed fire engine object
     */
    public FireEngine createFireEngine(int createdNumber) {
        int[] statProfile = {};

        try
        {
            statProfile = Constants.getFireEngineProfiles()[createdNumber];
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Not enough Profiles defines in constants");
            System.exit(0);
        }


        //encapsulates the balance
        int health = statProfile[0];
        int damage = statProfile[1];
        int range = statProfile[2];
        int speed = statProfile[3];
        int waterCapacity = statProfile[4];

        return new FireEngine(health, damage, range, null, speed, waterCapacity, "fireEngineSprite.png");
    }


}
