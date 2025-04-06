package entity;

import entity.components.Skill;
import main.GamePanel;
import misc.KeyHandler;

public class MOB_Veyra extends Entity {

    public MOB_Veyra(GamePanel gp) {
        super(gp);

        setName("Sylvie");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;

        getImage("veyra");
        setDefaultValues(60, 150, 150,3, 15, 15, 15, 15,  0);

        skills.add(new Skill("Healing Grace", "Heals herself.", mag*2, 100+maxEnergy*0.2, 2, "BUFF_SELF"));
        skills.add(new Skill("Corrupted Whip", "Uses corrupted roots to attack.", mag*4, 100+maxEnergy*0.4, 3, "DAMAGE"));
        skills.add(new Skill("Implode", "Creates an explosion of corrupted energy that heals herself..", mag*6, 100+maxEnergy*0.8, 5, "BUFF_ALLY"));
    }


    public void setStatIncrements(){
        this.vit += 5;
        this.pow += 5;
        this.mag += 5;
        this.agi += 5;
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


