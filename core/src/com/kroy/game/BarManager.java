package com.kroy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.nio.charset.CharsetEncoder;

/**
 * Class created by Septagon
 */

public class BarManager
{
    private Character[] team;
    private ShapeRenderer shapeRenderer;

    private int shiftX, shiftY;

    public BarManager(Character[] team, Map map)
    {
        this.team = team;
        shapeRenderer = new ShapeRenderer();
    }

    public void renderBars(OrthographicCamera cam)
    {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(Character character: team)
        {
            renderHealthBarForEngine(character);
            renderWaterMeterForEngine(character);
        }
        shapeRenderer.end();
    }

    private void renderHealthBarForEngine(Character character)
    {
        int xPosition = character.getLocation().getMapX() * Constants.getTileSize() + shiftX;
        int yPosition = character.getLocation().getMapY() * Constants.getTileSize() + shiftY + Constants.getTileSize();

        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(xPosition, yPosition, Constants.getTileSize(), 11);

        getColourOfBar(character);
        float widthOfHealth = (float)((Constants.getTileSize() - 4) / character.getMaxHealth()) * character.getHealth();
        shapeRenderer.rect(xPosition + 2, yPosition + 2, widthOfHealth, 7);
    }

    private void renderWaterMeterForEngine(Character character)
    {

    }

    private void getColourOfBar(Character character)
    {
        if(character.getHealth() <= character.getMaxHealth() / 4){
            shapeRenderer.setColor(Color.RED);
        }else if(character.getHealth() <= character.getMaxHealth() / 2){
            shapeRenderer.setColor(Color.YELLOW);
        }else{
            shapeRenderer.setColor(Color.GREEN);
        }
    }

    public void setShiftX(int shiftX) { this.shiftX = shiftX; }
    public void setShiftY(int shiftY) { this.shiftY = shiftY; }
    public void setTeam(Character[] team) { this.team = team; }
}
