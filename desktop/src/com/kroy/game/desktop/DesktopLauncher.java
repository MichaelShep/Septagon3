package com.kroy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kroy.game.Constants;
import com.kroy.game.MainClass;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");

        config.width = Constants.getResolutionWidth();
        config.height = Constants.getResolutionHeight();


        config.fullscreen = true;
        config.vSyncEnabled = true;
        config.title = "WARKROY";
        config.resizable = false;

        try{
            String assetRoot = findAssetRoot();
            Constants.setResourceRoot(assetRoot);
        } catch (Exception e){
            System.exit(0);
        }


        //set resolution if fullscreen on
        if (Constants.isFULLSCREEN())
        {
            Dimension deviceDimensions = Toolkit.getDefaultToolkit().getScreenSize();
            Constants.setResolutionWidth(deviceDimensions.width);
            Constants.setResolutionHeight(deviceDimensions.height);
        }

        System.out.println("----------------------------------------------");
        System.out.println("Width: " + Constants.getResolutionWidth());
        System.out.println("Height: " + Constants.getResolutionHeight());
        System.out.println("Asset Root: " + Constants.getResourceRoot());
        System.out.println("----------------------------------------------");







        new LwjglApplication(new MainClass(), config);


    }

    public static String findAssetRoot() throws Exception
    {
        ArrayList<String> listOfDir = new ArrayList<>();
        listOfDir = findAllDir(Constants.getResourceRoot());
        for (String dir: listOfDir)
        {
            if (dir.endsWith("\\assets"))
            {
                return (dir.replace("\\","/") + "/");
            }
        }
        throw new Exception("asset folder does not exist");
    }


    public static ArrayList<String> findAllDir(String currentPath)
    {
        ArrayList<String> newPathList = new ArrayList<>();
        File dir = new File(currentPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory()) {
                    for (String queryDir: findAllDir(currentPath+"\\"+child.getName()))
                    {
                        if (!newPathList.contains(queryDir))
                        {
                            newPathList.add(queryDir);
                        }
                    }
                }
                else
                {
                    newPathList.add(currentPath);
                }
            }
        }
        return newPathList;
    }





}
