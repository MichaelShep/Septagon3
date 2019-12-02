package com.kroy.game;

import java.util.ArrayList;

public class Enemy extends Player {

    public Enemy(String name, Boolean myTurn, int teamSize)
    {
        super(name,myTurn, teamSize);

        for (int members = 0; members < teamSize; members++)
        {
            team[members] = createFortress();
        }

    }



    public Fortress createFortress()
    {
        //encapsulates the balance
        int health = 100;
        int damage = 10;
        int range = 3;
        int speed = 3;
        int waterCapacity = 100;

        return new Fortress(health,damage,range,null,"Default Fortress Name","fortressSprite.png");
    }







}

/*
    public Enemy(String name, boolean myTurn, ArrayList<Fortress> team) {
        super(name, myTurn);
        team = new ArrayList<>();
    }

    private void contructTeam(int numberOfFortesses, Tile[] characterPositions) { // Not sure what this will look like
        for (int fireEngine= 0; fireEngine < Constants.getFireEngineCount(); fireEngine++) {
            this.getTeam().add(Fortress());
        }
    }

    public void setDeath(Fortress dead) {
        this.getTeam().remove(dead);
    }







*/

