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
    Graphics2D g2;


    BufferedImage background = null;
    BufferedImage actionWindow = null;
    BufferedImage actionWindow1 = null;
    BufferedImage heart = null;
    BufferedImage energy = null;

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
        UtilityTool uTool = new UtilityTool();
        try {
            if(gp.currentMap == 0){
                background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/battle/forestIntro.png")));
            }
            else{
                background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/battle/corruptedForest.png")));
            }
            actionWindow = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/actionWindow2.png")));
            actionWindow = uTool.scaleImage(actionWindow, gp.screenWidth, gp.screenHeight);
            actionWindow1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/actionWindow1.png")));
            actionWindow1 = uTool.scaleImage(actionWindow1, gp.screenWidth, gp.screenHeight);
            heart = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/heart.png")));
            heart = uTool.scaleImage(heart, gp.tileSize/2, gp.tileSize/2);
            energy = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/energy.png")));
            energy = uTool.scaleImage(energy, gp.tileSize/2, gp.tileSize/2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emptyImages(){
        background = null;
        actionWindow = null;
        actionWindow1 = null;
        heart = null;
        energy = null;
    }

    public void startBattle(Entity enemy) {
        gp.gameState = gp.battleState;
        currentTurn = 0;
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
        this.g2 = g2;
        g2.setColor(Color.white);
        g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);

        gp.ui.drawSubWindow(gp.tileSize*6, 0, gp.tileSize*12, gp.tileSize*3);
        gp.ui.drawSubWindow(gp.tileSize*6, gp.screenHeight-gp.tileSize*4, gp.tileSize*12, gp.tileSize*4);
        gp.ui.drawSubWindow(gp.tileSize-3, gp.tileSize/2-10, gp.tileSize*battleQueue.size()+3, gp.tileSize*2+15);


        update();
        gp.ui.drawMessage(gp.tileSize*6 + 10, gp.tileSize/2);

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

        if(currentEntity.hp <= 0){
            currentTurnFinished = true;
        }

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
        int y = 0;

        x += gp.tileSize*8;

    }

    public void attack() {
        isAttacking = true;
        commandNum = 0;
    }

    private void drawPartyMembers(Graphics2D g2) {
        int baseX = gp.tileSize * 2 + gp.tileSize;
        int baseY = gp.screenHeight / 2 - gp.tileSize;
        int spacing = gp.tileSize * 4;

        int x = baseX;
        int y = baseY;
        for (int i = 0; i < battleQueue.size(); i++) {
            Entity entity = battleQueue.get(i);
            if (entity != null && entity != currentEnemy) {
                if (entity == gp.player) {
                    x = baseX + (spacing);
                    y = baseY + (gp.tileSize);
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
                if(entity.hp <= 0){
                    sprite = entity.defeated1;
                }

                g2.drawImage(sprite, x, y, gp.tileSize, gp.tileSize, null);

                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14F));
                g2.drawString(entity.getName(), x, y-50);
                String text = "Level: " + entity.level;
                g2.drawString(text, x, y-35);

                double oneScale = gp.tileSize/entity.maxHP;
                double hpBarValue = oneScale * entity.hp;

                g2.setColor(new Color(255, 255, 255));
                g2.fillRect(x, y-29, gp.tileSize, 10);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(x, y-28, (int) hpBarValue, 8);
                g2.drawImage(heart, x-10, y-gp.tileSize/2-7, gp.tileSize/3-3, gp.tileSize/3-3, null);

                text = gp.df.format(entity.hp) + "/" + gp.df.format(entity.maxHP);
                g2.setFont(g2.getFont().deriveFont( 10f));
                g2.setColor(Color.white);
                g2.drawString(text, x + gp.tileSize+2, y-21);

                double oneScale1 = gp.tileSize/entity.maxEnergy;
                double energyBarValue = oneScale1 * entity.energy;

                g2.setColor(new Color(255, 255, 255));
                g2.fillRect(x, y-14, gp.tileSize, 10);

                g2.setColor(new Color(255, 227, 24));
                g2.fillRect(x, y-13, (int) energyBarValue, 8);
                g2.drawImage(energy, x-10, y-gp.tileSize/2+8, gp.tileSize/3-3, gp.tileSize/3-3, null);

                text = gp.df.format(entity.energy) + "/" + gp.df.format(entity.maxEnergy);
                g2.setFont(g2.getFont().deriveFont( 10f));
                g2.setColor(Color.white);
                g2.drawString(text, x + gp.tileSize+2, y-5);

                if (i == currentTurn) {
                    if(!isAttacking) {
                        int tempX = x - (gp.tileSize*2+gp.tileSize/2)+5;
                        int tempY = y + 15;
                        drawOption("ATTACK", tempX, tempY, 0);
                        tempX += 3;
                        tempY += gp.tileSize/2;

                        drawOption("SKILL", tempX, tempY, 1);
                        tempX += 3;
                        tempY += gp.tileSize/2;

                        drawOption("ITEM", tempX, tempY, 2);
                        tempX += 3;
                        tempY += gp.tileSize/2;

                        drawOption("FLEE", tempX, tempY, 3);
                    }
                    if(isAttacking){
                        int tempX = x - (gp.tileSize*2+gp.tileSize/2)+5;
                        int tempY = y + 10;
                        drawOption("HEAD", tempX, tempY, 0);
                        tempX += 3;
                        tempY += gp.tileSize/2;

                        drawOption("TORSO", tempX, tempY, 1);
                    }
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
        int spacing = gp.tileSize;

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14F));
        g2.drawString("TURN QUEUE", gp.tileSize, gp.tileSize-10);

        int x = baseX;
        int y = baseY;

        for (int i = 0; i < battleQueue.size(); i++) {
            Entity entity = battleQueue.get(i);

            BufferedImage sprite = entity.down1;

            g2.drawImage(sprite, x, y, gp.tileSize, gp.tileSize, null);

            if (i == currentTurn) {
                if(entity == currentEnemy){
                    g2.setColor(Color.RED);
                }
                else{
                    g2.setColor(Color.YELLOW);
                }
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
                int y = gp.screenHeight / 2;
                BufferedImage sprite = currentEnemy.left1;

                g2.drawImage(sprite, x, y-currentEnemy.sizeIncrement, gp.tileSize+currentEnemy.sizeIncrement, gp.tileSize+currentEnemy.sizeIncrement, null);

                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14F));
                g2.drawString(entity.getName(), x, y-50-currentEnemy.sizeIncrement);
                String text = "Level: " + entity.level;
                g2.drawString(text, x, y-35-currentEnemy.sizeIncrement);

                double oneScale = gp.tileSize/entity.maxHP;
                double hpBarValue = oneScale * entity.hp;

                g2.setColor(new Color(255, 255, 255));
                g2.fillRect(x, y-29-currentEnemy.sizeIncrement, gp.tileSize, 10);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(x, y-28-currentEnemy.sizeIncrement, (int) hpBarValue, 8);
                g2.drawImage(heart, x-10, y-gp.tileSize/2-7-currentEnemy.sizeIncrement, gp.tileSize/3-3, gp.tileSize/3-3, null);

                text = gp.df.format(entity.hp) + "/" + gp.df.format(entity.maxHP);
                g2.setFont(g2.getFont().deriveFont( 10f));
                g2.setColor(Color.white);
                g2.drawString(text, x + gp.tileSize+2, y-21-currentEnemy.sizeIncrement);

                double oneScale1 = gp.tileSize/entity.maxEnergy;
                double energyBarValue = oneScale1 * entity.energy;

                g2.setColor(new Color(255, 255, 255));
                g2.fillRect(x, y-14-currentEnemy.sizeIncrement, gp.tileSize, 10);

                g2.setColor(new Color(255, 227, 24));
                g2.fillRect(x, y-13-currentEnemy.sizeIncrement, (int) energyBarValue, 8);
                g2.drawImage(energy, x-10, y-gp.tileSize/2+8-currentEnemy.sizeIncrement, gp.tileSize/3-3, gp.tileSize/3-3, null);

                text = gp.df.format(entity.energy) + "/" + gp.df.format(entity.maxEnergy);
                g2.setFont(g2.getFont().deriveFont( 10f));
                g2.setColor(Color.white);
                g2.drawString(text, x + gp.tileSize+2, y-5-currentEnemy.sizeIncrement);

                if(currentEnemy.isAttacked){
                    damaged(g2, entity, x, y);
                }
            }
        }
    }

    public void damaged(Graphics2D g2, Entity entity, int x, int y) {
        showDamage(g2, entity, x, y, damage, entity.isHealed);
    }

    public void showDamage(Graphics2D g2, Entity entity, int x, int y, double amount, boolean isHealing) {
        if (entity.isAttacked || entity.isHealed) {
            y++;
            damageBuffer++;

            String text = "";
            Color textColor;

            if (entity.dodged) {
                text = "Dodged!";
                textColor = Color.WHITE;
            } else if (isHealing) {
                text = "+" + (int)amount;
                textColor = new Color(0, 255, 0);
            } else {
                text = "-" + (int)amount;
                textColor = new Color(255, 0, 0);
            }

            g2.setColor(new Color(0, 0, 0));
            g2.setFont(g2.getFont().deriveFont(20f));
            g2.drawString(text, x + gp.tileSize*2 + 10, y - gp.tileSize);

            // Draw main text with appropriate color
            g2.setColor(textColor); // Use the color we determined above
            g2.setFont(g2.getFont().deriveFont(18f));
            g2.drawString(text, x + gp.tileSize*2 + 10, y - gp.tileSize);

            if (damageBuffer > 20 && !currentTurnFinished) {
                entity.isAttacked = false;
                entity.isHealed = false;
                damageBuffer = 0;
            }
        }
    }

    public double calculateDamage(Entity attacker, Entity defender, String targetArea) {
        int hitRoll = gp.randomize(1, 100);
        double luckFactor = attacker.luck * 0.01 - (defender.luck * 0.01);

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
        }

        hitChance = Math.max(hitChance, 5);

        if (hitRoll <= hitChance) {
            double damage = (attacker.attack - defender.defense) * damageMultiplier;
            damage = Math.max(damage, 1);
            return damage;
        } else {
            return 0;
        }
    }

    public void playerAttack(String targetArea) {
        Entity attacker = battleQueue.get(currentTurn);

        damage = calculateDamage(attacker, currentEnemy, targetArea);

        if (damage > 0) {
            currentEnemy.hp -= damage;
            currentEnemy.isAttacked = true;

            if (currentEnemy.hp < 0) {
                currentEnemy.hp = 0;
            }
            currentEnemy.dodged = false;
            gp.ui.addMessage("Hit! " + attacker.getName() + " deals " + (int)damage + " damage to " + currentEnemy.getName());
        } else {
            currentEnemy.dodged = true;
            gp.ui.addMessage(attacker.getName() + " missed!");
        }

        if (currentEnemy.hp == 0) {
            endBattle();
        }

        isAttacking = false;
        currentTurnFinished = true;
    }

    public void enemyAttack(Entity target) {
        if (currentEnemy == null) return;

        String targetArea = (gp.randomize(0, 1) == 0) ? "HEAD" : "TORSO";

        damage = calculateDamage(currentEnemy, target, targetArea);

        if (damage > 0) {
            target.hp -= damage;
            target.isAttacked = true;

            if (target.hp < 0) {
                target.hp = 0;
            }

            if (target.hp <= 50 && currentEnemy == gp.livingEntity[3][0]) {
                target.hp = 50;
                eventEndBattle();
                return;
            }
            target.dodged = false;
            gp.ui.addMessage("Hit! " + currentEnemy.getName() + " deals " + (int)damage + " damage to " + target.getName());
        } else {
            target.dodged = true;
            gp.ui.addMessage(currentEnemy.getName() + " missed!");
        }

        boolean allDefeated = true;
        for (Entity member : battleQueue) {
            if (member != currentEnemy && member != null && member.hp > 0) {
                allDefeated = false;
                break;
            }
        }

        if (allDefeated) {
            enemyEndBattle();
        }
    }

    public void endBattle(){
        double expGain = currentEnemy.nextLevelExp/3;

        for(Entity member : battleQueue){
            if(member != currentEnemy){
                member.exp += expGain;
                member.hp += 5;
                member.regen();
                member.checkLevelUp();
            }
        }
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
            ArrayList<Entity> targets = new ArrayList<>();
            for (Entity entity : battleQueue) {
                if (entity != currentEnemy && entity != null && entity.hp > 0) {
                    targets.add(entity);
                }
            }

            int targetIndex = gp.randomize(0, targets.size() - 1);

            enemyAttack(targets.get(targetIndex));
        }
    }

    public void drawOption(String option, int x, int y, int commandNum){
        g2.setColor(new Color(20, 61, 143, 200));
        drawActionWindow(x-8, y-gp.tileSize/2+5, gp.tileSize*2+gp.tileSize/2, gp.tileSize/2);
        g2.setFont(g2.getFont().deriveFont(20f));
        g2.setColor(new Color(59, 61, 62));
        g2.drawString(option, x, y);

        if(this.commandNum == commandNum){
            g2.setColor(new Color(255, 0, 0));
            g2.drawString(option, x, y);
        }
    }

    public void drawActionWindow(int x, int y, int width, int height){
        g2.drawImage(actionWindow, x, y, width, height, null);
    }

}
