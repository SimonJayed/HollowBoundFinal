package entity;

import entity.components.Skill;
import main.GamePanel;

public class NPC_Sylvie extends Entity {

    public NPC_Sylvie(GamePanel gp) {
        super(gp);

        type = 2;
        setName("Sylvie");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;

        getImage("sylvie");
        setDefaultValues(1, 100, 250,3, 7, 5, 19, 5,  9);

        skills.add(new Skill("Nature's Embrace", "Channels natural energy to heal a single ally", mag*2, maxEnergy*0.2, 2));
        skills.add(new Skill("Thorned Whip", "Summons thorny vines to strike at an enemy", 25, maxEnergy*0.4, 1));
        skills.add(new Skill("Bloom of Life", "Creates an explosion of natural energy that damages enemies and heals allies", 135.6, maxEnergy*0.8, 2));

    }

    public void setStatIncrements(){
        this.vit += 1;
        this.pow += 1;
        this.mag += 4;
        this.agi += 1;
    }

    public void useSkill1(Entity target){
        skills.get(0).power += skills.get(0).energyCost;
        target.hp += skills.get(0).power;
        gp.ui.addMessage(getName() + " heals " + target.getName() + " for " + skills.get(0).power + ".");
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

        if (actionLockCounter >= gp.randomize(120, 750)) {
//            System.out.println(getName() + " moves but has been defeated is " + isDefeated );
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
        gp.event.dialogues.add("Hello, lad.");
//        gp.event.dialogues[1] = "You seem lost.";
//        gp.event.dialogues[2] = "I can fix that.";
//        gp.event.dialogues[3] = "No. Seriously, dude... \nI can.";
//        gp.event.dialogues[4] = "Why tf does no one believe what I \nsay?";
    }

    public void speak(){
        super.speak();
    }
}


