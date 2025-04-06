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

        skills.add(new Skill("Rage Bait", "Taunts the enemy and increases defense for 2 turns.", vit/2, 25+maxEnergy*0.2, 3, "BUFF_SELF"));
        skills.add(new Skill("Meat Shield", "Grants additional defense to an ally for 3 turns.", vit*1.5, 25+maxEnergy*0.4, 3, "BUFF_ALLY"));
        skills.add(new Skill("SMAAAAASHHHHH", "Hits the enemy equivalent to lost health and vitality stat.", (maxHP-hp*0.8)+(vit*1.2), 25+maxEnergy*0.8, 5, "DAMAGE"));
    }

    public void setStatIncrements(){
        this.vit += 3;
        this.pow += 2;
        this.mag += 1;
        this.agi += 1;
    }

    public void useSkill(int skillIndex, Entity target) {
        switch(skillIndex){
            case 0:{
                this.energy -= skills.get(0).energyCost;
                gp.battleScreen.output = skills.get(0).power;
                skills.get(0).use();

                target.tempDef = target.defense;
                target.defense += gp.battleScreen.output;
                gp.ui.addMessage(getName() + " taunts " + target.getName() + " and increases own defense by " + gp.battleScreen.output + " for 2 turns.");

                target.hardened = 2;
                aggro = 2;
                break;
            }
            case 1:{
                this.energy -= skills.get(1).energyCost;
                gp.battleScreen.output += skills.get(1).power;
                skills.get(1).use();

                target.defense += gp.battleScreen.output;
                gp.ui.addMessage(getName() + " shields " + target.getName() + " and increases their defense by " + gp.battleScreen.output + " for 3 turns.");

                target.hardened = 3;
                break;
            }
            case 2:{
                this.energy -= skills.get(2).energyCost;
                gp.battleScreen.output += skills.get(2).power;
                skills.get(2).use();

                target.defense += gp.battleScreen.output;
                gp.ui.addMessage(getName() + " hits " + target.getName() + " with the UNITED STATES OF SMASH for " + gp.battleScreen.output + " damage.");

                break;
            }
        }
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


