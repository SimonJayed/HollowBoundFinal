package entity;

import main.GamePanel;

public class MOB_HollowHuman extends Entity {
    public MOB_HollowHuman(GamePanel gp) {
        super(gp);

        type = 2;
        isMob = true;
        setName("Hollow Human");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;

        getImage("hollowedMob");
        setDefaultValues(1, 250, 250,2,5, 5, 5, 5, 0);
        setDialogue();
    }

    public void setStatIncrements(){
        this.vit += 2;
        this.pow += 2;
        this.mag += 2;
        this.agi += 2;
    }

    public void checkDefeated(){
        if (!isDefeated) return;

        this.deathBuffer++;
        isIdling = true;
        collision = false;

        if (this.deathBuffer < 1000 && hollowCounter >= 5) return;
        if (this.deathBuffer < 250 && hollowCounter < 5) return;
        if (hasEvent) return;

        isDefeated = false;
        hp = maxHP;
        energy = maxEnergy;
        exp = nextLevelExp;

        for (int i = 0; i < hollowCounter; i++) {
            setStatIncrements();
            calculateStats();
            System.out.println(getName() + " is being strengthened.");
        }

        this.deathBuffer = 0;
        this.worldX = this.spawnPointX;
        this.worldY = this.spawnPointY;
        collision = true;

        System.out.println(getName() + " has respawned.");
        System.out.println(getName() + " died " + hollowCounter + " times");
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


