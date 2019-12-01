package com.kroy.game;


class Fortress extends Character {

    String name;

    public Fortress(int health, int damage, int range, Tile location, String charName, String charTex) {
        super(health, damage, range, location, charTex);

        name = charName;

    }


    /**public canShoot() {
     } maybe return an array of all fire engines within range. Then what?
     }**/
}
