package lubin.guitar;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
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

public class Guitar2 extends Activity {

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

    Button changeInstrument;

    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    int soundId;
    int tone; //druh tonu
    TextView textView;
    float normal_playback_rate;
    int numberInstrument = 0; //cislo nastroje
    private GestureDetector gestureDetector;
    Tones tones = new Tones();

    ImageView E2string;
    ImageView Bstring;
    ImageView Gstring;
    ImageView Dstring;
    ImageView Astring;
    ImageView Estring;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guitar2);
        string14 = (ImageButton)findViewById(R.id.imageButton14);
        string13 = (ImageButton)findViewById(R.id.imageButton13);
        string12 = (ImageButton)findViewById(R.id.imageButton12);
        string11 = (ImageButton)findViewById(R.id.imageButton11);
        string10 = (ImageButton)findViewById(R.id.imageButton10);
        string24 = (ImageButton)findViewById(R.id.imageButton24);
        string23 = (ImageButton)findViewById(R.id.imageButton23);
        string22 = (ImageButton)findViewById(R.id.imageButton22);
        string21 = (ImageButton)findViewById(R.id.imageButton21);
        string20 = (ImageButton)findViewById(R.id.imageButton20);
        string34 = (ImageButton)findViewById(R.id.imageButton34);
        string33 = (ImageButton)findViewById(R.id.imageButton33);
        string32 = (ImageButton)findViewById(R.id.imageButton32);
        string31 = (ImageButton)findViewById(R.id.imageButton31);
        string30 = (ImageButton)findViewById(R.id.imageButton30);
        string44 = (ImageButton)findViewById(R.id.imageButton44);
        string43 = (ImageButton)findViewById(R.id.imageButton43);
        string42 = (ImageButton)findViewById(R.id.imageButton42);
        string41 = (ImageButton)findViewById(R.id.imageButton41);
        string40 = (ImageButton)findViewById(R.id.imageButton40);
        string54 = (ImageButton)findViewById(R.id.imageButton54);
        string53 = (ImageButton)findViewById(R.id.imageButton53);
        string52 = (ImageButton)findViewById(R.id.imageButton52);
        string51 = (ImageButton)findViewById(R.id.imageButton51);
        string50 = (ImageButton)findViewById(R.id.imageButton50);
        string64 = (ImageButton)findViewById(R.id.imageButton64);
        string63 = (ImageButton)findViewById(R.id.imageButton63);
        string62 = (ImageButton)findViewById(R.id.imageButton62);
        string61 = (ImageButton)findViewById(R.id.imageButton61);
        string60 = (ImageButton)findViewById(R.id.imageButton60);
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
        changeInstrument = (Button)findViewById(R.id.changeInstrument);
        changeInstrument.setOnClickListener(btnChangeOnClickListener);

        Estring = (ImageView)findViewById(R.id.Estring);
        Astring = (ImageView)findViewById(R.id.Astring);
        Dstring = (ImageView)findViewById(R.id.Dstring);
        Gstring = (ImageView)findViewById(R.id.Gstring);
        Bstring = (ImageView)findViewById(R.id.Bstring);
        E2string = (ImageView)findViewById(R.id.E2string);



        //Estring.setColorFilter(0x80ff0000); zmena barvy






        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

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

        //textView = (TextView)findViewById(R.id.text);
        tone = 0;
        normal_playback_rate = 0.5f;

        numberInstrument = 1;


    }



    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    OnLoadCompleteListener soundPoolOnLoadCompleteListener =
            new OnLoadCompleteListener(){

                @Override
                public void onLoadComplete(SoundPool soundPool,
                                           int sampleId, int status) {
                    if(status==0){
                        //string11.setEnabled(true);
                    }else{
                        Toast.makeText(Guitar2.this,
                                "SoundPool.load() fail",
                                Toast.LENGTH_LONG).show();
                    }

                }
            };




    OnClickListener stringPlayOnClickListener =
            new OnClickListener(){

                @Override
                public void onClick(View v ) {
                    float vol = audioManager.getStreamVolume(
                            AudioManager.STREAM_MUSIC);
                    float maxVol = audioManager.getStreamMaxVolume(
                            AudioManager.STREAM_MUSIC);
                    float leftVolume = vol/maxVol;
                    float rightVolume = vol/maxVol;
                    int priority = 1;
                    int no_loop = 0;


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

                    soundPool.play(soundId,
                            leftVolume,
                            rightVolume,
                            priority,
                            no_loop,
                            normal_playback_rate);
                }
            };

            private void Shaking(ImageView string){

                android.view.animation.Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                string.startAnimation(animation);
            }

            private void Touching(final ImageButton imgButton){


                android.view.animation.Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
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
}
