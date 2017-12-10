package lubin.guitar;

import android.content.Context;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import lubin.guitar.R;


public class MainActivity extends AppCompatActivity {

    Button btnPlay; //hraci tlacitko
    Button btnChange; // tlacitko meni nastroje
    Button btnGuitar; //tlacitko presun na kytaru
    Button btnGuitar2;
    Button trySong;
    Button previewSong;
    Button choiceAccount;
    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    EditText editText;
    int soundId;
    TextView textView;
    float normal_playback_rate;
    int numberInstrument = 0; //cislo nastroje
    String nameOfSong;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent draw = new Intent (MainActivity.this, drawer.class);
        startActivity(draw);

        btnPlay = (Button) findViewById(R.id.play);
        //btnPlay.setEnabled(false);
        btnPlay.setOnClickListener(btnPlayOnClickListener);
        btnChange = (Button) findViewById(R.id.bt2);
        btnChange.setOnClickListener(btnChangeOnClickListener);
        btnGuitar = (Button) findViewById(R.id.guitar);
        btnGuitar.setOnClickListener(changeGuitar);
        editText = (EditText) findViewById(R.id.editText);
        btnGuitar2 = (Button) findViewById(R.id.guitar2);
        btnGuitar2.setOnClickListener(changeGuitar2);
        trySong = (Button) findViewById(R.id.trySong);
        trySong.setOnClickListener(btnTrySong);
        previewSong = (Button) findViewById(R.id.previewSong);
        previewSong.setOnClickListener(btnPreviewSong);
        choiceAccount = (Button) findViewById(R.id.choiceAccount);
        choiceAccount.setOnClickListener(btnChoiceAccount);


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
        textView = (TextView) findViewById(R.id.text);

        normal_playback_rate = 0.5f;
        numberInstrument = 1;









//        Intent i = new Intent(MainActivity.this, ChoiceAccount.class);
//        startActivity(i);







    }

    OnClickListener changeGuitar = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, Guitar.class);
            startActivity(i);

        }
    };

    OnClickListener btnChoiceAccount = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, ChoiceAccount.class);
            startActivity(i);

        }
    };


    OnClickListener btnTrySong = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, TrySong.class);
            startActivity(i);

        }
    };


    OnClickListener btnPreviewSong = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, PreviewSong.class);
            startActivity(i);

        }
    };

    OnClickListener changeGuitar2 = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, Guitar2.class);
            startActivity(i);

        }
    };


    OnLoadCompleteListener soundPoolOnLoadCompleteListener =
            new OnLoadCompleteListener() {

                @Override
                public void onLoadComplete(SoundPool soundPool,
                                           int sampleId, int status) {
                    if (status == 0) {
                        btnPlay.setEnabled(true);
                    } else {
                        Toast.makeText(MainActivity.this,
                                "SoundPool.load() fail",
                                Toast.LENGTH_LONG).show();
                    }

                }
            };

    OnClickListener btnPlayOnClickListener =
            new OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (float i = 1; i < 2; i = i + 0.1f) {
                        float vol = audioManager.getStreamVolume(
                                AudioManager.STREAM_MUSIC);
                        float maxVol = audioManager.getStreamMaxVolume(
                                AudioManager.STREAM_MUSIC);
                        final float leftVolume = vol / maxVol;
                        final float rightVolume = vol / maxVol;
                        final int priority = 1;
                        final int no_loop = 0;
                        float highString = 0.5f;
                        try {
                            highString = Float.valueOf(editText.getText().toString());
                        } catch (Exception e) {

                        }

                        if (highString > 0f && highString < 2.1f) {
                            normal_playback_rate = highString;

                        }


                        soundPool.play(soundId,
                                leftVolume,
                                rightVolume,
                                priority,
                                no_loop,
                                normal_playback_rate);


                        textView.setText(Float.toString(normal_playback_rate));

                    }

                }
            };


    OnClickListener btnChangeOnClickListener = new OnClickListener() { //zmena nastroje
        @Override
        public void onClick(View view) {

            if (numberInstrument < 11) {
                numberInstrument++;
            } else {
                numberInstrument = 1;
            }
            normal_playback_rate = 0.5f;


            int path = getResources().getIdentifier(("s" + String.valueOf(numberInstrument)), "raw", getPackageName());

            soundId = soundPool.load(getApplicationContext(), path, 1);
            textView.setText("s" + String.valueOf(numberInstrument));
        }


    };
}

