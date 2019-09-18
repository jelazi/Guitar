package lubin.guitar;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.view.menu.MenuBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

// TODO při zmáčknutí tlačítka zpět se nezastaví hudba


public class PreviewSongActivity extends VirtualGuitarActivity {

    Button btnplayMusic;

    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    int soundId;
    int tone; //druh tonu

    float normal_playback_rate;

    Tones tones = new Tones();

    boolean isPlaying = false;
    boolean nextTone = false;

    Song skladba = new Song();

    ArrayList<Tone> tonySkladby = new ArrayList<>();
    ArrayList<GuitarTone> pokus = new ArrayList<>();
    TextView nameOfSongView;
    ArrayList<Integer> streamIDs;

    long[] delays;
    long startTime;
    int toneStop;

    boolean stopBeforeTone;

boolean animationbool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_preview_song);
        createView();
        btnplayMusic = (Button) findViewById(R.id.playMusic);
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

        if (!settings.getBoolean("new_intent", true)){
            toneStop = settings.getInt("tone_stop", 0)+1;
        }
        else{
            settings.edit().putInt("tone_stop", 0).apply();
            toneStop = 0;
        }

        settings.edit().putBoolean("new_intent", true).apply();



        soundId = soundPool.load(getFilesDir() + "/Instruments/" + settings.getString("list_instruments", "a1.wav"), 1);

        tone = 0;
        normal_playback_rate = 0.5f;

        this.setTitle(R.string.action_preview_song);


        nameOfSongView.setText(settings.getString("list_songs", "Pro Elisku"));




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preview_song, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;

            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            previewSong();

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.settings:
                soundPool.release();
                startActivity(new Intent(this, this.getClass())); //spusteni nove instance z duvodu zastaveni prehravani skladby, pokud je prave prehravana
                overridePendingTransition(0, 0);
                finish();
                Intent i = new Intent(PreviewSongActivity.this, SettingsScreenActivity.class);
                startActivity(i);

                break;

            case R.id.change_instrument:
                changeInstrument();
                soundPool.release();
                settings.edit().putInt("tone_stop", toneStop).apply();
                settings.edit().putBoolean("new_intent", false).apply();
                startActivity(new Intent(this, this.getClass()));
                overridePendingTransition(0, 0);
                finish();

            break;

            case R.id.try_song:
                soundPool.release();
                i = new Intent(PreviewSongActivity.this, TrySongActivity.class);
                startActivity(i);
                break;


            case R.id.play_chord:
                soundPool.release();
                i = new Intent(PreviewSongActivity.this, PlayAcordActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public void onResume() { //aktualizace nastaveni hodnot ze SettingsScreenActivity
        super.onResume();
        settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        soundId = soundPool.load(getFilesDir() + "/Instruments/" + settings.getString("list_instruments", "a1.wav"), 1);
        nameOfSongView.setText(settings.getString("list_songs", "Pro Elisku"));
        stopBeforeTone = settings.getBoolean("stop_before_tone", false);
    }


    // zahrani skladby
    OnClickListener previewSong = new OnClickListener() {
        @Override
        public void onClick(View view) {

            previewSong();
        }

        ;
    };

    public void previewSong() {

        Object obj = new Object();


        if (!isPlaying) {
            skladba = Songs.callByName(getApplicationContext(), settings.getString("list_songs", "Pro Elisku"));

        }

        tonySkladby = skladba.getTones();


        pokus = createMusicFromTones(tonySkladby);
        if (isPlaying) {

            toneStop = getNumberTone(); //zjisteni tonu, na kterém se skoncilo

            isPlaying = false;
            this.btnplayMusic.setText("Hraj");

            soundPool.release();

            settings.edit().putInt("tone_stop", toneStop).apply();
            settings.edit().putBoolean("new_intent", false).apply();


            startActivity(new Intent(this, this.getClass()));
            overridePendingTransition(0, 0);
            finish();
        }
        else {


            delays = new long[tonySkladby.size()];
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    startTime =  System.currentTimeMillis();

                    int delay = 1000;
                    for (int i = toneStop; i < tonySkladby.size(); i++) {
                        if (isPlaying) {


                            if (!(tonySkladby.get(i).nameTone.equals("silent"))) //neni to pomlka?
                            {
                                playToneWithDelay(pokus.get(i), delay);
                            }

                            if (i == tonySkladby.size() - 1) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        isPlaying = true;
                                        btnplayMusic.setText("Zastav");

                                    }
                                }, delay + 1000);
                            }
                            delay = delay + tonySkladby.get(i).lenghtTone;
                            delays[i] = Long.valueOf(delay);
                        }
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isPlaying = false;
                            btnplayMusic.setText("Hraj");
                            toneStop = 0;
                            settings.edit().putInt("tone_stop", toneStop).apply();


                        }
                    }, delay + 100);

                }
            }, 1000);





/*        long expectedtime = System.currentTimeMillis();
        for (int i = 0; i <= tonySkladby.size() - 1; i++) {
            final GuitarTone tone = pokus.get(i);
            final int lenghtTone = tonySkladby.get(i).lenghtTone;

            if (i == 1) {
                expectedtime += tonySkladby.get(0).lenghtTone;
            }

            while (System.currentTimeMillis() < expectedtime) {

                //Empty Loop
            }
            expectedtime += (lenghtTone * 1.5);//Sample expectedtime += 1000; 1 second sleep
            playTone(tone);
        }*/

            isPlaying = true;
            btnplayMusic.setText("Zastav");

        /*try {
            synchronized (obj) {




                for (int i = 0; i <= tonySkladby.size() - 1; i++) {
                    final GuitarTone tone = pokus.get(i);
                    Shaking(tone.getStringImage());
                    Touching(tone.getStringTouch());
                        int lenghtTone = 0;
                        if (i == 0) { //prodloužení čekání prvniho tonu
                            lenghtTone = tonySkladby.get(i).lenghtTone + 100;
                        } else {
                            lenghtTone = tonySkladby.get(i).lenghtTone;
                        }
                        playTone(tone);
                        obj.wait(lenghtTone);
                }
                    }
                    Intent i = new Intent(PreviewSongActivity.this, PreviewSongActivity.class);
                    finish();
                    startActivity(i);
            } catch(InterruptedException ex){
            }
*/
            }
        }

        private int getNumberTone(){ //zjisteni cisla tonu, na kterem se skoncilo

            long difference = System.currentTimeMillis() - startTime - 490; //hodnota - pro vraceni tonu, ktery byl velmi rychle zahran a nemusel byt slysen


            for (int i = 0; i < tonySkladby.size(); i++){
                if (difference < delays[i]) {
                    return i;

                }
            }

            return 0;


        }

    // zahrani tonu bez zpozdeni
    protected void playTone(GuitarTone guitarTone) {
        final GuitarTone gtr = guitarTone;

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
                Touching(gtr.getStringTouch(), null);

                if (stopBeforeTone){
                    if (streamID != 0){ //zastavi ton, predchoziho
                        soundPool.stop(streamID);
                        streamID = 0;
                    }
                }



                streamID = soundPool.play(soundId,
                        leftVolume,
                        rightVolume,
                        priority,
                        no_loop,
                        normal_playback_rate);


    }




    // zahrani tonu s volitelnym zpozdenim
    protected void playToneWithDelay(GuitarTone guitarTone, int delay) {
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
                Touching(gtr.getStringTouch(), null);

                if (stopBeforeTone){
                    if (streamID != 0){ //zastavi ton, predchoziho
                        soundPool.stop(streamID);
                        streamID = 0;
                    }
                }

                streamID = soundPool.play(soundId,
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



            @Override
            public void onAnimationStart(Animation animation) {

                imgButton.setBackgroundResource(R.drawable.touch);


            }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                imgButton.setBackgroundResource(0);
                nextTone = true;

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