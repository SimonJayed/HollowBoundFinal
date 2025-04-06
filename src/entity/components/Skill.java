package entity.components;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Skill {
    DecimalFormat df = new DecimalFormat("#.##");

    public String name;
    public String description;
    public double power;
    public double energyCost;
    public int cooldown;
    public int currentCooldown;
    public String type;

    public Skill(String name, String description, double power, double energyCost, int cooldown, String type) {
        this.name = name;
        this.description = description;
        this.power = power;
        this.energyCost = energyCost;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
        this.type = type;
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
        return name + " (" + df.format(energyCost) + " energy)\n \n " + description + "\n \n Skill Power: " + df.format(power) + "\n Current Cooldown: " + currentCooldown;
    }
}