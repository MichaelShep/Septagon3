package com.kroy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BarManager
{
    private Character[] team;
    private ShapeRenderer shapeRenderer;

    public BarManager(Character[] team)
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
        int xPosition = character.getLocation().getMapX() * Constants.getTileSize() - (Gdx.graphics.getWidth() / 2);
        int yPosition = character.getLocation().getMapY() * Constants.getTileSize() - (Gdx.graphics.getHeight() / 2) + Constants.getTileSize();

        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(xPosition, yPosition, Constants.getTileSize(), 10);
    }

    private void renderWaterMeterForEngine(Character character)
    {

    }
}
