package com.kroy.game.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kroy.game.*;
import com.kroy.game.Character;

import javax.swing.text.Highlighter;
import java.util.ArrayList;

public class GameScene extends Scene
{
    private Tooltip humanToolTip;
    private Tooltip enemyToolTip;

    private Map map;
    private HighlightMap highlightMap;
    private SceneManager sceneManager;
    private Tile selectedTile;

    private Human humanData;
    private Enemy enemyData;
    private BarManager barManager;

    private Integer turnCounter = 0;

    public GameScene(BitmapFont font, OrthographicCamera cam, SceneManager sceneManager)
    {
        super(font, cam);
        this.sceneManager = sceneManager;
    }

    @Override
    /**
     * Initialise data needed for game screen
     *
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void initScene() {
        sceneType = SceneType.SCENE_TYPE_GAME;

        humanData = new Human( true, Constants.getFireengineCount());
        enemyData = new Enemy( false, Constants.getFortressCount(), Constants.getPatrolCount());

        map = new Map(Constants.getResourceRoot() + Constants.getMapFileName());
        map.setShiftX(-1024);
        map.setShiftY(-1728);

        highlightMap = new HighlightMap(map.getMapWidth(), map.getMapHeight());
        selectedTile = null;

        barManager = new BarManager(humanData.getTeam(), map);

        humanToolTip = new Tooltip("", -900, 400, 75, 200);
        humanToolTip.addValue("Icons/healthIcon.png", 0);
        humanToolTip.addValue("Icons/damageIcon.png", 0);
        humanToolTip.addValue("Icons/rangeIcon.png", 0);
        humanToolTip.addValue("Icons/speedIcon.png", 0);
        humanToolTip.addValue("Icons/waterIcon.png", 0);
        //humanToolTip.setX((int) (-((humanToolTip.getIconSize() + humanToolTip.getFontSpacing()) * humanToolTip.getValues().size()) / 2f));
        //humanToolTip.setY(100*(int)(Constants.getResolutionWidth()/1280f));
        //humanToolTip.setY(Constants.getResolutionHeight() / 2 - (int) (200 * (Constants.getResolutionWidth() / 1280f - 1)) * 2);


        enemyToolTip = new Tooltip("", -900, 400, 75, 200);
        enemyToolTip.addValue("Icons/healthIcon.png", 0);
        enemyToolTip.addValue("Icons/damageIcon.png", 0);
        enemyToolTip.addValue("Icons/rangeIcon.png", 0);
        //enemyToolTip.setX((int) (-((enemyToolTip.getIconSize() + enemyToolTip.getFontSpacing()) * enemyToolTip.getValues().size()) / 2f));
        //enemyToolTip.setY(Constants.getResolutionHeight() / 2 - (int) (200 * (Constants.getResolutionWidth() / 1280f - 1)) * 2);


        humanData.distributeTeamLocation(map.getNClosest(Constants.getFireengineCount(), map.getStationPosition(), TileType.TILE_TYPES_ROAD));
        enemyData.distributePatrols(map.getNClosest(Constants.getPatrolCount(), map.getFortressTiles()[0], TileType.TILE_TYPES_ROAD));
        enemyData.distributeTeamLocation(map.getFortressTiles());

        font.setColor(1f, 1f, 1f, 1f);

        cam.viewportWidth = map.getMapWidth() * Constants.getTileSize();
        cam.viewportHeight = map.getMapHeight() * Constants.getTileSize();
        cam.zoom = 0.5f;
    }

    @Override
    /**
     * Calculates actions during game scene
     *
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void resolveScene() {
        //in gameplay actions
        map.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        map.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));
        barManager.setShiftX(map.getShiftX());
        barManager.setShiftY(map.getShiftY());

        highlightMap.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        highlightMap.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));

        if (isEnemyWon()) {
            sceneManager.changeScene(new EnemyWinScene(font, cam));
        }

        if (isHumanWon()) {
            sceneManager.changeScene(new HumanWinScene(font, cam));
        }

        //enemy turn
        if (enemyData.isMyTurn()) {
            //check for deaths

            enemyData.decideTarget(enemyData.calculateTargets(map));

            enemyData.setMyTurn(false);
            humanData.setMyTurn(true);
            turnCounter++;

            //Station heals and repairs its surroundings
            ((Station) map.getStationPosition()).refillTiles(map.getWithRangeOfHub(map.getStationPosition(), Constants.getStationRange()));
            ((Station) map.getStationPosition()).repairTiles(map.getWithRangeOfHub(map.getStationPosition(), Constants.getStationRange()));

            if (turnCounter % 15 == 0) { //Added by Septagon
                enemyData.improveFortresses();
            }
        }
    }

    @Override
    /**
     * render the game screen
     *
     * @param batch     the batch to render the scene through
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void renderScene(Batch batch) {
        //renders the game screen
        batch.begin();
        renderMap(batch);
        renderEnemies(batch, enemyData);
        renderFireEngines(batch, humanData);
        if (highlightMap.isRender()) {
            renderHighLightMap(batch);
        }

        if (humanToolTip.isRender()) {
            renderTooltip(humanToolTip, batch);
        }
        if (enemyToolTip.isRender()) {
            renderTooltip(enemyToolTip, batch);
        }

        renderUI(batch);
        batch.end();
        barManager.renderBars(cam);
    }

    /**
     * render the fortresses
     *
     * @param batch     the batch to render it through
     * @param enemyData the data of the enemy player
     */
    public void renderEnemies(Batch batch, Enemy enemyData) {
        Character[] enemyCharacters = enemyData.getTeam();

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
        Character[] humanCharacters = humanData.getTeam();
        for (Character fe : humanCharacters) {
            if (!(fe == null)) {
                map.placeOnMap(fe);
                fe.draw(batch);
            }
        }
    }

    /**
     * render any UI components
     *
     * @param batch the batch to render it through
     */
    public void renderUI(Batch batch) {
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "borderArt.png", Texture.class), -1024, -576, 2048, 1152, 0, 0, 1280, 720, false, false);

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


    // ------------ Scene Utility ------------------------

    /**
     * test if the enemy has won in this scene
     *
     * @return the win state of the enemy
     */
    public boolean isEnemyWon() {
        humanData.resolveDeaths();
        return humanData.getAliveCharacters() == 0;

    }

    /**
     * test if the human has won in this scene
     *
     * @return the win state of the human
     */
    public boolean isHumanWon() {
        enemyData.resolveDeaths();
        return enemyData.getAliveCharacters() == 0;

    }


    /**
     * render a tool tip object on a given Batch
     *
     * @param data  the tooltip data
     * @param batch the batch to render it through
     */
    public void renderTooltip(Tooltip data, Batch batch) {


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

    public HighlightMap getHighlightMap()
    {
        return highlightMap;
    }

    public Map getMap()
    {
        return map;
    }

    public Tooltip getHumanToolTip(){
        return humanToolTip;
    }

    public Tooltip getEnemyToolTip(){
        return enemyToolTip;
    }

    public Tile getSelectedTile()
    {
        return selectedTile;
    }

    public Human getHumanData()
    {
        return humanData;
    }

    public Enemy getEnemyData()
    {
        return enemyData;
    }

    public void setSelectedTile(Tile selectedTile)
    {
        this.selectedTile = selectedTile;
    }
}
