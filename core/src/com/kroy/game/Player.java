package com.kroy.game;

abstract public class Player {

    protected boolean myTurn;
    protected Character[] team;

    /**
     * Constructs a Player Object
     * @param myTurn whether the player is active on instantiation
     * @param teamSize the number of Characters the player can control
     */
    public Player(boolean myTurn, int teamSize) {
        this.myTurn = myTurn;
        team = new Character[teamSize];
    }

    /**
     * automatically deallocate characters that are disabled
     */
    protected void resolveDeaths() {
        for (int i = 0; i < team.length; i++) {
            if (!(team[i] == null))
                if (team[i].isDisabled()) {
                    team[i].getLocation().setInhabitant(null);
                    team[i] = null;
                }
        }
    }

    /**
     * find the number of characters you control that are still alive
     * @return the number of alive characters
     */
    protected int getAliveCharacters() {
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
            team[locationIndex].setLocation(locations[locationIndex]);
            locations[locationIndex].setInhabitant(team[locationIndex]);
        }
    }




    public Character[] getTeam() {
        return this.team;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean state) {
        this.myTurn = state;
    }
}
