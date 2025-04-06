package data;

import main.GamePanel;

import javax.xml.crypto.Data;
import java.io.*;

public class SaveLoad {
    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() throws IOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

            DataStorage ds = new DataStorage();

            ds.level = gp.player.level;
            ds.name = gp.player.getName();
            ds.worldX = gp.player.worldX;
            ds.worldY = gp.player.worldY;
            ds.spawnPointX = gp.player.spawnPointX;
            ds.spawnPointY = gp.player.spawnPointY;
            ds.speed = gp.player.speed;
            ds.tempSpeed = gp.player.tempSpeed;
            ds.attack = gp.player.attack;
            ds.defense = gp.player.defense;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.sizeIncrement = gp.player.sizeIncrement;
            ds.initialHP = gp.player.initialHP;
            ds.initialEnergy = gp.player.initialEnergy;
            ds.maxHP = gp.player.maxHP;
            ds.hp = gp.player.hp;
            ds.maxEnergy = gp.player.maxEnergy;
            ds.energy = gp.player.energy;
            ds.energyRegen = gp.player.energyRegen;
            ds.vit = gp.player.vit;
            ds.pow = gp.player.pow;
            ds.mag = gp.player.mag;
            ds.agi = gp.player.agi;
            ds.luck = gp.player.luck;
            ds.playing = gp.player.playing;
            ds.up1 = gp.player.up1;
            ds.up2 = gp.player.up2;
            ds.down1 = gp.player.down1;
            ds.down2 = gp.player.down2;
            ds.left1 = gp.player.left1;
            ds.left2 = gp.player.left2;
            ds.right1 = gp.player.right1;
            ds.right2 = gp.player.right2;
            ds.defeated1 = gp.player.defeated1;
            ds.defeated2 = gp.player.defeated2;
            ds.image1 = gp.player.image1;
            ds.image2 = gp.player.image2;
            ds.image3 = gp.player.image3;
            ds.image4 = gp.player.image4;
            ds.portrait = gp.player.portrait;

            oos.writeObject(ds);

        } catch (Exception e) {
            System.out.println("Save exception.");
        }
    }

    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

            DataStorage ds = (DataStorage)ois.readObject();

            gp.player.level = ds.level;
            gp.player.setName(ds.name);
            gp.player.worldX = ds.worldX;
            gp.player.worldY = ds.worldY;
            gp.player.spawnPointX = ds.spawnPointX;
            gp.player.spawnPointY = ds.spawnPointY;
            gp.player.speed = ds.speed;
            gp.player.tempSpeed = ds.tempSpeed;
            gp.player.attack = ds.attack;
            gp.player.defense = ds.defense;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.sizeIncrement = ds.sizeIncrement;
            gp.player.initialHP = ds.initialHP;
            gp.player.initialEnergy = ds.initialEnergy;
            gp.player.maxHP = ds.maxHP;
            gp.player.hp = ds.hp;
            gp.player.maxEnergy = ds.maxEnergy;
            gp.player.energy = ds.energy;
            gp.player.energyRegen = ds.energyRegen;
            gp.player.vit = ds.vit;
            gp.player.pow = ds.pow;
            gp.player.mag = ds.mag;
            gp.player.agi = ds.agi;
            gp.player.luck = ds.luck;
            gp.player.playing = ds.playing;
            gp.player.up1 = ds.up1;
            gp.player.up2 = ds.up2;
            gp.player.down1 = ds.down1;
            gp.player.down2 = ds.down2;
            gp.player.left1 = ds.left1;
            gp.player.left2 = ds.left2;
            gp.player.right1 = ds.right1;
            gp.player.right2 = ds.right2;
            gp.player.defeated1 = ds.defeated1;
            gp.player.defeated2 = ds.defeated2;
            gp.player.image1 = ds.image1;
            gp.player.image2 = ds.image2;
            gp.player.image3 = ds.image3;
            gp.player.image4 = ds.image4;
            gp.player.portrait = ds.portrait;

        } catch (Exception e) {
            System.out.println("Load exception.");
        }
    }
}
