package com.kroy.game.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kroy.game.SceneType;

/***
 * Class added asa a result of refractoring by Septagon
 * Abstract class that holds all features that all scenes must have
 */

public abstract class Scene
{
    protected OrthographicCamera cam;
    protected Sprite titleSprite;
    protected SceneType sceneType;
    protected BitmapFont font;

    /***
     * Constructor used to pass values to the scene
     * @param font The games font
     * @param cam The games Camera
     */
    protected Scene(BitmapFont font, OrthographicCamera cam)
    {
        this.font = font;
        this.cam = cam;
    }

    /***
     * Used to initialise all the properties of the scene
     */
    public abstract void initScene();

    /**
     * Used to update the scene
     */
    public abstract void resolveScene();

    /**
     * Used to render everything in the scene
     * @param batch The SpriteBatch used for rendering in the game
     */
    public abstract void renderScene(Batch batch);

    //Getters
    public SceneType getSceneType() { return sceneType; }
}
