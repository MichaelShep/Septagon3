package com.kroy.game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Enemy extends Player {

    private Patrol[] patrols;

    /**
     * Constructs Enemy Object which is able to control a team of fire Engines
     * Extends from Player Object
     * @param myTurn   tells this player whether it can act
     * @param teamSize the amount of characters it controls
     */
    public Enemy( Boolean myTurn, int teamSize, int patrolSize) {
        super(myTurn, teamSize);

        for (int members = 0; members < teamSize; members++) {
            team[members] = createFortress(Constants.getFortressNames()[members], members);
        }

        patrols = new Patrol[patrolSize];
        for (int members = 0; members < patrolSize; members++ ){
            patrols[members] = createPatrol(members);
        }
    }


    /**
     * Gets a newly constructed Fortress
     *
     * @param name the name of the new Fortress
     * @return returns the new Fortress object
     */
    public Fortress createFortress(String name, int createdNumber) {
        int[] statProfile = {};
        try
        {
            statProfile = Constants.getFortressProfiles()[createdNumber];
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

        return new Fortress(health, damage, range, null, name, "fortressSprite.png");
    }


    /**
     * Gets a newly constructed Patrol
     *
     * @return returns the new Fortress object
     */
    public Patrol createPatrol(int createdNumber) {
        int[] statProfile = {};
        try
        {
            statProfile = Constants.getPatrolProfiles()[createdNumber];
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

        return new Patrol(health, damage, range, null, "PatrolSprite.png");
    }


    /**
     * Finds all the tiles on a given map that are in range of the Fortress Team
     * And Filters the tiles for only one that have Fire Engines
     *
     * @param mapData the map you want to check for targets on
     * @return returns a hashMap with the Tiles that are accepted\n Key: The Fortress Value: The Tile that it can hit
     */
    public HashMap<Fortress, Tile> calculateTargets(Map mapData) {
        HashMap<Fortress, Tile> targetLocations = new HashMap<>();

        for (Character fort : team) {
            if (!(fort == null)) {

                for (Tile tile : mapData.getWithRangeOfHub(fort.getLocation(), fort.getRange())) {
                    if (tile.getInhabitant() instanceof FireEngine) {
                        Fortress castData = (Fortress) fort;
                        targetLocations.put(castData, tile);
                    }
                }
            }

        }

        return targetLocations;


    }

    public void distributePatrols(Tile[] locations) {
        for (int locationIndex = 0; locationIndex < locations.length; locationIndex++) {
            patrols[locationIndex].setLocation(locations[locationIndex]);
            locations[locationIndex].setInhabitant(patrols[locationIndex]);
        }
    }

    /**
     * algorithm for determining which of the available tiles to shoot at
     *
     * @param targets A hashMap with Key: The Fortress Value: The Tile that it can hit
     */
    public void decideTarget(HashMap<Fortress, Tile> targets) {
        FireEngine target;
        Fortress source;

        if (targets.isEmpty()) {
            //no targets in range
            target = null;
            source = null;
            System.out.println("Enemy had no targets to shoot at");
        } else {
            //decide how to target characters
            Random rand = new Random();
            ArrayList<Fortress> ableToShoot = new ArrayList<Fortress>(targets.keySet());

            source = ableToShoot.get(rand.nextInt(ableToShoot.size()));
            target = (FireEngine) (targets.get(source).getInhabitant());

            source.shootTarget(target);

        }
    }

    public void improveFortresses () { //Added by Septagon
        for (int i = 0; i < team.length; i++) {
            if (!(team[i] == null)) {
                team[i].improve();
            }
        }
    }

    public Patrol[] getPatrols(){
        return patrols;
    }
}







