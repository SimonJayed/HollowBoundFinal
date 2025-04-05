package misc;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed,
            enterPressed, shiftPressed, tabPressed, ePressed,
            qPressed, mPressed, spacePressed, zeroPressed;


    public boolean showDebugTest = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLESTATE
        if(!gp.ui.fading){
            if (gp.gameState == gp.titleState) {
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gp.titleScreen.commandNum--;
                    if (gp.titleScreen.commandNum < 0) {
                        gp.titleScreen.commandNum = 2;
                    }
                    gp.playSoundEffect(3);
                    gp.sound.setVolume(-20.0f);
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gp.titleScreen.commandNum++;
                    if (gp.titleScreen.commandNum > 2) {
                        gp.titleScreen.commandNum = 0;
                    }
                    gp.playSoundEffect(3);
                    gp.sound.setVolume(-20.0f);
                }
                if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                    if (gp.titleScreen.commandNum == 0) {
                        System.out.println("NEW GAME");
                        gp.ui.startFadeIn();
                        gp.gameState = gp.characterPickState;
                        gp.titleScreen.emptyImages();
//                    gp.playMusic(2);
                        gp.sound.setVolume(-25.0f);
                    }
                    if (gp.titleScreen.commandNum == 1) {

                    }
                    if (gp.titleScreen.commandNum == 2) {

                    }
                    if (gp.titleScreen.commandNum == 3) {
                        System.exit(0);
                    }
                }
            }
            //CHARACTER PICK
            else if(gp.gameState == gp.characterPickState) {
                if(!gp.pickScreen.isPicking){
                    if(code == KeyEvent.VK_W){
                        gp.pickScreen.commandNum = 3;
                    }
                    else if(code == KeyEvent.VK_S){
                        gp.pickScreen.commandNum = 0;
                    }
                    else if (code == KeyEvent.VK_A) {
                        gp.pickScreen.commandNum--;
                        System.out.println(gp.pickScreen.commandNum);
                        if (gp.pickScreen.commandNum < 0) {
                            gp.pickScreen.commandNum = 0;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    else if (code == KeyEvent.VK_D) {
                        gp.pickScreen.commandNum++;
                        if (gp.pickScreen.commandNum > 2) {
                            gp.pickScreen.commandNum = 2;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if (code == KeyEvent.VK_ESCAPE) {
                        gp.ui.startFadeIn();
                        gp.gameState = gp.titleState;
                    }
                }
                else{
                    if(code == KeyEvent.VK_W){
                        gp.pickScreen.commandNum = 3;
                    }
                    if (code == KeyEvent.VK_ESCAPE) {
                        gp.ui.startFadeIn();
                        gp.pickScreen.isPicking = false;
                    }
                }
                if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                    if(gp.pickScreen.isPicking){
                        if (gp.pickScreen.commandNum == 0) {
                            gp.aSetter.setPlayer(0);
                            gp.ui.startFadeIn();
                            gp.gameState = gp.playState;
                        }
                        if (gp.pickScreen.commandNum == 1) {
                            gp.aSetter.setPlayer(1);
                            gp.ui.startFadeIn();
                            gp.gameState = gp.playState;
                        }
                        if (gp.pickScreen.commandNum == 2) {
                            gp.aSetter.setPlayer(2);
                            gp.ui.startFadeIn();
                            gp.gameState = gp.playState;
                        }
                        gp.pickScreen.emptyImages();
                    }
                    else{
                        gp.pickScreen.isPicking = true;
                        if (gp.pickScreen.commandNum == 3) {
                            gp.ui.startFadeOut();
                            gp.gameState = gp.titleState;
                        }
                    }

                }
            }
            //BATTLESTATE
            else if (gp.gameState == gp.battleState) {
                if(!gp.battleScreen.isAttacking && !gp.battleScreen.isEnemyTurn && gp.battleScreen.buffer>15){
                    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                        gp.battleScreen.commandNum--;
                        if (gp.battleScreen.commandNum < 0) {
                            gp.battleScreen.commandNum = 3;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                        gp.battleScreen.commandNum++;
                        if (gp.battleScreen.commandNum > 3) {
                            gp.battleScreen.commandNum = 0;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                        if (gp.battleScreen.commandNum == 0) {
                            gp.battleScreen.attack();
                        }
                        if (gp.battleScreen.commandNum == 1) {
                            gp.battleScreen.skill();
                        }
                        if (gp.battleScreen.commandNum == 3) {
                            if(gp.battleScreen.canEscape){
                                int num = gp.randomize(1, 8);
                                if(num == 8){
                                    gp.player.invincible = true;
                                    gp.gameState = gp.playState;
                                }
                                else {
                                    System.out.println("Escape Failed.");
                                    gp.ui.addMessage("Escape Failed");
                                }
                            }
                            else{
                                System.out.println("Can't Escape.");
                                gp.ui.addMessage("Can't Escape");
                            }
                            gp.battleScreen.isAttacking = false;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                }
                else if(gp.battleScreen.choosingSkill && !gp.battleScreen.isAttacking && !gp.battleScreen.isEnemyTurn){
                    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                        gp.battleScreen.commandNum--;
                        if (gp.battleScreen.commandNum < 0) {
                            gp.battleScreen.commandNum = 2;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                        gp.battleScreen.commandNum++;
                        if (gp.battleScreen.commandNum > 2) {
                            gp.battleScreen.commandNum = 0;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
                        if(gp.battleScreen.commandNum == 0){
                            gp.battleScreen.playerAttack("HEAD");
                        }
                        if(gp.battleScreen.commandNum == 1){
                            gp.battleScreen.playerAttack("TORSO");
                        }
                        if(gp.battleScreen.commandNum == 2){
                            gp.battleScreen.playerAttack("LEGS");
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if(code == KeyEvent.VK_ESCAPE){
                        gp.battleScreen.isAttacking = false;
                    }
                }
                else if(gp.battleScreen.isAttacking && !gp.battleScreen.isEnemyTurn){
                    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                        gp.battleScreen.commandNum--;
                        if (gp.battleScreen.commandNum < 0) {
                            gp.battleScreen.commandNum = 2;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                        gp.battleScreen.commandNum++;
                        if (gp.battleScreen.commandNum > 2) {
                            gp.battleScreen.commandNum = 0;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
                        if(gp.battleScreen.commandNum == 0){
                            gp.battleScreen.playerAttack("HEAD");
                        }
                        if(gp.battleScreen.commandNum == 1){
                            gp.battleScreen.playerAttack("TORSO");
                        }
                        if(gp.battleScreen.commandNum == 2){
                            gp.battleScreen.playerAttack("LEGS");
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if(code == KeyEvent.VK_ESCAPE){
                        gp.battleScreen.isAttacking = false;
                    }
                }
            }
            //PLAYSTATE
            else if (gp.gameState == gp.playState && !gp.player.isDefeated) {
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    upPressed = true;
                }
                if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                    leftPressed = true;
                }
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    downPressed = true;
                }
                if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                    rightPressed = true;
                }

                if (code == KeyEvent.VK_SHIFT) {
                    shiftPressed = true;
                }
                if (code == KeyEvent.VK_TAB) {
                    tabPressed = !tabPressed;
                }
                if (code == KeyEvent.VK_ENTER) {
                    enterPressed = !enterPressed;
                }
                if (code == KeyEvent.VK_SPACE) {
                    spacePressed = true;
                }
                if (code == KeyEvent.VK_0) {
                    zeroPressed = !zeroPressed;
                }
                if (code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE) {
                    gp.gameState = gp.inventoryState;
                    System.out.println("Inventory opened");
                }
                if (code == KeyEvent.VK_M ) {
                    gp.gameState = gp.mapState;
                    gp.map.miniMapOn = false;
                    System.out.println("Map opened");
                }
            }
            else if (gp.gameState == gp.dialogueState) {
                if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                    gp.gameState = gp.playState;
                }
            }
            else if(gp.gameState == gp.eventState){
                if(gp.event.dialogueOn){
                    if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
                        gp.event.nextDialogue();
                        gp.ui.startFadeIn();
                        if(gp.event.dialogueFinished && gp.event.eventFinished){
                        }
                    }
                }
            }
            else if (gp.gameState == gp.inventoryState) {
                if(gp.player.statPoints > 0){
                    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                        gp.inventoryScreen.commandNum--;
                        if (gp.inventoryScreen.commandNum < 0) {
                            gp.inventoryScreen.commandNum = 4;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                        gp.inventoryScreen.commandNum++;
                        if (gp.inventoryScreen.commandNum > 4) {
                            gp.inventoryScreen.commandNum = 0;
                        }
                        gp.playSoundEffect(3);
                        gp.sound.setVolume(-20.0f);
                    }
                    if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
                        if(gp.inventoryScreen.commandNum == 0){
                            gp.player.vit++;
                        }
                        if(gp.inventoryScreen.commandNum == 1){
                            gp.player.pow++;
                        }
                        if(gp.inventoryScreen.commandNum == 2){
                            gp.player.mag++;
                        }
                        if(gp.inventoryScreen.commandNum == 3){
                            gp.player.agi++;
                        }
                        if(gp.inventoryScreen.commandNum == 4){
                            gp.player.luck++;
                        }
                        gp.player.statPoints--;
                    }
                }

                if (code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE) {
                    gp.gameState = gp.playState;
                    System.out.println("Inventory closed");
                }

                if(code == KeyEvent.VK_Q){
                    System.out.println("Q pressed");
                    gp.inventoryScreen.charNum--;
                    if(gp.inventoryScreen.charNum < 0){
                        gp.inventoryScreen.charNum = 2;
                    }
                }
                if(code == KeyEvent.VK_R){
                    System.out.println("R pressed");
                    gp.inventoryScreen.charNum++;
                    if(gp.inventoryScreen.charNum > 2){
                        gp.inventoryScreen.charNum = 0;
                    }
                }
                System.out.println(gp.inventoryScreen.charNum);
            }
            else if (gp.gameState == gp.mapState) {
                if (code == KeyEvent.VK_M || code == KeyEvent.VK_ESCAPE) {
                    gp.gameState = gp.playState;
                    System.out.println("Map closed");
                }
            }
        }




        if (code == KeyEvent.VK_T) {
            if (!showDebugTest) {
                showDebugTest = true;
            } else {
                showDebugTest = false;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if (code == KeyEvent.VK_Q){
            qPressed = false;
        }
        if (code == KeyEvent.VK_E){
            ePressed = false;
        }
        if (code == KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
