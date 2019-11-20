package lubin.guitar.GuitarActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import lubin.guitar.MainActivity;
import lubin.guitar.Shop.ShopActivity;
import lubin.guitar.Song.Tonalities;
import lubin.guitar.Song.GuitarTone;
import lubin.guitar.R;
import lubin.guitar.Settings.SettingsScreenActivity;


public class PlayChordActivity extends VirtualGuitarActivity {

    Button chord1;
    Button chord2;
    Button chord3;
    Button chord4;
    Button chord5;
    Button chord6;
    int akordNumber;
    String currentChord;
    ArrayList<String> currentTonality;
    ArrayList<ArrayList<String>> allTonality;
    int tonalityNumber;
    MenuBuilder m;
    MenuInflater inflater;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_acord);
        addFretboard();
        createView();
        Tonalities.initTonality();

        allTonality = new ArrayList<>();
        allTonality.add(Tonalities.getCtonality());
        allTonality.add(Tonalities.getDtonality());
        allTonality.add(Tonalities.getEtonality());
        allTonality.add(Tonalities.getFtonality());
        allTonality.add(Tonalities.getGtonality());
        allTonality.add(Tonalities.getAtonality());
        allTonality.add(Tonalities.getBtonality());
        tonalityNumber = 0;

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

        chord1 = findViewById(R.id.chord1);
        chord2 = findViewById(R.id.chord2);
        chord3 = findViewById(R.id.chord3);
        chord4 = findViewById(R.id.chord4);
        chord5 = findViewById(R.id.chord5);
        chord6 = findViewById(R.id.chord6);

        chord1.setOnClickListener(btnChangeAkord);
        chord2.setOnClickListener(btnChangeAkord);
        chord3.setOnClickListener(btnChangeAkord);
        chord4.setOnClickListener(btnChangeAkord);
        chord5.setOnClickListener(btnChangeAkord);
        chord6.setOnClickListener(btnChangeAkord);

        akordNumber = 0;
        fillnameChords();

        currentChord = getResources().getString(R.string.empty_strings);
        showAkordOnBoard();

        this.setTitle(R.string.action_play_chords);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Toast.makeText(this,
                        "down",
                        Toast.LENGTH_SHORT).show();
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d("tag","Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d("tag","Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d("tag","Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d("tag","Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }


    public void fillnameChords () {
        currentTonality = allTonality.get(tonalityNumber);
        chord1.setText(currentTonality.get(0));
        chord2.setText(currentTonality.get(1));
        chord3.setText(currentTonality.get(2));
        chord4.setText(currentTonality.get(3));
        chord5.setText(currentTonality.get(4));
        chord6.setText(currentTonality.get(5));
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_play_chords, menu);
        if(menu instanceof MenuBuilder){
            m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }
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

            case R.id.settings_menu:
                Intent i = new Intent(PlayChordActivity.this, SettingsScreenActivity.class);
                startActivity(i);
                break;

            case R.id.change_instrument:
                changeInstrument();
                break;

            case R.id.try_song:
               // soundPool.release();
                i = new Intent(PlayChordActivity.this, TrySongActivity.class);
                startActivity(i);
                break;

            case R.id.preview_song:
              //  soundPool.release();
                i = new Intent(PlayChordActivity.this, PreviewSongActivity.class);
                startActivity(i);
                break;
            case R.id.change_tonality:
                changeTonality();
                break;

            case R.id.open_shop:
                i = new Intent(PlayChordActivity.this, ShopActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public void onResume(){ //aktualizace nastaveni hodnot ze SettingsScreenActivity
        super.onResume();

        settings = PreferenceManager
                .getDefaultSharedPreferences(this);

        soundId = soundPool.load( getFilesDir()+"/Instruments/"+currentUser.getCurrentNameInstrument(), 1);

        stopBeforeTone = settings.getBoolean("stop_before_tone", false);
    }

    public void changeTonality () {
        if (tonalityNumber == 6) {
            tonalityNumber = 0;
        } else {
            tonalityNumber++;
        }
        akordNumber = 0;
        fillnameChords();

        currentChord = getResources().getString(R.string.empty_strings);
        ChangeChord(currentChord);
        showAkordOnBoard();

        chord1.setEnabled(true);
        chord2.setEnabled(true);
        chord3.setEnabled(true);
        chord4.setEnabled(true);
        chord5.setEnabled(true);
        chord6.setEnabled(true);
    }


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
    public void playTone(GuitarTone guitarTone, int delay) {
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
                Touching(gtr.getStringTouch(), gtr.getStringTouch().getBackground());

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


    //prepsna metoda dotyku - zelene kolecko akordu
    @Override
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
                eraseAkordOnBoard();
                showAkordOnBoard();
            }
        });
    }


    // oznaci struny dle akordu
    private void showAkordOnBoard() {


        if (Etone.getStringValue() != 0) {
            Etone.getStringTouch().setBackgroundResource(R.drawable.touchakord);
        }

        if (Atone.getStringValue() != 0) {
            Atone.getStringTouch().setBackgroundResource(R.drawable.touchakord);
        }

        if (Dtone.getStringValue() != 0) {
            Dtone.getStringTouch().setBackgroundResource(R.drawable.touchakord);
        }

        if (Gtone.getStringValue() != 0) {
            Gtone.getStringTouch().setBackgroundResource(R.drawable.touchakord);
        }

        if (Btone.getStringValue() != 0) {
            Btone.getStringTouch().setBackgroundResource(R.drawable.touchakord);
        }

        if (E2tone.getStringValue() != 0) {
            E2tone.getStringTouch().setBackgroundResource(R.drawable.touchakord);
        }
    }

    // vymaze oznaceni strun akordu
    private void eraseAkordOnBoard() {
        if (Etone.getStringValue() != 0) {
            Etone.getStringTouch().setBackgroundResource(0);
        }

        if (Atone.getStringValue() != 0) {
            Atone.getStringTouch().setBackgroundResource(0);
        }

        if (Dtone.getStringValue() != 0) {
            Dtone.getStringTouch().setBackgroundResource(0);
        }

        if (Gtone.getStringValue() != 0) {
            Gtone.getStringTouch().setBackgroundResource(0);
        }

        if (Btone.getStringValue() != 0) {
            Btone.getStringTouch().setBackgroundResource(0);
        }

        if (E2tone.getStringValue() != 0) {
            E2tone.getStringTouch().setBackgroundResource(0);
        }
    }

    private void ChangeChord (String nameChord){

        float[] guitarStringValue = new float[6];

        GuitarTone[] guitarTones = new GuitarTone[6]; //kytarove tony akordu

        eraseAkordOnBoard();

        guitarTones[0] = Etone;
        guitarTones[1] = Atone;
        guitarTones[2] = Dtone;
        guitarTones[3] = Gtone;
        guitarTones[4] = Btone;
        guitarTones[5] = E2tone;
        guitarStringValue = tones.getAkord(nameChord);

        this.setTitle(currentUser.getName() + getResources().getString(R.string.play) + nameChord);

        for (int i = 0; i <= 5; i++) {
            guitarTones[i].setStringValue(guitarStringValue[i]);
        }
        guitarTones = FillStringsValue(guitarTones);
        if (Etone.getStringValue() == 0) {
            string10.setEnabled(false);
            string11.setEnabled(false);
            string12.setEnabled(false);
            string13.setEnabled(false);
            string14.setEnabled(false);
        } else {
            string10.setEnabled(true);
            string11.setEnabled(true);
            string12.setEnabled(true);
            string13.setEnabled(true);
            string14.setEnabled(true);
        }
        if (Atone.getStringValue() == 0) {
            string20.setEnabled(false);
            string21.setEnabled(false);
            string22.setEnabled(false);
            string23.setEnabled(false);
            string24.setEnabled(false);
        } else {
            string20.setEnabled(true);
            string21.setEnabled(true);
            string22.setEnabled(true);
            string23.setEnabled(true);
            string24.setEnabled(true);
        }
        if (Dtone.getStringValue() == 0) {
            string30.setEnabled(false);
            string31.setEnabled(false);
            string32.setEnabled(false);
            string33.setEnabled(false);
            string34.setEnabled(false);
        } else {
            string30.setEnabled(true);
            string31.setEnabled(true);
            string32.setEnabled(true);
            string33.setEnabled(true);
            string34.setEnabled(true);
        }
        if (Gtone.getStringValue() == 0) {
            string40.setEnabled(false);
            string41.setEnabled(false);
            string42.setEnabled(false);
            string43.setEnabled(false);
            string44.setEnabled(false);

        } else {
            string40.setEnabled(true);
            string41.setEnabled(true);
            string42.setEnabled(true);
            string43.setEnabled(true);
            string44.setEnabled(true);
        }
        if (Btone.getStringValue() == 0) {
            string50.setEnabled(false);
            string51.setEnabled(false);
            string52.setEnabled(false);
            string53.setEnabled(false);
            string54.setEnabled(false);
        } else {
            string50.setEnabled(true);
            string51.setEnabled(true);
            string52.setEnabled(true);
            string53.setEnabled(true);
            string54.setEnabled(true);
        }
        if (E2tone.getStringValue() == 0) {
            string60.setEnabled(false);
            string61.setEnabled(false);
            string62.setEnabled(false);
            string63.setEnabled(false);
            string64.setEnabled(false);
        } else {
            string60.setEnabled(true);
            string61.setEnabled(true);
            string62.setEnabled(true);
            string63.setEnabled(true);
            string64.setEnabled(true);
        }
        showAkordOnBoard();
    }

    //zmacknute tlacitko zmen akord
    OnClickListener btnChangeAkord = new OnClickListener() {
        @Override
        public void onClick(View view) {

            String nameChord = ((Button) view).getText().toString();
            chord1.setEnabled(view != chord1);
            chord2.setEnabled(view != chord2);
            chord3.setEnabled(view != chord3);
            chord4.setEnabled(view != chord4);
            chord5.setEnabled(view != chord5);
            chord6.setEnabled(view != chord6);

            ChangeChord(nameChord);

            showAkordOnBoard();
        }
    };

    //naplni struny podle akordu
    public GuitarTone[] FillStringsValue(GuitarTone[] oldStrings) {
        GuitarTone[] newStrings = new GuitarTone[6];
        newStrings = oldStrings;

        for (int i = 0; i <= 5; i++) {
            if (oldStrings[i].getStringValue() == tones.getString14()) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string14);
            }
            if (oldStrings[i].getStringValue() == tones.getString13()) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string13);
            }
            if (oldStrings[i].getStringValue() == tones.getString12()) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string12);
            }
            if (oldStrings[i].getStringValue() == tones.getString11()) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string11);
            }
            if (oldStrings[i].getStringValue() == tones.getString10()) {
                newStrings[i].setStringImage(Estring);
                newStrings[i].setStringTouch(this.string10);
            }
            if (oldStrings[i].getStringValue() == tones.getString24()) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string24);
            }
            if (oldStrings[i].getStringValue() == tones.getString23()) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string23);
            }
            if (oldStrings[i].getStringValue() == tones.getString22()) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string22);
            }
            if (oldStrings[i].getStringValue() == tones.getString21()) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string21);
            }
            if (oldStrings[i].getStringValue() == tones.getString20()) {
                newStrings[i].setStringImage(Astring);
                newStrings[i].setStringTouch(this.string20);
            }
            if (oldStrings[i].getStringValue() == tones.getString34()) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string34);
            }
            if (oldStrings[i].getStringValue() == tones.getString33()) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string33);
            }
            if (oldStrings[i].getStringValue() == tones.getString32()) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string32);
            }
            if (oldStrings[i].getStringValue() == tones.getString31()) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string31);
            }
            if (oldStrings[i].getStringValue() == tones.getString30()) {
                newStrings[i].setStringImage(Dstring);
                newStrings[i].setStringTouch(this.string30);
            }
            if (oldStrings[i].getStringValue() == tones.getString44() && oldStrings[i] == Gtone) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string44);
            }
            if (oldStrings[i].getStringValue() == tones.getString43()) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string43);
            }
            if (oldStrings[i].getStringValue() == tones.getString42()) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string42);
            }
            if (oldStrings[i].getStringValue() == tones.getString41()) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string41);
            }
            if (oldStrings[i].getStringValue() == tones.getString40()) {
                newStrings[i].setStringImage(Gstring);
                newStrings[i].setStringTouch(this.string40);
            }
            if (oldStrings[i].getStringValue() == tones.getString54()) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string54);
            }
            if (oldStrings[i].getStringValue() == tones.getString53()) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string53);
            }
            if (oldStrings[i].getStringValue() == tones.getString52()) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string52);
            }
            if (oldStrings[i].getStringValue() == tones.getString51()) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string51);
            }
            if (oldStrings[i].getStringValue() == tones.getString50()) {
                newStrings[i].setStringImage(Bstring);
                newStrings[i].setStringTouch(this.string50);
            }
            if (oldStrings[i].getStringValue() == tones.getString64()) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string64);
            }
            if (oldStrings[i].getStringValue() == tones.getString63()) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string63);
            }
            if (oldStrings[i].getStringValue() == tones.getString62()) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string62);
            }
            if (oldStrings[i].getStringValue() == tones.getString61()) {
                newStrings[i].setStringImage(E2string);
                newStrings[i].setStringTouch(this.string61);
            }
            if (oldStrings[i].getStringValue() == tones.getString60()) {
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
    public GuitarTone getToneFromTouch(int imageButtonId) {
        GuitarTone guitarTone = new GuitarTone("", string10, tones.getString10(), Estring);

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
        GuitarTone guitarTone = new GuitarTone("", string60, tones.getString60(), E2string);


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
}
