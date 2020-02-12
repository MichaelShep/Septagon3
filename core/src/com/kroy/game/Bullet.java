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
    /**
     * new things
     */
    private ShapeRenderer shapeRenderer;
    private Human humanData;
    private Enemy enemyData;

    //creates a bullet with attacker and target positions

    public Bullet(Character attacker, Character target, boolean water) {
        if (attacker != null) {

            xPosition = attacker.getLocation().getMapX() * Constants.getTileSize() /10;
            yPosition = attacker.getLocation().getMapY() * Constants.getTileSize() /100;
            targetX = target.getLocation().getMapX() * Constants.getTileSize();
            targetY = target.getLocation().getMapY() * Constants.getTileSize();

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
    public Bullet (int attackerX, int attackerY, int targetX, int targetY, boolean water) {
        this.xPosition = attackerX;
        this.yPosition = attackerY;
        this.targetX = targetX;
        this.targetY = targetY;
        deltaY = this.targetY - this.yPosition;
        deltaX = this.targetX - this.xPosition;

        //calculate relative speed in both x and y directions in order to move from attacker to target
        ySPEED = deltaY / (deltaX*deltaX + deltaY*deltaY);
        xSPEED = deltaX / (deltaX*deltaX + deltaY*deltaY);

        if (texture == null) {
            if (water) {
                texture = new Texture("water.png");
            }
            else {
                texture = new Texture("gunge.png");
            }
        }
    }
*/
    /**
     * Move bullets in required directions
     */
    public void update(float deltaTime) {
        System.out.print("xPosition " + xPosition + "   yPosition " + yPosition + "   targetX " + targetX +"   targetY " + targetY + "   delta time " + deltaTime +"\n");
        yPosition += ySPEED * deltaTime *3000 ;
        xPosition += xSPEED * deltaTime *3000 ;
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