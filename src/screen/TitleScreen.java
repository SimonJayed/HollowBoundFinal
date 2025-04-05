package screen;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class TitleScreen implements Screen{
    GamePanel gp;

    BufferedImage background;

    public int commandNum = 0;

    public TitleScreen(GamePanel gp) {
        this.gp = gp;
        loadImages();
    }

    public void loadImages() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/titleBackground.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emptyImages(){
        background = null;
    }

    @Override
    public void draw(Graphics2D g2) {
        int x = 0;
        int y = 0;

        g2.drawImage(background, x, y, gp.screenWidth, gp.screenHeight, null);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        String text = "HollowBound";
        x = gp.ui.getXforCenteredText(text);
        y = gp.screenHeight / 5;

        g2.drawString(text, x, y);


        x = gp.screenWidth / 2 - gp.tileSize;

        text = "NEW GAME";
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.setColor(Color.black);
        x = gp.ui.getXforCenteredText(text);
        y += gp.tileSize * 6;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize + 10, y);
        }


        g2.drawString(text, x - 3, y - 3);


        text = "LOAD GAME";
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.setColor(Color.black);
        x = gp.ui.getXforCenteredText(text);
        y += gp.tileSize + 5;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize + 10, y);
        }


        g2.drawString(text, x - 3, y - 3);

        text = "QUIT GAME";
        g2.setFont(g2.getFont().deriveFont(40f));
        g2.setColor(Color.black);
        x = gp.ui.getXforCenteredText(text);
        y += gp.tileSize + 5;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize + 10, y);
        }
        g2.drawString(text, x - 3, y - 3);
    }
}
