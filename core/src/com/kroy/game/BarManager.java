package com.kroy.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BarManager
{
    private FireEngine[] team;
    private ShapeRenderer shapeRenderer;

    public BarManager(FireEngine[] team)
    {
        this.team = team;
        shapeRenderer = new ShapeRenderer();
    }

    public void renderBars()
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(FireEngine fireEngine: team)
        {
            renderHealthBarForEngine(fireEngine);
            renderWaterMeterForEngine(fireEngine);
        }
        shapeRenderer.end();
    }

    private void renderHealthBarForEngine(FireEngine engine)
    {
        int xPosition = engine.getLocation().getMapX();
        int yPosition = engine.getLocation().getMapY();

        System.out.println("X: " + xPosition + ", Y: " + yPosition);
    }

    private void renderWaterMeterForEngine(FireEngine engine)
    {

    }
}
