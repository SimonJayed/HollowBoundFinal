package entity.components;

public class BuffSkill extends Skill {
    public BuffSkill(String name, String description, double power, double energyCost, int cooldown) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.energyCost = energyCost;
        this.skillType = "BUFF";
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }
}