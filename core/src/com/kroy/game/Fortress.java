package com.kroy.game;


public class Fortress extends Character {

    private String name;

    public Fortress(int health, int damage, int range, Tile location, String charName, String charTex) {
        super(health, damage, range, location, charTex);

        name = charName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
