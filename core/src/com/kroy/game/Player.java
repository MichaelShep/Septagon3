package com.kroy.game;

abstract public class Player {

    protected static int turnCount = 1;
    protected String name;
    protected boolean myTurn;
    protected Character[] team;

    public Player(String name, boolean myTurn, int teamSize) {
        this.name = name;
        this.myTurn = myTurn;
        team = new Character[teamSize];
    }

    protected void resolveDeaths() {

        for(int i = 0; i < team.length; i ++) {
            if (!(team[i] == null))
                if(team[i].getDisabled()) {
                    team[i].getLocation().setInhabitant(null);
                    team[i] = null;
                }
        }
    }

    protected int getAliveCharacters()
    {
        int alive = 0;
        for (Character character: team)
        {
            if (!(character == null))
            {
                alive++;
            }
        }

        return  alive;
    }

    public void distributeTeamLocation(Tile[] locations)
    {
        for(int locationIndex = 0; locationIndex < locations.length; locationIndex++)
        {
            team[locationIndex].setLocation(locations[locationIndex]);
            locations[locationIndex].setInhabitant(team[locationIndex]);
        }
    }



    public int getTurns() {
        return Player.turnCount;
    }

    public String getName() {
        return this.name;
    }


    public void setMyTurn(boolean state) {
        this.myTurn = state;
    }

    public Character[] getTeam() {
        return this.team;
    }

    public boolean isMyTurn() {
        return myTurn;
    }
}
