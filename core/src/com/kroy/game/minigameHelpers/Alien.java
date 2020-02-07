package com.kroy.game.minigameHelpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kroy.game.Constants;
import com.kroy.game.Patrol;
import com.kroy.game.Player;

/**
 * Created by Septagon
 * Class that handles the Aliens that are used in the minigame
 */

public class Alien extends Sprite {

    private float movementSpeed;
    private float health;
    private boolean left = false;
    private Rectangle playableArea;
    private Patrol passedPatrol;
    private MinigameBullet bullet;
    private boolean hasLost = false;
    private boolean shouldMoveDown = false;

    /***
     * Used to pass values from the minigame to each Alien
     * @param health CHECK WHAT THIS PARAMETER IS BEING USED FOR
     * @param playableArea The area of the screen that is used for the minigame
     * @param passedPatrol The Patrol that triggered the minigame
     * @param texture The texture of the alien
     */
    public Alien(float health, Rectangle playableArea, Patrol passedPatrol, Texture texture){
        this.health = health;
        this.playableArea = playableArea;
        this.passedPatrol = passedPatrol;
        this.setTexture(texture);
    }

    /***
     * Initalise all properties of the alien
     */
    public void init(){
        movementSpeed = Constants.getMinigameBaseAlienMovementSpeed() * passedPatrol.getRange() * 2;
        this.setSize(Constants.getTileSize(), Constants.getTileSize());
        bullet = new MinigameBullet(this, false);
    }

    /***
     * Moves the alien depending on which direction it is travelling in
     */
    public void move(){
        bullet.update();
        if(left) {
            this.setX(this.getX() - Constants.getMinigameBaseAlienMovementSpeed());
        }else{
            this.setX(this.getX() + Constants.getMinigameBaseAlienMovementSpeed());
        }
        checkBounds();
    }

    /***
     * Fires the aliens bullet
     */
    public void fire(){
        bullet.fire();
    }

    /***
     * Checks that the alien does not go off the edge of the screen
     */
    private void checkBounds(){
        if(this.getX() <= playableArea.x + Constants.getMinigameEdgeBuffer()){
            shouldMoveDown = true;
        }else if(this.getX() + this.getWidth() >= playableArea.getX() + playableArea.getWidth() - Constants.getMinigameEdgeBuffer()){
            shouldMoveDown = true;
        }

        //Checks if the alien has reached the players level - if so means the player lost
        if(this.getY() <= playableArea.y + Constants.getTileSize()){
            hasLost = true;
        }
    }

    @Override
    /***
     * Draws the alien
     */
    public void draw(Batch batch){
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    //Getters and Setters

    public float getMovementSpeed() { return movementSpeed; }
    public float getHealth() { return health; }
    public boolean isLeft() { return left; }
    public MinigameBullet getBullet() { return bullet; }
    public boolean isHasLost() { return hasLost; }
    public boolean isShouldMoveDown() { return shouldMoveDown; }

    public void setLeft(boolean left) { this.left = left; }
    public void setShouldMoveDown(boolean  shouldMoveDown) { this.shouldMoveDown = shouldMoveDown; }
}
