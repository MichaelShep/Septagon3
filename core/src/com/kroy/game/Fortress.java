package com.kroy.game;


public class Fortress extends Character {

    private String name;


    /**
     * Construct a character that Enemy can control
     * Extends Character
     *
     * @param health   the starting and max health
     * @param damage   the amount health it removes when shooting
     * @param range    how many tiles away it can shoot
     * @param location the starting location
     * @param charName the name of the location on the map
     * @param charTex  the sprite that will be rendered for this character
     */
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
