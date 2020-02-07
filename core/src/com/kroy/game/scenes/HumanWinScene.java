package com.kroy.game.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kroy.game.Constants;
import com.kroy.game.SceneType;

/***
 * Class added as a result of refractoring by Team Septagon
 * Used to handle the game when the user has won
 */

public class HumanWinScene extends Scene
{
    /***
     * Constructor used to pass values to the scene
     * @param font The games font
     * @param cam the games camera
     */
    public HumanWinScene(BitmapFont font, OrthographicCamera cam) {
        super(font, cam);
    }

    @Override
    /**
     * Initialise the human win screen
     */
    public void initScene() {
        //set humanWinScreen Components;
        sceneType = SceneType.SCENE_TYPE_HUMANWIN;
        font.setColor(1f, 1f, 1f, 1f);
        cam.zoom = 2f;
        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();
    }

    @Override
    /***
     * Used to handle updating the scene
     */
    public void resolveScene() {

    }

    @Override
    /**
     * Render human win screen
     *
     * @param batch the batch to render the scene through
     */
    public void renderScene(Batch batch) {
        batch.begin();
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);

        font.draw(batch, "YOU WIN", -(Constants.getResolutionWidth() / 6f), 100);
        font.draw(batch, "THE KROY ARE NO MORE!", -(Constants.getResolutionWidth() / 2.8f), 0);
        font.draw(batch, "Press -SPACE- To RETURN", -(Constants.getResolutionWidth() / 2.75f), -100);
        batch.end();
    }
}
