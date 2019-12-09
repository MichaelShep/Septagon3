package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.tools.javac.util.SharedNameTable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.util.concurrent.CopyOnWriteArrayList;


public class MainClass extends ApplicationAdapter {


	Map mapData;
	SpriteBatch batch;
	OrthographicCamera cam;
	Human humanData;
	Enemy enemyData;





	@Override
	public void create () {

		initSetting();
		loadTextures("");

		while(!Constants.getManager().update())
		{
			//System.out.println("--LOADING-- " + Constants.getManager().getProgress() + " --LOADING--");
		}
		System.out.println("Finished Loading!");


		mapData = new Map(Constants.getMapFileName());
		humanData = new Human("humanName",true, Constants.getFireengineCount());
		enemyData = new Enemy("EnemyName",false, Constants.getFortressCount());

		humanData.distributeTeamLocation(mapData.getNClosest(Constants.getFireengineCount(),mapData.getStationPosition(),TileType.TILE_TYPES_ROAD));
		enemyData.distributeTeamLocation(mapData.getFortressTiles());

		for(Character fe: humanData.getTeam())
		{
			mapData.placeOnMap(fe);
		}

		for(Character fort: enemyData.getTeam())
		{
			mapData.placeOnMap(fort);
		}

		batch = new SpriteBatch();



		cam = new OrthographicCamera();
		cam.position.set(0,0,0);
		cam.zoom = 0.5f;
		cam.update();

	}

	@Override
	public void render () {
		if (Constants.getManager().update()){
			Gdx.gl.glClearColor(0, 0, 0, 1);

			handleInput();
			cam.update();
			batch.setProjectionMatrix(cam.combined);


			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//render loop
			batch.begin();
			renderMap(batch);
			renderFortresses();
			renderFireEngines();
			//renderUI();
            batch.end();

		}
		else {
		    // do something while waiting for assets to load

		}


	}



	@Override
	public void dispose () {
		batch.dispose();
	}

	public void loadTextures(String root){

		File dir = new File(Constants.getResourceRoot()+ root);
		System.out.println("Searching in: " + root);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.isDirectory())
				{
					loadTextures( child.getName()+"/");
				}
				else {
				    if (child.getName().contains("Thumbs.db"))
                    {
                        System.out.println("Thumbs was found.");
                    }
				    else
                    {
                        System.out.println("Asset Found: " + root+child.getName());
                        Constants.getManager().load(root+child.getName(), Texture.class);
                    }
				}
			}
		}
		else
		{
			System.out.println(root + " cannot be Found");
		}




	}

	public void initSetting(){
		Gdx.graphics.setWindowedMode(Constants.getResolutionWidth(),Constants.getResolutionHeight());
	}




	public void renderMap(SpriteBatch batch)
	{
		for(int height = 0; height < mapData.getMapHeight(); height++)
		{
			for(int width = 0; width < mapData.getMapWidth(); width++)
			{
				batch.draw(Constants.getManager().get(mapData.getMapData()[height][width].getTexName(), Texture.class),(width * Constants.getTileSize()) + mapData.getShiftX(),(height*Constants.getTileSize())+ mapData.getShiftY(), Constants.getTileSize(),Constants.getTileSize(),0,0,Constants.getTileSize(),Constants.getTileSize(),false,false);
			}
		}
	}

	public void renderFireEngines()
	{
		Character[] humanCharacters = humanData.getTeam();
		for(Character fe: humanCharacters)
		{
			mapData.placeOnMap(fe);
		}

		for (int feIndex = 0; feIndex < humanCharacters.length; feIndex++)
		{
			humanCharacters[feIndex].draw(batch);
		}
	}

	public void renderUI()
	{
		batch.draw(Constants.getManager().get("borderArt.png", Texture.class), 0, 0, Constants.getResolutionWidth(), Constants.getResolutionHeight(),0,0,1280,720,false,false);
	}

	public void renderFortresses()
	{
		Character[] enemyCharacters = enemyData.getTeam();

		for(Character fort: enemyCharacters)
		{
			mapData.placeOnMap(fort);
		}

		for (int fortIndex = 0; fortIndex < enemyCharacters.length; fortIndex++)
		{
			enemyCharacters[fortIndex].draw(batch);
		}
	}


	private void handleInput() {
		int moveSpeed = 32;

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			//cam.translate(-moveSpeed, 0, 0);
			mapData.setShiftX(mapData.getShiftX() + moveSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			//cam.translate(moveSpeed, 0, 0);
			mapData.setShiftX(mapData.getShiftX() - moveSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			//cam.translate(0, -moveSpeed, 0);
			mapData.setShiftY(mapData.getShiftY() + moveSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			//cam.translate(0, moveSpeed, 0);
			mapData.setShiftY(mapData.getShiftY() - moveSpeed);
		}

		//fix later

		//cam.position.x = MathUtils.clamp(cam.position.x, -704, 1344);
		//cam.position.y = MathUtils.clamp(cam.position.y, -256, 970);

		mapData.setShiftX(MathUtils.clamp(mapData.getShiftX(),-3072,-1024));
		mapData.setShiftY(MathUtils.clamp(mapData.getShiftY(),-1728,-576));


		//System.out.println("X: " + cam.position.x + " Y: " + cam.position.y);
		//System.out.println("shiftY: " + mapData.getShiftY());

	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = mapData.getMapWidth()*Constants.getTileSize();
		cam.viewportHeight = mapData.getMapHeight()*Constants.getTileSize();
		cam.update();
	}







}
