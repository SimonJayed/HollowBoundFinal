package entity;

import entity.components.Skill;
import main.GamePanel;

public class NPC_VillagerOldMan extends Entity {

    public NPC_VillagerOldMan(GamePanel gp) {
        super(gp);

        type = 1;

        setName("Krone");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 31;
        this.solidArea.height = 33;

        getImage("villagerOldMan");

        setDefaultValues(1, 250, 250,1,15, 10, 10, 13, 9);
        setDialogue();

        skills.add(new Skill("Punch", "Punches enemy.", pow*2, 25+maxEnergy*0.2, 2, "DAMAGE"));
        skills.add(new Skill("Stronger Punch", "Punches enemy harder.", pow*3, 25+maxEnergy*0.4, 3, "DAMAGE"));
        skills.add(new Skill("STRONGEST PUNCH", "PUNCHES ENEMY HARDEST.", pow*5, 25+maxEnergy*0.8, 5, "DAMAGE"));

    }

    public void setStatIncrements(){
        this.vit += 3;
        this.pow += 4;
        this.mag += 3;
        this.agi += 3;
    }

    public void useSkill(int skillIndex, Entity target) {
        switch(skillIndex){
            case 0:{
                this.energy -= skills.get(0).energyCost;
                skills.get(0).use();
                gp.battleScreen.output = skills.get(0).power;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);


                target.hp -= gp.battleScreen.output;

                gp.ui.addMessage(getName() + " punches " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
            case 1:{
                this.energy -= skills.get(1).energyCost;
                skills.get(1).use();
                gp.battleScreen.output = skills.get(1).power;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);


                target.hp -= gp.battleScreen.output;

                gp.ui.addMessage(getName() + " punches hard on " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
            case 2:{
                this.energy -= skills.get(2).energyCost;
                skills.get(2).use();
                gp.battleScreen.output = skills.get(1).power;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);

                target.hp -= gp.battleScreen.output;
                gp.ui.addMessage(getName() + " punches REALLY HARD on " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
        }
    }


    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter >= gp.randomize(120, 500)) {
            int i = gp.randomize(1, 150);

            if (i <= 25 ) {
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

    public void setDialogue() {
        dialogues[0] = "Villager:\n Hello hehe, welcome to our beloved village.";
        dialogues[1] = "Fort: \n Weird.";
    }

    public void speak(){
        super.speak();
    }
}
