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
