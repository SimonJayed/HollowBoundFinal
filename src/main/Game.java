package main;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(864, 672);
        window.setTitle("HollowBound");

        ImageIcon icon = new ImageIcon("C:\\Users\\USER\\IdeaProjects\\HollowBound2.1\\res\\ui\\HollowBound.png");
        window.setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
