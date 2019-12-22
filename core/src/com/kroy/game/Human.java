package com.kroy.game;

import java.util.ArrayList;
import java.util.Random;

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
        Random r = new Random();

        //encapsulates the balance
        int health = r.nextInt((10 - 5) + 1) + 5;;
        int damage = r.nextInt((7 - 2) + 1) + 2;;
        int range = r.nextInt((6 - 3) + 1) + 3;;
        int speed = r.nextInt((6 - 3) + 1) + 3;;
        int waterCapacity = r.nextInt((5 - 2) + 1) + 2;;

        return new FireEngine(health,damage,range,null  ,speed,waterCapacity,"fireEngineSprite.png");
    }


}
