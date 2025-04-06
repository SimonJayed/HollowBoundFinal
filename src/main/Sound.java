package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    private Clip clip;
    private URL soundURL[] = new URL[30];
    private FloatControl volumeControl;


    public Sound(){
        try {
            soundURL[0] = getClass().getResource("/sound/LetterBee.wav");
            soundURL[1] = getClass().getResource("/sound/AnimalCrossingTalkingSound.wav");
            soundURL[2] = getClass().getResource("/sound/LetterBee-Lag&Niche.wav");
            soundURL[3] = getClass().getResource("/sound/ClickSound.wav");
            soundURL[4] = getClass().getResource("/sound/EpilogueMusic.wav");
            soundURL[5] = getClass().getResource("/sound/FirstMap.wav");
            soundURL[6] = getClass().getResource("/sound/GraveyardMusic.wav");
            soundURL[7] = getClass().getResource("/sound/CayCaveMusiz.wav");
            soundURL[8] = getClass().getResource("/sound/SecondMapMusic.wav");
            soundURL[9] = getClass().getResource("/sound/VeyraBattleMusic.wav");
            soundURL[10] = getClass().getResource("/sound/VeyraDomainMusic.wav");
            soundURL[11] = getClass().getResource("/sound/Vs Susie.wav");
            soundURL[12] = getClass().getResource("/sound/EnemyApproaching.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFile(int i){

        try {
            if (soundURL[i] == null) {
                System.out.println("Invalid sound index: " + i);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(int i){
        clip.loop(i);
    }

    public void stop(){
        clip.stop();
    }

    public void setVolume(float volume){
        if (volumeControl != null){
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float newVolume = Math.min(Math.max(volume, min), max);
            volumeControl.setValue(newVolume);
        }
    }

}
