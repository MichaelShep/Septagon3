package com.kroy.game.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kroy.game.*;
import com.kroy.game.Character;
import com.kroy.game.scenes.GameScene;

import java.util.ArrayList;

/**
 * Class added as a result of refractoring by Septagon
 * Used to handle all the rendering code for the GameScene so that the GameScene class is not too overcrowded
 */

public class Renderer {

    private Map map;
    private HighlightMap highlightMap;

    /***
     * Constructor used to pass values to the Renderer
     * @param map The Games map
     * @param highlightMap The Games HighlightMap
     */
    public Renderer(Map map, HighlightMap highlightMap){
        this.map = map;
        this.highlightMap = highlightMap;
    }

    /**
     * render the fortresses
     *
     * @param batch     the batch to render it through
     * @param enemyData the data of the enemy player
     */
    public void renderEnemies(Batch batch, Enemy enemyData) {
        ArrayList<Character> enemyCharacters = enemyData.getTeam();

        for (Character patrol : enemyData.getPatrols()){
            if (!(patrol == null)){
                map.placeOnMap(patrol);
                patrol.draw(batch);
            }
        }

        for (Character fort : enemyCharacters) {
            if (!(fort == null)) {
                map.placeOnMap(fort);
                fort.draw(batch);
            }
        }
    }

    /**
     * render the fire engines
     *
     * @param batch     the batch to render it through
     * @param humanData the data of the human player
     */
    public void renderFireEngines(Batch batch, Human humanData) {
        ArrayList<Character> humanCharacters = humanData.getTeam();
        for (Character fe : humanCharacters) {
            if (!(fe == null)) {
                map.placeOnMap(fe);
                fe.draw(batch);
            }
        }
    }

    /**
     * render a Map
     *
     * @param batch the batch to render it through
     */
    public void renderMap(Batch batch) {
        for (int height = 0; height < map.getMapHeight(); height++) {
            for (int width = 0; width < map.getMapWidth(); width++) {
                batch.draw(Constants.getManager().get(Constants.getResourceRoot() + map.getMapData()[height][width].getTexName(), Texture.class), (width * Constants.getTileSize()) + map.getShiftX(), (height * Constants.getTileSize()) + map.getShiftY(), Constants.getTileSize(), Constants.getTileSize(), 0, 0, Constants.getTileSize(), Constants.getTileSize(), false, false);
            }
        }
    }

    /**
     * render a highlight map
     *
     * @param batch the batch to render it through
     */
    public void renderHighLightMap(Batch batch) {
        for (int height = 0; height < highlightMap.getMapHeight(); height++) {
            for (int width = 0; width < highlightMap.getMapWidth(); width++) {
                batch.draw(Constants.getManager().get(Constants.getResourceRoot() + highlightMap.getMapData()[height][width].getTexName(), Texture.class), (width * Constants.getTileSize()) + highlightMap.getShiftX(), (height * Constants.getTileSize()) + highlightMap.getShiftY(), Constants.getTileSize(), Constants.getTileSize(), 0, 0, Constants.getTileSize(), Constants.getTileSize(), false, false);
            }
        }
    }

    /**
     * render any UI components
     *
     * @param batch the batch to render it through
     */
    public void renderUI(Batch batch) {
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "borderArt.png", Texture.class), -Constants.getActualScreenWidth() / 2, -Constants.getActualScreenHeight() / 2, Constants.getActualScreenWidth(), Constants.getActualScreenHeight(), 0, 0, 1280, 720, false, false);

    }

    /**
     * render a tool tip object on a given Batch
     *
     * @param data  the tooltip data
     * @param batch the batch to render it through
     */
    public void renderTooltip(Tooltip data, Batch batch, BitmapFont font) {


        int baseIconSize = data.getIconSize();
        font.getData().setScale(1);
        int textSize = data.getFontSpacing();

        //int baseWidth = (int)(data.getValues().size() * (baseIconSize + textSize) / ((float) Constants.getResolutionWidth() / 1280f));
        int xPos = data.getX();
        int yPos = data.getY();

        int baseWidth = xPos*-2;
        int baseHeight = yPos;



        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "HighlightTexture/blank.png", Texture.class), xPos , yPos, baseWidth, baseHeight, 0, 0, 64, 64, false, false);
        font.draw(batch, data.getName(), -920, -450);

        xPos += baseIconSize / 2;
        yPos += baseIconSize / 2;
        ArrayList<String> keys = new ArrayList<String>(data.getValues().keySet());

        for (int keyIndex = 0; keyIndex < keys.size(); keyIndex++) {
            batch.draw(Constants.getManager().get(Constants.getResourceRoot() + keys.get(keyIndex), Texture.class), xPos + (keyIndex * (baseIconSize + textSize)), yPos, baseIconSize, baseIconSize, 0, 0, 64, 64, false, false);
            font.draw(batch, " : " + (data.getValues().get(keys.get(keyIndex))).toString(), xPos + (keyIndex * (baseIconSize + textSize)) + baseIconSize, yPos + baseIconSize);

        }

        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "controlInfo.png", Texture.class), 700, -400, 272, 720, 0, 0, 272, 720, false, false);
    }

    /**
     * Render bullets
     * @param batch
     */
    public void renderBullets(Batch batch){
        for (Bullet bullet : GameScene.bullets) {
            bullet.renderBullets(batch);
        }
    }
}
