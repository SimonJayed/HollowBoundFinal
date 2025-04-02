package misc;

import entity.*;
import main.GamePanel;
import object.*;
import object.foreground.FG_Tree1;
import object.foreground.FG_Tree2;
import object.foreground.FG_Tree3;

public class AssetSetter {
    GamePanel gp;

    int buffer = 0;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }


    public void setPlayer(int choice){
        switch(choice){
            case 0:{
                gp.player.playing = "amaryllis";
                gp.companion1 = new NPC_Sylvie(gp);
                gp.companion2 = new NPC_Fort(gp);
                break;
            }
            case 1:{
                gp.player.playing = "fort";
                gp.companion1 = new NPC_Sylvie(gp);
                gp.companion2 = new NPC_Amaryllis(gp);
                break;
            }
            case 2: {
                gp.player.playing = "sylvie";
                gp.companion1 = new NPC_Fort(gp);
                gp.companion2 = new NPC_Amaryllis(gp);
                break;
            }
        }
        gp.player.setDefaultValues();
    }

    public void setObjectEntity(){
        int mapNum = 0;
        int i= 0;
        gp.objectEntity[0][i] = new OBJ_Boots(gp);
        gp.objectEntity[0][i].worldX = 23 * gp.tileSize;
        gp.objectEntity[0][i].worldY = 10 * gp.tileSize;
        i++;

        //MAP1
        i = 0;
//        gp.foreground[1][i] = new FG_Tree3(gp);
//        gp.foreground[1][i].worldX = 0;
//        gp.foreground[1][i].worldY = 45 * gp.tileSize;
//        i++;
        gp.foreground[1][i] = new FG_Tree2(gp);
        gp.foreground[1][i].worldX = 0;
        gp.foreground[1][i].worldY = 46 * gp.tileSize;
        i++;
        gp.foreground[1][i] = new FG_Tree2(gp);
        gp.foreground[1][i].worldX = 3 * gp.tileSize;
        gp.foreground[1][i].worldY = 45 * gp.tileSize;
        i++;
        gp.foreground[1][i] = new FG_Tree3(gp);
        gp.foreground[1][i].worldX = 16 * gp.tileSize;
        gp.foreground[1][i].worldY = 45 * gp.tileSize;
        i++;
        gp.foreground[1][i] = new FG_Tree2(gp);
        gp.foreground[1][i].worldX = 11 * gp.tileSize;
        gp.foreground[1][i].worldY = 46 * gp.tileSize;
        i++;


        //MAP2
        gp.foreground[2][i] = new OBJ_Door(gp);
        gp.foreground[2][i].worldX = 29 * gp.tileSize;
        gp.foreground[2][i].worldY = 22 * gp.tileSize;

        //MAP 5
        i = 0;
        gp.foreground[5][i] = new FG_Tree3(gp);
        gp.foreground[5][i].worldX = 2 * gp.tileSize;
        gp.foreground[5][i].worldY = 18 * gp.tileSize;
        i++;
        gp.foreground[5][i] = new FG_Tree2(gp);
        gp.foreground[5][i].worldX = 7 * gp.tileSize;
        gp.foreground[5][i].worldY = 17 * gp.tileSize;
        i++;
        gp.foreground[5][i] = new FG_Tree1(gp);
        gp.foreground[5][i].worldX = 0 * gp.tileSize;
        gp.foreground[5][i].worldY = 20 * gp.tileSize;
        i++;
        gp.foreground[5][i] = new FG_Tree1(gp);
        gp.foreground[5][i].worldX = 16 * gp.tileSize;
        gp.foreground[5][i].worldY = 19 * gp.tileSize;
        i++;
        gp.foreground[5][i] = new FG_Tree1(gp);
        gp.foreground[5][i].worldX = 25 * gp.tileSize;
        gp.foreground[5][i].worldY = 26 * gp.tileSize;
        i++;
        gp.foreground[5][i] = new FG_Tree1(gp);
        gp.foreground[5][i].worldX = 14 * gp.tileSize;
        gp.foreground[5][i].worldY = 39 * gp.tileSize;
        i++;
        gp.foreground[5][i] = new FG_Tree1(gp);
        gp.foreground[5][i].worldX = 24 * gp.tileSize;
        gp.foreground[5][i].worldY = 41 * gp.tileSize;
        i++;

        //MAP 8
        i = 0;
        gp.objectEntity[8][i] = new OBJ_House1(gp);
        gp.objectEntity[8][i].worldX = 8 * gp.tileSize;
        gp.objectEntity[8][i].worldY = 11 * gp.tileSize;
        i++;
        gp.objectEntity[8][i] = new OBJ_House2(gp);
        gp.objectEntity[8][i].worldX = 34 * gp.tileSize;
        gp.objectEntity[8][i].worldY = 8 * gp.tileSize;
        i++;
        gp.objectEntity[8][i] = new OBJ_House3(gp);
        gp.objectEntity[8][i].worldX = 41 * gp.tileSize;
        gp.objectEntity[8][i].worldY = 9 * gp.tileSize;
        i++;
        gp.objectEntity[8][i] = new OBJ_House3(gp);
        gp.objectEntity[8][i].worldX = 3 * gp.tileSize;
        gp.objectEntity[8][i].worldY = 31 * gp.tileSize;
        i++;
        gp.objectEntity[8][i] = new OBJ_House2(gp);
        gp.objectEntity[8][i].worldX = 10 * gp.tileSize;
        gp.objectEntity[8][i].worldY = 35 * gp.tileSize;
        i++;
        gp.objectEntity[8][i] = new OBJ_House1(gp);
        gp.objectEntity[8][i].worldX = 26 * gp.tileSize;
        gp.objectEntity[8][i].worldY = 35 * gp.tileSize;
        i++;

        gp.foreground[8][i] = new FG_Tree2(gp);
        gp.foreground[8][i].worldX = 46 * gp.tileSize;
        gp.foreground[8][i].worldY = 16 * gp.tileSize;
        i++;
        gp.foreground[8][i] = new FG_Tree1(gp);
        gp.foreground[8][i].worldX = 37 * gp.tileSize;
        gp.foreground[8][i].worldY = 15 * gp.tileSize;
        i++;
        gp.foreground[8][i] = new FG_Tree1(gp);
        gp.foreground[8][i].worldX = 7 * gp.tileSize;
        gp.foreground[8][i].worldY = 17 * gp.tileSize;
        i++;
        gp.foreground[8][i] = new FG_Tree1(gp);
        gp.foreground[8][i].worldX = 0 * gp.tileSize;
        gp.foreground[8][i].worldY = 12 * gp.tileSize;
        i++;
        gp.foreground[8][i] = new FG_Tree1(gp);
        gp.foreground[8][i].worldX = 0 * gp.tileSize;
        gp.foreground[8][i].worldY = 38 * gp.tileSize;
        i++;
        gp.foreground[8][i] = new FG_Tree1(gp);
        gp.foreground[8][i].worldX = 4 * gp.tileSize;
        gp.foreground[8][i].worldY = 44 * gp.tileSize;
        i++;

    }

    public void setLivingEntity(){
        int mapNum = 0;
        int i = 0;
        //MAP1
        gp.livingEntity[0][i] = new NPC_OldMan(gp);
        gp.livingEntity[0][i].worldX = gp.livingEntity[0][i].spawnPointX = 11 * gp.tileSize;
        gp.livingEntity[0][i].worldY = gp.livingEntity[0][i].spawnPointY = 31 * gp.tileSize;
        gp.livingEntity[0][i].setLevel(100);
        i++;
        gp.livingEntity[0][i] = new NPC_Sylvie(gp);
        gp.livingEntity[0][i].worldX = gp.livingEntity[0][i].spawnPointX = 11 * gp.tileSize;
        gp.livingEntity[0][i].worldY = gp.livingEntity[0][i].spawnPointY = 28 * gp.tileSize;
        gp.livingEntity[0][i].setLevel(5);
        i++;
        gp.livingEntity[0][i] = new NPC_Fort(gp);
        gp.livingEntity[0][i].worldX = gp.livingEntity[0][i].spawnPointX = 11 * gp.tileSize;
        gp.livingEntity[0][i].worldY = gp.livingEntity[0][i].spawnPointY = 30 * gp.tileSize;
        gp.livingEntity[0][i].setLevel(10);
        i++;
        gp.livingEntity[0][i] = new NPC_Amaryllis(gp);
        gp.livingEntity[0][i].worldX = gp.livingEntity[0][i].spawnPointX = 8 * gp.tileSize;
        gp.livingEntity[0][i].worldY = gp.livingEntity[0][i].spawnPointY = 15 * gp.tileSize;
        gp.livingEntity[0][i].setLevel(50);
        i++;


        //MAP 2
        gp.livingEntity[1][i] = new NPC_Amaryllis(gp);
        gp.livingEntity[1][i].worldX = gp.livingEntity[1][i].spawnPointX = 19 * gp.tileSize;
        gp.livingEntity[1][i].worldY = gp.livingEntity[1][i].spawnPointY = 47 * gp.tileSize;
        gp.livingEntity[1][i].hasEvent = true;
        gp.livingEntity[1][i].direction = "up";
        gp.livingEntity[1][i].setLevel(10);
        System.out.println(gp.livingEntity[1][i].getName() +" has event in Map " + gp.currentMap);

        //MAP 3
        gp.livingEntity[3][0] = new NPC_MamaPausy(gp);
        gp.livingEntity[3][0].worldX = gp.livingEntity[3][0].spawnPointX = 46 * gp.tileSize;
        gp.livingEntity[3][0].worldY = gp.livingEntity[3][0].spawnPointY = 39 * gp.tileSize;
        gp.livingEntity[3][0].hasEvent = true;

        //MAP 4
        i = 0;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 32 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 18 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 33 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 18 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 34 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 19 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 34 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 21 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 40 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 18 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 41 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 19 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 20 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 15 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 21 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 15 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 22 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 15 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 33 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 15 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 34 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 15 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 34 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 32 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 35 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 32 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 36 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 32 * gp.tileSize;
        i++;
        gp.livingEntity[4][i] = new NPC_Miming(gp);
        gp.livingEntity[4][i].worldX = gp.livingEntity[4][i].spawnPointX = 36 * gp.tileSize;
        gp.livingEntity[4][i].worldY = gp.livingEntity[4][i].spawnPointY = 31 * gp.tileSize;


        //MAP 5
        i = 0;
        gp.livingEntity[5][i] = new MOB_Swordsman(gp);
        gp.livingEntity[5][i].worldX = gp.livingEntity[5][i].spawnPointX = 36 * gp.tileSize;
        gp.livingEntity[5][i].worldY = gp.livingEntity[5][i].spawnPointY = 31 * gp.tileSize;
        i++;

        //MAP 8
        i = 0;
        gp.livingEntity[8][i] = new NPC_VillagerSynthia(gp);
        gp.livingEntity[8][i].worldX = gp.livingEntity[8][i].spawnPointX = 7 * gp.tileSize;
        gp.livingEntity[8][i].worldY = gp.livingEntity[8][i].spawnPointY = 36 * gp.tileSize;
        i++;
        gp.livingEntity[8][i] = new NPC_VillagerOldMan(gp);
        gp.livingEntity[8][i].worldX = gp.livingEntity[8][i].spawnPointX = 38 * gp.tileSize;
        gp.livingEntity[8][i].worldY = gp.livingEntity[8][i].spawnPointY = 13 * gp.tileSize;
        i++;
    }

}
