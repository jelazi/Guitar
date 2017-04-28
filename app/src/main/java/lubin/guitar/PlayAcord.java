package lubin.guitar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



public class PlayAcord extends Activity {

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
    Button changeAkord;
    Button setting;


    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    int soundId;
    int tone; //druh tonu

    float normal_playback_rate;
    int numberInstrument = 0; //cislo nastroje
    private GestureDetector gestureDetector;
    Tones tones = new Tones();

    GuitarTone Etone;
    GuitarTone Atone;
    GuitarTone Dtone;
    GuitarTone Gtone;
    GuitarTone Btone;
    GuitarTone E2tone;
    int akordNumber;
    String[] akords = {"Cdur", "Ddur", "Edur", "Fdur", "Gdur", "Adur", "Bdur", "Cmi"};
    boolean isPlaying = false;



    int moneyValue = 0;

    TextView akordName;





    ArrayList<Tone> skladba = new ArrayList<>();
    ArrayList<GuitarTone> pokus = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_acord);
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
        string14.setOnTouchListener(stringPlayOnTouchListener);
        string13.setOnTouchListener(stringPlayOnTouchListener);
        string12.setOnTouchListener(stringPlayOnTouchListener);
        string11.setOnTouchListener(stringPlayOnTouchListener);
        string10.setOnTouchListener(stringPlayOnTouchListener);
        string24.setOnTouchListener(stringPlayOnTouchListener);
        string23.setOnTouchListener(stringPlayOnTouchListener);
        string22.setOnTouchListener(stringPlayOnTouchListener);
        string21.setOnTouchListener(stringPlayOnTouchListener);
        string20.setOnTouchListener(stringPlayOnTouchListener);
        string34.setOnTouchListener(stringPlayOnTouchListener);
        string33.setOnTouchListener(stringPlayOnTouchListener);
        string32.setOnTouchListener(stringPlayOnTouchListener);
        string31.setOnTouchListener(stringPlayOnTouchListener);
        string30.setOnTouchListener(stringPlayOnTouchListener);
        string44.setOnTouchListener(stringPlayOnTouchListener);
        string43.setOnTouchListener(stringPlayOnTouchListener);
        string42.setOnTouchListener(stringPlayOnTouchListener);
        string41.setOnTouchListener(stringPlayOnTouchListener);
        string40.setOnTouchListener(stringPlayOnTouchListener);
        string54.setOnTouchListener(stringPlayOnTouchListener);
        string53.setOnTouchListener(stringPlayOnTouchListener);
        string52.setOnTouchListener(stringPlayOnTouchListener);
        string51.setOnTouchListener(stringPlayOnTouchListener);
        string50.setOnTouchListener(stringPlayOnTouchListener);
        string64.setOnTouchListener(stringPlayOnTouchListener);
        string63.setOnTouchListener(stringPlayOnTouchListener);
        string62.setOnTouchListener(stringPlayOnTouchListener);
        string61.setOnTouchListener(stringPlayOnTouchListener);
        string60.setOnTouchListener(stringPlayOnTouchListener);
        Estring = (ImageView) findViewById(R.id.Estring);
        Astring = (ImageView) findViewById(R.id.Astring);
        Dstring = (ImageView) findViewById(R.id.Dstring);
        Gstring = (ImageView) findViewById(R.id.Gstring);
        Bstring = (ImageView) findViewById(R.id.Bstring);
        E2string = (ImageView) findViewById(R.id.E2string);


        changeInstrument = (Button) findViewById(R.id.changeInstrument);
        changeInstrument.setOnClickListener(btnChangeOnClickListener);
        changeAkord = (Button) findViewById(R.id.changeAkord);
        changeAkord.setOnClickListener(btnChangeAkord);
        setting = (Button) findViewById(R.id.btnTrySong);
        setting.setOnClickListener(getSetting);





        E2tone = new GuitarTone((ImageButton) findViewById(R.id.imageButton60), tones.getString60(), E2string);
        Btone = new GuitarTone((ImageButton) findViewById(R.id.imageButton50), tones.getString50(), Bstring);
        Gtone = new GuitarTone((ImageButton) findViewById(R.id.imageButton40), tones.getString40(), Gstring);
        Dtone = new GuitarTone((ImageButton) findViewById(R.id.imageButton30), tones.getString30(), Dstring);
        Atone = new GuitarTone((ImageButton) findViewById(R.id.imageButton20), tones.getString20(), Astring);
        Etone = new GuitarTone((ImageButton) findViewById(R.id.imageButton10), tones.getString10(), Estring);

        //Etone.setColorFilter(0x80ff0000); zmena barvy

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
        akordNumber = 0;
        akordName = (TextView)findViewById(R.id.akordValue);
        akordName.setText("Volné struny");





    }

    OnClickListener getSetting = new OnClickListener() {
        @Override
        public void onClick(View view) {

            soundPool.release();

            Intent i = new Intent(PlayAcord.this, Settings.class);
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
                        Toast.makeText(PlayAcord.this,
                                "SoundPool.load() fail",
                                Toast.LENGTH_LONG).show();
                    }

                }
            };



    OnClickListener stringPlayOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View v ) {
                    playTone(getToneFromTouch(v.getId()),0);

                }
            };





    View.OnTouchListener stringPlayOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

     boolean play = true;

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    if (play) {
                        playTone(getToneFromTouch(view.getId()), 0);
                        play = false;

                    }
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    play = true;
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    play = false;
                    break;
                }
            }

            // gdt.onTouchEvent(motionEvent);
            return true;
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



    // oznaci struny dle akordu
    private void showAkordOnBoard(){
        if (Etone.getStringTouch().isEnabled()) {
            Etone.getStringTouch().setBackgroundResource(R.drawable.touch);
            Etone.getStringTouch().setColorFilter(0x80002233);
        }

        if (Atone.getStringTouch().isEnabled()) {
            Atone.getStringTouch().setBackgroundResource(R.drawable.touch);
            Atone.getStringTouch().setColorFilter(0x80112233);
        }

        if (Dtone.getStringTouch().isEnabled()) {
            Dtone.getStringTouch().setBackgroundResource(R.drawable.touch);
            Dtone.getStringTouch().setColorFilter(0x80112233);
        }

        if (Gtone.getStringTouch().isEnabled()) {
            Gtone.getStringTouch().setBackgroundResource(R.drawable.touch);
            Gtone.getStringTouch().setColorFilter(0x80112233);
        }

        if (Btone.getStringTouch().isEnabled()) {
            Btone.getStringTouch().setBackgroundResource(R.drawable.touch);
            Btone.getStringTouch().setColorFilter(0x80112233);
        }
        if (E2tone.getStringTouch().isEnabled()) {
            E2tone.getStringTouch().setBackgroundResource(R.drawable.touch);
            E2tone.getStringTouch().setColorFilter(0x80112233);
        }


    }

    // vymaze oznaceni strun akordu
    private void eraseAkordOnBoard(){
        if (Etone.getStringTouch().isEnabled()) {
            Etone.getStringTouch().setBackgroundResource(0);
        }
        if (Atone.getStringTouch().isEnabled()) {
            Atone.getStringTouch().setBackgroundResource(0);
        }
        if (Dtone.getStringTouch().isEnabled()) {
            Dtone.getStringTouch().setBackgroundResource(0);
        }
        if (Gtone.getStringTouch().isEnabled()) {
            Gtone.getStringTouch().setBackgroundResource(0);
        }
        if (Btone.getStringTouch().isEnabled()) {
            Btone.getStringTouch().setBackgroundResource(0);
        }
        if (E2tone.getStringTouch().isEnabled()) {
            E2tone.getStringTouch().setBackgroundResource(0);
        }



    }


    //animace vibrace struny
    private void Shaking(ImageView string){

        android.view.animation.Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        string.startAnimation(animation);
    }

    //animace dotyku
    private void Touching(final ImageButton imgButton){

        android.view.animation.Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imgButton.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            boolean isBackground = false;


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



            }
        });
    }


    //zmacknute tlacitko
    OnClickListener btnChangeAkord = new OnClickListener() {
        @Override
        public void onClick(View view) {

            float[] guitarStringValue = new float[6];
            GuitarTone[] guitarTone = new GuitarTone[6];
         //   eraseAkordOnBoard();

            guitarTone[0] = Etone;
            guitarTone[1] = Atone;
            guitarTone[2] = Dtone;
            guitarTone[3] = Gtone;
            guitarTone[4] = Btone;
            guitarTone[5] = E2tone;
            guitarStringValue = tones.getAkord(akords[akordNumber]);
            if (akordNumber < 7) {
                akordNumber++;
            }
            else
            {
                akordNumber = 0;
            }

            akordName.setText(akords[akordNumber-1]);
            for (int i = 0; i <=5; i++){
                guitarTone[i].setStringValue(guitarStringValue[i]);
            }
            guitarTone = FillStringsValue(guitarTone);
            if (Etone.getStringValue() == 0){
                string10.setEnabled(false);
                string11.setEnabled(false);
                string12.setEnabled(false);
                string13.setEnabled(false);
                string14.setEnabled(false);
            }
            else{
                string10.setEnabled(true);
                string11.setEnabled(true);
                string12.setEnabled(true);
                string13.setEnabled(true);
                string14.setEnabled(true);
            }
            if (Atone.getStringValue() == 0){
                string20.setEnabled(false);
                string21.setEnabled(false);
                string22.setEnabled(false);
                string23.setEnabled(false);
                string24.setEnabled(false);
            }
            else{
                string20.setEnabled(true);
                string21.setEnabled(true);
                string22.setEnabled(true);
                string23.setEnabled(true);
                string24.setEnabled(true);
            }
            if (Dtone.getStringValue() == 0){
                string30.setEnabled(false);
                string31.setEnabled(false);
                string32.setEnabled(false);
                string33.setEnabled(false);
                string34.setEnabled(false);
            }
            else{
                string30.setEnabled(true);
                string31.setEnabled(true);
                string32.setEnabled(true);
                string33.setEnabled(true);
                string34.setEnabled(true);
            }
            if (Gtone.getStringValue() == 0){
                string40.setEnabled(false);
                string41.setEnabled(false);
                string42.setEnabled(false);
                string43.setEnabled(false);
                string44.setEnabled(false);

            }
            else{
                string40.setEnabled(true);
                string41.setEnabled(true);
                string42.setEnabled(true);
                string43.setEnabled(true);
                string44.setEnabled(true);
            }
            if (Btone.getStringValue() == 0){
                string50.setEnabled(false);
                string51.setEnabled(false);
                string52.setEnabled(false);
                string53.setEnabled(false);
                string54.setEnabled(false);
            }
            else{
                string50.setEnabled(true);
                string51.setEnabled(true);
                string52.setEnabled(true);
                string53.setEnabled(true);
                string54.setEnabled(true);
            }
            if (E2tone.getStringValue() == 0){
                string60.setEnabled(false);
                string61.setEnabled(false);
                string62.setEnabled(false);
                string63.setEnabled(false);
                string64.setEnabled(false);
            }
            else{
                string60.setEnabled(true);
                string61.setEnabled(true);
                string62.setEnabled(true);
                string63.setEnabled(true);
                string64.setEnabled(true);
            }
          //  showAkordOnBoard();
        }
    };

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



    //naplni struny podle akordu
    public GuitarTone[] FillStringsValue(GuitarTone[] oldStrings) {
        GuitarTone[] newStrings = new GuitarTone[6];
        newStrings = oldStrings;

        for (int i = 0; i <= 5; i++) {
            if (oldStrings[i].getStringValue() == tones.string14) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string14);
            }
            if (oldStrings[i].getStringValue() == tones.string13) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string13);
            }
            if (oldStrings[i].getStringValue() == tones.string12) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string12);
            }
            if (oldStrings[i].getStringValue() == tones.string11) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string11);
            }
            if (oldStrings[i].getStringValue() == tones.string10) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string10);
            }
            if (oldStrings[i].getStringValue() == tones.string24) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string24);
            }
            if (oldStrings[i].getStringValue() == tones.string23) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string23);
            }
            if (oldStrings[i].getStringValue() == tones.string22) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string22);
            }
            if (oldStrings[i].getStringValue() == tones.string21) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string21);
            }
            if (oldStrings[i].getStringValue() == tones.string20) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string20);
            }
            if (oldStrings[i].getStringValue() == tones.string34) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string34);
            }
            if (oldStrings[i].getStringValue() == tones.string33) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string33);
            }
            if (oldStrings[i].getStringValue() == tones.string32) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string32);
            }
            if (oldStrings[i].getStringValue() == tones.string31) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string31);
            }
            if (oldStrings[i].getStringValue() == tones.string30) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string30);
            }
            if (oldStrings[i].getStringValue() == tones.string44 && oldStrings[i] == Gtone) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string44);
            }
            if (oldStrings[i].getStringValue() == tones.string43) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string43);
            }
            if (oldStrings[i].getStringValue() == tones.string42) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string42);
            }
            if (oldStrings[i].getStringValue() == tones.string41) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string41);
            }
            if (oldStrings[i].getStringValue() == tones.string40) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string40);
            }
            if (oldStrings[i].getStringValue() == tones.string54) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string54);
            }
            if (oldStrings[i].getStringValue() == tones.string53) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string53);
            }
            if (oldStrings[i].getStringValue() == tones.string52) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string52);
            }
            if (oldStrings[i].getStringValue() == tones.string51) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string51);
            }
            if (oldStrings[i].getStringValue() == tones.string50) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string50);
            }
            if (oldStrings[i].getStringValue() == tones.string64) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string64);
            }
            if (oldStrings[i].getStringValue() == tones.string63) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string63);
            }
            if (oldStrings[i].getStringValue() == tones.string62) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string62);
            }
            if (oldStrings[i].getStringValue() == tones.string61) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string61);
            }
            if (oldStrings[i].getStringValue() == tones.string60) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string60);
            }
            if (oldStrings[i].getStringValue() == 0) {
                newStrings[i].setStringImage(null);
                newStrings[i].setStringTouch(null);
            }
        }
        return newStrings;
    }

    // vrati ton podle zmacknute struny
    public GuitarTone getToneFromTouch (int imageButtonId){
        GuitarTone guitarTone = new GuitarTone(string10, tones.getString10(), Estring);

        if (imageButtonId == string14.getId()) {
            guitarTone = Etone;
            return guitarTone;
        }
        if (imageButtonId == string13.getId()) {
            guitarTone = Etone;
            return guitarTone;
        }
        if (imageButtonId == string12.getId()) {
            guitarTone = Etone;
            return guitarTone;
        }
        if (imageButtonId == string11.getId()) {
            guitarTone = Etone;
            return guitarTone;
        }
        if (imageButtonId == string10.getId()) {
            guitarTone = Etone;
            return guitarTone;
        }
        if (imageButtonId == string24.getId()) {
            guitarTone = Atone;
            return guitarTone;
        }
        if (imageButtonId == string23.getId()) {
            guitarTone = Atone;
            return guitarTone;
        }
        if (imageButtonId == string22.getId()) {
            guitarTone = Atone;
            return guitarTone;
        }
        if (imageButtonId == string21.getId()) {
            guitarTone = Atone;
            return guitarTone;
        }
        if (imageButtonId == string20.getId()) {
            guitarTone = Atone;
            return guitarTone;
        }
        if (imageButtonId == string34.getId()) {
            guitarTone = Dtone;
            return guitarTone;
        }
        if (imageButtonId == string33.getId()) {
            guitarTone = Dtone;
            return guitarTone;
        }
        if (imageButtonId == string32.getId()) {
            guitarTone = Dtone;
            return guitarTone;
        }
        if (imageButtonId == string31.getId()) {
            guitarTone = Dtone;
            return guitarTone;
        }
        if (imageButtonId == string30.getId()) {
            guitarTone = Dtone;
            return guitarTone;
        }
        if (imageButtonId == string44.getId()) {
            guitarTone = Gtone;
            return guitarTone;
        }
        if (imageButtonId == string43.getId()) {
            guitarTone = Gtone;
            return guitarTone;
        }
        if (imageButtonId == string42.getId()) {
            guitarTone = Gtone;
            return guitarTone;
        }
        if (imageButtonId == string41.getId()) {
            guitarTone = Gtone;
            return guitarTone;
        }
        if (imageButtonId == string40.getId()) {
            guitarTone = Gtone;
            return guitarTone;
        }
        if (imageButtonId == string54.getId()) {
            guitarTone = Btone;
            return guitarTone;
        }
        if (imageButtonId == string53.getId()) {
            guitarTone = Btone;
            return guitarTone;
        }
        if (imageButtonId == string52.getId()) {
            guitarTone = Btone;
            return guitarTone;
        }
        if (imageButtonId == string51.getId()) {
            guitarTone = Btone;
            return guitarTone;
        }
        if (imageButtonId == string50.getId()) {
            guitarTone = Btone;
            return guitarTone;
        }
        if (imageButtonId == string64.getId()) {
            guitarTone = E2tone;
            return guitarTone;
        }
        if (imageButtonId == string63.getId()) {
            guitarTone = E2tone;
            return guitarTone;
        }
        if (imageButtonId == string62.getId()) {
            guitarTone = E2tone;
            return guitarTone;
        }
        if (imageButtonId == string61.getId()) {
            guitarTone = E2tone;
            return guitarTone;
        }
        if (imageButtonId == string60.getId()) {
            guitarTone = E2tone;
            return guitarTone;
        }

        return guitarTone;
    }

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
