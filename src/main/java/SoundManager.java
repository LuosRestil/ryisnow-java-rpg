package main.java;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
    Clip clip;
    URL[] soundUrls = new URL[30];

    public SoundManager() {
        soundUrls[0] = getClass().getResource("../resources/sound/BlueBoyAdventure.wav");
        soundUrls[1] = getClass().getResource("../resources/sound/coin.wav");
        soundUrls[2] = getClass().getResource("../resources/sound/powerup.wav");
        soundUrls[3] = getClass().getResource("../resources/sound/unlock.wav");
        soundUrls[4] = getClass().getResource("../resources/sound/fanfare.wav");
    }

    public void setFile(int idx) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrls[idx]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
