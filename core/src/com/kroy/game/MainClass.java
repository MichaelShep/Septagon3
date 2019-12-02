package com.kroy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;


public class MainClass extends ApplicationAdapter {


	Map mapData;
	SpriteBatch batch;
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

		for(Character fe: humanData.getTeam())
		{
			mapData.placeOnMap(fe);
		}


		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		if (Constants.getManager().update()){
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 1);

			batch.begin();


			renderMap(batch);
			renderFireEngines();
            batch.draw(Constants.getManager().get("borderArt.png", Texture.class), 0, 0, Constants.getResolutionWidth(), Constants.getResolutionHeight(),0,0,1280,720,false,false);


            batch.end();

		}
		else {
		}


	}



	@Override
	public void dispose () {
		batch.dispose();
	}

	public void loadTextures(String root){
		/*

		Constants.getManager().load("grassTile.png", Texture.class);
		Constants.getManager().load("roadTile.png", Texture.class);
		Constants.getManager().load("borderArt.png", Texture.class);
		Constants.getManager().load("BuildingTexture/TileBuild.png", Texture.class);
		Constants.getManager().load("stationTile.png", Texture.class);
		 */

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
					System.out.println("Asset Found: " + root+child.getName());
					Constants.getManager().load(root+child.getName(), Texture.class);
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
				batch.draw(Constants.getManager().get(mapData.getMapData()[height][width].getTexName(), Texture.class),(width * Constants.getTileSize()) + mapData.getShiftX(),(height*Constants.getTileSize())+ mapData.getShiftY(), Constants.getTileSize(),Constants.getTileSize(),0,0,32,32,false,false);
			}
		}
	}

	public void renderFireEngines()
	{
		Character[] humanCharacters = humanData.getTeam();
		for (int feIndex = 0; feIndex < humanCharacters.length; feIndex++)
		{
			humanCharacters[feIndex].draw(batch);
		}


	}








}
