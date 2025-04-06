package entity;

import entity.components.Skill;
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
        setDefaultValues(1, 50, 50,2,5, 5, 5, 5, 0);
        setLevel(gp.randomize(gp.player.areaLevel, gp.player.areaLevel+8));

        skills.add(new Skill("Punch", "Punches enemy.", pow*2, 25+maxEnergy*0.2, 2, "DAMAGE"));
        skills.add(new Skill("Stronger Punch", "Punches enemy harder.", pow*4, 25+maxEnergy*0.4, 3, "DAMAGE"));
        skills.add(new Skill("STRONGEST PUNCH", "PUNCHES ENEMY HARDEST.", pow*8, 25+maxEnergy*0.8, 5, "DAMAGE"));
    }

    public void setStatIncrements(){
        this.vit += 2;
        this.pow += 2;
        this.mag += 2;
        this.agi += 2;
    }

    public void useSkill(int skillIndex, Entity target) {
        switch(skillIndex){
            case 0:{
                this.energy -= skills.get(0).energyCost;
                skills.get(0).use();
                gp.battleScreen.output = skills.get(0).power - target.defense;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);


                target.hp -= gp.battleScreen.output;

                gp.ui.addMessage(getName() + " punches " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
            case 1:{
                this.energy -= skills.get(1).energyCost;
                skills.get(1).use();
                gp.battleScreen.output = skills.get(1).power - target.defense;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);


                target.hp -= gp.battleScreen.output;

                gp.ui.addMessage(getName() + " punches hard on " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
            case 2:{
                this.energy -= skills.get(2).energyCost;
                skills.get(2).use();
                gp.battleScreen.output = skills.get(1).power - target.defense;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);

                target.hp -= gp.battleScreen.output;
                gp.ui.addMessage(getName() + " punches REALLY HARD on " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
        }
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


