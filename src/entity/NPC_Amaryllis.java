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

        skills.add(new Skill("Hack n Slash", "Amaryllis uses her nails to penetrate the enemy.", agi*3, 25+maxEnergy*0.3, 2, "DAMAGE"));
        skills.add(new Skill("Experimental Salve", "Strengthens ally. Lasts 3 turns", agi, 25+maxEnergy*0.2, 3, "BUFF_ALLY"));
        skills.add(new Skill("Unleash", "Unleashes a flurry of 3-5 attacks at the enemy.", agi*2, 50+maxEnergy*0.8, 5, "DAMAGE"));
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


                gp.ui.addMessage(getName() + " lunges at " + target.getName() + " for " + gp.battleScreen.output + " damage.");

                break;
            }
            case 1:{
                this.energy -= skills.get(1).energyCost;
                skills.get(1).use();
                gp.battleScreen.output = skills.get(1).power;

                target.tempAtk = target.attack;

                target.attack += gp.battleScreen.output;
                target.strengthened = 3;

                gp.ui.addMessage(getName() + " experiments on " + target.getName() + ", boostings attack by" + gp.battleScreen.output + ".");
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

                gp.ui.addMessage(getName() + " goes crazy on " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                break;
            }
        }
    }
}


