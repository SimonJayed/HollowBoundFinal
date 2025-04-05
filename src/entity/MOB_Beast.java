package entity;

import main.GamePanel;

public class MOB_Beast extends Entity {
    public MOB_Beast(GamePanel gp) {
        super(gp);

        type = 2;
        isMob = true;
        setName("Hollow Beast");

        this.solidArea.x = 10;
        this.solidArea.y = 28;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 88;
        this.solidArea.height = 70;
        sizeIncrement = 80;

        getImage("hollowedBeast");
        setDefaultValues(1, 250, 250,2,10, 13, 10, 13, 0);
        setLevel(gp.randomize(gp.player.areaLevel, gp.player.areaLevel+8));
        setDialogue();
    }

    public void setStatIncrements(){
        this.vit += 8;
        this.pow += 5;
        this.mag += 1;
        this.agi += 1;
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


