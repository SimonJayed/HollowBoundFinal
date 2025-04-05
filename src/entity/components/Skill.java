package entity.components;

public class Skill {
    public String name;
    public String description;
    public double power;
    public double energyCost;
    public int cooldown;
    public int currentCooldown;

    public Skill(String name, String description, double power, double energyCost, int cooldown) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.energyCost = energyCost;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }

    public Skill(Skill skill) {
        this.name = skill.name;
        this.description = skill.description;
        this.power = skill.power;
        this.energyCost = skill.energyCost;
        this.cooldown = skill.cooldown;
        this.currentCooldown = 0;
    }

    public void use() {
        if (currentCooldown <= 0) {
            currentCooldown = cooldown;
        }
    }

    public void updateCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    @Override
    public String toString() {
        return name + " (" + energyCost + " energy) - " + description;
    }
}