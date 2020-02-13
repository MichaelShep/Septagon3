package com.kroy.game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/***
 * Used to handle all info about patrols and fortresses
 */

public class Enemy extends Player {

    //Used to keep track of all the patrols in the game [ID: P1]
    private List<Patrol> patrols;

    /**
     * Constructs Enemy Object which is able to control a team of fire Engines
     * Extends from Player Object
     * @param myTurn   tells this player whether it can act
     * @param teamSize the amount of characters it controls
     */
    public Enemy( Boolean myTurn, int teamSize, int patrolSize) {
        super(myTurn, teamSize);

        for (int members = 0; members < teamSize; members++) {
            team.add(createFortress(Constants.getFortressNames()[members], members));
        }

        //Initalise all the patrols [ID: P2]
        patrols = new ArrayList<Patrol>();
        for (int members = 0; members < patrolSize; members++ ){
            patrols.add(createPatrol(members));
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
     * Gets a newly constructed Patrol from the patrol profiles given in the Constants file
     * [ID: P3]
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

    /**
     * [ID: [P4]
     * Used to setup the locations for the patrols
     * @param map The games map used to get the location of the fortresses
     */
    public void distributePatrols(Map map) {
        /*for (int locationIndex = 0; locationIndex < locations.length; locationIndex++) {
            patrols.get(locationIndex).setLocation(locations[locationIndex]);
            locations[locationIndex].setInhabitant(patrols.get(locationIndex));
        }*/

        for(int i = 0; i < Constants.getPatrolCount(); i++){
            Tile[] locations = map.getNClosest(1, map.getFortressTiles()[i], TileType.TILE_TYPES_ROAD);
            patrols.get(i).setLocation(locations[0]);
            locations[0].setInhabitant(patrols.get(i));
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

    /***
     * [ID: E1]
     * Method used to improve the fortress as time increases in the game
     */
    public void improveFortresses () {
        for (int i = 0; i < team.size(); i++) {
            if (!(team.get(i) == null)) {
                team.get(i).improve();
            }
        }
    }

    //Getters and Setters
    public List<Patrol> getPatrols(){
        return patrols;
    }
}







