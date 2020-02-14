package com.kroy.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Class that is used to create animations when the engines and fortresses are attacking each other
 */
public class Bullet {

    private double ySPEED, xSPEED;
    public Texture texture;
    private double deltaY, deltaX;
    float xPosition, yPosition;
    float targetX, targetY;
    public boolean remove = false;
    public Character attacker, target;
    float updateYPosition, updateXPosition;

    /**
     * new things
     */
    private ShapeRenderer shapeRenderer;
    private Human humanData;
    private Enemy enemyData;
    private int shiftX, shiftY;

    //Setter Method
    public void setShiftX(int shiftX) { this.shiftX = shiftX; }
    public void setShiftY(int shiftY) { this.shiftY = shiftY; }
    public void setxPosition(float xPosition) {this.xPosition = xPosition;}
    public void setyPosition(float yPosition) {this.yPosition = yPosition;}
    public void setTargetX(float targetX) {this.targetX = targetX;}
    public void setTargetY(float targetY) {this.targetY = targetY;}

    //creates a bullet with attacker and target positions
    public Bullet(Character attacker, Character target, boolean water) {

        this.attacker = attacker;
        this.target = target;

        if ((attacker != null) && (target != null)){

            xPosition = attacker.getLocation().getMapX() * Constants.getTileSize() + shiftX ;
            yPosition = attacker.getLocation().getMapY() * Constants.getTileSize() + shiftY;

            targetX = target.getLocation().getMapX() * Constants.getTileSize() + shiftX;
            targetY = target.getLocation().getMapY() * Constants.getTileSize() + shiftY;

            //difference in distance between attacker and target
            deltaY = targetY - yPosition;
            deltaX = targetX - xPosition;

            //calculate relative speed in both x and y directions in order to move from attacker to target
            ySPEED = deltaY / (deltaX * deltaX + deltaY * deltaY);
            xSPEED = deltaX / (deltaX * deltaX + deltaY * deltaY);

            if (water) {
                texture = new Texture("water.png");
            } else {
                texture = new Texture("gunge.png");
            }
        }
    }



    /**
     * Move bullets in required directions
     */
    public void update(float deltaTime) {

        xPosition = attacker.getLocation().getMapX() * Constants.getTileSize() + shiftX ;
        yPosition = attacker.getLocation().getMapY() * Constants.getTileSize() + shiftY;

        targetX = target.getLocation().getMapX() * Constants.getTileSize() + shiftX;
        targetY = target.getLocation().getMapY() * Constants.getTileSize() + shiftY;

        System.out.print("xPosition " + xPosition + "  yPosition " + yPosition + "  targetX " + targetX +"  targetY " + targetY + "\n");

        yPosition += ySPEED * deltaTime * 3000;
        xPosition += xSPEED * deltaTime * 3000 ;

        if ((deltaX * (targetX - xPosition) < 0) && (deltaY * (targetY - yPosition) < 0))
            remove = true;
    }

    /**
     * Draws the bullet to the screen
     *
     * @param batch The batch which is used for drawing objects to the screen
     */
    public void renderBullets(Batch batch) {
        batch.draw(texture, xPosition, yPosition);
    }
}