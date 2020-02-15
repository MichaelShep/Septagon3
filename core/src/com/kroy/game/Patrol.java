package com.kroy.game;

import java.util.ArrayList;

/*
Added by Septagon
Used to handle all info about the Patrols in the game
 */
public class Patrol extends Character {
    private int[] u = {0,1}; int[] l = {-1,0}; int[] d = {0,-1}; int[] r = {1,0};
    private int[][] directionArray = {u,r,d,l};
    private int direction = 2;

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

    /***
     * To get the correct movement, patrols must complete a loop around a fortress block. This can be thought of as
     * always turning right whenever possible. This is done by having a list of unit vectors in all four directions with
     * a pointer storing the current direction of movement [u,l,d,r]. To produce the required movement, the pointer will
     * want to move right, down the list so a nested for loop is used. The inner loop iterates through the adjacent
     * tiles while the outer one is used to calculate which direction to be looking in ie starting with the direction to
     * the right in the list (the current direction's right), then the current direction (moving forward), then, the
     * worst case, the left value (turning left). The patrol will never have to move backward as the game map is made of
     * loops. Once the adjacent tile closest to a right turn is found, the patrol is moved to this position and the
     * direction pointer is updated appropriately and the loop is broke.
     * @param patrolSceneHelper used to get tile functions
     */
    public void move(SceneManager patrolSceneHelper){
        ArrayList<Tile> movable = patrolSceneHelper.getMap().getWithRangeOfHub(this.getLocation(), 2, TileType.TILE_TYPES_ROAD);
        boolean moved = false;
        if (movable.size() > 0) {
            for (int i = 1; i > -2; i--) {
                for (Tile tile : movable) {
                    System.out.println((-1 % 4 + 4) % 4);
                    //System.out.println("chosen tile ("+ directionArray[Math.abs(direction + i % 4)].toString() + "): " +((int) this.getLocation().getMapX() + directionArray[Math.abs(direction + i) % 4][0] + " " + (int) this.getLocation().getMapY() + directionArray[Math.abs(direction + i) % 4][1]));
                    int desiredTileX = (int) this.getLocation().getMapX() + directionArray[(((direction + i) % 4 + 4)% 4)][0];
                    int desiredTileY = (int) this.getLocation().getMapY() + directionArray[(((direction + i) % 4 + 4)% 4)][1];
                    if ((tile.getMapX() == desiredTileX) && (tile.getMapY() == desiredTileY) && (tile.getInhabitant() == null)) {
                        this.transferTo(tile);
                        direction = Math.abs((((direction + i) % 4 + 4)% 4));
                        moved = true;
                    }
                    if (moved) break;
                }
                if (moved) break;
            }
        }
    }
}