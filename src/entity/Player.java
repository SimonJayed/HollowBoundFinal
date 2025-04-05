package entity;

import entity.components.Skill;
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
    public int areaLevel = 1;
    public int steps = 0;
    public int stepLimit;

    public boolean isSafe = false;
    public boolean encounterReset = true;

    public BufferedImage attack1, attack2;
    public BufferedImage runUp1, runUp2, runDown1, runDown2, runLeft1, runLeft2, runRight1, runRight2;

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

    public void getImage(String folder) {
        up1 = setup("/sprites/" + folder + "/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/sprites/" + folder + "/up2", gp.tileSize, gp.tileSize);
        down1 = setup("/sprites/" + folder + "/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/sprites/" + folder + "/down2", gp.tileSize, gp.tileSize);
        left1 = setup("/sprites/" + folder + "/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/sprites/" + folder + "/left2", gp.tileSize, gp.tileSize);
        right1 = setup("/sprites/" + folder + "/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/sprites/" + folder + "/right2", gp.tileSize, gp.tileSize);
        portrait = setup("/sprites/" + folder + "/portrait", gp.tileSize, gp.tileSize);

        defeated1 = setup("/sprites/" + folder + "/defeated/front", gp.tileSize, gp.tileSize);
        defeated2 = setup("/sprites/" + folder + "/defeated/side", gp.tileSize, gp.tileSize);

        runUp1 = setup("/sprites/" + folder + "/running/up1", gp.tileSize, gp.tileSize);
        runUp2 = setup("/sprites/" + folder + "/running/up2", gp.tileSize, gp.tileSize);
        runDown1 = setup("/sprites/" + folder + "/running/down1", gp.tileSize, gp.tileSize);
        runDown2 = setup("/sprites/" + folder + "/running/down2", gp.tileSize, gp.tileSize);
        runLeft1 = setup("/sprites/" + folder + "/running/left1", gp.tileSize, gp.tileSize);
        runLeft2 = setup("/sprites/" + folder + "/running/left2", gp.tileSize, gp.tileSize);
        runRight1 = setup("/sprites/" + folder + "/running/right1", gp.tileSize, gp.tileSize);
        runRight2 = setup("/sprites/" + folder + "/running/right2", gp.tileSize, gp.tileSize);
    }

    public void setDefaultValues(){
//        gp.currentMap = 0;
//        worldX = spawnPointX = gp.tileSize * 1;
//        worldY = spawnPointY = gp.tileSize * 10;
        gp.currentMap = 0;
        worldX = spawnPointX = gp.tileSize * 46;
        worldY = spawnPointY = gp.tileSize * 44;
//        gp.currentMap = 9;
//        worldX = spawnPointX = gp.tileSize * 2;
//        worldY = spawnPointY = gp.tileSize * 43;
//        direction = "right";

        statPoints = 5;
        switch(playing){
            case "fort":{
                setName("Fort");
                getImage("fort");
                setDefaultValues(1, 100, 50,4, 15, 6, 4, 5,  15);
                getCombatImages("fort");
                skills.add(new Skill("Rage Bait", "Taunts the enemy for 3 turns and increases defense by skill power.", (vit*1.2)+(maxEnergy*0.2), maxEnergy*0.2, 4));
                skills.add(new Skill("Meat Shield", "Grants a shield to an ally", (maxHP*0.2)+(vit*1.5)+(maxEnergy*0.4), maxEnergy*0.4, 2));
                skills.add(new Skill("SMAAAAASHHHHH", "Deals damage to the enemy equivalent to health lost and vitality stat.", (maxHP-hp*0.8)+(vit*1.2), maxEnergy*0.8, 2));
                break;
            }
            case "amaryllis":{
                setName("Amaryllis");
                getImage("amaryllis");
                setDefaultValues(1, 75, 100,5,5, 8, 10, 13, 9);
                skills.add(new Skill("Nature's Embrace", "Lowers the enemy's agility with a chance to stun.", 135.6, maxEnergy*0.3, 2));
                skills.add(new Skill("Liquid Experiment", "Gives a random buff to an ally.", 25, maxEnergy*0.3, 2));
                skills.add(new Skill("Unleash", "Transforms into her beast form and gives a boost to her stats for 3 turns", 135.6, maxEnergy*0.8, 5));
                break;
            }
            case "sylvie":{
                setName("Sylvie");
                getImage("sylvie");
                setDefaultValues(1, 50, 150,3, 7, 5, 19, 5,  9);
                skills.add(new Skill("Nature's Embrace", "Channels natural energy to heal a single ally", mag*2, maxEnergy*0.2, 2));
                skills.add(new Skill("Thorned Whip", "Summons thorny vines to strike at an enemy", 25, maxEnergy*0.4, 1));
                skills.add(new Skill("Bloom of Life", "Creates an explosion of natural energy that damages enemies and heals allies", 135.6, maxEnergy*0.8, 2));
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
        encounter();
        if (keyH.qPressed) {
            System.out.println("Q is pressed...");
        }

        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            steps++;
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
                gp.companion1.hp = gp.companion1.maxHP;
                gp.companion1.energy = gp.companion1.maxEnergy;
                gp.companion2.hp = gp.companion2.maxHP;
                gp.companion2.energy = gp.companion2.maxEnergy;
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
            nextLevelExp = 10 * Math.pow(level, 2);
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

    public void encounter(){
        if(isSafe){
            return;
        }

        encounterReset = true;

        if(stepLimit == 0){
            if(gp.currentMap != 5){
                stepLimit = gp.randomize(200, 800);
            }
            else{
                stepLimit = gp.randomize(200, 300);
            }
        }

//        System.out.println(stepLimit + " and " + steps);

        int enemy = gp.randomize(0,10);

        if(steps>=stepLimit){
            gp.ui.message.clear();
            switch(gp.currentMap){
                case 0:{
                    areaLevel = 1;
                    if(enemy >= 8){
                        gp.battleScreen.startBattle(new MOB_Swordsman(gp));
                        gp.ui.addMessage("Encountered Hollowed Swordsman!!");
                    }
                    else if(enemy >= 5){
                        gp.battleScreen.startBattle(new MOB_Beast(gp));
                        gp.ui.addMessage("Encountered Hollowed Beast!!");
                    }
                    else{
                        gp.battleScreen.startBattle(new MOB_HollowHuman(gp));
                        gp.ui.addMessage("Encountered Hollowed Human!!");
                    }
                    break;
                }
                case 1:{
                    areaLevel = 5;
                    if(enemy >= 8){
                        gp.battleScreen.startBattle(new MOB_Swordsman(gp));
                        gp.ui.addMessage("Encountered Hollowed Swordsman!!");
                    }
                    else if(enemy >= 5){
                        gp.battleScreen.startBattle(new MOB_Beast(gp));
                        gp.ui.addMessage("Encountered Hollowed Beast!!");
                    }
                    else{
                        gp.battleScreen.startBattle(new MOB_HollowHuman(gp));
                        gp.ui.addMessage("Encountered Hollowed Human!!");
                    }
                    break;
                }
                case 2:{
                    areaLevel = 5;
                    if(enemy >= 8){
                        gp.battleScreen.startBattle(new MOB_Swordsman(gp));
                        gp.ui.addMessage("Encountered Hollowed Swordsman!!");
                    }
                    else if(enemy >= 5){
                        gp.battleScreen.startBattle(new MOB_Beast(gp));
                        gp.ui.addMessage("Encountered Hollowed Beast!!");
                    }
                    else{
                        gp.battleScreen.startBattle(new MOB_HollowHuman(gp));
                        gp.ui.addMessage("Encountered Hollowed Human!!");
                    }
                    break;
                }
                case 3:{
                    areaLevel = 10;
                    if(enemy >= 8){
                        gp.battleScreen.startBattle(new MOB_Swordsman(gp));
                        gp.ui.addMessage("Encountered Hollowed Swordsman!!");
                    }
                    else if(enemy >= 5){
                        gp.battleScreen.startBattle(new MOB_Beast(gp));
                        gp.ui.addMessage("Encountered Hollowed Beast!!");
                    }
                    else{
                        gp.battleScreen.startBattle(new MOB_HollowHuman(gp));
                        gp.ui.addMessage("Encountered Hollowed Human!!");
                    }
                    break;
                }
                case 5:{
                    areaLevel = 25;
                    if(enemy >= 8){
                        gp.battleScreen.startBattle(new MOB_Swordsman(gp));
                        gp.ui.addMessage("Encountered Hollowed Swordsman!!");
                    }
                    else if(enemy >= 5){
                        gp.battleScreen.startBattle(new MOB_Beast(gp));
                        gp.ui.addMessage("Encountered Hollowed Beast!!");
                    }
                    else{
                        gp.battleScreen.startBattle(new MOB_HollowHuman(gp));
                        gp.ui.addMessage("Encountered Hollowed Human!!");
                    }
                }
            }
            steps = 0;
            stepLimit = 0;
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
