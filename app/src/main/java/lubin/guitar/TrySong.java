package lubin.guitar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;
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


public class TrySong extends Activity {

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

    Button setting;
    Button btnTryMusic;

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
    int akordNumber;
    String[] akords = {"Cdur", "Ddur", "Edur", "Fdur", "Gdur", "Adur", "Bdur", "Cmi"};



    TextView money;
    int moneyValue = 0;
    boolean playingSong = false;
    int numberTone = 0;
    GuitarTone playingTone;





    ArrayList<Tone> skladba = new ArrayList<>();
    ArrayList<GuitarTone> pokus = new ArrayList<>();


    ArrayList<ImageButton> imageButtons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_song);
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
//        string14.setOnTouchListener(stringPlayOnTouchListener);
//        string13.setOnTouchListener(stringPlayOnTouchListener);
//        string12.setOnTouchListener(stringPlayOnTouchListener);
//        string11.setOnTouchListener(stringPlayOnTouchListener);
//        string10.setOnTouchListener(stringPlayOnTouchListener);
//        string24.setOnTouchListener(stringPlayOnTouchListener);
//        string23.setOnTouchListener(stringPlayOnTouchListener);
//        string22.setOnTouchListener(stringPlayOnTouchListener);
//        string21.setOnTouchListener(stringPlayOnTouchListener);
//        string20.setOnTouchListener(stringPlayOnTouchListener);
//        string34.setOnTouchListener(stringPlayOnTouchListener);
//        string33.setOnTouchListener(stringPlayOnTouchListener);
//        string32.setOnTouchListener(stringPlayOnTouchListener);
//        string31.setOnTouchListener(stringPlayOnTouchListener);
//        string30.setOnTouchListener(stringPlayOnTouchListener);
//        string44.setOnTouchListener(stringPlayOnTouchListener);
//        string43.setOnTouchListener(stringPlayOnTouchListener);
//        string42.setOnTouchListener(stringPlayOnTouchListener);
//        string41.setOnTouchListener(stringPlayOnTouchListener);
//        string40.setOnTouchListener(stringPlayOnTouchListener);
//        string54.setOnTouchListener(stringPlayOnTouchListener);
//        string53.setOnTouchListener(stringPlayOnTouchListener);
//        string52.setOnTouchListener(stringPlayOnTouchListener);
//        string51.setOnTouchListener(stringPlayOnTouchListener);
//        string50.setOnTouchListener(stringPlayOnTouchListener);
//        string64.setOnTouchListener(stringPlayOnTouchListener);
//        string63.setOnTouchListener(stringPlayOnTouchListener);
//        string62.setOnTouchListener(stringPlayOnTouchListener);
//        string61.setOnTouchListener(stringPlayOnTouchListener);
//        string60.setOnTouchListener(stringPlayOnTouchListener);

        string14.setOnClickListener(stringPlayOnClickListener);
        string13.setOnClickListener(stringPlayOnClickListener);
        string12.setOnClickListener(stringPlayOnClickListener);
        string11.setOnClickListener(stringPlayOnClickListener);
        string10.setOnClickListener(stringPlayOnClickListener);
        string24.setOnClickListener(stringPlayOnClickListener);
        string23.setOnClickListener(stringPlayOnClickListener);
        string22.setOnClickListener(stringPlayOnClickListener);
        string21.setOnClickListener(stringPlayOnClickListener);
        string20.setOnClickListener(stringPlayOnClickListener);
        string34.setOnClickListener(stringPlayOnClickListener);
        string33.setOnClickListener(stringPlayOnClickListener);
        string32.setOnClickListener(stringPlayOnClickListener);
        string31.setOnClickListener(stringPlayOnClickListener);
        string30.setOnClickListener(stringPlayOnClickListener);
        string44.setOnClickListener(stringPlayOnClickListener);
        string43.setOnClickListener(stringPlayOnClickListener);
        string42.setOnClickListener(stringPlayOnClickListener);
        string41.setOnClickListener(stringPlayOnClickListener);
        string40.setOnClickListener(stringPlayOnClickListener);
        string54.setOnClickListener(stringPlayOnClickListener);
        string53.setOnClickListener(stringPlayOnClickListener);
        string52.setOnClickListener(stringPlayOnClickListener);
        string51.setOnClickListener(stringPlayOnClickListener);
        string50.setOnClickListener(stringPlayOnClickListener);
        string64.setOnClickListener(stringPlayOnClickListener);
        string63.setOnClickListener(stringPlayOnClickListener);
        string62.setOnClickListener(stringPlayOnClickListener);
        string61.setOnClickListener(stringPlayOnClickListener);
        string60.setOnClickListener(stringPlayOnClickListener);

        Estring = (ImageView) findViewById(R.id.Estring);
        Astring = (ImageView) findViewById(R.id.Astring);
        Dstring = (ImageView) findViewById(R.id.Dstring);
        Gstring = (ImageView) findViewById(R.id.Gstring);
        Bstring = (ImageView) findViewById(R.id.Bstring);
        E2string = (ImageView) findViewById(R.id.E2string);


        changeInstrument = (Button) findViewById(R.id.changeInstrument);
        changeInstrument.setOnClickListener(btnChangeOnClickListener);
        setting = (Button) findViewById(R.id.btnTrySong);
        setting.setOnClickListener(getSetting);
        btnTryMusic = (Button)findViewById(R.id.tryMusic);
        btnTryMusic.setOnClickListener(trySong);
        money = (TextView) findViewById(R.id.valueMoney);


        money.setText(Integer.toString(moneyValue));

        E2tone = new GuitarTone((ImageButton) findViewById(R.id.imageButton60), tones.getString60(), E2string);
        Btone = new GuitarTone((ImageButton) findViewById(R.id.imageButton50), tones.getString50(), Bstring);
        Gtone = new GuitarTone((ImageButton) findViewById(R.id.imageButton40), tones.getString40(), Gstring);
        Dtone = new GuitarTone((ImageButton) findViewById(R.id.imageButton30), tones.getString30(), Dstring);
        Atone = new GuitarTone((ImageButton) findViewById(R.id.imageButton20), tones.getString20(), Astring);
        Etone = new GuitarTone((ImageButton) findViewById(R.id.imageButton10), tones.getString10(), Estring);
        playingTone = new GuitarTone((ImageButton) findViewById(R.id.imageButton10), tones.getString10(), Estring);

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



    }


    OnClickListener stringPlayOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View v ) {
                    if (playingSong){

                        if (playingTone.getStringValue() == (getToneFromTouch(v.getId()).getStringValue())){

                            playToneFromTouch(v);

                            moneyValue++;
                            money.setText(Integer.toString(moneyValue));


                            numberTone++;

                            playingTone.getStringImage().clearColorFilter();
                            playingTone.getStringTouch().setBackgroundResource(0);

                            if (numberTone <= pokus.size()-1)
                            {
                                trytrySong();
                            }

                            else
                            {
                                playingSong = false;
                                btnTryMusic.setText("Zkus hrát");
                                btnTryMusic.setBackgroundResource(android.R.drawable.btn_default);
                            }




                        }
                        else{



                        }

                    }
                    else {

                        playToneFromTouch(v);

                    }



                }
            };

    OnClickListener getSetting = new OnClickListener() {
        @Override
        public void onClick(View view) {

            soundPool.release();

            Intent i = new Intent(TrySong.this, Settings.class);
            startActivity(i);

        }
    };


    public void fillImageButtons(){


        imageButtons.add(string14);
        imageButtons.add(string13);
        imageButtons.add(string12);
        imageButtons.add(string11);
        imageButtons.add(string10);
        imageButtons.add(string24);
        imageButtons.add(string23);
        imageButtons.add(string22);
        imageButtons.add(string21);
        imageButtons.add(string20);
        imageButtons.add(string34);
        imageButtons.add(string33);
        imageButtons.add(string32);
        imageButtons.add(string31);
        imageButtons.add(string30);
        imageButtons.add(string44);
        imageButtons.add(string43);
        imageButtons.add(string42);
        imageButtons.add(string41);
        imageButtons.add(string40);
        imageButtons.add(string54);
        imageButtons.add(string53);
        imageButtons.add(string52);
        imageButtons.add(string51);
        imageButtons.add(string50);
        imageButtons.add(string64);
        imageButtons.add(string63);
        imageButtons.add(string62);
        imageButtons.add(string61);
        imageButtons.add(string60);


    }






    OnLoadCompleteListener soundPoolOnLoadCompleteListener =
            new OnLoadCompleteListener(){

                @Override
                public void onLoadComplete(SoundPool soundPool,
                                           int sampleId, int status) {
                    if(status==0){
                        //string11.setEnabled(true);
                    }else{
                        Toast.makeText(TrySong.this,
                                "SoundPool.load() fail",
                                Toast.LENGTH_LONG).show();
                    }

                }
            };



            View.OnTouchListener stringPlayOnTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (playingSong){





                    }
                    else {
                        boolean play = true;

                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                if (play) {
                                    playToneFromTouch(view);

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
                    }





                    return true;
                }


            };

// zkouska skladby
    OnClickListener trySong = new OnClickListener() {
    @Override
    public void onClick(View view) {
        if (playingSong){
            playingSong = false;
            btnTryMusic.setText("Zkus hrát");
            btnTryMusic.setBackgroundResource(android.R.drawable.btn_default);
            playingTone.getStringImage().clearColorFilter();
            playingTone.getStringTouch().setBackgroundResource(0);
        }
        else{
            playingSong = true;
            btnTryMusic.setText("Hrajeme...");
            btnTryMusic.setBackgroundColor(0x800a33f5);

            skladba = Songs.getSong2();

            pokus = createMusicFromTones(skladba);

            trytrySong();
        }


    };


};



    public void trytrySong(){

        playingTone = pokus.get(numberTone);
        playingTone.getStringImage().setColorFilter(0x80ff0000);
        playingTone.getStringTouch().setBackgroundResource(R.drawable.touch);


    }


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

                if (playingSong){

                }
                else
                {
                    imgButton.setBackgroundResource(R.drawable.touch);
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                if (playingSong){

                }
                else{
                    imgButton.setBackgroundResource(0);

                }




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

public void playToneFromTouch (View v){
    switch (v.getId()){
        case R.id.imageButton14:{
            normal_playback_rate = tones.getString14();
            Shaking(Estring);
            Touching(string14);
            break;
        }
        case R.id.imageButton13:{
            normal_playback_rate = tones.getString13();
            Shaking(Estring);
            Touching(string13);
            break;
        }
        case R.id.imageButton12:{
            normal_playback_rate = tones.getString12();
            Shaking(Estring);
            Touching(string12);
            break;
        }
        case R.id.imageButton11:{
            normal_playback_rate = tones.getString11();
            Shaking(Estring);
            Touching(string11);
            break;
        }
        case R.id.imageButton10:{
            normal_playback_rate = tones.getString10();
            Shaking(Estring);
            Touching(string10);
            break;
        }
        case R.id.imageButton24:{
            normal_playback_rate = tones.getString24();
            Shaking(Astring);
            Touching(string24);
            break;
        }
        case R.id.imageButton23:{
            normal_playback_rate = tones.getString23();
            Shaking(Astring);
            Touching(string23);
            break;
        }
        case R.id.imageButton22:{
            normal_playback_rate = tones.getString22();
            Shaking(Astring);
            Touching(string22);
            break;
        }
        case R.id.imageButton21:{
            normal_playback_rate = tones.getString21();
            Shaking(Astring);
            Touching(string21);
            break;
        }
        case R.id.imageButton20:{
            normal_playback_rate = tones.getString20();
            Shaking(Astring);
            Touching(string20);
            break;
        }
        case R.id.imageButton34:{
            normal_playback_rate = tones.getString34();
            Shaking(Dstring);
            Touching(string34);
            break;
        }
        case R.id.imageButton33:{
            normal_playback_rate = tones.getString33();
            Shaking(Dstring);
            Touching(string33);
            break;
        }
        case R.id.imageButton32:{
            normal_playback_rate = tones.getString32();
            Shaking(Dstring);
            Touching(string32);
            break;
        }
        case R.id.imageButton31:{
            normal_playback_rate = tones.getString31();
            Shaking(Dstring);
            Touching(string31);
            break;
        }
        case R.id.imageButton30:{
            normal_playback_rate = tones.getString30();
            Shaking(Dstring);
            Touching(string30);
            break;
        }
        case R.id.imageButton44:{
            normal_playback_rate = tones.getString44();
            Shaking(Gstring);
            Touching(string44);
            break;
        }
        case R.id.imageButton43:{
            normal_playback_rate = tones.getString43();
            Shaking(Gstring);
            Touching(string43);
            break;
        }
        case R.id.imageButton42:{
            normal_playback_rate = tones.getString42();
            Shaking(Gstring);
            Touching(string42);
            break;
        }
        case R.id.imageButton41:{
            normal_playback_rate = tones.getString41();
            Shaking(Gstring);
            Touching(string41);
            break;
        }
        case R.id.imageButton40:{
            normal_playback_rate = tones.getString40();
            Shaking(Gstring);
            Touching(string40);
            break;
        }
        case R.id.imageButton54:{
            normal_playback_rate = tones.getString54();
            Shaking(Bstring);
            Touching(string54);
            break;
        }
        case R.id.imageButton53:{
            normal_playback_rate = tones.getString53();
            Shaking(Bstring);
            Touching(string53);
            break;
        }
        case R.id.imageButton52:{
            normal_playback_rate = tones.getString52();
            Shaking(Bstring);
            Touching(string52);
            break;
        }
        case R.id.imageButton51:{
            normal_playback_rate = tones.getString51();
            Shaking(Bstring);
            Touching(string51);
            break;
        }
        case R.id.imageButton50:{
            normal_playback_rate = tones.getString50();
            Shaking(Bstring);
            Touching(string50);
            break;
        }
        case R.id.imageButton64:{
            normal_playback_rate = tones.getString64();
            Shaking(E2string);
            Touching(string64);
            break;
        }
        case R.id.imageButton63:{
            normal_playback_rate = tones.getString63();
            Shaking(E2string);
            Touching(string63);
            break;
        }
        case R.id.imageButton62:{
            normal_playback_rate = tones.getString62();
            Shaking(E2string);
            Touching(string62);
            break;
        }
        case R.id.imageButton61:{
            normal_playback_rate = tones.getString61();
            Shaking(E2string);
            Touching(string61);
            break;
        }
        case R.id.imageButton60:{
            normal_playback_rate = tones.getString60();
            Shaking(E2string);
            Touching(string60);
            break;
        }
        default:{
            normal_playback_rate = tones.getString10();
            Shaking(E2string);
            Touching(string10);
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

    soundPool.play(soundId,
            leftVolume,
            rightVolume,
            priority,
            no_loop,
            normal_playback_rate);


}



    // vrati ton podle zmacknute struny
    public GuitarTone getToneFromTouch (int imageButtonId){
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
