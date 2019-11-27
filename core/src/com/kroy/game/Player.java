package com.kroy.game;
import java.util.ArrayList;

abstract public class Player {

    private static int turns = 1;
    private String name;
    protected boolean myTurn;
    private ArrayList<Character> team;

    public Player(String name, boolean myTurn) {
        this.name = name;
        this.myTurn = myTurn;
    }

    protected void resolveDeaths() {
        for(int i = 0; i < team.size(); i ++) {
            if(team.get(i).getDisabled()) {
                team.remove(i);
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

    public ArrayList<Character> getTeam() {
        return this.team;
    }

    public void killTeam(Character entity) {
        this.team.remove(entity);
    }

}
