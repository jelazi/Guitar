package lubin.guitar.GuitarActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lubin.guitar.Files.DialogType;
import lubin.guitar.Shop.ShopActivity;
import lubin.guitar.Song.GuitarTone;
import lubin.guitar.R;
import lubin.guitar.Settings.SettingsScreenActivity;
import lubin.guitar.Song.Song;
import lubin.guitar.Song.Songs;
import lubin.guitar.Song.Tone;
import lubin.guitar.Song.Tones;
import lubin.guitar.Users.SingletonManagerUsers;

public class PreviewSongActivity extends VirtualGuitarActivity implements OnClickListener {

    Button btnplayMusic;
    SoundPool soundPool; //zvuk
    AudioManager audioManager;
    int soundId;
    int tone; //druh tonu
    float normal_playback_rate;
    Tones tones = new Tones();
    boolean isPlaying = false;
    boolean nextTone = false;
    Song song = new Song();
    ArrayList<Tone> tonySkladby = new ArrayList<>();
    ArrayList<GuitarTone> pokus = new ArrayList<>();
    TextView lblNameOfSongView;
    ArrayList<Integer> streamIDs;
    long[] delays;
    long startTime;
    int toneStop;
    boolean isTest;
    Song songForTesting;
    String nameSongForTesting;

    MenuItem settingsMenu;
    MenuItem changeInstrument;
    MenuItem trySong;
    MenuItem playChord;
    MenuItem openShop;
    ImageView btnChangeInstrument;

    Intent intent;

    boolean animationbool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        isTest = intent.getBooleanExtra("is_test", false);
        nameSongForTesting = intent.getStringExtra("name_test_song");
        setContentView(R.layout.activity_preview_song);
        createView();
        addFretboard();
        btnplayMusic = findViewById(R.id.playMusic);
        btnplayMusic.setOnClickListener(this);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        lblNameOfSongView = findViewById(R.id.nameSong);
        lblNameOfSongView.setOnClickListener(this);
        btnChangeInstrument = findViewById(R.id.btn_change_instrument);
        btnChangeInstrument.setOnClickListener(this);
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

        if (!settings.getBoolean("new_intent", true)) {
            toneStop = settings.getInt("tone_stop", 0)+1;
        }
        else{
            settings.edit().putInt("tone_stop", 0).apply();
            toneStop = 0;
        }
        settings.edit().putBoolean("new_intent", true).apply();
        soundId = soundPool.load(getFilesDir() + "/Instruments/" + currentUser.getCurrentNameInstrument(), 1);
        tone = 0;
        normal_playback_rate = 0.5f;
        this.setTitle(R.string.action_preview_song);
        lblNameOfSongView.setText(currentUser.getCurrentNameSong());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preview_song, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;

            m.setOptionalIconsVisible(true);
        }
        if (isTest) {
            setMenuForTest(menu);
        }
        return true;
    }

    protected void setMenuForTest (Menu menu) {
        settingsMenu = menu.findItem(R.id.settings_menu);
        changeInstrument = menu.findItem(R.id.btn_change_instrument);
        trySong = menu.findItem(R.id.try_song);
        playChord = menu.findItem(R.id.play_chord);
        openShop = menu.findItem(R.id.open_shop);
        settingsMenu.setVisible(false);
        changeInstrument.setVisible(false);
        trySong.setVisible(false);
        playChord.setVisible(false);
        openShop.setVisible(false);
    }

    protected void setActivityForTest() {

        if (nameSongForTesting.isEmpty()) {
            Log.e("Error:", "problem with intent");
            return;
        } else {
            lblNameOfSongView.setText(nameSongForTesting);
        }
        songForTesting = SingletonManagerUsers.getCurrentSong();
        if (songForTesting == null || songForTesting.getTones().size() == 0) {
            Log.e("Error:", "problem with songForTesting");
            return;
        } else {
            currentSong = songForTesting;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1) {
            previewSong();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (isPlaying) {
            Toast.makeText(this, getResources().getString(R.string.warning_first_stop_song), Toast.LENGTH_SHORT).show();
            return true;
        }

        switch (id) {
            case android.R.id.home:

                onBackPressed();
                break;

            case R.id.settings_menu:
                soundPool.release();
                startActivity(new Intent(this, this.getClass())); //spusteni nove instance z duvodu zastaveni prehravani skladby, pokud je prave prehravana
                overridePendingTransition(0, 0);
                finish();
                Intent i = new Intent(PreviewSongActivity.this, SettingsScreenActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.btn_change_instrument:
                changeInstrument(true);
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
                finish();
                break;


            case R.id.play_chord:
                soundPool.release();
                i = new Intent(PreviewSongActivity.this, PlayChordActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.open_shop:
                i = new Intent(PreviewSongActivity.this, ShopActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onResume() { //aktualizace nastaveni hodnot ze SettingsScreenActivity
        super.onResume();

        settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        soundId = soundPool.load(getFilesDir() + "/Instruments/" + currentUser.getCurrentNameInstrument(), 1);
        lblNameOfSongView.setText(currentUser.getCurrentNameSong());
        stopBeforeTone = true;
        currentSong = Songs.getSongByName(this, currentUser.getCurrentNameSong());
        if (isTest) {
            setActivityForTest();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnplayMusic) {
            previewSong();
        }
        if (view == lblNameOfSongView) {
            if (isPlaying) {
                Toast.makeText(this, getResources().getString(R.string.warning_first_stop_song), Toast.LENGTH_SHORT).show();
                return;
            }
            showDialog(DialogType.CHOICE_SONG);
        }
        if (view == btnChangeInstrument) {
            if (isPlaying) {
                Toast.makeText(this, getResources().getString(R.string.warning_first_stop_song), Toast.LENGTH_SHORT).show();
                return;
            }
            changeInstrument(true);
        }
    }

    protected void showDialog (DialogType dialogType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (dialogType == DialogType.CHOICE_SONG) {
            final List<String> listSongs = currentUser.getAllowedSongs();
            String[] arrayUsers = new String[listSongs.size()];
            listSongs.toArray(arrayUsers);

            builder.setTitle(getResources().getString(R.string.choice_song));

            builder.setItems(arrayUsers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    changeCurrentSong(listSongs.get(which));
                    toneStop = 0;
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void changeCurrentSong (String nameSong) {
        currentUser.setCurrentNameSong(nameSong);
        SingletonManagerUsers.changeUser(currentUser);
        currentSong = Songs.getSongByName(this, currentUser.getCurrentNameSong());
        song = currentSong;
        lblNameOfSongView.setText(currentSong.getNameOfSong());
    }


    public void previewSong() {
        Object obj = new Object();

        if (!isPlaying) {
            song = currentSong;
            btnChangeInstrument.setImageResource(R.drawable.guitar_disabled);

        }
        tonySkladby = song.getTones();
        pokus = createMusicFromTones(tonySkladby);

        if (isPlaying) {
            btnChangeInstrument.setImageResource(R.drawable.guitar);
            toneStop = getNumberTone(); //zjisteni tonu, na kterém se skoncilo
            isPlaying = false;
            this.btnplayMusic.setText(getResources().getString(R.string.play_order));
            soundPool.release();
            settings.edit().putInt("tone_stop", toneStop).apply();
            settings.edit().putBoolean("new_intent", false).apply();
            startActivity(intent);
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
                            if (!(tonySkladby.get(i).nameTone.equals("silent"))) { //neni to pomlka?
                                playToneWithDelay(pokus.get(i), delay, isEmptyGuitarTone(pokus.get(i)));
                            }
                            if (i == tonySkladby.size() - 1) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        isPlaying = true;
                                        btnplayMusic.setText(getResources().getString(R.string.stop_order));

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
                            btnplayMusic.setText(getResources().getString(R.string.play_order));
                            btnChangeInstrument.setImageResource(R.drawable.guitar);
                            toneStop = 0;
                            settings.edit().putInt("tone_stop", toneStop).apply();
                        }
                    }, delay + 100);
                }
            }, 1000);
            isPlaying = true;
            btnplayMusic.setText(getResources().getString(R.string.stop_order));
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
                Touching(gtr.getStringTouch(), null, false);

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
    protected void playToneWithDelay(GuitarTone guitarTone, int delay, final boolean isEmpty) {
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
                Touching(gtr.getStringTouch(), null, isEmpty);

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
    private void Touching(final ImageButton imgButton, final boolean isEmpty){

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imgButton.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isEmpty) {
                    imgButton.setBackgroundResource(R.drawable.touch_empty);
                } else {
                    imgButton.setBackgroundResource(R.drawable.touch);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgButton.setBackgroundResource(0);
                nextTone = true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
             soundPool.release();
        }
        return super.onKeyDown(keyCode, event);
    }


}