package com.kroy.game.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kroy.game.Assets;
import com.kroy.game.Constants;
import com.kroy.game.SceneType;

/***
 * Class made as a result of refractoring by Septagon
 * Used to handle the game once the enemy has won (player lost)
 */

public class EnemyWinScene extends Scene {

    protected EnemyWinScene(BitmapFont font, OrthographicCamera cam) {
        super(font, cam);
    }

    @Override
    /**
     * Initialise the enemy win screen
     */
    public void initScene() {
        //set humanWinScreen Components;
        sceneType = SceneType.SCENE_TYPE_ENEMYWIN;
        font.setColor(1f, 1f, 1f, 1f);
        cam.zoom = 2f;
        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();

    }

    @Override
    /**
     * Calculates actions during enemy win scene
     */
    public void resolveScene() {

    }

    @Override
    /**
     * render the enemy the win screen
     *
     * @param batch the batch to render the scene through
     */
    public void renderScene(Batch batch) {
        batch.begin();
        batch.draw(Assets.menuBackgroundTexture, -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);

        font.draw(batch, "YOU LOSE", -(Constants.getResolutionWidth() / 6f), 100);
        font.draw(batch, "OUR HEROES HAVE FALLEN!", -(Constants.getResolutionWidth() / 2.65f), 0);
        font.draw(batch, "Press -SPACE- To RETURN", -(Constants.getResolutionWidth() / 2.9f), -100);
        batch.end();

    }
}
