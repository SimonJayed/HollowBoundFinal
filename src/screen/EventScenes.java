package screen;

import entity.Entity;
import entity.NPC_MamaPausy;
import main.GamePanel;
import misc.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class EventScenes implements Screen{
    GamePanel gp;
    Graphics2D g2;

    BufferedImage portrait = null;
    BufferedImage sceneBackground = null;

    public ArrayList<String> dialogues = new ArrayList<>();
    public String currentDialogue = "...";
    public int dialogueIndex = 0;

    int buffer = 0;
    int sequenceCheck = 0;
    public int eventNum = 0;

    public boolean dialogueOn = false;
    public boolean dialogueFinished = false;
    public boolean eventFinished = false;
    public boolean entitySet = false;

    public boolean event0Flag = false;
    public boolean event1Flag = false;
    public boolean event2Flag = false;
    public boolean event3Flag = false;
    public boolean event4Flag = false;
    public boolean event5Flag = false;
    public boolean event6Flag = false;

    public EventScenes(GamePanel gp){
        this.gp = gp;
    }

    public void updateEvent() {
        switch(eventNum){
            case 0:{
                if(gp.currentMap == 1 && !event0Flag){
                    if(!entitySet){
                        //SET ENTITIES
                        dialogues.clear();
                        gp.livingEntity[1][1] = gp.livingEntity[0][3];
                        gp.livingEntity[1][1].setEvent("left", 7, 45, 2, 1, true);


                        //SET PLAYER
                        gp.player.isRunning = false;

                        //SET DIALOGUE
                        gp.event.dialogues.add("Lol, Lmao, lmfao, rofl,, Trial Rani.");
                        gp.event.dialogues.add("Hello, bum.");
                        gp.event.dialogues.add("lolzies.");
                        currentDialogue = dialogues.get(dialogueIndex);

                        entitySet = true;
                    }
                    else{
                        playEvent0();
                    }
                }
                break;
            }
            case 1:{
                if(gp.currentMap == 4 && !event1Flag){
                    if(!entitySet){
                        //SET ENTITIES
                        dialogues.clear();
                        gp.livingEntity[4][5] = gp.livingEntity[0][3];
                        gp.livingEntity[4][5].setEvent("up", 26, 37, 2, 1, true);
                        gp.livingEntity[4][6].setEvent("left", 26, 33, 2, 1, true);

                        //SET PLAYER
                        gp.player.isRunning = false;

                        //SET DIALOGUE
                        gp.event.dialogues.add("KITTTYYUH!!!");
                        gp.event.dialogues.add("Hello, bum.");
                        gp.event.dialogues.add("Fat ass.");
                        currentDialogue = dialogues.get(dialogueIndex);

                        entitySet = true;
                    }
                    else{
                        playEvent1();
                    }
                }
            break;
            }
            case 2:{
                if(gp.currentMap == 3 && !event2Flag){

                    if(!entitySet){
                        //SET ENTITIES
                        dialogues.clear();
                        gp.livingEntity[3][0].setEvent("left", 46, 39, 50, 1, true);
                        gp.livingEntity[3][0].setLevel(60);

                        //SET PLAYER
                        gp.player.isRunning = false;
                        gp.player.worldX = 27 *gp.tileSize;
                        gp.player.worldY = 40 * gp.tileSize;

                        //SET DIALOGUES
                        gp.event.dialogues.add("MEOWR!!!");
                        gp.event.dialogues.add("Meowr");
                        gp.event.dialogues.add("Moewr?");
                        gp.event.dialogues.add("HHIIISSS!!");
                        gp.event.dialogues.add("Grrrrrrrrrr!!!");
                        gp.event.dialogues.add("MEOWR!!!");
                        gp.event.dialogues.add("HHIIISSS!!");
                        gp.event.dialogues.add("Moewr?");
                        currentDialogue = dialogues.get(dialogueIndex);

                        entitySet = true;
                        System.out.println("Mama Pausy Dialogue Set");
                    }
                    else{
                        playEvent2();
                    }
                }
                break;
            }
        }
    }

    public void playEvent0(){
        if(!gp.ui.fading){
            eventNum = 0;
            int sequenceLimit = dialogues.size();
            System.out.println("Dialogue size: " + dialogues.size());
            System.out.println("Entityet: " + entitySet);
            buffer++;


            if (gp.livingEntity[0][3] != null) {
                gp.livingEntity[1][1] = gp.livingEntity[0][3];
            }

            if(sequenceCheck == 0 && buffer > 20){
                gp.livingEntity[1][1].isIdling = false;
            }

            if (gp.livingEntity[1][1].collisionOn && sequenceCheck == 1) {
                System.out.println("Sequence: " + sequenceCheck);
                buffer = 0;
            }

            if (sequenceCheck == 2) {
                System.out.println("Sequence: " + sequenceCheck);
//                sceneBackground = setBackground("catCaveScene1");
            }
            if (sequenceCheck == 3) {
                System.out.println("Sequence: " + sequenceCheck);
            }

            if (sequenceCheck == sequenceLimit) {
                if(buffer > 50){
                    eventFinished = true;
                    event0Flag = true;

                    Entity entity = gp.livingEntity[1][1];
                    System.out.println("Sequence: " + sequenceCheck);
                    if (entity != null) {
                        gp.livingEntity[1][1] = null;
                        gp.entityList.remove(entity);
                        gp.livingEntity[0][3] = entity;
                        entity.worldX = entity.spawnPointX = 8 * gp.tileSize;
                        entity.worldY = entity.spawnPointY = 10 * gp.tileSize;
                    }

                    eventExit();
                }
            }
        }
    }
    public void playEvent1(){
        eventNum = 1;
        int sequenceLimit = dialogues.size();
        System.out.println("Event: " + eventNum);
        System.out.println("Dialogue size: " + dialogues.size());
        System.out.println("Buffer: " + buffer);
        buffer++;

        if(sequenceCheck == 0 && buffer > 2) {
            gp.livingEntity[4][5].worldY = 37*gp.tileSize+1;
            if(buffer > 3){
                gp.livingEntity[4][5].worldY = 37*gp.tileSize-1;
                System.out.println("Sequence: " + sequenceCheck);
                buffer = 0;
                dialogueOn = true;
            }

        }
        if(sequenceCheck == 1){
            gp.livingEntity[4][5].isIdling = false;
        }
        if(gp.livingEntity[4][5].collisionOn && sequenceCheck == 2){
            sceneBackground = setBackground("catCaveScene1");
            System.out.println("Sequence: " + sequenceCheck);
        }
        if(sequenceCheck == sequenceLimit) {
            if(buffer > 250){
                eventFinished = true;
                event1Flag = true;

                Entity entity = gp.livingEntity[0][3];
                gp.livingEntity[0][3] = null;
                gp.entityList.remove((entity));
                gp.livingEntity[4][5].hasEvent = false;
                gp.livingEntity[4][6].hasEvent = false;

                eventExit();
            }
        }
    }
    public void playEvent2(){
        eventNum = 2;
        int sequenceLimit = dialogues.size();
        System.out.println("Event: " + eventNum);
        System.out.println("Dialogue size: " + dialogues.size());
        buffer++;

        System.out.println("Buffer:  " + buffer);
        if(buffer>150){

            gp.livingEntity[3][0].isIdling = false;
            if(gp.livingEntity[3][0].collisionOn && sequenceCheck == 0){
                gp.battleScreen.startBattle(gp.livingEntity[3][0]);
                gp.player.isUnconscious = true;
                buffer = 0;
            }
        }
        if (!gp.player.collisionOn && sequenceCheck == 0){
            dialogueOn = false;
            gp.player.worldX++;
            gp.player.spriteAnim(2);

            System.out.println(gp.player.worldX);
        }

        if(gp.livingEntity[3][0].collisionOn && sequenceCheck == 1){
            dialogueOn = true;
            System.out.println("Sequence: " + sequenceCheck);
            gp.player.isUnconscious = true;
        }
        if(sequenceCheck == 3){
            sceneBackground = setBackground("nightmareScene1");
            gp.player.isUnconscious = true;
        }
        if(sequenceCheck == 4){
            sceneBackground = setBackground("nightmareScene2");
            System.out.println("Sequence: " + sequenceCheck);
        }
        if(sequenceCheck == 5){
            sceneBackground = setBackground("nightmareScene3");
            System.out.println("Sequence: " + sequenceCheck);
        }
        if(sequenceCheck == 6){
            sceneBackground = setBackground("nightmareScene4");
            System.out.println("Sequence: " + sequenceCheck);
        }
        if(sequenceCheck == 7){
            sceneBackground = setBackground("nightmareScene3");
            System.out.println("Sequence: " + sequenceCheck);
            buffer = 0;
        }

        if(sequenceCheck >= sequenceLimit) {
            if(buffer > 150){
                eventFinished = true;
                event2Flag = true;

                gp.ui.addMessage("Invoked");
                gp.player.isUnconscious = true;
                gp.ui.startFadeOut();
                gp.currentMap = 4;
                gp.player.worldX = gp.player.spawnPointX = 20 * gp.tileSize;
                gp.player.worldY = gp.player.spawnPointY = 14 * gp.tileSize;
                Entity entity = gp.livingEntity[3][0];
                gp.livingEntity[3][0] = null;
                gp.entityList.remove(entity);
                gp.livingEntity[4][0] = entity;
                gp.livingEntity[4][0].hasEvent = false;
                gp.livingEntity[4][0].speed = 3;
                gp.livingEntity[4][0].worldX = gp.livingEntity[4][0].spawnPointX = 21 * gp.tileSize;
                gp.livingEntity[4][0].worldY = gp.livingEntity[4][0].spawnPointY = 22 * gp.tileSize;
                eventExit();

            }
        }
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        int x = 0;
        int y = 0;
        g2.setColor(Color.black);
        g2.fillRect(x, y, gp.screenWidth, gp.tileSize*2);
        g2.fillRect(x, gp.screenHeight-gp.tileSize*2, gp.screenWidth, gp.tileSize*2);

        if(dialogueOn && !dialogueFinished && !eventFinished){
            drawDialogueScreen();
        }
    }

    public BufferedImage setBackground(String filename){
        BufferedImage background = null;
        try{
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/"+ filename + ".png")));
        }catch (IOException e){
            System.err.println("Error loading background: " + filename);
            return null;
        }
        return background;
    }

    public BufferedImage setDialoguePortrait(String characterName){
        UtilityTool uTool = new UtilityTool();
        try {
            portrait = ImageIO.read(getClass().getResourceAsStream("/sprites/" + characterName +"/portrait.png"));
            uTool.scaleImage(portrait,gp.tileSize * 11, gp.tileSize * 8 );
        } catch (IOException e) {
            System.err.println("Error loading background: " + characterName);
            return null;
        }
        return portrait;
    }

    public void drawDialogueScreen() {
        int x = 0;
        int y = 0;

        if(sceneBackground != null){
            g2.drawImage(sceneBackground, x, y,gp.screenWidth, gp.screenHeight, null);
        }

        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 4;
        x = gp.tileSize;
        y = gp.tileSize * 9 + gp.tileSize/2;

        drawSubWindow(x, y, width, height);

        if (portrait != null) {
            int imgX = gp.tileSize * 10;
            int imgY = gp.tileSize * 4;
            g2.drawImage(portrait, imgX, imgY, null);
        }

        x += gp.tileSize;
        y += gp.tileSize;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18f));
        g2.setColor(Color.white);
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void nextDialogue() {
        if (dialogueIndex < dialogues.size()) {
            currentDialogue = dialogues.get(dialogueIndex);
            System.out.println("Current Dialogue: " + currentDialogue);
            dialogueIndex++;
            sequenceCheck++;
        } else {
            dialogueFinished = true;
            dialogueOn = false;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 150);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height,20, 20);

        g2.setColor(new Color(255,255,255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 20, 20);
    }

    public void eventExit(){
        eventNum++;
        eventFinished = false;
        entitySet = false;
//        gp.player.isUnconscious = false;
        dialogueFinished = false;
        dialogueIndex = 0;
        sequenceCheck = 0;
        portrait = null;
        sceneBackground = null;
        dialogues.clear();
        buffer = 0;

        System.out.println("Event " + eventNum + " finished");
        System.out.println("Finishded : " + eventFinished );
        System.out.println("Entityset : " + entitySet );
        System.out.println("buffer MAN "+ buffer);

        gp.gameState = gp.playState;
    }
}

