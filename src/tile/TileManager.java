package tile;

import main.GamePanel;
import misc.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/forestIntroMap.txt", 0);
        loadMap("/maps/forestFlatPath.txt", 1);
        loadMap("/maps/forestCatCaveEntrance.txt", 2);
        loadMap("/maps/forestUphillPath.txt", 3);
        loadMap("/maps/catCave.txt", 4);
        loadMap("/maps/graveyard.txt", 5);
        loadMap("/maps/oldManForest.txt", 6);
        loadMap("/maps/village.txt", 8);
        loadMap("/maps/veyraHill.txt", 9);
    }

    public void getTileImage() {
        setup(0, "grass", false);
        setup(1, "treeThick", true);
        setup(2, "waterWow", false);
        setup(3, "waterWow", true);
        setup(4, "grassBald", false);
        setup(6, "bush", true);

        setup(9, "blk", false);
        setup(10, "treeLeaves", true);
        setup(11, "grassPlain", true);


        setup(30, "grassPlain2", false);
        setup(31, "grassPlain", false);
        setup(32, "grassNearLedge", false);
        setup(33, "grassLedge", true);
        setup(34, "treeTrunk", true);
        setup(35, "treeLong", true);
        setup(36, "grassNearLedge", true);
        setup(49, "blk", true);

//        setup(0, "grass1", false);
//        setup(1, "tree", false);
//        setup(2, "waterWow", false);
//
//
//        setup(30, "grassPlain2", false);
//        setup(31, "grassPlain", false);
//        setup(32, "grassNearLedge", false);
//        setup(33, "grassLedge", false);
//        setup(34, "treeTrunk", false);
//        setup(35, "treeLong", false);
//        setup(36, "grassNearLedge", false);
//        setup(49, "blk", false);
    }

    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e){
            System.out.println(index + ": error.");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tile map missing", "Tile Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void loadMap(String filePath, int map) {
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while (col < gp.maxWorldCol){
                    try{
                        String numbers[] = line.split(" ");

                        int num = Integer.parseInt(numbers[col]);

                        mapTileNum[map][col][row] = num;
                        col++;
                    } catch (Exception e){
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Tile map missing", "Tile Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }

                }

                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            try {

                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            } catch(Exception e){
                e.printStackTrace();
                System.exit(0);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
