package data;

import entity.Entity;

import java.io.Serializable;

public class DataStorage implements Serializable {

    public int level;
    public String name;
    public int worldX, worldY;
    public int spawnPointX, spawnPointY;
    public double speed, tempSpeed;
    public double attack, defense;
    public double exp, nextLevelExp;
    public int sizeIncrement;
    public double initialHP, initialEnergy;
    public double maxHP, hp;
    public double maxEnergy, energy, energyRegen;
    public double vit, pow, mag, agi, luck;
    public boolean hasSave;
    public int event0Flag;
    public int event1Flag;
    public int event2Flag;
    public int event3Flag;
    public int event4Flag;


    public String playing;

    public int currentMap;

}

