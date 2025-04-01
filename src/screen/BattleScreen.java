package screen;

import entity.Entity;
import main.GamePanel;
import misc.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class BattleScreen implements Screen{
    GamePanel gp;


    BufferedImage background = null;

    public ArrayList<Entity> battleQueue = new ArrayList<>();
    public Entity currentEnemy;
    public int currentTurn = 0;
    public int commandNum = 0;

    public double damage = 0;
    public int buffer = 0;
    public int damageBuffer = 0;

    public boolean isAttacking = false;
    public boolean canEscape = true;
    private boolean battleActive = false;
    public boolean currentTurnFinished = false;
    public boolean isEnemyTurn = false;

    public BattleScreen(GamePanel gp){
        this.gp = gp;
    }

    public void loadImages(){
        try {
            if(gp.currentMap == 0){
                background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/battle/forestIntro.png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emptyImages(){
        background = null;
    }

    public void startBattle(Entity enemy) {
        gp.gameState = gp.battleState;
        loadImages();
        currentEnemy = enemy;
        battleQueue.clear();
        battleQueue.add(gp.player);
        battleQueue.add(gp.companion1);
        battleQueue.add(gp.companion2);
        battleQueue.add(currentEnemy);

        battleQueue.sort(Comparator.comparingInt(e -> (int) -e.agi));

        battleActive = true;
    }

    @Override
    public void draw(Graphics2D g2){
        g2.setColor(Color.white);
        g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);

        update();
        gp.ui.drawMessage();

        drawEnemy(g2);

        drawPartyMembers(g2);

        drawMenu(g2);

        drawTurnQueue(g2);
    }

    public void update() {
        if (!battleActive) {
            return;
        }

        Entity currentEntity = battleQueue.get(currentTurn);

        if (currentEntity == currentEnemy && !currentTurnFinished) {
            buffer++;
            isEnemyTurn = true;
            System.out.println("Enemy Choosing. ");
            if (buffer > 50) {
                enemyTurn();
                isEnemyTurn = false;
                currentTurnFinished = true;
                System.out.println("Enemy Turn Finished. ");
                buffer = 0;
            }
        }
        if(currentTurnFinished){
            currentTurn = (currentTurn + 1) % battleQueue.size();
            currentTurnFinished = false;
        }

    }



    public void drawMenu(Graphics2D g2){

        int x = gp.tileSize/2;
        int y = gp.tileSize*9 + (gp.tileSize/2);
        g2.setColor(new Color(0,0,0, 200));
        g2.fillRoundRect(x, y, gp.tileSize*5, gp.tileSize*4, 20, 20);

        x += gp.tileSize*5 + gp.tileSize;
        g2.setColor(new Color(0,0,0, 200));
        g2.fillRoundRect(x, y, gp.tileSize*11, gp.tileSize*4, 20, 20);

        String text = "";

        if(!isAttacking && !isEnemyTurn){
            text = "ATTACK";
            g2.setFont(g2.getFont().deriveFont(25f));
            g2.setColor(Color.white);
            x = gp.tileSize/2+5;
            y = gp.tileSize*10+5;
            g2.drawString(text, x, y);

            if(commandNum == 0){
                g2.setColor(new Color(255, 0, 0));
                g2.drawString("ATTACK", x, y);
            }

            text = "SKILL";
            g2.setFont(g2.getFont().deriveFont(25f));
            g2.setColor(Color.white);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1  && !isAttacking){
                g2.setColor(new Color(255, 0, 0));
                g2.drawString("SKILL", x, y);
            }

            text = "INVENTORY";
            g2.setFont(g2.getFont().deriveFont(25f));
            g2.setColor(Color.white);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2  && !isAttacking){
                g2.setColor(new Color(255, 0, 0));
                g2.drawString("INVENTORY", x, y);
            }

            text = "FLEE";
            g2.setFont(g2.getFont().deriveFont(25f));
            g2.setColor(Color.white);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 3 && !isAttacking){
                g2.setColor(new Color(255, 0, 0));
                g2.drawString("FLEE", x, y);
            }
        }
        else{

            g2.setFont(g2.getFont().deriveFont(25f));
            x = gp.tileSize/2+5;
            y = gp.tileSize*10+5;
            g2.setColor(Color.white);
            g2.drawString("HEAD", x, y);
            if(commandNum == 0 && isAttacking){
                g2.setColor(new Color(255, 0, 0));
                g2.drawString("HEAD", x, y);
            }

            g2.setFont(g2.getFont().deriveFont(25f));
            x = gp.tileSize/2+5;
            y += gp.tileSize;
            g2.setColor(Color.white);
            g2.drawString("TORSO", x, y);
            if(commandNum == 1 && isAttacking){
                g2.setColor(new Color(255, 0, 0));
                g2.drawString("TORSO", x, y);
            }

            g2.setFont(g2.getFont().deriveFont(25f));
            x = gp.tileSize/2+5;
            y += gp.tileSize;
            g2.setColor(Color.white);
            g2.drawString("LEGS", x, y);
            if(commandNum == 2 && isAttacking){
                g2.setColor(new Color(255, 0, 0));
                g2.drawString("LEGS", x, y);
            }
        }
    }

    public void attack() {
        isAttacking = true;
        commandNum = 0;
    }

    private void drawPartyMembers(Graphics2D g2) {
        int baseX = gp.tileSize * 2;
        int baseY = gp.screenHeight / 2 - gp.tileSize * 3;
        int spacing = gp.tileSize * 4;

        int x = baseX;
        int y = baseY;
        for (int i = 0; i < battleQueue.size(); i++) {
            Entity entity = battleQueue.get(i);
            if (entity != null && entity != currentEnemy) {
                if (entity == gp.player) {
                    x = baseX + (spacing);
                    y = baseY + (gp.tileSize * 2);
                }
                else if (entity == gp.companion2){
                    x = baseX;
                    y = baseY + (spacing);
                }
                else{
                    x = baseX;
                    y = baseY;
                }

                BufferedImage sprite = entity.right1;

                g2.drawImage(sprite, x, y, gp.tileSize*2, gp.tileSize*2, null);

                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14F));
                g2.drawString(entity.getName(), x, y-50);
                String text = "Level: " + entity.level;
                g2.drawString(text, x, y-35);

                double oneScale = gp.tileSize/entity.maxHP;
                double hpBarValue = oneScale * entity.hp;

                g2.setColor(new Color(255, 255, 255));
                g2.fillRect(x, y-33, gp.tileSize, 10);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(x, y-31, (int) hpBarValue, 8);

                text = gp.df.format(entity.hp) + "/" + gp.df.format(entity.maxHP);
                g2.setFont(g2.getFont().deriveFont( 10f));
                g2.setColor(Color.white);
                g2.drawString(text, x + gp.tileSize+2, y-23);

                double oneScale1 = gp.tileSize/entity.maxEnergy;
                double energyBarValue = oneScale1 * entity.energy;

                g2.setColor(new Color(255, 255, 255));
                g2.fillRect(x, y-22, gp.tileSize, 10);

                g2.setColor(new Color(255, 227, 24));
                g2.fillRect(x, y-20, (int) energyBarValue, 8);

                text = gp.df.format(entity.energy) + "/" + gp.df.format(entity.maxEnergy);
                g2.setFont(g2.getFont().deriveFont( 10f));
                g2.setColor(Color.white);
                g2.drawString(text, x + gp.tileSize+2, y-9);

                if (i == currentTurn) {
                    g2.setColor(Color.YELLOW);
                    g2.drawRect(x - 2, y - 2, gp.tileSize*2 + 4, gp.tileSize*2  + 4);
                }
                if(entity.isAttacked){
                    damaged(g2, entity, x, y);
                }
            }
        }
    }

    public void drawTurnQueue(Graphics2D g2) {
        int baseX = gp.tileSize;
        int baseY = gp.tileSize;
        int spacing = gp.tileSize * 2; // Fixed spacing

        int x = baseX;
        int y = baseY;

        for (int i = 0; i < battleQueue.size(); i++) {
            Entity entity = battleQueue.get(i);

            BufferedImage sprite = entity.down1;

            g2.drawImage(sprite, x, y, gp.tileSize, gp.tileSize, null);

            if (i == currentTurn) {
                g2.setColor(Color.YELLOW);
                g2.drawRect(x - 2, y - 2, gp.tileSize + 4, gp.tileSize + 4);
            }

            x += spacing;
        }
    }

    public void drawEnemy(Graphics2D g2){
        for (int i = 0; i < battleQueue.size(); i++) {
            Entity entity = battleQueue.get(i);
            if (entity != null && entity == currentEnemy) {
                int x = gp.screenWidth / 2 + gp.tileSize * 2;
                int y = gp.screenHeight / 2 - gp.tileSize;
                BufferedImage sprite = currentEnemy.left1;

                g2.drawImage(sprite, x, y, gp.tileSize*2, gp.tileSize*2, null);

                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14F));
                g2.drawString(currentEnemy.getName(), x, y - 50);
                String text = "Level: " + currentEnemy.level;
                g2.drawString(text, x, y - 35);

                double oneScale = gp.tileSize / currentEnemy.maxHP;
                double hpBarValue = oneScale * currentEnemy.hp;

                g2.setColor(new Color(255, 255, 255));
                g2.fillRect(x, y - 33, gp.tileSize, 10);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(x, y - 31, (int) hpBarValue, 8);

                text = gp.df.format(currentEnemy.hp) + "/" + gp.df.format(currentEnemy.maxHP);
                g2.setFont(g2.getFont().deriveFont(10f));
                g2.setColor(Color.white);
                g2.drawString(text, x + gp.tileSize + 2, y - 23);

                double oneScale1 = gp.tileSize / currentEnemy.maxEnergy;
                double energyBarValue = oneScale1 * currentEnemy.energy;

                g2.setColor(new Color(255, 255, 255));
                g2.fillRect(x, y - 22, gp.tileSize, 10);

                g2.setColor(new Color(255, 227, 24));
                g2.fillRect(x, y - 20, (int) energyBarValue, 8);

                text = gp.df.format(currentEnemy.energy) + "/" + gp.df.format(currentEnemy.maxEnergy);
                g2.setFont(g2.getFont().deriveFont(10f));
                g2.setColor(Color.white);
                g2.drawString(text, x + gp.tileSize + 2, y - 9);

                if (i == currentTurn) {
                    g2.setColor(Color.YELLOW);
                    g2.drawRect(x - 2, y - 2, gp.tileSize*2  + 4, gp.tileSize*2  + 4);
                }
                if(currentEnemy.isAttacked){
                    damaged(g2, entity, x, y);
                }
            }
        }
    }

    public void damaged(Graphics2D g2, Entity entity, int x, int y){
        if(entity.isAttacked){
            y++;
            damageBuffer++;
            g2.setColor(new Color(0, 0, 0));
            g2.setFont(g2.getFont().deriveFont(20f));
            g2.drawString(String.valueOf(damage), x + gp.tileSize*2 + 10, y - gp.tileSize);
            g2.setColor(new Color(255, 0, 0));
            g2.setFont(g2.getFont().deriveFont(18f));
            g2.drawString(String.valueOf(damage), x + gp.tileSize*2 + 10, y - gp.tileSize );
            if(damageBuffer > 200){
                entity.isAttacked = false;
                damageBuffer = 0;
            }
        }
    }

    public void damage(String targetArea) {
        isAttacking = true;
        int hitRoll = gp.randomize(1, 100);
        double luckFactor = gp.player.luck * 0.01;

        int hitChance = 0;
        double damageMultiplier = 1.0;

        switch (targetArea) {
            case "HEAD":
                hitChance = (int)(40 + (luckFactor * 100));
                damageMultiplier = gp.randomize(2, 3);
                break;
            case "TORSO":
                hitChance = (int)(85 + (luckFactor * 100));
                damageMultiplier = 1.0;
                break;
            case "LEGS":
                hitChance = (int)(65 + (luckFactor * 100));
                damageMultiplier = 1.0;

                currentEnemy.speed -= 1;
                break;
        }

        if (hitRoll <= hitChance) {
            damage = (gp.player.attack - currentEnemy.defense) * damageMultiplier;
            damage = Math.max(damage, 1);
            currentEnemy.hp -= damage;
            currentEnemy.isAttacked = true;
            if(currentEnemy.hp < 0){
                currentEnemy.hp = 0;
            }
//            gp.ui.addMessage("Hit!" + gp.player.getName() + " deals " + damage + " damage to " + currentEnemy.getName());
        } else {
//            gp.ui.addMessage(gp.player.getName() + " missed!");
        }

        if(currentEnemy.hp == 0){
            endBattle();
        }
    }

    public void damagePlayer(String targetArea) {
        int hitRoll = gp.randomize(1, 100);
        double luckFactor = currentEnemy.luck * 0.01;

        int hitChance = 0;
        double damageMultiplier = 1.0;


        switch (targetArea) {
            case "HEAD":
                hitChance = (int)(40 + (luckFactor * 100));
                damageMultiplier = gp.randomize(2, 3);
                break;
            case "TORSO":
                hitChance = (int)(85 + (luckFactor * 100));
                damageMultiplier = 1.0;
                break;
            case "LEGS":
                hitChance = (int)(65 + (luckFactor * 100));
                damageMultiplier = 1.0;
                gp.player.speed -= 1;
                break;
        }

        if (hitRoll <= hitChance) {
            damage = (currentEnemy.attack - gp.player.defense) * damageMultiplier;
            damage = Math.max(damage, 1);
            gp.player.hp -= damage;
            if(gp.player.hp < 0){
                gp.player.hp = 0;
            }
            if(gp.player.hp <= 50 && currentEnemy == gp.livingEntity[3][0]){
                gp.player.hp = 50;
                eventEndBattle();
            }
//            gp.ui.addMessage("Hit! " + currentEnemy.getName() + " deals " + damage + " damage to " + gp.player.getName());
        } else {
//            gp.ui.addMessage(currentEnemy.getName() + " missed!");
        }

        if(gp.player.hp == 0){
            enemyEndBattle();
        }
    }

    public void endBattle(){
        double expGain = currentEnemy.nextLevelExp/2;

        gp.player.exp += expGain;
        currentEnemy.isDefeated = true;
        currentEnemy.hollowCounter++;
        System.out.println(currentEnemy.getName() + " has died " + currentEnemy.hollowCounter + " times");
        gp.gameState = gp.playState;
        System.out.println("Battle finished.");
    }

    public void enemyEndBattle(){
        double expGain = gp.player.nextLevelExp/3;

        currentEnemy.exp += expGain;

        gp.player.isDefeated = true;
        gp.player.hollowCounter++;
        System.out.println(gp.player.getName() + " has died " + gp.player.hollowCounter + " times");
        gp.gameState = gp.playState;
        System.out.println("Battle finished.");
        emptyImages();
    }
    public void eventEndBattle(){
        double expGain = gp.player.nextLevelExp;

        gp.player.exp += expGain;

        System.out.println(gp.player.getName() + " has died " + gp.player.hollowCounter + " times but its an event");
        gp.gameState = gp.eventState;
        gp.event.sequenceCheck++;
        System.out.println("Battle finished.");
        emptyImages();
    }

    public void enemyTurn(){
        if(currentEnemy != null){
            int choice = gp.randomize(0,2);

            switch (choice){
                case 0:{
                    damagePlayer("HEAD");
                    break;
                }
                case 1:{
                    damagePlayer("TORSO");
                    break;
                }
                case 2:{
                    damagePlayer("LEGS");
                    break;
                }
            }
        }
    }

}
