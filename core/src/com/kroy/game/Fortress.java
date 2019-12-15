package com.kroy.game;


class Fortress extends Character {

    private String name;

    public Fortress(int health, int damage, int range, Tile location, String charName, String charTex) {
        super(health, damage, range, location, charTex);

        name = charName;

    }

    public void shootTarget(FireEngine target)
    {
        target.setHealth(Math.max(0, target.getHealth() - damage));

    }





}
