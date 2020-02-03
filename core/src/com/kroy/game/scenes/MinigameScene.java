package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Class created by Team Septagon
 * This is used to handle all of the features and events of the minigame
 */

public class MinigameScene extends Scene {

    private GlyphLayout minigameText;

    protected MinigameScene(BitmapFont font, OrthographicCamera cam) {
        super(font, cam);
    }

    @Override
    public void initScene() {
        minigameText = new GlyphLayout();

        font.getData().setScale(1.0f);
        font.setColor(Color.WHITE);
        minigameText.setText(font, "MiniGame");
    }

    @Override
    public void resolveScene() {

    }

    @Override
    public void renderScene(Batch batch) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, minigameText, -(minigameText.width / 2), Gdx.graphics.getHeight() / 2 - 75);
        batch.end();
    }
}
