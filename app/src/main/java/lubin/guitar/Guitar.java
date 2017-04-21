package lubin.guitar;

import android.app.Activity;
import android.content.Context;

import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import lubin.guitar.R;

public class Guitar extends Activity {

    ImageButton string1;
    ImageButton string2;
    ImageButton string3;
    ImageButton string4;
    ImageButton string5;
    ImageButton string6;
    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    int soundId;
    int tone; //druh tonu
    TextView textView;
    float normal_playback_rate;
    int numberInstrument = 0; //cislo nastroje



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guitar);
        string1 = (ImageButton)findViewById(R.id.imageButton);
        string2 = (ImageButton)findViewById(R.id.imageButton2);
        string3 = (ImageButton)findViewById(R.id.imageButton3);
        string4 = (ImageButton)findViewById(R.id.imageButton4);
        string5 = (ImageButton)findViewById(R.id.imageButton5);
        string6 = (ImageButton)findViewById(R.id.imageButton6);
        string1.setOnClickListener(stringPlayOnClickListener);
        string2.setOnClickListener(stringPlayOnClickListener);
        string3.setOnClickListener(stringPlayOnClickListener);
        string4.setOnClickListener(stringPlayOnClickListener);
        string5.setOnClickListener(stringPlayOnClickListener);
        string6.setOnClickListener(stringPlayOnClickListener);


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

    OnLoadCompleteListener soundPoolOnLoadCompleteListener =
            new OnLoadCompleteListener(){

                @Override
                public void onLoadComplete(SoundPool soundPool,
                                           int sampleId, int status) {
                    if(status==0){
                        string1.setEnabled(true);
                    }else{
                        Toast.makeText(Guitar.this,
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
                        case R.id.imageButton:{
                            normal_playback_rate = 1.214f;
                            break;
                        }
                        case R.id.imageButton2:{
                            normal_playback_rate = 0.908f;
                            break;
                        }
                        case R.id.imageButton3:{
                            normal_playback_rate = 0.721f;
                            break;
                        }
                        case R.id.imageButton4:{
                            normal_playback_rate = 0.542f;
                            break;
                        }
                        case R.id.imageButton5:{
                            normal_playback_rate = 0.405f;
                            break;
                        }
                        case R.id.imageButton6:{
                            normal_playback_rate = 0.303f;
                            break;
                        }
                        default:{
                            normal_playback_rate = 1.214f;
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
