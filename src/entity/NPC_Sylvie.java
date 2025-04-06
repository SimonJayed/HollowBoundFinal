package entity;

import entity.components.Skill;
import main.GamePanel;

public class NPC_Sylvie extends Entity {

    public NPC_Sylvie(GamePanel gp) {
        super(gp);

        setName("Sylvie");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;

        getImage("sylvie");
        setDefaultValues(5, 50, 150,3, 7, 5, 19, 5,  9);

        skills.add(new Skill("Nature's Embrace", "Channels natural energy to heal a single ally", mag*2, 100+maxEnergy*0.2, 2, "BUFF_ALLY"));
        skills.add(new Skill("Thorned Whip", "Summons thorny vines to strike at an enemy.", mag*4, 100+maxEnergy*0.4, 3, "DAMAGE"));
        skills.add(new Skill("Nature's Blessing", "Creates an explosion of natural energy that increases allies' strength and heals them for 3 turns.", mag*5, 100+maxEnergy*0.8, 5, "BUFF_ALLY"));
    }

    public void setStatIncrements(){
        this.vit += 1;
        this.pow += 1;
        this.mag += 4;
        this.agi += 1;
    }

    public void useSkill(int skillIndex, Entity target) {
        switch(skillIndex){
            case 0:{
                this.energy -= skills.get(0).energyCost;
                gp.battleScreen.output += skills.get(0).power;
                skills.get(0).use();

                target.hp += gp.battleScreen.output;
                if(target.hp > target.maxHP){
                    target.hp = target.maxHP;
                }
                gp.ui.addMessage(getName() + " heals " + target.getName() + " for " + skills.get(0).power + ".");

                target.isHealed = true;
                this.aggro++;
                break;
            }
            case 1:{
                this.energy -= skills.get(1).energyCost;
                skills.get(1).use();
                gp.battleScreen.output = skills.get(1).power - target.defense;
                gp.battleScreen.output = Math.max(gp.battleScreen.output, 1);


                target.hp -= gp.battleScreen.output;

                gp.ui.addMessage(getName() + " whips " + target.getName() + " dealing " + gp.battleScreen.output + " damage.");
                this.aggro++;
                break;
            }
            case 2:{
                this.energy -= skills.get(2).energyCost;
                skills.get(2).use();
                gp.battleScreen.output = skills.get(2).power;
                break;
            }
        }
    }
}


