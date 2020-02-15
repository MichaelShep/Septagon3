package com.kroy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Assets
{
    public static Texture menuBackgroundTexture;
    public static Texture menuTitleTexture;

    public static Texture grassTileTexture;
    public static Texture roadTileTexture;
    public static Texture waterTileTexture;
    public static Texture lavaTileTexture;
    public static Texture stationTileTexture;
    public static Texture roadVerticalTexture;
    public static Texture roadHorizontalTexture;
    public static Texture roadTwoWayRightUpTexture;
    public static Texture roadTwoWayLeftUpTexture;
    public static Texture roadTwoWayRightDownTexture;
    public static Texture roadTwoWayLeftDownTexture;
    public static Texture roadThreeWayNoUpTexture;
    public static Texture roadThreeWayNoRightTexture;
    public static Texture roadThreeWayNoLeftTexture;
    public static Texture roadThreeWayNoDownTexture;
    public static Texture roadFourWayTexture;
    public static Texture tileBuildTexture;
    public static Texture patrolSpriteTexture;
    public static Texture fortressSpriteTexture;
    public static Texture fireEngineSpriteTexture;

    public static Texture healthIconTexture;
    public static Texture damageIconTexture;
    public static Texture rangeIconTexture;
    public static Texture speedIconTexture;
    public static Texture waterIconTexture;

    public static Texture blankTexture;
    public static Texture attackTexture;
    public static Texture moveTexture;

    public static List<Texture> GRASS_TEXTURES;
    public static List<Texture> BUILDING_TEXTURES;
    public static List<Texture> MINIGAME_TEXTURES;

    public static void loadGameAssets(){
        menuBackgroundTexture = new Texture(Gdx.files.internal("menuBackground.jpeg"));
        menuTitleTexture = new Texture(Gdx.files.internal("title.png"));

        grassTileTexture = new Texture(Gdx.files.internal("grassTile.png"));
        roadTileTexture = new Texture(Gdx.files.internal("roadTile.png"));
        waterTileTexture = new Texture(Gdx.files.internal("waterTile.png"));
        lavaTileTexture = new Texture(Gdx.files.internal("lavaTile.png"));
        stationTileTexture = new Texture(Gdx.files.internal("stationTile.png"));
        roadVerticalTexture = new Texture(Gdx.files.internal("RoadVertical.png"));
        roadHorizontalTexture = new Texture(Gdx.files.internal("RoadHorizontal.png"));
        roadTwoWayRightUpTexture = new Texture(Gdx.files.internal("RoadTwoWayRightUp.png"));
        roadTwoWayLeftUpTexture = new Texture(Gdx.files.internal("RoadTwoWayLeftUp.png"));
        roadTwoWayRightDownTexture = new Texture(Gdx.files.internal("RoadTwoWayRightDown.png"));
        roadTwoWayLeftDownTexture = new Texture(Gdx.files.internal("RoadTwoWayLeftDown.png"));
        roadThreeWayNoUpTexture = new Texture(Gdx.files.internal("RoadThreeWayNoUp.png"));
        roadThreeWayNoRightTexture = new Texture(Gdx.files.internal("RoadThreeWayNoRight.png"));
        roadThreeWayNoLeftTexture = new Texture(Gdx.files.internal("RoadThreeWayNoLeft.png"));
        roadThreeWayNoDownTexture = new Texture(Gdx.files.internal("RoadThreeWayNoDown.png"));
        roadFourWayTexture = new Texture(Gdx.files.internal("RoadFourWay.png"));
        tileBuildTexture = new Texture(Gdx.files.internal("BuildingTexture/TileBuild.png"));
        patrolSpriteTexture = new Texture(Gdx.files.internal("PatrolSprite.png"));
        fortressSpriteTexture = new Texture(Gdx.files.internal("fortressSprite.png"));
        fireEngineSpriteTexture = new Texture(Gdx.files.internal("fireEngineSprite.png"));

        healthIconTexture = new Texture(Gdx.files.internal("Icons/healthIcon.png"));
        damageIconTexture = new Texture(Gdx.files.internal("Icons/damageIcon.png"));
        rangeIconTexture = new Texture(Gdx.files.internal("Icons/rangeIcon.png"));
        speedIconTexture = new Texture(Gdx.files.internal("Icons/speedIcon.png"));
        waterIconTexture = new Texture(Gdx.files.internal("Icons/waterIcon.png"));

        blankTexture = new Texture(Gdx.files.internal("HighlightTexture/blank.png"));
        attackTexture = new Texture(Gdx.files.internal("HighlightTexture/attack.png"));
        moveTexture = new Texture(Gdx.files.internal("HighlightTexture/move.png"));

        loadListTextures();
    }

    private static void loadListTextures(){
        GRASS_TEXTURES = new ArrayList<Texture>();
        GRASS_TEXTURES.add(new Texture(Gdx.files.internal("GreeneryTexture/GrassTile1.png")));
        GRASS_TEXTURES.add(new Texture(Gdx.files.internal("GreeneryTexture/BushTile1.png")));
        GRASS_TEXTURES.add(new Texture(Gdx.files.internal("GreeneryTexture/GrassTile1.png")));
        GRASS_TEXTURES.add(new Texture(Gdx.files.internal("GreeneryTexture/TreeTile1.png")));
        GRASS_TEXTURES.add(new Texture(Gdx.files.internal("GreeneryTexture/GrassTile1.png")));
        GRASS_TEXTURES.add(new Texture(Gdx.files.internal("GreeneryTexture/GrassTile1.png")));

        BUILDING_TEXTURES = new ArrayList<Texture>();
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile1.png")));
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile2.png")));
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile3.png")));
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile4.png")));
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile5.png")));
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile6.png")));
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile7.png")));
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile8.png")));
        BUILDING_TEXTURES.add(new Texture(Gdx.files.internal("BuildingTexture/BuildingTile9.png")));

        MINIGAME_TEXTURES = new ArrayList<Texture>();
        MINIGAME_TEXTURES.add(new Texture(Gdx.files.internal("MiniGameTexture/ethan.png")));
        MINIGAME_TEXTURES.add(new Texture(Gdx.files.internal("MiniGameTexture/michael.png")));
        MINIGAME_TEXTURES.add(new Texture(Gdx.files.internal("MiniGameTexture/thanh.png")));
    }
}
