package entity;

import main.GamePanel;

public class MOB_Swordsman extends Entity {

    public MOB_Swordsman(GamePanel gp) {
        super(gp);

        type = 2;

        setName("Hollow Swordsman");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;
        sizeIncrement = 10;

        getImage("hollowedSwordsman");
        setDefaultValues(1, 100, 100,5,5, 8, 10, 13, 9);
        setLevel(gp.randomize(gp.player.areaLevel, gp.player.areaLevel+5));
        setDialogue();
    }

    public void setStatIncrements(){
        this.vit += 1;
        this.pow += 1;
        this.mag += 2;
        this.agi += 3;
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

    public void speak(){
        super.speak();
    }
}


