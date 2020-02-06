package com.kroy.game;

import java.util.ArrayList;

abstract public class Player {

    protected boolean myTurn;
    protected ArrayList<Character> team;

    /**
     * Constructs a Player Object
     * @param myTurn whether the player is active on instantiation
     * @param teamSize the number of Characters the player can control
     */
    public Player(boolean myTurn, int teamSize) {
        this.myTurn = myTurn;
        team = new ArrayList<Character>();
    }

    /**
     * automatically deallocate characters that are disabled
     */
    public void resolveDeaths() {
        for (int i = 0; i < team.size(); i++) {
            if (!(team.get(i) == null))
                if (team.get(i).isDisabled()) {
                    team.get(i).getLocation().setInhabitant(null);
                    team.remove(i);
                }
        }
    }

    /**
     * find the number of characters you control that are still alive
     * @return the number of alive characters
     */
    public int getAliveCharacters() {
        int alive = 0;
        for (Character character : team) {
            if (!(character == null)) {
                alive++;
            }
        }

        return alive;
    }

    /**
     * places you team in the locations supplied
     * @param locations the locations you want to allocate, linearly across your team.
     */
    public void distributeTeamLocation(Tile[] locations) {
        for (int locationIndex = 0; locationIndex < locations.length; locationIndex++) {
            team.get(locationIndex).setLocation(locations[locationIndex]);
            locations[locationIndex].setInhabitant(team.get(locationIndex));
        }
    }




    public ArrayList<Character> getTeam() {
        return this.team;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean state) {
        this.myTurn = state;
    }
}
