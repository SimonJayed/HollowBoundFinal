package entity;

import entity.components.Skill;
import main.GamePanel;
import misc.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Entity {
    public GamePanel gp;

    public int level = 1;
    private String name;
    public int worldX, worldY;
    public int spawnPointX = worldX;
    public int spawnPointY = worldY;
    public double speed = 1;
    public double tempSpeed = 0;
    public double attack = 2;
    public double defense = 1;
    public double exp = 1;
    public double nextLevelExp;
    public int sizeIncrement = 0;

    public double initialHP;
    public double initialEnergy;
    public double maxHP;
    public double hp;
    public double maxEnergy = 10;
    public double energy = 10;
    public double energyRegen = maxEnergy*0.1;

    public double vit;
    public double pow;
    public double mag;
    public double agi;
    public double luck;

    public ArrayList<Skill> skills = new ArrayList<>();

    public int hollowCounter = 0;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage defeated1, defeated2;
    public BufferedImage image1, image2, image3, image4;
    public BufferedImage portrait;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 2;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int type;

    public String dialogues[] = new String[20];
    int dialogueIndex = 0;

    public boolean collision = false;
    public boolean invincible = false;
    public boolean collisionOn = false;
    public boolean isIdling = true;
    public boolean isRunning = false;
    public boolean isAlive = true;
    public boolean isDefeated = false;
    public boolean isUnconscious = false;
    public boolean hpBarOn = true;
    public boolean hasEvent = false;
    public boolean isMob = false;

    public boolean isAttacked = false;
    public boolean isHealed = false;
    public boolean dodged = false;

    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;

    public int buffer = 0;
    public int deathBuffer = 0;


    public Entity(GamePanel gp){
        this.gp = gp;
        setDefaultValues(1, 200, 200, 2,1, 1, 1, 1, 1);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name =  name;}

    public void setDefaultValues(int level, double initialHP, double initialEnergy, double speed, double vit, double pow, double mag, double agi, double luck){
        this.level = level;
        this.initialHP = initialHP;
        this.initialEnergy = initialEnergy;
        this.speed = speed;
        this.tempSpeed = speed;

        this.vit = vit;
        this.pow = pow;
        this.mag = mag;
        this.agi = agi;
        this.luck = luck;

        nextLevelExp = 10 * Math.pow(level, 2);
        setLevel(level);
        calculateStats();
        this.hp = maxHP;
        this.energy = maxEnergy;
    }
    public void setEvent(String direction, int worldX, int worldY, double speed, int type, boolean isIdling){
        hasEvent = true;
        this.direction = direction;
        this.worldX = worldX * gp.tileSize;
        this.worldY = worldY * gp.tileSize;
        this.speed = speed;
        this.type = type;
        this.isIdling = isIdling;
    }
    public void setLevel(int level){
        this.level = level;
        int tempLevel = level;
        while(tempLevel > 0){
            setStatIncrements();
            tempLevel--;
        }
    }
    public void setDialogue(){

    }
    public void calculateStats(){
        this.maxHP = initialHP + (15 * level) + (vit * 2);
        this.maxEnergy = initialEnergy + (15 * level) + (mag * 2);
        this.energyRegen = maxEnergy * 0.1 + (mag / 100);
        this.hp = maxHP;
        this.energy = maxEnergy;

        this.attack = pow * 3;
        this.defense = vit * 1.5;
        nextLevelExp = 10 * Math.pow(level, 2);
    }
    public void setStatIncrements(){
        this.vit += 1;
        this.pow += 1;
        this.mag += 1;
        this.agi += 1;
    }

    public void checkLevelUp(){
        while (exp >= nextLevelExp) {
            level++;
            setStatIncrements();
            calculateStats();
            nextLevelExp = 10 * Math.pow(level, 2);
            gp.ui.addMessage(name + " has leveled up! (Lvl " + level + ")");
        }
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
    }

    public void speak(){
        if(gp.gameState == gp.eventState){
            gp.event.dialogueOn = true;
            gp.event.dialogueFinished = false;
        }
        if(dialogues != null && gp.gameState != gp.eventState){
            gp.gameState = gp.dialogueState;
            if (dialogues[dialogueIndex] == null){
                dialogueIndex = 0;
            }
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
            gp.playSoundEffect(1);
        }

        if(!isDefeated){
            switch (gp.player.direction){
                case "up": {
                    direction = "down";
                    break;
                }
                case "down": {
                    direction = "up";
                    break;
                }
                case "left": {
                    direction = "right";
                    break;
                }
                case "right": {
                    direction = "left";
                    break;
                }
                default:{
                    direction = "down";
                }
            }
        }
    }
    public void setAction(){}
    public void regen(){
        buffer++;
        if(buffer >= 250 && energy < maxEnergy && !isRunning){
            energy += energyRegen;
            buffer = 0;
            System.out.println(name + " is regenerating.");
            if(energy > maxEnergy){
                energy = maxEnergy;
            }
        }
    }

    public void update(){
        spriteAnim(2);
        if(!hasEvent && !isDefeated){
            setAction();
        }
        regen();
        checkDefeated();
        checkLevelUp();
        calculateStats();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.livingEntity);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(contactPlayer && gp.gameState == gp.eventState){
            if(type == 2){
                gp.battleScreen.startBattle(this);
            }
            else{
                speak();
                gp.event.dialogueOn = true;
            }
        }

        if (!collisionOn && isIdling == false) {
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
        else{
            isIdling = true;
        }
        if (invincible){
            invincibleCounter++;
            if (invincibleCounter > 40){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    public void checkDefeated(){
        if(isDefeated && hollowCounter < 5){
            this.deathBuffer++;
            isIdling = true;
            collision = false;
            if(this.deathBuffer > 250 && !hasEvent){
                isDefeated = false;
                hp = maxHP;
                energy = maxEnergy;
                exp = nextLevelExp;
                int num = 0;
                while(num < hollowCounter){
                    setStatIncrements();
                    calculateStats();
                    num++;
                    System.out.println(getName() + " is being strengthened.");
                }
                this.deathBuffer = 0;
                this.worldX = this.spawnPointX;
                this.worldY = this.spawnPointY;
                collision = true;
                System.out.println(getName() + " has respawned.");
                System.out.println(getName() + " died " + hollowCounter + " times");
            }
        }
    }

    public void spriteAnim(int spriteQuantity){
        spriteCounter++;
        if (spriteCounter > spriteQuantity * 13) {
            spriteCounter = 1;
        }
        spriteNum = (spriteCounter - 1) / 13 + 1;
    }

    public void displayEntityStats(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(new Color(98, 146, 255, 155));
        g2.fillRect(screenX, screenY, 48, 48);

        g2.setColor(new Color(172, 69, 69, 172));
        g2.fillRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);

//        for (int row = 0; row < gp.maxWorldRow; row++) {
//            for (int col = 0; col < gp.maxWorldCol; col++) {
//                int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
//
//                if (gp.tileM.tile[tileNum].collision) {
//                    int tileScreenX = col * gp.tileSize - gp.player.worldX + gp.player.screenX;
//                    int tileScreenY = row * gp.tileSize - gp.player.worldY + gp.player.screenY;
//
//                    g2.setColor(new Color(69, 172, 69, 100)); // Semi-transparent green
//                    g2.fillRect(tileScreenX, tileScreenY, gp.tileSize, gp.tileSize);
//                }
//            }
//        }



    }

    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image1 = null;

        try{
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image1 = uTool.scaleImage(image1, width+sizeIncrement, height+sizeIncrement);
        } catch (IOException e){
            e.printStackTrace();
        }
        return image1;
    }

    public void draw(Graphics2D g2){
        BufferedImage image1 = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY || worldX + gp.tileSize*6 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize*6 < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize*6 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize*6 < gp.player.worldY + gp.player.screenY){
            switch (direction) {
                case "up": {
                    if(!isDefeated && !isUnconscious){
                        if (spriteNum == 1){
                            image1 = up1;
                        }
                        if (spriteNum == 2) {
                            image1 = up2;
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
                    if(!isDefeated && !isUnconscious){
                        if (spriteNum == 1){
                            image1 = down1;
                        }
                        if (spriteNum == 2) {
                            image1 = down2;
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
                    if(!isDefeated && !isUnconscious){
                        if (spriteNum == 1){
                            image1 = left1;
                        }
                        if (spriteNum == 2) {
                            image1 = left2;
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
                    if(!isDefeated && !isUnconscious){
                        if (spriteNum == 1){
                            image1 = right1;
                        }
                        if (spriteNum == 2) {
                            image1 = right2;
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
        }
        displayEntityStats(g2);

        if (invincible){
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2, 0.4f);
        }

        g2.drawImage(image1, screenX, screenY, null);

        changeAlpha(g2,1f);
    }

}
