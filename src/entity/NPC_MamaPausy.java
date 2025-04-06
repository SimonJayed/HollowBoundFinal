package entity;

import entity.components.Skill;
import main.GamePanel;

public class NPC_MamaPausy extends Entity {

    public NPC_MamaPausy(GamePanel gp) {
        super(gp);

        type = 1;

        setName("Mama Pausy");

        this.solidArea.x = 10;
        this.solidArea.y = 28;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 88;
        this.solidArea.height = 70;
        sizeIncrement = 50;

        getImage("mamapausy");
        setDefaultValues(1, 250, 250,5,5, 8, 10, 30, 9);
        setDialogue();

        skills.add(new Skill("Scatch", "Scatches enemy.", agi*2, 25+maxEnergy*0.2, 2, "DAMAGE"));
        skills.add(new Skill("Stronger Scatch", "Scatches enemy harder.", agi*4, 25+maxEnergy*0.4, 3, "DAMAGE"));
        skills.add(new Skill("STRONGEST SCRATCH", "SCRATCHES ENEMY HARDEST.", agi*8, 25+maxEnergy*0.8, 5, "DAMAGE"));

    }

    public void setStatIncrements(){
        this.vit += 1;
        this.pow += 1;
        this.mag += 2;
        this.agi += 4;
    }

    public void useSkill(int skillIndex, Entity target) {
        switch(skillIndex){
            case 0:{
                this.energy -= skills.get(0).energyCost;
                skills.get(0).use();
                gp.battleScreen.output = skills.get(0).power;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);


                target.hp -= gp.battleScreen.output;

                gp.ui.addMessage(getName() + " scratches " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
            case 1:{
                this.energy -= skills.get(1).energyCost;
                skills.get(1).use();
                gp.battleScreen.output = skills.get(1).power;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);


                target.hp -= gp.battleScreen.output;

                gp.ui.addMessage(getName() + " scratcges hard on " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
            case 2:{
                this.energy -= skills.get(2).energyCost;
                skills.get(2).use();
                gp.battleScreen.output = skills.get(1).power;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);

                target.hp -= gp.battleScreen.output;
                gp.ui.addMessage(getName() + " slices REALLY HARD on " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
        }
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter >= gp.randomize(120, 250)) {
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

    public void setDialogue() {
    }

    public void speak(){
        super.speak();
    }
}


