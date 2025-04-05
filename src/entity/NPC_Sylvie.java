package entity;

import entity.components.Skill;
import main.GamePanel;

public class NPC_Sylvie extends Entity {

    public NPC_Sylvie(GamePanel gp) {
        super(gp);

        type = 2;
        setName("Sylvie");

        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;

        getImage("sylvie");
        setDefaultValues(1, 50, 150,3, 7, 5, 19, 5,  9);

        skills.add(new Skill("Nature's Embrace", "Channels natural energy to heal a single ally", mag*2, maxEnergy*0.2, 2));
        skills.add(new Skill("Thorned Whip", "Summons thorny vines to strike at an enemy", 25, maxEnergy*0.4, 1));
        skills.add(new Skill("Bloom of Life", "Creates an explosion of natural energy that damages enemies and heals allies", 135.6, maxEnergy*0.8, 2));


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
                skills.get(0).power += skills.get(0).energyCost;
                target.hp += skills.get(0).power;
                gp.ui.addMessage(getName() + " heals " + target.getName() + " for " + skills.get(0).power + ".");
                skills.get(0).use();
                break;
            }
            case 1:{
                break;
            }
            case 2:{
                break;
            }
        }

    }
}


