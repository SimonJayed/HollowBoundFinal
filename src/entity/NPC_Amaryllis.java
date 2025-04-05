package entity;

import entity.components.Skill;
import main.GamePanel;

public class NPC_Amaryllis extends Entity {

    public NPC_Amaryllis(GamePanel gp) {
        super(gp);

        setName("Amaryllis");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;

        getImage("amaryllis");
        setDefaultValues(1, 250, 250,5,5, 8, 10, 13, 9);
        setDialogue();

        skills.add(new Skill("Nature's Embrace", "Lowers the enemy's agility with a chance to stun.", 135.6, maxEnergy*0.3, 2));
        skills.add(new Skill("Liquid Experiment", "Gives a random buff to an ally.", 25, maxEnergy*0.3, 2));
        skills.add(new Skill("Unleash", "Transforms into her beast form and gives a boost to her stats for 3 turns", 135.6, maxEnergy*0.8, 5));
    }

    public void setStatIncrements(){
        this.vit += 1;
        this.pow += 1;
        this.mag += 2;
        this.agi += 3;
    }

    public void useSkill1(Entity entity){


        gp.ui.addMessage(getName() + "");
        skills.get(0).use();
    }

    public void useSkill2(Entity entity){
        skills.get(1).use();
    }

    public void useSkill3(Entity entity){
        skills.get(2).use();
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

    public void speak(){
        super.speak();
    }
}


