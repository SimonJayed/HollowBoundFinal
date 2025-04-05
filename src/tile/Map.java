package tile;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{
    GamePanel gp;
    BufferedImage worldMap[];
    public boolean miniMapOn = true;

    public Map(GamePanel gp){
        super(gp);
        this.gp = gp;
        createWorldMap();
    }

    public void addMapInfo(){
        switch(gp.currentMap){
            case 0:{
                gp.ui.addDescription("<Forest Entrance>\n <Lvl 1-5>");
                break;
            }
            case 1:{
                gp.ui.addDescription("<Forest Path>\n <Lvl 1-5>");
                break;
            }
            case 2:{
                gp.ui.addDescription("<Cave Entrance>\n <Lvl 5-10>");
                break;
            }
            case 3:{
                gp.ui.addDescription("<Forest Uphill>\n <Lvl 10-15>");
                break;
            }
            case 4:{
                gp.ui.addDescription("<Cat Cave>\n <Safe Area>");
                break;
            }
            case 5:{
                gp.ui.addDescription("<Village Path>\n <Lvl 40-45>");
                break;
            }
            case 6:{
                gp.ui.addDescription("<Old Man's Forest>\n <Safe Area>");
                break;
            }
            case 7:{
                gp.ui.addDescription("<Old Man Lars' Hut>\n <Safe Area>");
                break;
            }
            case 8:{
                gp.ui.addDescription("<Veyra's Village>\n <Safe Area>");
                break;
            }
            case 9:{
                gp.ui.addDescription("<Veyra Hill>\n <Lvl 45-50>");
                break;
            }
        }
    }

    public void createWorldMap(){
        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth = gp.tileSize * gp.maxWorldCol;
        int worldMapHeight = gp.tileSize * gp.maxWorldRow;

        for (int i = 0; i < gp.maxMap; i++){

            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){

                int tileNum = mapTileNum[i][col][row];
                int x = gp.tileSize * col;
                int y = gp.tileSize * row;
                if(!tile[tileNum].collision) {
                    g2.setColor(new Color(255, 255, 255, 255));
                    g2.fillRect(x, y, 50, 50);
                }
                col++;
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
        }
    }

    public void drawFullMapScreen(Graphics2D g2){
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        int width = 500;
        int height = 500;
        int x = gp.screenWidth/2 - width/2;
        int y = gp.screenHeight/2 - height/2;
        g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

        double scale = (double) (gp.tileSize * gp.maxWorldCol)/width;
        int playerX = (int) (x + gp.player.worldX/scale);
        int playerY = (int) (y + gp.player.worldY/scale);
        int playerSize = (int) (gp.tileSize/scale);

        g2.setColor(new Color(255, 70, 0));
        g2.fillRect(playerX, playerY, playerSize, playerSize);

        for (Entity entity : gp.livingEntity[gp.currentMap]) {
            if (entity != null && entity != gp.player && !entity.hasEvent) {
                int npcX = (int) (x + entity.worldX / scale);
                int npcY = (int) (y + entity.worldY / scale);

                if (entity.type == 1) {
                    g2.setColor(new Color(37, 255, 0));
                } else {
                    g2.setColor(new Color(255, 0, 0));
                }

                g2.fillRect(npcX, npcY, playerSize, playerSize);
            }
        }
    }

    public void drawMiniMap(Graphics2D g2) {
        if (miniMapOn) {
            int width = 200;
            int height = 200;
            int x = gp.screenWidth - width;
            int y = gp.tileSize+10;

            g2.getComposite();
            g2.drawImage(worldMap[gp.currentMap], x-5, y, width, height, null);

            double scale = (double) (gp.tileSize * gp.maxWorldCol) / width;
            int playerX = (int) (x + gp.player.worldX / scale);
            int playerY = (int) (y + gp.player.worldY / scale);
            int playerSize = (int) (gp.tileSize / scale);

            g2.setColor(new Color(255, 70, 0));
            g2.fillRect(playerX, playerY, playerSize, playerSize);

            for (Entity entity : gp.livingEntity[gp.currentMap]) {
                if (entity != null && entity != gp.player && !entity.hasEvent) {
                    int npcX = (int) (x + entity.worldX / scale);
                    int npcY = (int) (y + entity.worldY / scale);

                    if (entity.type == 1) {
                        g2.setColor(new Color(37, 255, 0));
                    } else {
                        g2.setColor(new Color(255, 0, 0));
                    }

                    g2.fillRect(npcX, npcY, playerSize, playerSize);
                }
            }
        }
    }
}
