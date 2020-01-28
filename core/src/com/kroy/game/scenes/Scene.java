package com.kroy.game.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kroy.game.SceneType;

public abstract class Scene
{
    protected OrthographicCamera cam;
    protected Sprite titleSprite;
    protected SceneType sceneType;
    protected BitmapFont font;

    protected Scene(BitmapFont font, OrthographicCamera cam)
    {
        this.font = font;
        this.cam = cam;
    }

    public abstract void initScene();

    public abstract void resolveScene();

    public abstract void renderScene(Batch batch);

    public SceneType getSceneType() { return sceneType; }
}
