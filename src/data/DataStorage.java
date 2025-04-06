package data;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class DataStorage implements Serializable {
    public int level = 1;
    public String name;
    public int worldX, worldY;
    public int spawnPointX = worldX;
    public int spawnPointY = worldY;
    public double speed = 1;
    public double tempSpeed = 0;
    public double attack = 2;
    public double defense = 1;
    public double exp = 1;
    public double nextLevelExp;
    public int sizeIncrement = 0;

    public double initialHP;
    public double initialEnergy;
    public double maxHP;
    public double hp;
    public double maxEnergy = 10;
    public double energy = 10;
    public double energyRegen = maxEnergy*0.1;

    public double vit;
    public double pow;
    public double mag;
    public double agi;
    public double luck;
    public String playing;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage defeated1, defeated2;
    public BufferedImage image1, image2, image3, image4;
    public BufferedImage portrait;
}
