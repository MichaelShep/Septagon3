package com.kroy.game;

import java.util.ArrayList;

/*
Added by Septagon
Used to handle all info about the Patrols in the game
 */
public class Patrol extends Character {
    private int[] u = {0,1};
    private int[] l = {-1,0};
    private int[] d = {0,-1};
    private int[] r = {1,0};
    private int[][] directionArray = {u,r,d,l};
    private int direction;

    public int getDirection(){
        return direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

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
     * worst case, the left value (turning left). A new variable is made to account for pointers exceeding the list to
     * make them wrap around appropriately ie -1 to 3 and 4 to 0. The patrol will never have to move backward as the
     * game map is made of loops so a fourth case is not required. Once the adjacent tile closest to a right turn is
     * found, the patrol is moved to this position and the direction pointer is updated appropriately and the loop is
     * broke.
     * [ID: M1]
     */
    public void move(SceneManager patrolSceneHelper){
        ArrayList<Tile> movableTiles = patrolSceneHelper.getMap().getWithRangeOfHub(this.getLocation(), 2, TileType.TILE_TYPES_ROAD);
        boolean moved = false;
        if (movableTiles.size() > 0) {
            for (int desiredDirectionPointer = direction + 1; desiredDirectionPointer > direction - 2; desiredDirectionPointer--) {
                for (Tile tile : movableTiles) {
                    int correctedDirectionPointer = (((desiredDirectionPointer) % 4 + 4) % 4);
                    int desiredTileX = (int) this.getLocation().getMapX() + directionArray[correctedDirectionPointer][0];
                    int desiredTileY = (int) this.getLocation().getMapY() + directionArray[correctedDirectionPointer][1];
                    if ((tile.getMapX() == desiredTileX) && (tile.getMapY() == desiredTileY) && (tile.getInhabitant() == null)) {
                        this.transferTo(tile);
                        direction = correctedDirectionPointer;
                        moved = true;
                    }
                    if (moved) break;
                }
                if (moved) break;
            }
        }
    }
}