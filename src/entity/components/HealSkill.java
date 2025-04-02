package entity.components;

public class HealSkill extends Skill {
    public HealSkill(String name, String description, double power, double energyCost, int cooldown) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.energyCost = energyCost;
        this.skillType = "HEAL";
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }
}
