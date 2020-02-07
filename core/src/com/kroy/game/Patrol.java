package com.kroy.game;

/*
Added by Septagon
Used to handle all info about the Patrols in the game
 */
public class Patrol extends Character {

    /**
     * Constructs a Character object which is an entity that can shoot, move and be killed.
     * Extends from Sprite to allow rendering capability
     * Character is abstract and must be constructed via a child
     *
     * @param health    the starting health of the character
     * @param damage    the amount of damage this deals
     * @param range     how far from its location it can shoot
     * @param spawn     the starting location of this character
     * @param spriteTex the image that the sprite class is loaded with
     */
    public Patrol(int health, int damage, int range, Tile spawn, String spriteTex) {
        super(health, damage, range, spawn, spriteTex);
        this.type = Type.PATROL;
    }

    /***
     * Used to handle moving the patrols to a new location
     * @param newLocation The new tile that the patrol should be moved to
     */
    public void transferTo(Tile newLocation){
        location.setInhabitant(null);
        location = newLocation;
        location.setInhabitant(this);
    }






}
