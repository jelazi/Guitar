package lubin.guitar;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

// TODO při zmáčknutí tlačítka zpět se nezastaví hudba


public class PreviewSong extends VirtualGuitar {

    Button btnplayMusic;

    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    int soundId;
    int tone; //druh tonu

    float normal_playback_rate;

    Tones tones = new Tones();

    boolean isPlaying = false;

    Song skladba = new Song();

    ArrayList<Tone> tonySkladby = new ArrayList<>();
    ArrayList<GuitarTone> pokus = new ArrayList<>();
    TextView nameOfSongView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        songs = new Songs(this);


        setContentView(R.layout.activity_preview_song);
        createView();



        btnplayMusic = (Button)findViewById(R.id.playMusic);
        btnplayMusic.setOnClickListener(previewSong);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        nameOfSongView = (TextView) findViewById(R.id.nameSong);



        //maximalni mnozstvi zaroven prehravanych zvuku
        int maxStreams = 4;
        //audiostream v audiomanageru
        int streamType = AudioManager.STREAM_MUSIC;
        //kvalita streamu
        int srcQuality = 0;

        soundPool = new SoundPool(maxStreams, streamType, srcQuality);
        //listener zvuku
        soundPool.setOnLoadCompleteListener(soundPoolOnLoadCompleteListener);
        //id zvuku
        File dirInstruments = new File(getFilesDir()+"/Instruments/");
        String[] nameInstruments = dirInstruments.list();
        String nameInstrument = nameInstruments[numberInstrument - 1];

        soundId = soundPool.load( getFilesDir()+"/Instruments/"+nameInstrument, 1);

        tone = 0;
        normal_playback_rate = 0.5f;

        this.setTitle("Přehrávání písně");



        nameOfSongView.setText(nameOfSong);



    }


    // zahrani skladby
    OnClickListener previewSong = new OnClickListener() {
        @Override
        public void onClick(View view) {

            previewSong();
        };

        public void previewSong(){



            if (!isPlaying) {
                skladba = songs.callByName(nameOfSong);

            }



            tonySkladby = skladba.getTones();


            pokus = createMusicFromTones(tonySkladby);
            if (isPlaying){

                soundPool.release();
                isPlaying = false;

                Intent i = new Intent(PreviewSong.this, PreviewSong.class);
                i.putExtra("oldName", nameOfSong);
                finish();
                startActivity(i);





            }

            isPlaying = true;
            btnplayMusic.setText("Zastav");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int delay = 1000;
                    for (int i = 0; i <= tonySkladby.size() - 1; i++) {
                        if (!(tonySkladby.get(i).nameTone.equals("silent"))) //neni to pomlka?
                        {
                            playTone(pokus.get(i), delay);
                        }

                        if (i == tonySkladby.size() - 1){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    btnplayMusic.setText("Přehraj");
                                    isPlaying = false;
                                }
                            }, delay + 1000);
                        }
                        delay = delay + tonySkladby.get(i).lenghtTone;
                    }
                }
            }, 1000);
        }
    };

    // zahrani tonu s volitelnym zpozdenim
    public void playTone(GuitarTone guitarTone, int delay){
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
    public ArrayList<GuitarTone> createMusicFromTones(ArrayList<Tone> musicTone){
        int length = musicTone.size();
        ArrayList<GuitarTone> music = new ArrayList<>();
        for (int i = 0; i <=length-1;i++){

            music.add(getToneFromName(musicTone.get(i).nameTone));
        }
        return music;
    }


    //animace dotyku
    private void Touching(final ImageButton imgButton){

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imgButton.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            boolean isBackground = false;


            @Override
            public void onAnimationStart(Animation animation) {
                // if (imgButton == R.drawable.touch){
                //        isBackground = true;
                //}
                imgButton.setBackgroundResource(R.drawable.touch);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                //if (isBackground){

                //}
                //else{
                imgButton.setBackgroundResource(0);
                //  }


            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

             soundPool.release();
        }
        return super.onKeyDown(keyCode, event);
    }
}