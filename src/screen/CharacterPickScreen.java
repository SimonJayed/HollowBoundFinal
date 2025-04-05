package screen;

import entity.Entity;
import entity.NPC_Fort;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class CharacterPickScreen implements Screen{
    GamePanel gp;

    BufferedImage background, selectFort, selectSylvie, selectAmaryllis, pickFort, pickSylvie, pickAmaryllis;

    public int commandNum = 1;
    public boolean isPicking = false;

    public CharacterPickScreen(GamePanel gp) {
        this.gp = gp;
    }

    public void loadImages() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/characterPickScreen.png")));
            selectAmaryllis = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/pick/amaryllisPick2.png")));
            selectFort = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/pick/fortPick2.png")));
            selectSylvie = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/pick/sylviePick2.png")));
            pickAmaryllis = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/pick/amaryllisPick.png")));
            pickFort = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/pick/fortPick.png")));
            pickSylvie = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/pick/sylviePick.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emptyImages(){
        System.out.println("Title Images Unloaded");
        background = null;
        selectSylvie = null;
        selectFort = null;
        selectAmaryllis = null;
        pickSylvie = null;
        pickFort = null;
        pickAmaryllis = null;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setFont(gp.customFont);
        g2.setColor(Color.white);

        int x = 0;
        int y = 0;

        g2.drawImage(background, x, y,gp.screenWidth, gp. screenHeight, null);

        g2.setFont(g2.getFont().deriveFont(20f));
        g2.setColor(Color.black);
        g2.drawString("<< BACK", gp.tileSize/2+12, gp.tileSize+12);
        g2.setColor(Color.white);
        g2.drawString("<< BACK", gp.tileSize/2+10, gp.tileSize+10);
        g2.setFont(g2.getFont().deriveFont(8f));
        g2.drawString("ESC", gp.tileSize/2, gp.tileSize-5);

        if(!isPicking){
            if(commandNum == 0){
                g2.drawImage(selectAmaryllis, x, y,gp.screenWidth, gp. screenHeight, null);
            }
            else if(commandNum == 1){
                g2.drawImage(selectFort, x, y,gp.screenWidth, gp. screenHeight, null);
            }
            else if (commandNum == 2){
                g2.drawImage(selectSylvie, x, y,gp.screenWidth, gp. screenHeight, null);
            }
            else if(commandNum == 3){
                g2.setColor(Color.black);
                g2.fillRect(gp.tileSize/2, gp.tileSize/2, gp.tileSize*2+20, gp.tileSize);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 21f));

                g2.drawString("<< BACK", gp.tileSize/2+12, gp.tileSize+12);
                g2.setColor(Color.white);
                g2.drawString("<< BACK", gp.tileSize/2+10, gp.tileSize+10);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,9f));
                g2.drawString("ESC", gp.tileSize/2, gp.tileSize-10);
            }
        }
        else{
            if(commandNum == 0){
                g2.drawImage(pickAmaryllis, x - gp.tileSize, y,gp.screenWidth, gp. screenHeight, null);
                showAmaryllisInfo();
            }
            else if(commandNum == 1){
                g2.drawImage(pickFort, x, y,gp.screenWidth, gp. screenHeight, null);
                showFortInfo();
            }
            else if (commandNum == 2){
                g2.drawImage(pickSylvie, x, y,gp.screenWidth, gp. screenHeight, null);
                showSylvieInfo();
            }

        }

    }

    public void showFortInfo(){
        int x = gp.tileSize/2;
        int y = gp.tileSize*3;
        gp.ui.drawSubWindow(x, y, gp.tileSize*9, gp.tileSize*9);

        gp.ui.addDescription("Fort\n" +
                "\n \nAn exiled villager now living as a wanderer, traveling from place to place.\n" +
                "\n \nInit.HP: 100 Init.Energy: 50 ATK: 21 DEF: 24 SPD: \n" +
                "\n \n  Main Stat: VIT \n" +
                "VIT: 16 | STR: 7 | MAG: 5 | AGI: 6 | LUCK: 15\n" +
                "\n \nSkills:\n" +
                "Rage Bait \n" +
                "Meat Shield \n" +
                "SMAAAAASHHHHH\n");

        gp.ui.drawDescription(x+10, y+25, 30);
    }
    public void showAmaryllisInfo(){
        int x = gp.tileSize*8;
        int y = gp.tileSize*3;
        gp.ui.drawSubWindow(x, y, gp.tileSize*9, gp.tileSize*10);

        gp.ui.addDescription("Amaryllis\n" +
                "\n \nOnce an apprentice to the Eldren, she mastered nature’s alchemy. Now an outcast, she travels in search of answers.\n" +
                "\n \nInit.HP: 75  Init.Energy: 100 ATK: 11 DEF: 3 SPD: 5\n" +
                "\n \n  Main Stat: AGI, POW \n" +
                "VIT: 6 | STR: 14 | MAG: 5 | AGI: 16 | LUCK: 9\n" +
                "\n \nSkills:\n" +
                "Binding Toxin\n" +
                "Liquid Experiment\n" +
                "Unleash\n");

        gp.ui.drawDescription(x+10, y+25, 30);

    }
    public void showSylvieInfo(){
        int x = gp.tileSize+gp.tileSize/2;
        int y = gp.tileSize*3;
        gp.ui.drawSubWindow(x, y, gp.tileSize*9, gp.tileSize*10+10);

        gp.ui.addDescription("Sylvie\n" +
                "\n \nA former princess of Eldenbloom, the once-flourishing kingdom now lost to the Hollow. She wields powerful magic, determined to reclaim what was taken.\n" +
                "\n \nInit.HP: 100 Init.Energy: 50 ATK: 8 DEF: 4 SPD: 3\n" +
                "\n \n  Main Stat: MAG \n" +
                "VIT: 6 | STR: 6 | MAG: 23 | AGI: 6 | LUCK: 9\n" +
                "\n \nSkills:\n" +
                "Nature's Embrace\n" +
                "Thorned Whip\n" +
                "Bloom of Life\n");

        gp.ui.drawDescription(x+10, y+25, 30);
    }
}
