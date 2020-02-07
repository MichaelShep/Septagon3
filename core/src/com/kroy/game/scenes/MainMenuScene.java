package com.kroy.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kroy.game.Constants;
import com.kroy.game.SceneType;

/***
 * Class added as a result of Refractoring by team Septagon
 * Used to handle the game when it is in the menu
 */

public class MainMenuScene extends Scene
{
    private float runTime;

    public MainMenuScene(BitmapFont font, OrthographicCamera cam)
    {
        super(font, cam);
    }

    /**
     * Initialise the data needed for the main menu
     */
    public void initScene() {
        sceneType = SceneType.SCENE_TYPE_MAINMENU;
        titleSprite = new Sprite(Constants.getManager().get(Constants.getResourceRoot() + "title.png", Texture.class), 0, 0, 621, 168);
        titleSprite.setScale(2 * Constants.getResolutionWidth() / 1280f);
        titleSprite.setCenterX(0);
        titleSprite.setCenterY(Constants.getResolutionHeight() / 4);

        runTime = 0;

        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();
        cam.zoom = 2f;
    }

    /**
     * Calculates actions during main menu scene
     */
    public void resolveScene() {
        runTime += Gdx.graphics.getDeltaTime();

        //titleSprite.setCenterX();
        titleSprite.setX((titleSprite.getX() - (float) Math.sin(2 * runTime)));
        titleSprite.setY((titleSprite.getY() + (float) Math.sin(4 * runTime)));


        font.setColor(1f, 1f, 1f, (float) Math.pow(Math.sin(0.5 * runTime), 2));
        cam.zoom = (float) (1.9 - Math.sin(0.5 * runTime) / 10f);
    }


    /**
     * Render the main menu scene
     *
     * @param batch the batch to render the scene through
     */
    public void renderScene(Batch batch) {
        batch.begin();

        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);
        titleSprite.draw(batch);
        font.getData().setScale((Constants.getResolutionWidth()/1280f));
        font.draw(batch, "Press -SPACE- To Start", -Constants.getResolutionWidth() / 3f, 0);
        font.draw(batch, "Press -ESC- To Exit", -Constants.getResolutionWidth() / 3.2f, -150);

        batch.end();
    }
}
