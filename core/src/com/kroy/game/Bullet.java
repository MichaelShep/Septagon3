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
    private float xPosition, yPosition;
    private float targetX, targetY;
    public boolean remove = false;
    private Character attacker, target;
    private float updateYPosition, updateXPosition;
    private float relativeXPosition, relativeYPosition;
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

    //creates a bullet with attacker and target positions
    public Bullet(Character attacker, Character target, boolean water) {

        this.attacker = attacker;
        this.target = target;

        if ((attacker != null) && (target != null)){

            xPosition = attacker.getLocation().getMapX() * Constants.getTileSize() + Constants.getTileSize()/2;
            yPosition = attacker.getLocation().getMapY() * Constants.getTileSize() + Constants.getTileSize()/2;

            targetX = target.getLocation().getMapX() * Constants.getTileSize() + Constants.getTileSize()/2;
            targetY = target.getLocation().getMapY() * Constants.getTileSize() + Constants.getTileSize()/2;

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
        relativeXPosition = 0;
        relativeYPosition = 0;
    }



    /**
     * Move bullets in required directions
     */
    /*
    public void _update(float deltaTime) {


        xPosition = attacker.getLocation().getMapX() * Constants.getTileSize() + shiftX ;
        yPosition = attacker.getLocation().getMapY() * Constants.getTileSize() + shiftY ;

        targetX = target.getLocation().getMapX() * Constants.getTileSize() + shiftX;
        targetY = target.getLocation().getMapY() * Constants.getTileSize() + shiftY;

        System.out.print("xPosition " + xPosition + "  yPosition " + yPosition + "  targetX " + targetX +"  targetY " + targetY + "\n");


        yPosition += ySPEED * deltaTime * 3000;
        xPosition += xSPEED * deltaTime * 3000;

        if ((deltaX * (targetX - xPosition) < 0) && (deltaY * (targetY - yPosition) < 0))
            remove = true;
    }*/

    public void update(float deltaTime) {

        updateXPosition = attacker.getLocation().getMapX() * Constants.getTileSize() + shiftX;
        updateYPosition = attacker.getLocation().getMapY() * Constants.getTileSize() + shiftY;

        targetX = target.getLocation().getMapX() * Constants.getTileSize() + Constants.getTileSize()/2 + shiftX;
        targetY = target.getLocation().getMapY() * Constants.getTileSize() + Constants.getTileSize()/2 + shiftY;

        System.out.print("xPosition " + xPosition + "  yPosition " + yPosition + "  targetX " + targetX +"  targetY " + targetY + "\n");

        relativeXPosition += xSPEED * deltaTime * 6000;;
        relativeYPosition += ySPEED * deltaTime * 6000;

        xPosition = relativeXPosition + updateXPosition;
        yPosition = relativeYPosition + updateYPosition;

        if ((deltaX * (targetX - xPosition) < 0) && (deltaY * (targetY - yPosition) < 0)){
            remove = true;
        }
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