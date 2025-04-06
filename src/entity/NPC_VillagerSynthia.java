package entity;

import main.GamePanel;

public class NPC_VillagerSynthia extends Entity {

    public NPC_VillagerSynthia(GamePanel gp) {
        super(gp);

        type = 1;

        setName("Synthia");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 31;
        this.solidArea.height = 33;

        getImage("villagerGirl");

        setDefaultValues(1, 250, 250,2,5, 8, 10, 13, 9);
        setDialogue();
    }

    public void setStatIncrements(){
        this.vit += 1;
        this.pow += 1;
        this.mag += 2;
        this.agi += 3;
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
        dialogues[1] = "Fort: \n Don't you have anything else to say?";
        dialogues[2] = "Villager:\n Hello hehe, welcome to our beloved village.";
        dialogues[3] = "Villager:\n Hello hehe, welcome to our beloved village.";
        dialogues[4] = "Fort: \n Let's go, guys. This place creeps me out.";
    }

    public void speak(){
        super.speak();
    }
}
