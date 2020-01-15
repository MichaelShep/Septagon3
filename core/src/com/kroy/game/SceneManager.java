package com.kroy.game;

// ---------- clean up ----------

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;


public class SceneManager {

    SceneType scene;
    OrthographicCamera cam;
    BitmapFont font;

    Map map;
    HighlightMap highlightMap;


    Tile selectedTile;

    //assets
    Sprite titleSprite;
    Tooltip humanToolTip;
    Tooltip enemyToolTip;

    /**
     * Constructs a Class to manage and render scenes
     *
     * @param fontRef the font you want to use in the game
     */
    SceneManager(BitmapFont fontRef) {
        scene = SceneType.SCENE_TYPE_MAINMENU;

        font = fontRef;

        humanToolTip = null;
        enemyToolTip = null;

        //define camera
        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        cam.zoom = 0.5f;
        cam.update();

        selectedTile = null;
        titleSprite = null;

        map = null;
        highlightMap = null;


    }


    // ------------ MAIN MENU ------------------------

    /**
     * Initialise the data needed for the main menu
     */
    public void initMainMenuScreen() {
        scene = SceneType.SCENE_TYPE_MAINMENU;
        titleSprite = new Sprite(Constants.getManager().get(Constants.getResourceRoot() + "title.png", Texture.class), 0, 0, 621, 168);
        titleSprite.setScale(2 * Constants.getResolutionWidth() / 1280f);
        titleSprite.setCenterX(0);
        titleSprite.setCenterY(Constants.getResolutionHeight() / 4);


        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();
        cam.zoom = 2f;


    }

    /**
     * Calculates actions during main menu scene
     *
     * @param runTime the change in time between last resolve
     */
    public void resolveMainMenuScreen(float runTime) {
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
    public void renderMainMenuScreen(Batch batch) {
        batch.begin();

        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);
        titleSprite.draw(batch);
        font.draw(batch, "Press -SPACE- To Start", -(Constants.getResolutionWidth() / 2.8f), 0);
        font.draw(batch, "Press -ESC- To Exit", -(Constants.getResolutionWidth() / 3.3f), -150);


        batch.end();
    }

    // ------------ GAME SCREEN ------------------------

    /**
     * Initialise data needed for game screen
     *
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void initGameScreen(Human humanData, Enemy enemyData) {
        scene = SceneType.SCENE_TYPE_GAME;


        map = new Map(Constants.getResourceRoot() + Constants.getMapFileName());
        map.setShiftX(-1024);
        map.setShiftY(-1728);

        highlightMap = new HighlightMap(map.getMapWidth(), map.getMapHeight());
        selectedTile = null;


        humanToolTip = new Tooltip("", 0, 0, (int) (Constants.getTileSize() * (Constants.getResolutionWidth() / 1280f)), 312 * (int) (Constants.getResolutionWidth() / 1280f));
        humanToolTip.addValue("Icons/healthIcon.png", 0);
        humanToolTip.addValue("Icons/damageIcon.png", 0);
        humanToolTip.addValue("Icons/rangeIcon.png", 0);
        humanToolTip.addValue("Icons/speedIcon.png", 0);
        humanToolTip.addValue("Icons/waterIcon.png", 0);
        humanToolTip.setX((int) (-((humanToolTip.getIconSize() + humanToolTip.getFontSpacing()) * humanToolTip.getValues().size()) / 2f));
        //humanToolTip.setY(100*(int)(Constants.getResolutionWidth()/1280f));
        humanToolTip.setY(Constants.getResolutionHeight() / 2 - (int) (200 * (Constants.getResolutionWidth() / 1280f - 1)) * 2);


        enemyToolTip = new Tooltip("", 0, 0, (int) (Constants.getTileSize() * (Constants.getResolutionWidth() / 1280f)), 312 * (int) (Constants.getResolutionWidth() / 1280f));
        enemyToolTip.addValue("Icons/healthIcon.png", 0);
        enemyToolTip.addValue("Icons/damageIcon.png", 0);
        enemyToolTip.addValue("Icons/rangeIcon.png", 0);
        enemyToolTip.setX((int) (-((enemyToolTip.getIconSize() + enemyToolTip.getFontSpacing()) * enemyToolTip.getValues().size()) / 2f));
        enemyToolTip.setY(Constants.getResolutionHeight() / 2 - (int) (200 * (Constants.getResolutionWidth() / 1280f - 1)) * 2);


        humanData.distributeTeamLocation(map.getNClosest(Constants.getFireengineCount(), map.getStationPosition(), TileType.TILE_TYPES_ROAD));
        enemyData.distributeTeamLocation(map.getFortressTiles());

        font.setColor(1f, 1f, 1f, 1f);

        cam.viewportWidth = map.getMapWidth() * Constants.getTileSize();
        cam.viewportHeight = map.getMapHeight() * Constants.getTileSize();
        cam.zoom = 0.5f;

    }

    /**
     * Calculates actions during game scene
     *
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void resolveGameScreen(Human humanData, Enemy enemyData) {
        //in gameplay actions
        map.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        map.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));

        highlightMap.setShiftX(map.getShiftX() - (map.getShiftX() % Constants.getTileSize()));
        highlightMap.setShiftY(map.getShiftY() - (map.getShiftY() % Constants.getTileSize()));

        if (isEnemyWon(humanData)) {
            initEnemyWinScreen();
        }

        if (isHumanWon(enemyData)) {
            initHumanWinScreen();
        }

        //enemy turn
        if (enemyData.isMyTurn()) {
            //check for deaths

            enemyData.decideTarget(enemyData.calculateTargets(map));

            enemyData.setMyTurn(false);
            humanData.setMyTurn(true);

            System.out.println("Enemy took their turn!");

            //Station heals and repairs its surroundings
            ((Station) map.getStationPosition()).refillTiles(map.getWithRangeOfHub(map.getStationPosition(), Constants.getStationRange()));
            ((Station) map.getStationPosition()).repairTiles(map.getWithRangeOfHub(map.getStationPosition(), Constants.getStationRange()));

        }
    }

    /**
     * render the game screen
     *
     * @param batch     the batch to render the scene through
     * @param humanData the data of the human player
     * @param enemyData the data of the enemy player
     */
    public void renderGameScreen(Batch batch, Human humanData, Enemy enemyData) {
        //renders the game screen
        batch.begin();
        renderMap(batch);
        renderFortresses(batch, enemyData);
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
    }


    /**
     * Initialise the human win screen
     */
    public void initHumanWinScreen() {
        //set humanWinScreen Components;
        scene = SceneType.SCENE_TYPE_HUMANWIN;
        font.setColor(1f, 1f, 1f, 1f);
        cam.zoom = 2f;
        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();


    }

    /**
     * Calculates actions during human win scene
     */
    public void resolveHumanWinScreen() {
        //resolve actions during this scene
    }

    /**
     * Render human win screen
     *
     * @param batch the batch to render the scene through
     */
    public void renderHumanWinScreen(Batch batch) {
        batch.begin();
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);

        font.draw(batch, "YOU WIN", -(Constants.getResolutionWidth() / 6f), 100);
        font.draw(batch, "THE KROY ARE NO MORE!", -(Constants.getResolutionWidth() / 2.8f), 0);
        font.draw(batch, "Press -SPACE- To RETURN", -(Constants.getResolutionWidth() / 2.75f), -100);
        batch.end();

    }


    // ------------ LOSE SCREEN ------------------------

    /**
     * Initialise the enemy win screen
     */
    public void initEnemyWinScreen() {
        //set humanWinScreen Components;
        scene = SceneType.SCENE_TYPE_ENEMYWIN;
        font.setColor(1f, 1f, 1f, 1f);
        cam.zoom = 2f;
        cam.viewportWidth = Constants.getResolutionWidth();
        cam.viewportHeight = Constants.getResolutionHeight();


    }

    /**
     * Calculates actions during enemy win scene
     */
    public void resolveEnemyWinScreen() {
        //resolve actions during this scene
    }

    /**
     * render the enemy the win screen
     *
     * @param batch the batch to render the scene through
     */
    public void renderEnemyWinScreen(Batch batch) {
        batch.begin();
        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "menuBackground.jpeg", Texture.class), -Constants.getResolutionWidth(), -Constants.getResolutionHeight(), Constants.getResolutionWidth() * 2, Constants.getResolutionHeight() * 2, 0, 0, 1880, 1058, false, false);

        font.draw(batch, "YOU LOSE", -(Constants.getResolutionWidth() / 6f), 100);
        font.draw(batch, "OUR HEROES HAVE FALLEN!", -(Constants.getResolutionWidth() / 2.65f), 0);
        font.draw(batch, "Press -SPACE- To RETURN", -(Constants.getResolutionWidth() / 2.9f), -100);
        batch.end();

    }


    // ------------ COMPONENTS ------------------------

    /**
     * render a tool tip
     *
     * @param data  the tooltip data
     * @param batch the batch to render it through
     */
    public void renderTooltip(Tooltip data, Batch batch) {
        int baseIconSize = data.getIconSize();
        int textSize = data.getFontSpacing();

        //int baseWidth = (int)(data.getValues().size() * (baseIconSize + textSize) / ((float) Constants.getResolutionWidth() / 1280f));
        int baseWidth = data.getValues().size() * (baseIconSize + textSize);
        int baseHeight = (int) (200 * (Constants.getResolutionWidth() / 1280f));

        int xPos = data.getX();
        int yPos = data.getY();


        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "HighlightTexture/blank.png", Texture.class), xPos, yPos, baseWidth, baseHeight, 0, 0, 64, 64, false, false);
        font.draw(batch, data.getName(), -(float) Constants.getResolutionWidth() / 2f, -Constants.getResolutionHeight() / 2.5f);

        xPos += data.getIconSize() / 2;

        ArrayList<String> keys = new ArrayList<String>(data.getValues().keySet());

        for (int keyIndex = 0; keyIndex < keys.size(); keyIndex++) {
            batch.draw(Constants.getManager().get(Constants.getResourceRoot() + keys.get(keyIndex), Texture.class), xPos + (keyIndex * (baseIconSize + textSize)), yPos + 100 * (Constants.getResolutionWidth() / 1280), baseIconSize, baseIconSize, 0, 0, 64, 64, false, false);
            font.draw(batch, " : " + (data.getValues().get(keys.get(keyIndex))).toString(), xPos + (keyIndex * (baseIconSize + textSize)) + baseIconSize, yPos + baseIconSize + 100 * (Constants.getResolutionWidth() / 1280));

        }

        batch.draw(Constants.getManager().get(Constants.getResourceRoot() + "controlInfo.png", Texture.class), Constants.getResolutionWidth() / 3f, -Constants.getResolutionHeight() / 2f, 272, 720, 0, 0, 272, 720, false, false);

    }

    /**
     * render the fortresses
     *
     * @param batch     the batch to render it through
     * @param enemyData the data of the enemy player
     */
    public void renderFortresses(Batch batch, Enemy enemyData) {
        Character[] enemyCharacters = enemyData.getTeam();

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
     * @param humanData the data of the human player
     * @return the win state of the enemy
     */
    public boolean isEnemyWon(Human humanData) {
        humanData.resolveDeaths();
        return humanData.getAliveCharacters() == 0;

    }

    /**
     * test if the human has won in this scene
     *
     * @param enemyData the data of the enemy player
     * @return the win state of the human
     */
    public boolean isHumanWon(Enemy enemyData) {
        enemyData.resolveDeaths();
        return enemyData.getAliveCharacters() == 0;

    }

    /**
     * any operations the camera needs to do every frame
     *
     * @param batch the batch the camera is rendering
     */
    public void cameraFrameOperation(Batch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }


    public OrthographicCamera getCam() {
        return cam;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SceneType getScene() {
        return scene;
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public HighlightMap getHighlightMap() {
        return highlightMap;
    }

    public Map getMap() {
        return map;
    }

    public Tooltip getEnemyToolTip() {
        return enemyToolTip;
    }

    public Tooltip getHumanToolTip() {
        return humanToolTip;
    }
}
