package com.kroy.game;

import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

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
        int damage = 50;
        int range = 5;
        int speed = 5;
        int waterCapacity = 100;

        return new Fortress(health,damage,range,null,"Default Fortress Name","fortressSprite.png");
    }



    public HashMap<Fortress,Tile> calculateTargets(Map mapData)
    {
        HashMap<Fortress,Tile> targetLocations = new HashMap<>();

        for (Character fort: team)
        {
            for (Tile tile: mapData.getWithRangeOfHub(fort.getLocation(),fort.getRange()))
            {
                if (tile.getInhabitant() instanceof FireEngine)
                {
                    Fortress castData = (Fortress)fort;
                    targetLocations.put(castData,tile);
                }
            }

        }

        return targetLocations;


    }


    public void decideTarget(HashMap<Fortress,Tile> targets)
    {
        FireEngine target;
        Fortress source;

        if (targets.isEmpty())
        {
            //no targets in range
            target = null;
            source = null;
            System.out.println("Enemy had no targets to shoot at");
        }
        else
        {
            //decide how to target characters
            Random rand = new Random();
            ArrayList<Fortress> ableToShoot = new ArrayList<Fortress>(targets.keySet());

            source = ableToShoot.get(rand.nextInt(ableToShoot.size()));
            target = (FireEngine)(targets.get(source).getInhabitant());

            source.shootTarget(target);

        }

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

