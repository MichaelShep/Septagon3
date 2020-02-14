package com.kroy.game.input;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.kroy.game.*;
import com.kroy.game.Character;
import com.kroy.game.scenes.GameScene;
import com.kroy.game.scenes.MinigameScene;

import java.util.ArrayList;
import java.util.Random;

/**
 * Code refactored into this class by Septagon
 */

public class InputManager extends ApplicationAdapter
{
    //member variables that are used to change states
    private SceneManager sceneHelper;
    private BitmapFont font;
    private OrthographicCamera cam;

    /***
     * Constructor to pass values to the InputManager
     * @param sceneHelper SceneManager to swap the current state of the game
     * @param font The games font
     * @param cam The games camera
     */
    public InputManager(SceneManager sceneHelper, BitmapFont font, OrthographicCamera cam){
        this.sceneHelper = sceneHelper;
        this.font = font;
        this.cam = cam;
    }

    /**
     * Handles all key and mouse input for each scene
     */
    public void handleInput() {
        if (sceneHelper.getScene() == SceneType.SCENE_TYPE_MAINMENU) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                sceneHelper.changeScene(new GameScene(font, cam, sceneHelper));
            }

        } else if (sceneHelper.getScene() == SceneType.SCENE_TYPE_GAME) {

            int moveSpeed = Constants.getTileSize();


            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                sceneHelper.getMap().setShiftX(sceneHelper.getMap().getShiftX() + moveSpeed);
                sceneHelper.getHighlightMap().setShiftX(sceneHelper.getHighlightMap().getShiftX() + moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                sceneHelper.getMap().setShiftX(sceneHelper.getMap().getShiftX() - moveSpeed);
                sceneHelper.getHighlightMap().setShiftX(sceneHelper.getHighlightMap().getShiftX() - moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                sceneHelper.getMap().setShiftY(sceneHelper.getMap().getShiftY() + moveSpeed);
                sceneHelper.getHighlightMap().setShiftY(sceneHelper.getHighlightMap().getShiftY() + moveSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                sceneHelper.getMap().setShiftY(sceneHelper.getMap().getShiftY() - moveSpeed);
                sceneHelper.getHighlightMap().setShiftY(sceneHelper.getHighlightMap().getShiftY() - moveSpeed);
            }


            sceneHelper.getMap().setShiftX(MathUtils.clamp(sceneHelper.getMap().getShiftX(), -3072, -1024));
            sceneHelper.getMap().setShiftY(MathUtils.clamp(sceneHelper.getMap().getShiftY(), -1728, -576));

            sceneHelper.getHighlightMap().setShiftX(MathUtils.clamp(sceneHelper.getHighlightMap().getShiftX(), -3072, -1024));
            sceneHelper.getHighlightMap().setShiftY(MathUtils.clamp(sceneHelper.getHighlightMap().getShiftY(), -1728, -576));


            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {


                double remapedShiftX = Remap(sceneHelper.getMap().getShiftX(), -3072, -1024, 0, sceneHelper.getMap().getMapWidth() * Constants.getTileSize() * sceneHelper.getCam().zoom);
                double remapedShiftY = Remap(sceneHelper.getMap().getShiftY(), -1728, -576, 0, sceneHelper.getMap().getMapHeight() * Constants.getTileSize() * sceneHelper.getCam().zoom);

                int tileX = (int) Math.floor(((Gdx.input.getX()) / (float) Constants.getResolutionWidth()) * sceneHelper.getMap().getMapWidth() * sceneHelper.getCam().zoom) + (int) (sceneHelper.getMap().getMapWidth() * sceneHelper.getCam().zoom - (remapedShiftX / Constants.getTileSize()));
                int tileY = sceneHelper.getMap().getMapHeight() - ((int) Math.floor(((Gdx.input.getY()) / (float) Constants.getResolutionHeight()) * sceneHelper.getMap().getMapHeight() * sceneHelper.getCam().zoom) + (int) ((remapedShiftY / Constants.getTileSize()))) - 1;

                tileClicked(tileX, tileY);

            }

            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {


                double remapedShiftX = Remap(sceneHelper.getMap().getShiftX(), -3072, -1024, 0, sceneHelper.getMap().getMapWidth() * Constants.getTileSize() * sceneHelper.getCam().zoom);
                double remapedShiftY = Remap(sceneHelper.getMap().getShiftY(), -1728, -576, 0, sceneHelper.getMap().getMapHeight() * Constants.getTileSize() * sceneHelper.getCam().zoom);


                System.out.println("RIGHT MOUSE PRESSED!");

                int tileX = (int) Math.floor(((Gdx.input.getX()) / (float) Constants.getResolutionWidth()) * sceneHelper.getMap().getMapWidth() * sceneHelper.getCam().zoom) + (int) (sceneHelper.getMap().getMapWidth() * sceneHelper.getCam().zoom - (remapedShiftX / Constants.getTileSize()));
                int tileY = sceneHelper.getMap().getMapHeight() - ((int) Math.floor(((Gdx.input.getY()) / (float) Constants.getResolutionHeight()) * sceneHelper.getMap().getMapHeight() * sceneHelper.getCam().zoom) + (int) ((remapedShiftY / Constants.getTileSize()))) - 1;

                System.out.println("[" + Gdx.input.getX() + "]X tile: " + tileX + " [" + Gdx.input.getY() + "] Y tile: " + tileY);
                System.out.println("X shift: " + remapedShiftX + " Y shift: " + remapedShiftY);


                tileRightClicked(tileX, tileY);

            }

            if (!Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                sceneHelper.getHumanToolTip().setRender(false);
                sceneHelper.getEnemyToolTip().setRender(false);
            }


        } else if (sceneHelper.getScene() == SceneType.SCENE_TYPE_HUMANWIN || sceneHelper.getScene() == SceneType.SCENE_TYPE_ENEMYWIN) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                sceneHelper.initCurrentScene();
            }

        }
        //Minigame input handling - [ID: MI1]
        else if(sceneHelper.getScene() == SceneType.SCENE_TYPE_MINIGAME)
        {
            MinigameScene currentScene = (MinigameScene) sceneHelper.getCurrentScene();

            //Move the engine left and right
            if(Gdx.input.isKeyPressed(Input.Keys.A) && currentScene.isStarted() && !currentScene.isFinished()){
                currentScene.moveEngine(-Constants.getMinigameBasePlayerMovementSpeed());
            }

            if(Gdx.input.isKeyPressed(Input.Keys.D) && currentScene.isStarted() && !currentScene.isFinished()){
                currentScene.moveEngine(Constants.getMinigameBasePlayerMovementSpeed());
            }

            //Firing bullets from the engine
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                currentScene.getMinigameEngine().fireBullet();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            System.exit(0);
        }


    }

    /**
     * Separately handles events when a tile is left clicked in gameScreen
     *
     * @param x the x value of the mouse
     * @param y the y value of the mouse
     */
    private void tileClicked(int x, int y) {
        //clicking tiles can only be done on your turn
        Tile queryTile = sceneHelper.getMap().getMapData()[y][x];

        if (sceneHelper.getHumanData().isMyTurn()) {
            if (sceneHelper.getSelectedTile() == null) {
                if (queryTile.getInhabitant() instanceof FireEngine) {
                    FireEngine currentEngine = (FireEngine) queryTile.getInhabitant();
                    if(!currentEngine.isDisabled()) {
                        sceneHelper.setSelectedTile(queryTile);
                        sceneHelper.getHighlightMap().getMapData()[y][x].setTexName("HighlightTexture/selected.png");

                        //place green moves
                        ArrayList<Tile> moveSpaces = sceneHelper.getMap().getWithRangeOfHub(queryTile, ((FireEngine) queryTile.getInhabitant()).getSpeed(), TileType.TILE_TYPES_ROAD);
                        for (Tile tile : moveSpaces) {
                            if (tile.getInhabitant() == null) {
                                sceneHelper.getHighlightMap().setTile(tile.getMapX(), tile.getMapY(), TileType.TILE_TYPES_ROAD);

                            }
                        }

                        ArrayList<Tile> attackSpaces = sceneHelper.getMap().getWithRangeOfHub(queryTile, queryTile.getInhabitant().getRange());
                        for (Tile tile : attackSpaces) {
                            if (tile.getInhabitant() instanceof Fortress) {
                                sceneHelper.getHighlightMap().setTile(tile.getMapX(), tile.getMapY(), TileType.TILE_TYPES_FORTRESS);
                            }
                        }


                        sceneHelper.getHighlightMap().setRender(true);
                        sceneHelper.getHighlightMap().removeUnreachable();
                    }
                }

            }

            //a tile is already selected
            else {
                System.out.println("---- " + sceneHelper.getHighlightMap().getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName());
                switch (sceneHelper.getHighlightMap().getMapData()[queryTile.getMapY()][queryTile.getMapX()].getTexName()) {
                    case "HighlightTexture/move.png":
                        System.out.println("MOVE");
                        ((FireEngine)sceneHelper.getSelectedTile().getInhabitant()).transferTo(queryTile);
                        //Move all patrols randomly (Added by Septagon)
                        for (Patrol patrols : sceneHelper.getEnemyData().getPatrols()){
                            patrols.move(sceneHelper);
                        }

                        sceneHelper.setSelectedTile(null);
                        sceneHelper.getHighlightMap().resetMap();
                        sceneHelper.getHighlightMap().setRender(false);
                        sceneHelper.getHumanData().setMyTurn(false);
                        sceneHelper.getEnemyData().setMyTurn(true);
                        break;
                    case "HighlightTexture/attack.png":
                        System.out.println("ATTACK");
                        if (((FireEngine) sceneHelper.getSelectedTile().getInhabitant()).getWaterAmount() > 0) {

                            //shoot bullets animation
                            GameScene.bullets.add(new Bullet(sceneHelper.getSelectedTile().getInhabitant(), queryTile.getInhabitant(), true));
                            GameScene.bullets.add(new Bullet(queryTile.getInhabitant(), sceneHelper.getSelectedTile().getInhabitant(), false));

                            sceneHelper.getSelectedTile().getInhabitant().shootTarget(queryTile.getInhabitant());
                            sceneHelper.setSelectedTile(null);
                            sceneHelper.getHighlightMap().resetMap();
                            sceneHelper.getHighlightMap().setRender(false);
                            sceneHelper.getHumanData().setMyTurn(false);
                            sceneHelper.getEnemyData().setMyTurn(true);
                        }


                        break;
                    case "HighlightTexture/selected.png":
                        sceneHelper.setSelectedTile(null);
                        sceneHelper.getHighlightMap().resetMap();
                        sceneHelper.getHighlightMap().setRender(false);
                        break;
                }
            }
        }
    }

    /**
     * Separately handles events when a tile is right clicked in gameScreen
     *
     * @param x the x value of the mouse
     * @param y the y value of the mouse
     */
    private void tileRightClicked(int x, int y) {
        Tile queryTile = sceneHelper.getMap().getMapData()[y][x];
        Character queryInhabitant = queryTile.getInhabitant();

        if (!(queryInhabitant == null)) {
            if (queryInhabitant instanceof FireEngine) {
                sceneHelper.getHumanToolTip().updateValue("Icons/healthIcon.png", queryInhabitant.getHealth() + "/" + queryInhabitant.getMaxHealth());
                sceneHelper.getHumanToolTip().updateValue("Icons/damageIcon.png", queryInhabitant.getDamage());
                sceneHelper.getHumanToolTip().updateValue("Icons/rangeIcon.png", queryInhabitant.getRange());
                sceneHelper.getHumanToolTip().updateValue("Icons/speedIcon.png", ((FireEngine) queryInhabitant).getSpeed());
                sceneHelper.getHumanToolTip().updateValue("Icons/waterIcon.png", ((FireEngine) queryInhabitant).getWaterAmount() + "/" + ((FireEngine) queryInhabitant).getWaterCapacity());
                sceneHelper.getHumanToolTip().setRender(true);
            } else if (queryInhabitant instanceof Fortress) {
                sceneHelper.getEnemyToolTip().setName(((Fortress) queryInhabitant).getName());
                sceneHelper.getEnemyToolTip().updateValue("Icons/healthIcon.png", queryInhabitant.getHealth());
                sceneHelper.getEnemyToolTip().updateValue("Icons/damageIcon.png", queryInhabitant.getDamage());
                sceneHelper.getEnemyToolTip().updateValue("Icons/rangeIcon.png", queryInhabitant.getRange());
                sceneHelper.getEnemyToolTip().setRender(true);
            }

        }
    }

    /**
     * Remap a value from one range to another range
     *
     * @param value the value to scale
     * @param from1 range1 start
     * @param to1   range1 end
     * @param from2 range2 start
     * @param to2   range2 end
     * @return returns scaled value
     */
    private static float Remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }
}

