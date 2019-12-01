package com.kroy.game;
import java.util.ArrayList;

abstract public class Player {

    protected static int turns = 1;
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
            if(team[i].getDisabled()) {
                team[i] = null;
            }
        }
    }

    protected void resolveMove() {    // not sure how to set moves yet
        if((turns % 2) == 0) {
            Player.turns += 1;
        } else {
            Player.turns += 1;
        }
    }

    public int getTurns() {
        return Player.turns;
    }

    public String getName() {
        return this.name;
    }

    public boolean getMyTurn() {
        return this.myTurn;
    }

    public void setMyTurn(boolean state) {
        this.myTurn = state;
    }

    public Character[] getTeam() {
        return this.team;
    }


}
