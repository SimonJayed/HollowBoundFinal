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
        setDefaultValues(5, 75, 100,5,5, 8, 10, 13, 9);
        setDialogue();

        skills.add(new Skill("Disabling Reagent", "Silences the enemy for 2 turns, rendering them unable to use skills, along with a random debuff.", agi, 50+maxEnergy*0.3, 4, "DAMAGE"));
        skills.add(new Skill("Experimental Cure", "Replaces an ally's status effects (including positive ones) with a random buff that lasts 3 turns", agi/2, 50+maxEnergy*0.3, 3, "BUFF_ALLY"));
        skills.add(new Skill("Unleash", "Unleashes a flurry of 3-5 attacks at the enemy.", agi/2, 50+maxEnergy*0.8, 5, "DAMAGE"));
    }

    public void setStatIncrements(){
        this.vit += 1;
        this.pow += 2;
        this.mag += 1;
        this.agi += 3;
    }


    public void useSkill(int skillIndex, Entity target) {
        switch(skillIndex){
            case 0:{
                this.energy -= skills.get(0).energyCost;
                gp.battleScreen.output = skills.get(0).power;
                skills.get(0).use();

                target.hp -= gp.battleScreen.output;


                gp.ui.addMessage(getName() + " heals " + target.getName() + " for " + skills.get(0).power + ".");

                target.isHealed = true;
                break;
            }
            case 1:{
                this.energy -= skills.get(1).energyCost;
                skills.get(1).use();
                gp.battleScreen.output = skills.get(1).power;

                target.healing = 0;
                target.strengthened = 0;
                target.hardened = 0;

                int buff = gp.randomize(0, 2);

                switch(buff){
                    case 0:{
                        target.healing = 3;
                        break;
                    }
                    case 1:{
                        target.tempDef = target.defense;

                        target.defense += gp.battleScreen.output;
                        target.hardened = 3;
                        break;
                    }
                    case 2:{
                        target.tempAtk = target.attack;

                        target.attack += gp.battleScreen.output;
                        target.strengthened = 3;
                        break;
                    }
                }

                gp.ui.addMessage(getName() + " experiments on " + target.getName() + ", giving a random buff .");
                break;
            }
            case 2:{
                this.energy -= skills.get(2).energyCost;
                skills.get(2).use();
                gp.battleScreen.output = skills.get(2).power;

                int attackNum = gp.randomize(3,5);

                for(int i = 0; i<attackNum; i++){
                    target.hp -= gp.battleScreen.output;
                }
                break;
            }
        }
    }
}


