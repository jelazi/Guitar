package lubin.guitar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

// TODO udělat dědičnost PreviewSong na virtual guitar - zatím nefunguje padá

//TODO chyba - při zmáčknutí tlačítka zpět, puštěná hudba se nechová standartně - hraje dál


public class PreviewSong extends VirtualGuitar {


    Button btnplayMusic;

    boolean isPlaying = false;


    ArrayList<Tone> skladba = new ArrayList<>();
    ArrayList<GuitarTone> pokus = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_song);


        btnplayMusic = (Button) findViewById(R.id.playMusic);
        btnplayMusic.setOnClickListener(previewSong);


    }


    // zahrani skladby
    OnClickListener previewSong = new OnClickListener() {
        @Override
        public void onClick(View view) {


            previewSong();

        }

        ;


        public void previewSong() {

            skladba = Songs.getSong2();

            pokus = createMusicFromTones(skladba);
            if (isPlaying) {

                soundPool.release();
                isPlaying = false;
                Intent i = new Intent(PreviewSong.this, PreviewSong.class);
                startActivity(i);
                previewSong();


            }

            isPlaying = true;
            btnplayMusic.setText("Zastav");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int delay = 1000;
                    for (int i = 0; i <= skladba.size() - 1; i++) {
                        if (!(skladba.get(i).nameTone.equals("silent"))) //neni to pomlka?
                        {
                            playTone(pokus.get(i), delay);
                        }

                        if (i == skladba.size() - 1) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btnplayMusic.setText("Přehraj");
                                    isPlaying = false;
                                }
                            }, delay + 1000);


                        }

                        delay = delay + skladba.get(i).lenghtTone;

                    }
                }
            }, 1000);


        }
    };


    // zahrani tonu s volitelnym zpozdenim
    public void playTone(GuitarTone guitarTone, int delay) {
        final GuitarTone gtr = guitarTone;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                float vol = audioManager.getStreamVolume(
                        AudioManager.STREAM_MUSIC);
                float maxVol = audioManager.getStreamMaxVolume(
                        AudioManager.STREAM_MUSIC);
                float leftVolume = vol / maxVol;
                float rightVolume = vol / maxVol;
                int priority = 1;
                int no_loop = 0;


                normal_playback_rate = gtr.getStringValue();
                Shaking(gtr.getStringImage());
                Touching(gtr.getStringTouch());


                soundPool.play(soundId,
                        leftVolume,
                        rightVolume,
                        priority,
                        no_loop,
                        normal_playback_rate);


            }
        }, delay);


    }

    ///vytvori skladbu
    public ArrayList<GuitarTone> createMusicFromTones(ArrayList<Tone> musicTone) {
        int length = musicTone.size();
        ArrayList<GuitarTone> music = new ArrayList<>();
        for (int i = 0; i <= length - 1; i++) {


            music.add(getToneFromName(musicTone.get(i).nameTone));

        }
        return music;

    }


    //animace dotyku
    private void Touching(final ImageButton imgButton) {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imgButton.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            boolean isBackground = false;


            @Override
            public void onAnimationStart(Animation animation) {
                // if (imgButton == R.drawable.touch){
                //        isBackground = true;
                //}
                imgButton.setBackgroundResource(R.drawable.touch);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //if (isBackground){

                //}
                //else{
                imgButton.setBackgroundResource(0);
                //  }


            }
        });
    }


}
