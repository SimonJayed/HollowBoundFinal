package entity;

import main.GamePanel;
import misc.KeyHandler;


import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public String playing = "";

    public int statPoints = 0;

    public BufferedImage attack1, attack2;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 31;
        this.solidArea.height = 33;

        buffer = 0;
    }

    public void setDefaultValues(){
        gp.currentMap = 0;
        worldX = spawnPointX = gp.tileSize * 14;
        worldY = spawnPointY = gp.tileSize * 28;
        direction = "down";

        statPoints = 1;
        switch(playing){
            case "fort":{
                setName("Fort");
                getImage("fort");
                setDefaultValues(1, 400, 100,4, 15, 6, 4, 5,  15);
                getCombatImages("fort");
                break;
            }
            case "amaryllis":{
                setName("Amaryllis");
                getImage("amaryllis");
                setDefaultValues(1, 250, 250,5,5, 8, 10, 13, 9);
                break;
            }
            case "sylvie":{
                setName("Sylvie");
                getImage("sylvie");
                setDefaultValues(1, 100, 400,3, 7, 5, 19, 5,  9);
                break;
            }
        }
    }

    public void getCombatImages(String filename){
        attack1 = setup("/sprites/" + filename + "/combat/hands1", gp.screenWidth, gp.screenHeight);
        attack2 = setup("/sprites/" + filename + "/combat/hands2", gp.screenWidth, gp.screenHeight);
    }

    public void calculateStats(){
        this.maxHP = initialHP + (15 * level) + (vit * 2);
        this.maxEnergy = initialEnergy + (15 * level) + (mag * 2);
        this.energyRegen = maxEnergy * 0.1 + (mag / 100);

        this.attack = pow * 3;
        this.defense = vit * 1.5;
    }


    public void update() {
        if (!keyH.upPressed && !keyH.downPressed && !keyH.rightPressed && !keyH.leftPressed){
            isIdling = true;
            isRunning = false;
        }
        regen();
        checkDefeated();
        checkLevelUp();
        calculateStats();
        if (keyH.qPressed) {
            System.out.println("Q is pressed...");
        }

        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            isUnconscious = false;
//            System.out.println(getName() + " moves but has been defeated is " + isDefeated );
            if (gp.keyH.upPressed) {
                direction = "up";
            }
            if (gp.keyH.downPressed) {
                direction = "down";
            }
            if (gp.keyH.rightPressed) {
                direction = "right";
            }
            if (gp.keyH.leftPressed) {
                direction = "left";
            }
            if (gp.keyH.shiftPressed && energy > 0) {
                buffer++;
                isRunning = true;
//                running();
                speed++;
                if (speed >= tempSpeed + 3) {
                    speed = tempSpeed + 3;
                }
                if(buffer >= 50){
                    energy--;
                    buffer = 0;
                }
            } else {
                keyH.shiftPressed = false;
                isRunning = false;
                speed = tempSpeed;
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int livingEntityIndex = gp.cChecker.checkEntity(this, gp.livingEntity);
            interactEntity(livingEntityIndex);

            gp.eHandler.checkEvent();

            if (!collisionOn) {
                switch (direction) {
                    case "up": {
                        worldY -= speed;
                        break;
                    }
                    case "down": {
                        worldY += speed;
                        break;
                    }
                    case "left": {
                        worldX -= speed;
                        break;
                    }
                    case "right": {
                        worldX += speed;
                        break;
                    }
                }
            }
            spriteAnim(2);

            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }

    public void checkDefeated(){
        if(isDefeated && hollowCounter < 5){
            this.deathBuffer++;
            isIdling = true;
            if(deathBuffer > 150 && !hasEvent){
                isDefeated = false;
                hp = maxHP;
                energy = maxEnergy;
                this.deathBuffer = 0;
                exp = nextLevelExp;
                int num = 0;
                while(num <= hollowCounter){
                    setStatIncrements();
                    calculateStats();
                    num++;
                    System.out.println(getName() + " is being strengthened.");
                }
                gp.player.worldX = spawnPointX;
                gp.player.worldY = spawnPointY;
                System.out.println(getName() + " has respawned.");
                System.out.println(getName() + " died " + hollowCounter + " times");
            }
        }
    }

    public void checkLevelUp(){
        while (exp >= nextLevelExp) {
            level++;
            statPoints += 5;
            setStatIncrements();
            calculateStats();
            nextLevelExp = 10 * Math.pow(level, 3);
            hp = maxHP;
            energy = maxEnergy;
            gp.ui.addMessage(getName() + " has leveled up! (Lvl " + level + ")");
        }
    }

    public void interactEntity(int i){
        if(i != 999){
            if (keyH.spacePressed){
                if(gp.livingEntity[gp.currentMap][i].type != 2 || gp.livingEntity[gp.currentMap][i].isDefeated) {
                    gp.gameState = gp.dialogueState;
                    gp.livingEntity[gp.currentMap][i].speak();
                }
                else{
                    gp.battleScreen.startBattle(gp.livingEntity[gp.currentMap][i]);
                    gp.battleScreen.canEscape = true;
                }
            }
        }
    }

    public void pickUpObject(int i){
        if (i != 999){
            String objectName = gp.objectEntity[gp.currentMap][i].getName();

            switch(objectName){
                case "Key":{
                    hasKey++;
                    gp.objectEntity[gp.currentMap][i] = null;
                    gp.ui.addMessage("Obtained KEY!");
                    break;
                }
                case "Door":{
                    gp.gameState = gp.dialogueState;
                    gp.objectEntity[gp.currentMap][i].speak();
                    break;
                }
                case "Boots":{
                    this.speed += 1;
                    gp.objectEntity[gp.currentMap][i] = null;
                    gp.ui.addMessage("Speed increased!");
                    break;
                }
                case "Chest":{
                    gp.objectEntity[gp.currentMap][i] = null;
                    gp.ui.addMessage("Chest opened!");

                    gp.ui.gameFinished = true;
                    break;
                }
            }
        }

    }


    public void draw(Graphics2D g2){

//        displayEntityStats(g2);

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up": {
                if(!isDefeated && !isRunning && !isUnconscious){
                    if (spriteNum == 1){
                        image1 = up1;
                    }
                    if (spriteNum == 2) {
                        image1 = up2;
                    }
                }
                else if(isRunning){
                    if (spriteNum == 1){
                        image1 = runUp1;
                    }
                    if (spriteNum == 2) {
                        image1 = runUp2;
                    }
                }
                else{
                    if (spriteNum == 1){
                        image1 = defeated1;
                    }
                    if (spriteNum == 2) {
                        image1 = defeated2;
                    }
                }
                break;
            }
            case "down": {
                if(!isDefeated && !isRunning && !isUnconscious){
                    if (spriteNum == 1){
                        image1 = down1;
                    }
                    if (spriteNum == 2) {
                        image1 = down2;
                    }
                }
                else if(isRunning){
                    if (spriteNum == 1){
                        image1 = runDown1;
                    }
                    if (spriteNum == 2) {
                        image1 = runDown2;
                    }
                }
                else{
                    if (spriteNum == 1){
                        image1 = defeated1;
                    }
                    if (spriteNum == 2) {
                        image1 = defeated2;
                    }
                }
                break;
            }
            case "left": {
                if(!isDefeated && !isRunning && !isUnconscious){
                    if (spriteNum == 1){
                        image1 = left1;
                    }
                    if (spriteNum == 2) {
                        image1 = left2;
                    }
                }
                else if(isRunning){
                    if (spriteNum == 1){
                        image1 = runLeft1;
                    }
                    if (spriteNum == 2) {
                        image1 = runLeft2;
                    }
                }
                else{
                    if (spriteNum == 1){
                        image1 = defeated1;
                    }
                    if (spriteNum == 2) {
                        image1 = defeated2;
                    }
                }
                break;
            }
            case "right": {
                if(!isDefeated && !isRunning && !isUnconscious){
                    if (spriteNum == 1){
                        image1 = right1;
                    }
                    if (spriteNum == 2) {
                        image1 = right2;
                    }
                }
                else if(isRunning){
                    if (spriteNum == 1){
                        image1 = runRight1;
                    }
                    if (spriteNum == 2) {
                        image1 = runRight2;
                    }
                }
                else{
                    if (spriteNum == 1){
                        image1 = defeated1;
                    }
                    if (spriteNum == 2) {
                        image1 = defeated2;
                    }
                }
                break;
            }
        }
        if (invincible){
            changeAlpha(g2,0.4f);
        }
        g2.drawImage(image1, tempScreenX, tempScreenY, null);

        changeAlpha(g2,1f);

    }
}
