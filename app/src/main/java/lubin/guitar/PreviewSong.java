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


public class PreviewSong extends Activity {

    ImageButton string14;
    ImageButton string13;
    ImageButton string12;
    ImageButton string11;
    ImageButton string10;
    ImageButton string24;
    ImageButton string23;
    ImageButton string22;
    ImageButton string21;
    ImageButton string20;
    ImageButton string34;
    ImageButton string33;
    ImageButton string32;
    ImageButton string31;
    ImageButton string30;
    ImageButton string44;
    ImageButton string43;
    ImageButton string42;
    ImageButton string41;
    ImageButton string40;
    ImageButton string54;
    ImageButton string53;
    ImageButton string52;
    ImageButton string51;
    ImageButton string50;
    ImageButton string64;
    ImageButton string63;
    ImageButton string62;
    ImageButton string61;
    ImageButton string60;


    ImageView Estring;
    ImageView Astring;
    ImageView Dstring;
    ImageView Gstring;
    ImageView Bstring;
    ImageView E2string;


    Button changeInstrument;
    Button trySong;
    Button btnplayMusic;

    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    int soundId;
    int tone; //druh tonu

    float normal_playback_rate;
    int numberInstrument = 0; //cislo nastroje
    Tones tones = new Tones();

    GuitarTone Etone;
    GuitarTone Atone;
    GuitarTone Dtone;
    GuitarTone Gtone;
    GuitarTone Btone;
    GuitarTone E2tone;

    boolean isPlaying = false;



    ArrayList<Tone> skladba = new ArrayList<>();
    ArrayList<GuitarTone> pokus = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_song);
        string14 = (ImageButton) findViewById(R.id.imageButton14);
        string13 = (ImageButton) findViewById(R.id.imageButton13);
        string12 = (ImageButton) findViewById(R.id.imageButton12);
        string11 = (ImageButton) findViewById(R.id.imageButton11);
        string10 = (ImageButton) findViewById(R.id.imageButton10);
        string24 = (ImageButton) findViewById(R.id.imageButton24);
        string23 = (ImageButton) findViewById(R.id.imageButton23);
        string22 = (ImageButton) findViewById(R.id.imageButton22);
        string21 = (ImageButton) findViewById(R.id.imageButton21);
        string20 = (ImageButton) findViewById(R.id.imageButton20);
        string34 = (ImageButton) findViewById(R.id.imageButton34);
        string33 = (ImageButton) findViewById(R.id.imageButton33);
        string32 = (ImageButton) findViewById(R.id.imageButton32);
        string31 = (ImageButton) findViewById(R.id.imageButton31);
        string30 = (ImageButton) findViewById(R.id.imageButton30);
        string44 = (ImageButton) findViewById(R.id.imageButton44);
        string43 = (ImageButton) findViewById(R.id.imageButton43);
        string42 = (ImageButton) findViewById(R.id.imageButton42);
        string41 = (ImageButton) findViewById(R.id.imageButton41);
        string40 = (ImageButton) findViewById(R.id.imageButton40);
        string54 = (ImageButton) findViewById(R.id.imageButton54);
        string53 = (ImageButton) findViewById(R.id.imageButton53);
        string52 = (ImageButton) findViewById(R.id.imageButton52);
        string51 = (ImageButton) findViewById(R.id.imageButton51);
        string50 = (ImageButton) findViewById(R.id.imageButton50);
        string64 = (ImageButton) findViewById(R.id.imageButton64);
        string63 = (ImageButton) findViewById(R.id.imageButton63);
        string62 = (ImageButton) findViewById(R.id.imageButton62);
        string61 = (ImageButton) findViewById(R.id.imageButton61);
        string60 = (ImageButton) findViewById(R.id.imageButton60);
        Estring = (ImageView) findViewById(R.id.Estring);
        Astring = (ImageView) findViewById(R.id.Astring);
        Dstring = (ImageView) findViewById(R.id.Dstring);
        Gstring = (ImageView) findViewById(R.id.Gstring);
        Bstring = (ImageView) findViewById(R.id.Bstring);
        E2string = (ImageView) findViewById(R.id.E2string);


        changeInstrument = (Button) findViewById(R.id.changeInstrument);
        changeInstrument.setOnClickListener(btnChangeOnClickListener);

        trySong = (Button) findViewById(R.id.btnTrySong);
        trySong.setOnClickListener(btnTrySong);
        btnplayMusic = (Button)findViewById(R.id.playMusic);
        btnplayMusic.setOnClickListener(previewSong);





        E2tone = new GuitarTone((ImageButton) findViewById(R.id.imageButton60), tones.getString60(), E2string);
        Btone = new GuitarTone((ImageButton) findViewById(R.id.imageButton50), tones.getString50(), Bstring);
        Gtone = new GuitarTone((ImageButton) findViewById(R.id.imageButton40), tones.getString40(), Gstring);
        Dtone = new GuitarTone((ImageButton) findViewById(R.id.imageButton30), tones.getString30(), Dstring);
        Atone = new GuitarTone((ImageButton) findViewById(R.id.imageButton20), tones.getString20(), Astring);
        Etone = new GuitarTone((ImageButton) findViewById(R.id.imageButton10), tones.getString10(), Estring);



        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

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
        soundId = soundPool.load(this, R.raw.s1, 1);

        tone = 0;
        normal_playback_rate = 0.5f;
        numberInstrument = 1;






    }

    OnClickListener btnTrySong = new OnClickListener() {
        @Override
        public void onClick(View view) {

            soundPool.release();

            Intent i = new Intent(PreviewSong.this, TrySong.class);
            startActivity(i);

        }
    };






    OnLoadCompleteListener soundPoolOnLoadCompleteListener =
            new OnLoadCompleteListener(){

                @Override
                public void onLoadComplete(SoundPool soundPool,
                                           int sampleId, int status) {
                    if(status==0){
                        //string11.setEnabled(true);
                    }else{
                        Toast.makeText(PreviewSong.this,
                                "SoundPool.load() fail",
                                Toast.LENGTH_LONG).show();
                    }

                }
            };







    // zahrani skladby
    OnClickListener previewSong = new OnClickListener() {
        @Override
        public void onClick(View view) {


            previewSong();

        };


        public void previewSong(){

            skladba = Songs.getSong2();

            pokus = createMusicFromTones(skladba);
            if (isPlaying){

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

                        if (i == skladba.size() - 1){
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




    //animace vibrace struny
    private void Shaking(ImageView string){

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        string.startAnimation(animation);
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




    OnClickListener btnChangeOnClickListener = new OnClickListener() { //zmena nastroje
        @Override
        public void onClick(View view) {

            if (numberInstrument <11){
                numberInstrument++;
            }
            else {
                numberInstrument = 1;
            }
            normal_playback_rate = 0.5f;

            int path = getResources().getIdentifier(("s" + String.valueOf(numberInstrument)), "raw", getPackageName());

            soundId = soundPool.load(getApplicationContext(), path, 1);
        }
    };







    // vrati tone podle jmena tonu
    public GuitarTone getToneFromName(String name) {
        GuitarTone guitarTone = new GuitarTone(string60, tones.getString60(), E2string);


        switch (name) {
            case "Gis4": {
                guitarTone.setStringImage(E2string);
                guitarTone.setStringValue(tones.getString64());
                guitarTone.setStringTouch(string64);
                break;
            }
            case "G4": {
                guitarTone.setStringImage(E2string);
                guitarTone.setStringValue(tones.getString63());
                guitarTone.setStringTouch(string63);
                break;
            }
            case "Fis4": {
                guitarTone.setStringImage(E2string);
                guitarTone.setStringValue(tones.getString62());
                guitarTone.setStringTouch(string62);
                break;
            }
            case "F4": {
                guitarTone.setStringImage(E2string);
                guitarTone.setStringValue(tones.getString61());
                guitarTone.setStringTouch(string61);
                break;
            }
            case "E4": {
                guitarTone.setStringImage(E2string);
                guitarTone.setStringValue(tones.getString60());
                guitarTone.setStringTouch(string60);
                break;
            }
            case "Dis4": {
                guitarTone.setStringImage(Bstring);
                guitarTone.setStringValue(tones.getString54());
                guitarTone.setStringTouch(string54);
                break;
            }
            case "D4": {
                guitarTone.setStringImage(Bstring);
                guitarTone.setStringValue(tones.getString53());
                guitarTone.setStringTouch(string53);
                break;
            }
            case "Cis4": {
                guitarTone.setStringImage(Bstring);
                guitarTone.setStringValue(tones.getString52());
                guitarTone.setStringTouch(string52);
                break;
            }
            case "C4": {
                guitarTone.setStringImage(Bstring);
                guitarTone.setStringValue(tones.getString51());
                guitarTone.setStringTouch(string51);
                break;
            }
            case "B3": {
                guitarTone.setStringImage(Bstring);
                guitarTone.setStringValue(tones.getString50());
                guitarTone.setStringTouch(string50);
                break;
            }
            case "B32 ": {
                guitarTone.setStringImage(Gstring);
                guitarTone.setStringValue(tones.getString44());
                guitarTone.setStringTouch(string44);
                break;
            }
            case "Ais3": {
                guitarTone.setStringImage(Gstring);
                guitarTone.setStringValue(tones.getString43());
                guitarTone.setStringTouch(string43);
                break;
            }
            case "A3": {
                guitarTone.setStringImage(Gstring);
                guitarTone.setStringValue(tones.getString42());
                guitarTone.setStringTouch(string42);
                break;
            }
            case "Gis3": {
                guitarTone.setStringImage(Gstring);
                guitarTone.setStringValue(tones.getString41());
                guitarTone.setStringTouch(string41);
                break;
            }
            case "G3": {
                guitarTone.setStringImage(Gstring);
                guitarTone.setStringValue(tones.getString40());
                guitarTone.setStringTouch(string40);
                break;
            }
            case "Fis3": {
                guitarTone.setStringImage(Dstring);
                guitarTone.setStringValue(tones.getString34());
                guitarTone.setStringTouch(string34);
                break;
            }
            case "F3": {
                guitarTone.setStringImage(Dstring);
                guitarTone.setStringValue(tones.getString33());
                guitarTone.setStringTouch(string33);
                break;
            }
            case "E3": {
                guitarTone.setStringImage(Dstring);
                guitarTone.setStringValue(tones.getString32());
                guitarTone.setStringTouch(string32);
                break;
            }
            case "Dis3": {
                guitarTone.setStringImage(Dstring);
                guitarTone.setStringValue(tones.getString31());
                guitarTone.setStringTouch(string31);
                break;
            }
            case "D3": {
                guitarTone.setStringImage(Dstring);
                guitarTone.setStringValue(tones.getString30());
                guitarTone.setStringTouch(string30);
                break;
            }
            case "Cis3": {
                guitarTone.setStringImage(Astring);
                guitarTone.setStringValue(tones.getString24());
                guitarTone.setStringTouch(string24);
                break;
            }
            case "C3": {
                guitarTone.setStringImage(Astring);
                guitarTone.setStringValue(tones.getString23());
                guitarTone.setStringTouch(string23);
                break;
            }
            case "B2": {
                guitarTone.setStringImage(Astring);
                guitarTone.setStringValue(tones.getString22());
                guitarTone.setStringTouch(string22);
                break;
            }
            case "Ais2": {
                guitarTone.setStringImage(Astring);
                guitarTone.setStringValue(tones.getString21());
                guitarTone.setStringTouch(string21);
                break;
            }
            case "A2": {
                guitarTone.setStringImage(Astring);
                guitarTone.setStringValue(tones.getString20());
                guitarTone.setStringTouch(string20);
                break;
            }
            case "Gis2": {
                guitarTone.setStringImage(Estring);
                guitarTone.setStringValue(tones.getString14());
                guitarTone.setStringTouch(string14);
                break;
            }
            case "G2": {
                guitarTone.setStringImage(Estring);
                guitarTone.setStringValue(tones.getString13());
                guitarTone.setStringTouch(string13);
                break;
            }
            case "Fis2": {
                guitarTone.setStringImage(Estring);
                guitarTone.setStringValue(tones.getString12());
                guitarTone.setStringTouch(string12);
                break;
            }
            case "F2": {
                guitarTone.setStringImage(Estring);
                guitarTone.setStringValue(tones.getString11());
                guitarTone.setStringTouch(string11);
                break;
            }
            case "E2": {
                guitarTone.setStringImage(Estring);
                guitarTone.setStringValue(tones.getString10());
                guitarTone.setStringTouch(string10);
                break;
            }
            case "silent": {
                guitarTone.setStringImage(Estring);
                guitarTone.setStringValue(0);
                guitarTone.setStringTouch(string10);
                break;

            }
            default:{
                guitarTone.setStringImage(Estring);
                guitarTone.setStringValue(0);
                guitarTone.setStringTouch(string10);
            }

        }

        return guitarTone;

    }

}
