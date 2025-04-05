package entity;

import entity.components.Skill;
import main.GamePanel;

import java.awt.*;

public class NPC_Fort extends Entity {

    public NPC_Fort(GamePanel gp) {
        super(gp);

        setName("Fort");

        solidArea = new Rectangle();
        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;

        getImage("fort");
        setDefaultValues(1, 100, 50,4, 15, 6, 4, 5,  15);

        skills.add(new Skill("Rage Bait", "Taunts the enemy for 3 turns and increases defense by skill power.", (vit*1.2)+(maxEnergy*0.2), maxEnergy*0.2, 4));
        skills.add(new Skill("Meat Shield", "Grants a shield to an ally", (maxHP*0.2)+(vit*1.5)+(maxEnergy*0.4), maxEnergy*0.4, 2));
        skills.add(new Skill("SMAAAAASHHHHH", "Deals damage to the enemy equivalent to health lost and vitality stat.", (maxHP-hp*0.8)+(vit*1.2), maxEnergy*0.8, 2));
    }

    public void setStatIncrements(){
        this.vit += 3;
        this.pow += 2;
        this.mag += 1;
        this.agi += 1;
    }

    public void calculateStats(){
        this.maxHP = initialHP + (15 * level) + (vit * 2);
        this.maxEnergy = initialEnergy + (15 * level) + (mag * 2);
        this.energyRegen = maxEnergy * 0.1 + (mag / 100);

        this.attack = pow * 3;
        this.defense = vit * 1.5;
        nextLevelExp = 10 * Math.pow(level, 2);
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter >= gp.randomize(120, 750)) {
            int i = gp.randomize(1, 150);

            if (i <= 25) {
                direction = "up";
                isIdling = false;
            } else if (i <= 50) {
                direction = "down";
                isIdling = false;
            } else if (i <= 75) {
                direction = "left";
                isIdling = false;
            } else if (i <= 100) {
                direction = "right";
                isIdling = false;
            } else if (i <= 125) {
                isIdling = true;
            }
            actionLockCounter = 0;
        }
        spriteAnim(2);
    }

    public void setDialogue(){
//        gp.event.dialogues[0] = "Hello, lad.";
//        gp.event.dialogues[1] = "You seem lost.";
//        gp.event.dialogues[2] = "I can fix that.";
//        gp.event.dialogues[3] = "No. Seriously, dude... \nI can.";
//        gp.event.dialogues[4] = "Why tf does no one believe what I \nsay?";
    }

    public void speak(){
        super.speak();
    }
}


