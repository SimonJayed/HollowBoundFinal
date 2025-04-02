package entity;

import main.GamePanel;

public class NPC_Miming extends Entity {

    public NPC_Miming(GamePanel gp) {
        super(gp);

        type = 1;

        setName("Miming");

        this.solidArea.x = 0;
        this.solidArea.y = 0;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 48-sizeIncrement;
        this.solidArea.height = 48-sizeIncrement;
        sizeIncrement = gp.randomize(-15, 0);

        int skin = gp.randomize(1,10);
        if(skin <= 3){
            getImage("miming2");
        }
        else if (skin <= 5){
            getImage("miming3");
        }
        else if (skin <= 9){
            getImage("miming");
        }
        else{
            getImage("mamapausy");
        }

        setDefaultValues(1, 250, 250,5,5, 8, 10, 13, 9);
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
        dialogues[0] = "Meowwww...";
        dialogues[1] = "Prrrrrrrrrrrrrrrrrrrrr...";
    }

    public void speak(){
        super.speak();
    }
}


