package entity.components;

public class AttackSkill extends Skill {
    public AttackSkill(String name, String description, double power, double energyCost, int cooldown) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.energyCost = energyCost;
        this.skillType = "DAMAGE";
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }
}