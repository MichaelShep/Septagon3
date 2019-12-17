package com.kroy.game;

import java.util.ArrayList;

public class Human extends Player {



    public Human(String name, boolean myTurn, int teamSize)
    {
        super(name, myTurn,teamSize);
        for (int members = 0; members < teamSize; members++)
        {
            team[members] = createFireEngine();
        }



    }





    public FireEngine createFireEngine()
    {
        //encapsulates the balance
        int health = 100;
        int damage = 50;
        int range = 12;
        int speed = 12;
        int waterCapacity = 100;

        return new FireEngine(health,damage,range,null  ,speed,waterCapacity,"fireEngineSprite.png");
    }


}
