package lubin.guitar.GuitarActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import lubin.guitar.Song.GuitarTone;
import lubin.guitar.R;
import lubin.guitar.Settings.SettingsScreenActivity;
import lubin.guitar.Song.Songs;
import lubin.guitar.Song.Tone;


public class TrySongActivity extends VirtualGuitarActivity

{
    Button btnTryMusic; //tlacitko hraj skladbu
    TextView nameUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_try_song);
        addFretboard();
        createView();
        money = (TextView) findViewById(R.id.valueMoney);

        money.setText(settings.getString("value_user", "0"));

        nameUser = (TextView) findViewById(R.id.name_user);

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
        btnTryMusic = (Button) findViewById(R.id.tryMusic); //prirazeni tlacitka - Hraj skladbu
        btnTryMusic.setOnClickListener(trySong); //listener tlacitka - Hraj skladbu

        this.setTitle(R.string.action_try_song);

        nameUser.setText(settings.getString("name_user", null)); //jmeno uživatele dle nastavení
        soundId = soundPool.load( getFilesDir()+"/Instruments/"+settings.getString("list_instruments", "a1.wav"), 1);

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
    public boolean onOptionsItemSelected(MenuItem item){ //nastaveni akci menu
        int id = item.getItemId();


        switch(id)
        {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.play_pause_try_song:

                        if (playingSong) {
                            playingSong = false;
                            playingTone.getStringImage().clearColorFilter();
                            item.setIcon(R.mipmap.play_circle);

                        } else {
                            playingSong = true;
                            currentSong = Songs.getSongByName(getApplicationContext(), settings.getString("list_songs", "song1.xml"));
                            playingTones = createMusicFromTones(currentSong.getTones());
                            item.setIcon(R.mipmap.pause_circle);
                            trytrySong();
                        }

                break;

            case R.id.settings_menu:

                Intent i = new Intent(TrySongActivity.this, SettingsScreenActivity.class);
                startActivity(i);

                break;

            case R.id.change_instrument:
                changeInstrument();
                break;

            case R.id.preview_song:
                //soundPool.release();
                i = new Intent(TrySongActivity.this, PreviewSongActivity.class);
                startActivity(i);
                break;

            case R.id.play_chord:
               // soundPool.release();
                i = new Intent(TrySongActivity.this, PlayChordActivity.class);
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

        nameUser.setText(settings.getString("name_user", null));

        soundId = soundPool.load( getFilesDir()+"/Instruments/"+settings.getString("list_instruments", "a1.wav"), 1);

         currentSong = Songs.getSongByName(getApplicationContext(), settings.getString("list_songs", "song1.xml"));
        numberTone = 0;
        cleanStrings();
        btnTryMusic.setText("Zkus hrát");
        btnTryMusic.setBackgroundResource(0);
        playingSong = false;
        stopBeforeTone = settings.getBoolean("stop_before_tone", false);
    }

    View.OnClickListener stringPlayOnClickListener = //uder do struny
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (playingSong) {
                        if (playingTone.getStringValue() == (getToneFromTouch(v.getId()).getStringValue())) {
                            playToneFromTouch(v, playingTones.get(numberTone + 1).getStringTouch(), null);
                            int value = Integer.parseInt(settings.getString("value_user", "0")) + 1;
                            settings.edit().putString("value_user", Integer.toString(value)).apply();
                            money.setText(Integer.toString(value));
                            numberTone++;
                            playingTone.getStringImage().clearColorFilter();
                            //   playingTone.getStringTouch().setBackgroundResource(0);
                            if (numberTone < playingTones.size() - 1) {
                                trytrySong();
                            } else {
                                playingSong = false;
                                btnTryMusic.setText("Zkus hrát");
                                btnTryMusic.setBackgroundResource(0);

                            }
                        } else {

                        }
                    } else {
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
                btnTryMusic.setText("Zkus hrát");
                btnTryMusic.setBackgroundResource(android.R.drawable.btn_default);
                playingTone.getStringImage().clearColorFilter();
                cleanStrings();

            } else {
                playingSong = true;
                btnTryMusic.setText("Hrajeme...");
                btnTryMusic.setBackgroundColor(0x800a33f5);

                currentSong = Songs.getSongByName(getApplicationContext(), settings.getString("list_songs", "song1.xml"));

                playingTones = createMusicFromTones(currentSong.getTones());
                trytrySong();
            }
        }
    };


    public void trytrySong() { //skutecne zahrani tonu
        playingTone = playingTones.get(numberTone);
        playingTone.getStringImage().setColorFilter(0x80ff0000);
        playingTone.getStringTouch().setBackgroundResource(R.drawable.touch);

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
}
