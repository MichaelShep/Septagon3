package com.kroy.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Class that is used to create animations when the engines and fortresses are attacking each other
 */
public class Bullet {

    private double ySPEED, xSPEED;
    public Texture texture;
    private double deltaY, deltaX;
    int xPosition, yPosition, targetX, targetY;
    public boolean remove = false;
    /**
     * new things
     */
    private ShapeRenderer shapeRenderer;
    private Human humanData;
    private Enemy enemyData;

    public Bullet(Character attacker, Character target, boolean water) {
        if (attacker != null) {

            xPosition = attacker.getLocation().getMapX() * Constants.getTileSize();
            yPosition = attacker.getLocation().getMapY() * Constants.getTileSize() - 11;
            targetX = target.getLocation().getMapX() * Constants.getTileSize();
            targetY = target.getLocation().getMapY() * Constants.getTileSize();

            deltaY = targetY - yPosition;
            deltaX = targetX - xPosition;

            //calculate relative speed in both x and y directions in order to move from attacker to target
            ySPEED = deltaY / (deltaX * deltaX + deltaY * deltaY);
            xSPEED = deltaX / (deltaX * deltaX + deltaY * deltaY);

            if (water) {
                shapeRenderer.setColor(Color.BLUE);
                shapeRenderer.circle(xPosition, yPosition, 1);
            } else {
                shapeRenderer.setColor(Color.LIME);
                shapeRenderer.circle(xPosition, yPosition, 1);
            }

        }
    }

    //creates a bullet with attacker and target positions
    public Bullet(int attackerX, int attackerY, int targetX, int targetY, boolean water) {


        this.yPosition = attackerX;
        this.yPosition = attackerY;
        this.targetX = targetX;
        this.targetY = targetY;
        deltaY = this.targetY - this.yPosition;
        deltaX = this.targetX - this.xPosition;

        //calculate relative speed in both x and y directions in order to move from attacker to target
        ySPEED = deltaY / (deltaX * deltaX + deltaY * deltaY);
        xSPEED = deltaX / (deltaX * deltaX + deltaY * deltaY);

        if (texture == null) {
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
        System.out.print("bullet fired");
        yPosition += this.ySPEED * deltaTime * 3000;
        xPosition += this.xSPEED * deltaTime * 3000;
        if ((deltaX * (targetX - xPosition) < 0) && (deltaY * (targetY - yPosition) < 0))
            remove = true;
    }

    /**
     * Draws the bullet to the screen
     *
     * @param batch The batch which is used for drawing objects to the screen
     */
    public void renderBullet(Batch batch) {
        batch.draw(texture, xPosition, yPosition);
    }

    public void renderBullets(OrthographicCamera cam) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Character attacker : humanData.getTeam()) {
            for (Character target : enemyData.getTeam()) {
                new Bullet(attacker, target, true);
            }
        }
        for (Character attacker : enemyData.getTeam()) {
            for (Character target : enemyData.getTeam()) {
                new Bullet(attacker, target, false);
            }
        }
        shapeRenderer.end();
    }
}