package com.kroy.game.minigameHelpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kroy.game.Constants;
import com.kroy.game.Player;
import com.sun.org.apache.bcel.internal.Const;

/**
 * Created by Septagon
 * Class that handles the Aliens that are used in the minigame
 */

public class Alien extends Sprite {

    private float movementSpeed;
    private float health;
    private boolean left = true;
    private Rectangle playableArea;

    public Alien(float movementSpeed, float health, Rectangle playableArea){
        this.movementSpeed = movementSpeed;
        this.health = health;
        this.playableArea = playableArea;
    }

    public void move(){
        if(left) {
            this.setX(this.getX() + Constants.getMinigameBaseAlienMovementSpeed());
        }else{
            this.setX(this.getX() - Constants.getMinigameBaseAlienMovementSpeed());
        }
        checkBounds();
    }

    private void checkBounds(){
        if(this.getX() <= playableArea.x + Constants.getMinigameEdgeBuffer()){
            this.setX(playableArea.x + Constants.getMinigameEdgeBuffer());
            this.setY(this.getY() - Constants.getTileSize());
            left = true;
        }else if(this.getX() + this.getWidth() + Constants.getMinigameEdgeBuffer() >= playableArea.getX() + playableArea.getWidth()){
            this.setX(playableArea.getX() + playableArea.getWidth() - this.getWidth() - Constants.getMinigameEdgeBuffer());
            this.setY(this.getY() - Constants.getTileSize());
            left = false;
        }
    }

    public float getMovementSpeed() { return movementSpeed; }
    public float getHealth() { return health; }
    public boolean isLeft() { return left; }

    public void setLeft(boolean left) { this.left = left; }
}
