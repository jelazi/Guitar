package lubin.guitar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public abstract class VirtualGuitarActivity extends AppCompatActivity {

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



    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    int soundId;
    int tone; //druh tonu

    float normal_playback_rate;


    Tones tones = new Tones();

    GuitarTone Etone;
    GuitarTone Atone;
    GuitarTone Dtone;
    GuitarTone Gtone;
    GuitarTone Btone;
    GuitarTone E2tone;


    TextView money;
    boolean playingSong = false;
    int numberTone = 0;
    GuitarTone playingTone;
    int numberInstrument = 0;
    String nameInstrument;

    boolean stopBeforeTone;

    String nameUser;





    Song skladba = new Song();


    ArrayList<Tone> tonySkladby = new ArrayList<>();
    ArrayList<GuitarTone> playingTones = new ArrayList<>();

    ArrayList<ImageButton> imageButtons = new ArrayList<>();
    Toast mToast;
    SharedPreferences settings;
    int streamID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        settings = PreferenceManager.getDefaultSharedPreferences(this);


        FileInOut.copyFromAssets(this, "Instruments", getFilesDir()+"/Instruments/");
        FileInOut.copyFromAssets(this, "Songs", getFilesDir()+"/Songs/");

        Songs.fillSongs(this);
        fillInstrument();

        skladba = Songs.callByName(getApplicationContext(), settings.getString("list_songs", "Pro Elisku"));

        stopBeforeTone = settings.getBoolean("stop_before_tone", false);

        nameUser = settings.getString("name_user", null);



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
        tone = 0;
        normal_playback_rate = 0.5f;


         //


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_virtual, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();


        switch(id)
        {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.settings:
              //  soundPool.release();
                Intent i = new Intent(VirtualGuitarActivity.this, SettingsScreenActivity.class);
                startActivity(i);
                break;

            case R.id.change_instrument:
                changeInstrument();
                break;

            case R.id.try_song:
                soundPool.release();
                i = new Intent(VirtualGuitarActivity.this, TrySongActivity.class);
                startActivity(i);
                break;

            case R.id.preview_song:
              //  soundPool.release();
                i = new Intent(VirtualGuitarActivity.this, PreviewSongActivity.class);
                startActivity(i);
                break;

            case R.id.play_chord:
               // soundPool.release();
                i = new Intent(VirtualGuitarActivity.this, PlayAcordActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }



    //vytvari tlacitka
    protected void createView() {

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


        Estring = (ImageView) findViewById(R.id.Estring); //obrazky strun
        Astring = (ImageView) findViewById(R.id.Astring);
        Dstring = (ImageView) findViewById(R.id.Dstring);
        Gstring = (ImageView) findViewById(R.id.Gstring);
        Bstring = (ImageView) findViewById(R.id.Bstring);
        E2string = (ImageView) findViewById(R.id.E2string);


        E2tone = new GuitarTone((ImageButton) findViewById(R.id.imageButton60), tones.getString60(), E2string);
        Btone = new GuitarTone((ImageButton) findViewById(R.id.imageButton50), tones.getString50(), Bstring);
        Gtone = new GuitarTone((ImageButton) findViewById(R.id.imageButton40), tones.getString40(), Gstring);
        Dtone = new GuitarTone((ImageButton) findViewById(R.id.imageButton30), tones.getString30(), Dstring);
        Atone = new GuitarTone((ImageButton) findViewById(R.id.imageButton20), tones.getString20(), Astring);
        Etone = new GuitarTone((ImageButton) findViewById(R.id.imageButton10), tones.getString10(), Estring);
        playingTone = new GuitarTone((ImageButton) findViewById(R.id.imageButton10), tones.getString10(), Estring);

    }

    View.OnClickListener stringPlayOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            };


    OnLoadCompleteListener soundPoolOnLoadCompleteListener =
            new OnLoadCompleteListener() {

                @Override
                public void onLoadComplete(SoundPool soundPool,
                                           int sampleId, int status) {
                    if (status == 0) {

                    } else {
                        Toast.makeText(VirtualGuitarActivity.this,
                                "SoundPool.load() fail",
                                Toast.LENGTH_LONG).show();
                    }
                }
            };


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

                soundPool.play(soundId,
                        leftVolume,
                        rightVolume,
                        priority,
                        no_loop,
                        normal_playback_rate);
            }
        }, delay);
    }



    //animace vibrace struny
    protected void Shaking(ImageView string) {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        string.startAnimation(animation);
    }

    //animace dotyku
    protected void Touching(final ImageButton imgButton, final Drawable background) {

        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imgButton.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {
                imgButton.setBackgroundResource(R.drawable.touch);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                imgButton.setBackground(background);
            }
        });
    }


    protected void Touching(final ImageButton imgButton, final ImageButton nextButton, final Drawable background) {

        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imgButton.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {
                imgButton.setBackgroundResource(R.drawable.touch);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgButton.setBackground(background);
                nextButton.setBackgroundResource(R.drawable.touch);
            }
        });
    }




    protected void changeInstrument (){ //zmena nastroje na zaklade slozky Instruments


        Songs.fillSongs(this);


        fillInstrument();

        int lenght= Songs.getNameInstruments().size();


        if (numberInstrument + 1 < lenght) {

            numberInstrument++;

        } else {
            numberInstrument = 0;
        }

        settings.edit().putString("list_instruments", Songs.getNameInstruments().get(numberInstrument)).apply();;

        fillInstrument();


        try {
            mToast.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mToast = Toast.makeText(this,"Zvuk kytary změněn na: " + nameInstrument.substring(0, nameInstrument.length()-4),Toast.LENGTH_SHORT);

        mToast.show();



            mToast = Toast.makeText(this,"Hudba: " + settings.getString("list_songs", null),Toast.LENGTH_SHORT);
            mToast.show();



    }


    protected void fillInstrument(){

        nameInstrument = settings.getString("list_instruments", "a1.wav");
        numberInstrument = Songs.getNameInstruments().indexOf(nameInstrument);

        try {
            soundId = soundPool.load( getFilesDir()+"/Instruments/"+nameInstrument, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void playToneFromTouch(View v, Drawable background) {
        switch (v.getId()) {
            case R.id.imageButton14: {
                normal_playback_rate = tones.getString14();
                Shaking(Estring);
                Touching(string14, background);
                break;
            }
            case R.id.imageButton13: {
                normal_playback_rate = tones.getString13();
                Shaking(Estring);
                Touching(string13, background);
                break;
            }
            case R.id.imageButton12: {
                normal_playback_rate = tones.getString12();
                Shaking(Estring);
                Touching(string12, background);
                break;
            }
            case R.id.imageButton11: {
                normal_playback_rate = tones.getString11();
                Shaking(Estring);
                Touching(string11, background);
                break;
            }
            case R.id.imageButton10: {
                normal_playback_rate = tones.getString10();
                Shaking(Estring);
                Touching(string10, background);
                break;
            }
            case R.id.imageButton24: {
                normal_playback_rate = tones.getString24();
                Shaking(Astring);
                Touching(string24, background);
                break;
            }
            case R.id.imageButton23: {
                normal_playback_rate = tones.getString23();
                Shaking(Astring);
                Touching(string23, background);
                break;
            }
            case R.id.imageButton22: {
                normal_playback_rate = tones.getString22();
                Shaking(Astring);
                Touching(string22, background);
                break;
            }
            case R.id.imageButton21: {
                normal_playback_rate = tones.getString21();
                Shaking(Astring);
                Touching(string21, background);
                break;
            }
            case R.id.imageButton20: {
                normal_playback_rate = tones.getString20();
                Shaking(Astring);
                Touching(string20, background);
                break;
            }
            case R.id.imageButton34: {
                normal_playback_rate = tones.getString34();
                Shaking(Dstring);
                Touching(string34, background);
                break;
            }
            case R.id.imageButton33: {
                normal_playback_rate = tones.getString33();
                Shaking(Dstring);
                Touching(string33, background);
                break;
            }
            case R.id.imageButton32: {
                normal_playback_rate = tones.getString32();
                Shaking(Dstring);
                Touching(string32, background);
                break;
            }
            case R.id.imageButton31: {
                normal_playback_rate = tones.getString31();
                Shaking(Dstring);
                Touching(string31, background);
                break;
            }
            case R.id.imageButton30: {
                normal_playback_rate = tones.getString30();
                Shaking(Dstring);
                Touching(string30, background);
                break;
            }
            case R.id.imageButton44: {
                normal_playback_rate = tones.getString44();
                Shaking(Gstring);
                Touching(string44, background);
                break;
            }
            case R.id.imageButton43: {
                normal_playback_rate = tones.getString43();
                Shaking(Gstring);
                Touching(string43, background);
                break;
            }
            case R.id.imageButton42: {
                normal_playback_rate = tones.getString42();
                Shaking(Gstring);
                Touching(string42, background);
                break;
            }
            case R.id.imageButton41: {
                normal_playback_rate = tones.getString41();
                Shaking(Gstring);
                Touching(string41, background);
                break;
            }
            case R.id.imageButton40: {
                normal_playback_rate = tones.getString40();
                Shaking(Gstring);
                Touching(string40, background);
                break;
            }
            case R.id.imageButton54: {
                normal_playback_rate = tones.getString54();
                Shaking(Bstring);
                Touching(string54, background);
                break;
            }
            case R.id.imageButton53: {
                normal_playback_rate = tones.getString53();
                Shaking(Bstring);
                Touching(string53, background);
                break;
            }
            case R.id.imageButton52: {
                normal_playback_rate = tones.getString52();
                Shaking(Bstring);
                Touching(string52, background);
                break;
            }
            case R.id.imageButton51: {
                normal_playback_rate = tones.getString51();
                Shaking(Bstring);
                Touching(string51, background);
                break;
            }
            case R.id.imageButton50: {
                normal_playback_rate = tones.getString50();
                Shaking(Bstring);
                Touching(string50, background);
                break;
            }
            case R.id.imageButton64: {
                normal_playback_rate = tones.getString64();
                Shaking(E2string);
                Touching(string64, background);
                break;
            }
            case R.id.imageButton63: {
                normal_playback_rate = tones.getString63();
                Shaking(E2string);
                Touching(string63, background);
                break;
            }
            case R.id.imageButton62: {
                normal_playback_rate = tones.getString62();
                Shaking(E2string);
                Touching(string62, background);
                break;
            }
            case R.id.imageButton61: {
                normal_playback_rate = tones.getString61();
                Shaking(E2string);
                Touching(string61, background);
                break;
            }
            case R.id.imageButton60: {
                normal_playback_rate = tones.getString60();
                Shaking(E2string);
                Touching(string60, background);
                break;
            }
            default: {
                normal_playback_rate = tones.getString10();
                Shaking(E2string);
                Touching(string10, background);
                break;
            }
        }

        float vol = audioManager.getStreamVolume(
                AudioManager.STREAM_MUSIC);
        float maxVol = audioManager.getStreamMaxVolume(
                AudioManager.STREAM_MUSIC);
        float leftVolume = vol / maxVol;
        float rightVolume = vol / maxVol;
        int priority = 1;
        int no_loop = 0;

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





    protected void playToneFromTouch(View v, ImageButton nextButton, Drawable background) {
        switch (v.getId()) {
            case R.id.imageButton14: {
                normal_playback_rate = tones.getString14();
                Shaking(Estring);
                Touching(string14, nextButton, background);
                break;
            }
            case R.id.imageButton13: {
                normal_playback_rate = tones.getString13();
                Shaking(Estring);
                Touching(string13, nextButton, background);
                break;
            }
            case R.id.imageButton12: {
                normal_playback_rate = tones.getString12();
                Shaking(Estring);
                Touching(string12, nextButton, background);
                break;
            }
            case R.id.imageButton11: {
                normal_playback_rate = tones.getString11();
                Shaking(Estring);
                Touching(string11, nextButton, background);
                break;
            }
            case R.id.imageButton10: {
                normal_playback_rate = tones.getString10();
                Shaking(Estring);
                Touching(string10, nextButton, background);
                break;
            }
            case R.id.imageButton24: {
                normal_playback_rate = tones.getString24();
                Shaking(Astring);
                Touching(string24, nextButton, background);
                break;
            }
            case R.id.imageButton23: {
                normal_playback_rate = tones.getString23();
                Shaking(Astring);
                Touching(string23, nextButton, background);
                break;
            }
            case R.id.imageButton22: {
                normal_playback_rate = tones.getString22();
                Shaking(Astring);
                Touching(string22, nextButton, background);
                break;
            }
            case R.id.imageButton21: {
                normal_playback_rate = tones.getString21();
                Shaking(Astring);
                Touching(string21, nextButton, background);
                break;
            }
            case R.id.imageButton20: {
                normal_playback_rate = tones.getString20();
                Shaking(Astring);
                Touching(string20, nextButton, background);
                break;
            }
            case R.id.imageButton34: {
                normal_playback_rate = tones.getString34();
                Shaking(Dstring);
                Touching(string34, nextButton, background);
                break;
            }
            case R.id.imageButton33: {
                normal_playback_rate = tones.getString33();
                Shaking(Dstring);
                Touching(string33, nextButton, background);
                break;
            }
            case R.id.imageButton32: {
                normal_playback_rate = tones.getString32();
                Shaking(Dstring);
                Touching(string32, nextButton, background);
                break;
            }
            case R.id.imageButton31: {
                normal_playback_rate = tones.getString31();
                Shaking(Dstring);
                Touching(string31, nextButton, background);
                break;
            }
            case R.id.imageButton30: {
                normal_playback_rate = tones.getString30();
                Shaking(Dstring);
                Touching(string30, nextButton, background);
                break;
            }
            case R.id.imageButton44: {
                normal_playback_rate = tones.getString44();
                Shaking(Gstring);
                Touching(string44, nextButton, background);
                break;
            }
            case R.id.imageButton43: {
                normal_playback_rate = tones.getString43();
                Shaking(Gstring);
                Touching(string43, nextButton, background);
                break;
            }
            case R.id.imageButton42: {
                normal_playback_rate = tones.getString42();
                Shaking(Gstring);
                Touching(string42, nextButton, background);
                break;
            }
            case R.id.imageButton41: {
                normal_playback_rate = tones.getString41();
                Shaking(Gstring);
                Touching(string41, nextButton, background);
                break;
            }
            case R.id.imageButton40: {
                normal_playback_rate = tones.getString40();
                Shaking(Gstring);
                Touching(string40, nextButton, background);
                break;
            }
            case R.id.imageButton54: {
                normal_playback_rate = tones.getString54();
                Shaking(Bstring);
                Touching(string54, nextButton, background);
                break;
            }
            case R.id.imageButton53: {
                normal_playback_rate = tones.getString53();
                Shaking(Bstring);
                Touching(string53, nextButton, background);
                break;
            }
            case R.id.imageButton52: {
                normal_playback_rate = tones.getString52();
                Shaking(Bstring);
                Touching(string52, nextButton, background);
                break;
            }
            case R.id.imageButton51: {
                normal_playback_rate = tones.getString51();
                Shaking(Bstring);
                Touching(string51, nextButton, background);
                break;
            }
            case R.id.imageButton50: {
                normal_playback_rate = tones.getString50();
                Shaking(Bstring);
                Touching(string50, nextButton, background);
                break;
            }
            case R.id.imageButton64: {
                normal_playback_rate = tones.getString64();
                Shaking(E2string);
                Touching(string64, nextButton, background);
                break;
            }
            case R.id.imageButton63: {
                normal_playback_rate = tones.getString63();
                Shaking(E2string);
                Touching(string63, nextButton, background);
                break;
            }
            case R.id.imageButton62: {
                normal_playback_rate = tones.getString62();
                Shaking(E2string);
                Touching(string62, nextButton, background);
                break;
            }
            case R.id.imageButton61: {
                normal_playback_rate = tones.getString61();
                Shaking(E2string);
                Touching(string61, nextButton, background);
                break;
            }
            case R.id.imageButton60: {
                normal_playback_rate = tones.getString60();
                Shaking(E2string);
                Touching(string60, nextButton, background);
                break;
            }
            default: {
                normal_playback_rate = tones.getString10();
                Shaking(E2string);
                Touching(string10, nextButton, background);
                break;
            }
        }

        float vol = audioManager.getStreamVolume(
                AudioManager.STREAM_MUSIC);
        float maxVol = audioManager.getStreamMaxVolume(
                AudioManager.STREAM_MUSIC);
        float leftVolume = vol / maxVol;
        float rightVolume = vol / maxVol;
        int priority = 1;
        int no_loop = 0;

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



    // vrati ton podle zmacknute struny
    protected GuitarTone getToneFromTouch(int imageButtonId) {
        GuitarTone guitarTone = new GuitarTone(string10, tones.getString10(), Estring);

        if (imageButtonId == string14.getId()) {
            guitarTone.setStringValue(tones.getString14());
            guitarTone.setStringImage(Estring);
            guitarTone.setStringTouch(string14);
            return guitarTone;
        }
        if (imageButtonId == string13.getId()) {
            guitarTone.setStringValue(tones.getString13());
            guitarTone.setStringImage(Estring);
            guitarTone.setStringTouch(string13);
            return guitarTone;
        }
        if (imageButtonId == string12.getId()) {
            guitarTone.setStringValue(tones.getString12());
            guitarTone.setStringImage(Estring);
            guitarTone.setStringTouch(string12);
            return guitarTone;
        }
        if (imageButtonId == string11.getId()) {
            guitarTone.setStringValue(tones.getString11());
            guitarTone.setStringImage(Estring);
            guitarTone.setStringTouch(string11);
            return guitarTone;
        }
        if (imageButtonId == string10.getId()) {
            guitarTone.setStringValue(tones.getString10());
            guitarTone.setStringImage(Estring);
            guitarTone.setStringTouch(string10);
            return guitarTone;
        }
        if (imageButtonId == string24.getId()) {
            guitarTone.setStringValue(tones.getString24());
            guitarTone.setStringImage(Astring);
            guitarTone.setStringTouch(string24);
            return guitarTone;
        }
        if (imageButtonId == string23.getId()) {
            guitarTone.setStringValue(tones.getString23());
            guitarTone.setStringImage(Astring);
            guitarTone.setStringTouch(string23);
            return guitarTone;
        }
        if (imageButtonId == string22.getId()) {
            guitarTone.setStringValue(tones.getString22());
            guitarTone.setStringImage(Astring);
            guitarTone.setStringTouch(string22);
            return guitarTone;
        }
        if (imageButtonId == string21.getId()) {
            guitarTone.setStringValue(tones.getString21());
            guitarTone.setStringImage(Astring);
            guitarTone.setStringTouch(string21);
            return guitarTone;
        }
        if (imageButtonId == string20.getId()) {
            guitarTone.setStringValue(tones.getString20());
            guitarTone.setStringImage(Astring);
            guitarTone.setStringTouch(string20);
            return guitarTone;
        }
        if (imageButtonId == string34.getId()) {
            guitarTone.setStringValue(tones.getString34());
            guitarTone.setStringImage(Dstring);
            guitarTone.setStringTouch(string34);
            return guitarTone;
        }
        if (imageButtonId == string33.getId()) {
            guitarTone.setStringValue(tones.getString33());
            guitarTone.setStringImage(Dstring);
            guitarTone.setStringTouch(string33);
            return guitarTone;
        }
        if (imageButtonId == string32.getId()) {
            guitarTone.setStringValue(tones.getString32());
            guitarTone.setStringImage(Dstring);
            guitarTone.setStringTouch(string32);
            return guitarTone;
        }
        if (imageButtonId == string31.getId()) {
            guitarTone.setStringValue(tones.getString31());
            guitarTone.setStringImage(Dstring);
            guitarTone.setStringTouch(string31);
            return guitarTone;
        }
        if (imageButtonId == string30.getId()) {
            guitarTone.setStringValue(tones.getString30());
            guitarTone.setStringImage(Dstring);
            guitarTone.setStringTouch(string30);
            return guitarTone;
        }
        if (imageButtonId == string44.getId()) {
            guitarTone.setStringValue(tones.getString44());
            guitarTone.setStringImage(Gstring);
            guitarTone.setStringTouch(string44);
            return guitarTone;
        }
        if (imageButtonId == string43.getId()) {
            guitarTone.setStringValue(tones.getString43());
            guitarTone.setStringImage(Gstring);
            guitarTone.setStringTouch(string43);
            return guitarTone;
        }
        if (imageButtonId == string42.getId()) {
            guitarTone.setStringValue(tones.getString42());
            guitarTone.setStringImage(Gstring);
            guitarTone.setStringTouch(string42);
            return guitarTone;
        }
        if (imageButtonId == string41.getId()) {
            guitarTone.setStringValue(tones.getString41());
            guitarTone.setStringImage(Gstring);
            guitarTone.setStringTouch(string41);
            return guitarTone;
        }
        if (imageButtonId == string40.getId()) {
            guitarTone.setStringValue(tones.getString40());
            guitarTone.setStringImage(Gstring);
            guitarTone.setStringTouch(string40);
            return guitarTone;
        }
        if (imageButtonId == string54.getId()) {
            guitarTone.setStringValue(tones.getString54());
            guitarTone.setStringImage(Bstring);
            guitarTone.setStringTouch(string54);
            return guitarTone;
        }
        if (imageButtonId == string53.getId()) {
            guitarTone.setStringValue(tones.getString53());
            guitarTone.setStringImage(Bstring);
            guitarTone.setStringTouch(string53);
            return guitarTone;
        }
        if (imageButtonId == string52.getId()) {
            guitarTone.setStringValue(tones.getString52());
            guitarTone.setStringImage(Bstring);
            guitarTone.setStringTouch(string52);
            return guitarTone;
        }
        if (imageButtonId == string51.getId()) {
            guitarTone.setStringValue(tones.getString51());
            guitarTone.setStringImage(Bstring);
            guitarTone.setStringTouch(string51);
            return guitarTone;
        }
        if (imageButtonId == string50.getId()) {
            guitarTone.setStringValue(tones.getString50());
            guitarTone.setStringImage(Bstring);
            guitarTone.setStringTouch(string50);
            return guitarTone;
        }
        if (imageButtonId == string64.getId()) {
            guitarTone.setStringValue(tones.getString64());
            guitarTone.setStringImage(E2string);
            guitarTone.setStringTouch(string64);
            return guitarTone;
        }
        if (imageButtonId == string63.getId()) {
            guitarTone.setStringValue(tones.getString63());
            guitarTone.setStringImage(E2string);
            guitarTone.setStringTouch(string63);
            return guitarTone;
        }
        if (imageButtonId == string62.getId()) {
            guitarTone.setStringValue(tones.getString62());
            guitarTone.setStringImage(E2string);
            guitarTone.setStringTouch(string62);
            return guitarTone;
        }
        if (imageButtonId == string61.getId()) {
            guitarTone.setStringValue(tones.getString61());
            guitarTone.setStringImage(E2string);
            guitarTone.setStringTouch(string61);
            return guitarTone;
        }
        if (imageButtonId == string60.getId()) {
            guitarTone.setStringValue(tones.getString60());
            guitarTone.setStringImage(E2string);
            guitarTone.setStringTouch(string60);
            return guitarTone;
        }

        return guitarTone;
    }

    // vrati GuitarTone podle jmena tonu
    protected GuitarTone getToneFromName(String name) {
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
            default: {
                guitarTone.setStringImage(Estring);
                guitarTone.setStringValue(0);
                guitarTone.setStringTouch(string10);
            }
        }

        return guitarTone;


    }

    public void cleanStrings(){ //vymazání všech oznacenych strun a dotyku

        ImageButton [] fretchs = {string10, string11, string12, string13, string14, string20,
                string21, string22, string23, string24, string30, string31, string32, string33,
                string34, string40, string41, string42, string43, string44, string50, string51,
                string52, string53, string54, string60, string61, string62, string63, string64};


        ImageView [] strings = {Estring, Astring, Dstring, Gstring, Bstring, E2string};



        for (ImageButton f : fretchs){
            f.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }


        for (ImageView s : strings) {
            s.getDrawable().clearColorFilter();
        }
    }


}