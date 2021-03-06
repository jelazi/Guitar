package lubin.guitar.GuitarActivity;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lubin.guitar.Shop.ShopActivity;
import lubin.guitar.Song.GuitarTone;
import lubin.guitar.R;
import lubin.guitar.Settings.SettingsScreenActivity;
import lubin.guitar.Song.Songs;
import lubin.guitar.Song.Tone;
import lubin.guitar.Users.SingletonManagerUsers;
import lubin.guitar.Users.UserLevel;

public class TrySongActivity extends VirtualGuitarActivity implements View.OnClickListener {
    Button btnTryMusic; //tlacitko hraj skladbu
    TextView lblLevel;
    UserLevel currentLevel;
    ArrayList<GuitarTone> playingTones = new ArrayList<>();
    ImageView nextSong;
    ImageView changeInstrumentBtn;
    int stepBeginner;
    int stepExpert;
    int stepProfessional;
    int stepGenius;
    int minusGenius;
    int stepChampion;
    int minusChampion;
    int valueStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_try_song);
        addFretboard();
        createView();
        money = findViewById(R.id.valueMoney);
        money.setText(Integer.toString(currentUser.getCoins()));


        lblLevel = findViewById(R.id.name_user);
        lblLevel.setOnClickListener(this);

        nextSong = findViewById(R.id.sheets);
        nextSong.setOnClickListener(this);

        changeInstrumentBtn = findViewById(R.id.guitar);
        changeInstrumentBtn.setOnClickListener(this);

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
        btnTryMusic = findViewById(R.id.tryMusic);
        btnTryMusic.setOnClickListener(trySong);

        this.setTitle(R.string.action_try_song);
        soundId = soundPool.load( currentUser.getCurrentNameInstrument(), 1);
        currentLevel = currentUser.getCurrentLevel();
    }

    @Override
    public void onClick(View view) {
        if (view == changeInstrumentBtn) {
            changeInstrument(true);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (view == lblLevel) {

            builder.setTitle(getResources().getString(R.string.current_level));
            final List<String> listAllowedLevel = SingletonManagerUsers.getListAllowLevel(currentUser.getAllowedLevel(), this);
            final String[] arrayAllowedLevel = new String[listAllowedLevel.size()];
            listAllowedLevel.toArray(arrayAllowedLevel);
            final int[] checkItem = {currentUser.getCurrentLevel().getValue() - 1};
            builder.setSingleChoiceItems(
                    arrayAllowedLevel,
                    checkItem[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            checkItem[0] = item;
                        }
                    }
            );
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    currentUser.setCurrentLevel(SingletonManagerUsers.getUserLevelByValue(checkItem[0]+1), TrySongActivity.this);
                    SingletonManagerUsers.changeUser(currentUser);
                    currentLevel = currentUser.getCurrentLevel();
                    lblLevel.setText(SingletonManagerUsers.getNameLevelByUserLevel(currentUser.getCurrentLevel(), TrySongActivity.this));
                }
            });

        }

        if (view == nextSong) {
            builder.setTitle(getResources().getString(R.string.current_song));
            final List<String> listAllowedSongs = currentUser.getAllowedSongs();
            final String[] allAllowedSongs = new String[listAllowedSongs.size()];
            listAllowedSongs.toArray(allAllowedSongs);
            final int[] checkItem = {0};
            if (listAllowedSongs.contains(currentUser.getCurrentNameSong())) {
                for (int i = 0; i < listAllowedSongs.size(); i++) {
                    if (listAllowedSongs.get(i).equals(currentUser.getCurrentNameSong())) {
                        checkItem[0] = i;
                        break;
                    }
                }
            } else {
                checkItem[0] = 0;
            }
            builder.setSingleChoiceItems(
                    allAllowedSongs,
                    checkItem[0],
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            checkItem[0] = item;
                        }
                    }
            );
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nameCurrentSong = "";
                    nameCurrentSong = listAllowedSongs.get(checkItem[0]);
                    if (!nameCurrentSong.isEmpty()) {
                        currentUser.setCurrentNameSong(nameCurrentSong);
                        SingletonManagerUsers.changeUser(currentUser);
                        TrySongActivity.this.onResume();
                    }
                }
            });
        }
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //vytvoreni menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_try_song, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //nastaveni akci menu
        int id = item.getItemId();
        SingletonManagerUsers.changeUser(currentUser);

        switch(id) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.settings_menu:
                Intent i = new Intent(TrySongActivity.this, SettingsScreenActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.btn_change_instrument:
                changeInstrument(true);
                break;

            case R.id.preview_song:
                i = new Intent(TrySongActivity.this, PreviewSongActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.play_chord:
                i = new Intent(TrySongActivity.this, PlayChordActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.open_shop:
                i = new Intent(TrySongActivity.this, ShopActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onResume() { //aktualizace nastaveni hodnot ze SettingsScreenActivity
        super.onResume();
        stopBeforeTone = currentUser.isChoiceMultiTone();
        currentLevel = currentUser.getCurrentLevel();
        settings = PreferenceManager
                .getDefaultSharedPreferences(this);

        stepBeginner = settings.getInt("stepBeginner", 1);
        stepExpert = settings.getInt("stepExpert", 2);
        stepProfessional = settings.getInt("stepProfessional", 5);
        stepGenius = settings.getInt("stepGenius", 7);
        minusGenius = settings.getInt("minusGenius", 1);
        stepChampion = settings.getInt("stepChampion", 10);
        minusChampion = settings.getInt("minusChampion", 2);

        lblLevel.setText(SingletonManagerUsers.getNameLevelByUserLevel(currentUser.getCurrentLevel(), this));

        soundId = soundPool.load( currentUser.getCurrentNameInstrument(), 1);

        currentSong = Songs.getSongByName(getApplicationContext(), currentUser.getCurrentNameSong());
        numberTone = 0;
        cleanStrings();
        fillInstrument();
        btnTryMusic.setText(getResources().getString(R.string.try_song) + " " + currentSong.getNameOfSong());
        btnTryMusic.setBackgroundResource(android.R.drawable.btn_default);
        playingSong = false;
    }

    View.OnClickListener stringPlayOnClickListener = //uder do struny
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                 //   Log.d("Debug", );
                    if (playingSong) {
                        boolean isNextToneEmpty = true;
                        ImageButton nextImageButton =  null;
                        if (numberTone < playingTones.size() - 1) {
                            isNextToneEmpty = isEmptyGuitarTone(playingTones.get(numberTone + 1));
                            if (playingTones.get(numberTone + 1).getName().equals("silent")) {
                                nextImageButton = null;

                            } else {
                                nextImageButton = playingTones.get(numberTone + 1).getStringTouch();
                            }
                        }
                        switch (currentLevel) {
                            case BEGINNER: {
                                    playToneFromTouch(playingTone.getStringTouch(), nextImageButton, null, true, isNextToneEmpty);
                                    int value = currentUser.getCoins() + stepBeginner;
                                    currentUser.setCoins(value);
                                    money.setText(Integer.toString(value));
                                    numberTone++;
                                    playingTone.getStringImage().clearColorFilter();
                                break;
                            }
                            case EXPERT: {
                                List<String> playilist = getListNameTonesStringByTouch(getToneFromTouch(v.getId()));
                                if (getListNameTonesStringByTouch(getToneFromTouch(v.getId())).contains(playingTone.getName())) {
                                    playToneFromTouch(playingTone.getStringTouch(), nextImageButton, null, true, isNextToneEmpty);
                                    int value = currentUser.getCoins() + stepExpert;
                                    currentUser.setCoins(value);
                                    money.setText(Integer.toString(value));
                                    numberTone++;
                                    playingTone.getStringImage().clearColorFilter();

                                } else {
                                    break;
                                }
                                break;
                            }
                            case PROFESSIONAL: {
                                if (playingTone.getStringValue() == (getToneFromTouch(v.getId()).getStringValue())) {
                                    playToneFromTouch(playingTone.getStringTouch(), nextImageButton, null, true, isNextToneEmpty);
                                    int value = currentUser.getCoins() + stepProfessional;
                                    currentUser.setCoins(value);
                                    money.setText(Integer.toString(value));
                                    numberTone++;
                                    playingTone.getStringImage().clearColorFilter();

                                } else {
                                    break;
                                }
                                break;
                            }
                            default: {
                                if (playingTone.getStringValue() == (getToneFromTouch(v.getId()).getStringValue())) {
                                    playToneFromTouch(v, nextImageButton, null, true, isNextToneEmpty);

                                    int value = currentUser.getCoins() + stepChampion;
                                    if (currentLevel == UserLevel.GENIUS) {
                                        value = currentUser.getCoins() + stepGenius;
                                    }
                                    currentUser.setCoins(value);
                                    money.setText(Integer.toString(value));
                                    numberTone++;
                                    playingTone.getStringImage().clearColorFilter();
                                } else {
                                    playToneFromTouch(v, nextImageButton, null, false, isNextToneEmpty);
                                    int value = currentUser.getCoins() - minusChampion;
                                    if (currentLevel == UserLevel.GENIUS) {
                                        value = currentUser.getCoins() - minusGenius;
                                    }
                                    currentUser.setCoins(value);
                                    money.setText(Integer.toString(value));
                                    playingTone.getStringImage().clearColorFilter();
                                }
                            }
                        }
                        if (numberTone < playingTones.size()) {
                            tryRealSong();
                        } else {
                            playingSong = false;
                            endingToast();
                            numberTone = 0;
                            btnTryMusic.setText(getResources().getString(R.string.try_song) + " " + currentSong.getNameOfSong());
                            btnTryMusic.setBackgroundResource(android.R.drawable.btn_default);
                            SingletonManagerUsers.changeUser(currentUser);
                        }
                    }
                    else {
                        playToneFromTouch(v, null);
                    }

                }
            };


    // zkouska skladby
    View.OnClickListener trySong = new View.OnClickListener() { //tlacitko, zkus hrat
        @Override
        public void onClick(View view) {
            if (playingSong) {
                playingSong = false;
                endingToast();
                numberTone = 0;
                btnTryMusic.setText(getResources().getString(R.string.try_song) + " " + currentSong.getNameOfSong());
                btnTryMusic.setBackgroundResource(android.R.drawable.btn_default);
                playingTone.getStringImage().clearColorFilter();

                SingletonManagerUsers.changeUser(currentUser);
                cleanStrings();
            } else {
                playingSong = true;
                valueStart = currentUser.getCoins();
                btnTryMusic.setText(getResources().getString(R.string.playing) + " " + currentSong.getNameOfSong());
                btnTryMusic.setBackgroundColor(0x800a33f5);
                currentSong = Songs.getSongByName(getApplicationContext(), currentUser.getCurrentNameSong());
                playingTones = createMusicFromTones(currentSong.getTones());
                tryRealSong();
            }
        }
    };


    public void tryRealSong() { //skutecne zahrani tonu
        playingTone = playingTones.get(numberTone);
        if (playingTone.getName().equals("silent")) {
            numberTone++;
            cleanStrings();
            tryRealSong();
            return;
        }
        if (numberTone == playingTones.size() -1) {
            cleanStrings();
            return;
        }
        if (currentLevel == UserLevel.CHAMPION) {
            playingTone.getStringImage().setColorFilter(0x80ff0000);
        } else {
            playingTone.getStringImage().setColorFilter(0x80ff0000);
            if (isEmptyGuitarTone(playingTone)) {
                playingTone.getStringTouch().setBackgroundResource(R.drawable.touch_empty);
            } else {
                playingTone.getStringTouch().setBackgroundResource(R.drawable.touch);
            }
        }
    }

    private void endingToast () {
        int coin = currentUser.getCoins() - valueStart;
        valueStart = 0;
        if (coin > 0) {
            toast(this.getResources().getString(R.string.warning_congr_plus) + Integer.toString(coin) + this.getResources().getString(R.string.warning_points), R.drawable.coin_bag);
        } else if (coin == 0) {
            toast(this.getResources().getString(R.string.warning_congr_null), R.drawable.coin_bag);
        } else {
            toast(this.getResources().getString(R.string.warning_congr_minus) + Integer.toString(Math.abs(coin)) + this.getResources().getString(R.string.warning_points), R.drawable.coin_bag);
        }
    }

    ///vytvori skladbu
    public ArrayList<GuitarTone> createMusicFromTones(ArrayList<Tone> musicTone) {
        int length = musicTone.size();
        ArrayList<GuitarTone> music = new ArrayList<>();
        for (int i = 0; i <= length - 1; i++) {
            music.add(getToneFromName(musicTone.get(i).nameTone));
        }
        return music;
    }

    protected void Touching(final ImageButton imgButton, final ImageButton nextButton, final Drawable background, final boolean isCorrect, final boolean isEmpty, final boolean isEmptyNext) {

        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imgButton.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationStart(Animation animation) {
                if (isCorrect) {
                    if (isEmpty) {
                        imgButton.setBackgroundResource(R.drawable.touch_empty);
                    } else {
                        imgButton.setBackgroundResource(R.drawable.touch);
                    }
                } else {
                    if (isEmpty) {
                        imgButton.setBackgroundResource(R.drawable.false_touch_empty);
                    } else {
                        imgButton.setBackgroundResource(R.drawable.false_touch);
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imgButton.setBackground(background);

                if (isCorrect && nextButton != null) {
                    if (currentUser.getCurrentLevel() != UserLevel.CHAMPION) {
                        if (isEmptyNext) {
                            nextButton.setBackgroundResource(R.drawable.touch_empty);
                        } else {
                            nextButton.setBackgroundResource(R.drawable.touch);
                        }
                    }
                    if (playingTone == playingTones.get(playingTones.size()-1)) {
                        playingTones.get(playingTones.size()-1).getStringImage().setColorFilter(0x80ff0000);
                    }
                }
            }
        });
    }



    protected List<String> getListNameTonesStringByTouch (GuitarTone touchTone) {
        List<String> e2String = new ArrayList<>(Arrays.asList("Gis4", "G4", "Fis4", "F4", "E4"));
        List<String> bString = new ArrayList<>(Arrays.asList("Dis4", "D4", "Cis4", "C4", "B3"));
        List<String> gString = new ArrayList<>(Arrays.asList("B32", "Ais3", "A3", "Gis3", "G3"));
        List<String> dString = new ArrayList<>(Arrays.asList("Fis3", "F3", "E3", "Dis3", "D3"));
        List<String> aString = new ArrayList<>(Arrays.asList("Cis3", "C3", "B2", "Ais2", "A2"));
        List<String> eString = new ArrayList<>(Arrays.asList("Gis2", "G2", "Fis2", "F2", "E2"));
        if (e2String.contains(touchTone.getName())) return e2String;
        if (bString.contains(touchTone.getName())) return bString;
        if (gString.contains(touchTone.getName())) return gString;
        if (dString.contains(touchTone.getName())) return dString;
        if (aString.contains(touchTone.getName())) return aString;
        if (eString.contains(touchTone.getName())) return eString;
        return null;
    }

    protected void playToneFromTouch(View v, ImageButton nextButton, Drawable background, boolean isCorrect, boolean isEmptyNextTone) {
        switch (v.getId()) {
            case R.id.imageButton14: {
                normal_playback_rate = tones.getString14();
                Shaking(Estring);
                Touching(string14, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton13: {
                normal_playback_rate = tones.getString13();
                Shaking(Estring);
                Touching(string13, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton12: {
                normal_playback_rate = tones.getString12();
                Shaking(Estring);
                Touching(string12, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton11: {
                normal_playback_rate = tones.getString11();
                Shaking(Estring);
                Touching(string11, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton10: {
                normal_playback_rate = tones.getString10();
                Shaking(Estring);
                Touching(string10, nextButton, background, isCorrect, true, isEmptyNextTone);
                break;
            }
            case R.id.imageButton24: {
                normal_playback_rate = tones.getString24();
                Shaking(Astring);
                Touching(string24, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton23: {
                normal_playback_rate = tones.getString23();
                Shaking(Astring);
                Touching(string23, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton22: {
                normal_playback_rate = tones.getString22();
                Shaking(Astring);
                Touching(string22, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton21: {
                normal_playback_rate = tones.getString21();
                Shaking(Astring);
                Touching(string21, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton20: {
                normal_playback_rate = tones.getString20();
                Shaking(Astring);
                Touching(string20, nextButton, background, isCorrect, true, isEmptyNextTone);
                break;
            }
            case R.id.imageButton34: {
                normal_playback_rate = tones.getString34();
                Shaking(Dstring);
                Touching(string34, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton33: {
                normal_playback_rate = tones.getString33();
                Shaking(Dstring);
                Touching(string33, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton32: {
                normal_playback_rate = tones.getString32();
                Shaking(Dstring);
                Touching(string32, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton31: {
                normal_playback_rate = tones.getString31();
                Shaking(Dstring);
                Touching(string31, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton30: {
                normal_playback_rate = tones.getString30();
                Shaking(Dstring);
                Touching(string30, nextButton, background, isCorrect, true, isEmptyNextTone);
                break;
            }
            case R.id.imageButton44: {
                normal_playback_rate = tones.getString44();
                Shaking(Gstring);
                Touching(string44, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton43: {
                normal_playback_rate = tones.getString43();
                Shaking(Gstring);
                Touching(string43, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton42: {
                normal_playback_rate = tones.getString42();
                Shaking(Gstring);
                Touching(string42, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton41: {
                normal_playback_rate = tones.getString41();
                Shaking(Gstring);
                Touching(string41, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton40: {
                normal_playback_rate = tones.getString40();
                Shaking(Gstring);
                Touching(string40, nextButton, background, isCorrect, true, isEmptyNextTone);
                break;
            }
            case R.id.imageButton54: {
                normal_playback_rate = tones.getString54();
                Shaking(Bstring);
                Touching(string54, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton53: {
                normal_playback_rate = tones.getString53();
                Shaking(Bstring);
                Touching(string53, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton52: {
                normal_playback_rate = tones.getString52();
                Shaking(Bstring);
                Touching(string52, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton51: {
                normal_playback_rate = tones.getString51();
                Shaking(Bstring);
                Touching(string51, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton50: {
                normal_playback_rate = tones.getString50();
                Shaking(Bstring);
                Touching(string50, nextButton, background, isCorrect, true, isEmptyNextTone);
                break;
            }
            case R.id.imageButton64: {
                normal_playback_rate = tones.getString64();
                Shaking(E2string);
                Touching(string64, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton63: {
                normal_playback_rate = tones.getString63();
                Shaking(E2string);
                Touching(string63, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton62: {
                normal_playback_rate = tones.getString62();
                Shaking(E2string);
                Touching(string62, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton61: {
                normal_playback_rate = tones.getString61();
                Shaking(E2string);
                Touching(string61, nextButton, background, isCorrect, false, isEmptyNextTone);
                break;
            }
            case R.id.imageButton60: {
                normal_playback_rate = tones.getString60();
                Shaking(E2string);
                Touching(string60, nextButton, background, isCorrect, true, isEmptyNextTone);
                break;
            }
            default: {
                normal_playback_rate = tones.getString10();
                Shaking(E2string);
                Touching(string10, nextButton, background, isCorrect, true, isEmptyNextTone);
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
}
