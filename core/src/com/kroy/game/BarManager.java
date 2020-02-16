package com.kroy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.nio.charset.CharsetEncoder;

/**
 * Class created by Septagon
 * Used to give the engines a health bar and a water meter so the stats can be seen easier
 */

public class BarManager
{
    private Human humanData;
    private Enemy enemyData;
    private ShapeRenderer shapeRenderer;

    private int shiftX, shiftY;

    public BarManager(Human humanData, Enemy enemyData)
    {
        this.humanData = humanData;
        this.enemyData = enemyData;
        shapeRenderer = new ShapeRenderer();
    }

    /***
     * Method that will render both the water meter and health bar for all engines in the game
     * @param cam Camera of the game that is used to set the projection matrix of the shape renderer
     */
    public void renderBars(OrthographicCamera cam)
    {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(Character character: humanData.getTeam())
        {
            renderHealthBar(character);
            renderWaterMeterForEngine(character);
        }
        for(Character character: enemyData.getTeam())
        {
            renderHealthBar(character);
        }
        for(Character character: enemyData.getPatrols())
        {
            renderHealthBar(character);
        }
        shapeRenderer.end();
    }

    /***
     * Method to draw the health bar for a specific engine
     * @param character The engine the health bar should be drawn for
     */
    private void renderHealthBar(Character character)
    {
        if(character != null) {
            int xPosition = character.getLocation().getMapX() * Constants.getTileSize() + shiftX;
            int yPosition = character.getLocation().getMapY() * Constants.getTileSize() + shiftY + Constants.getTileSize();

            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.rect(xPosition, yPosition, Constants.getTileSize(), 11);

            getColourOfBar(character);
            float widthOfHealth = (float) ((Constants.getTileSize() - 4) / character.getMaxHealth()) * character.getHealth();
            shapeRenderer.rect(xPosition + 2, yPosition + 2, widthOfHealth, 7);
        }
    }

    /***
     * Method to draw the water meter for a specific engine
     * @param character The engine the water meter should be drawn for
     */
    private void renderWaterMeterForEngine(Character character)
    {
        if(character != null) {
            FireEngine engine = (FireEngine) character;

            int xPosition = character.getLocation().getMapX() * Constants.getTileSize() + shiftX;
            int yPosition = character.getLocation().getMapY() * Constants.getTileSize() + shiftY - 11;

            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.rect(xPosition, yPosition, Constants.getTileSize(), 11);

            shapeRenderer.setColor(Color.BLUE);
            float widthOfWater = (float) ((Constants.getTileSize() - 4) / engine.getWaterCapacity()) * engine.getWaterAmount();
            shapeRenderer.rect(xPosition + 2, yPosition + 2, widthOfWater, 7);
        }
    }

    /***
     * Method to work out what colour the engines health should be shown in dependant on how much health they have
     * @param character The engine that we are currently drawing the health bar for
     */
    public void getColourOfBar(Character character)
    {
        if(character.getHealth() <= character.getMaxHealth() / 4){
            shapeRenderer.setColor(Color.RED);
        }else if(character.getHealth() <= character.getMaxHealth() / 2){
            shapeRenderer.setColor(Color.YELLOW);
        }else{
            shapeRenderer.setColor(Color.GREEN);
        }
    }

    //Setter Method
    public void setShiftX(int shiftX) { this.shiftX = shiftX; }
    public void setShiftY(int shiftY) { this.shiftY = shiftY; }

    public Human getHumanData() { return humanData; }
    public Enemy getEnemyData() { return enemyData; }
    public ShapeRenderer getShapeRenderer() { return shapeRenderer; }

}
