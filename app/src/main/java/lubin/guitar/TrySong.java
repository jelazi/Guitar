package lubin.guitar;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.Objects;


public class TrySong extends VirtualGuitar

{
    Button btnTryMusic; //tlacitko hraj skladbu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_try_song);
        createView();
        money = (TextView) findViewById(R.id.valueMoney);
        money.setText(Integer.toString(Globals.getValueUser()));


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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_try_song, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
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

            case R.id.play_pause_try_song:

                        if (playingSong) {
                            playingSong = false;
                            playingTone.getStringImage().clearColorFilter();
                            item.setIcon(R.mipmap.play_circle);

                        } else {
                            playingSong = true;
                            skladba = Songs.callByName(getApplicationContext(), Globals.getSongName());
                            pokus = createMusicFromTones(skladba.getTones());
                            item.setIcon(R.mipmap.pause_circle);
                            trytrySong();
                        }

                break;

            case R.id.settings:
                soundPool.release();
                Intent i = new Intent(TrySong.this, Settings.class);
                startActivity(i);
                break;

            case R.id.change_instrument:
                changeInstrument();
                break;

            case R.id.preview_song:
                soundPool.release();
                i = new Intent(TrySong.this, PreviewSong.class);
                startActivity(i);
                break;

            case R.id.play_chord:
                soundPool.release();
                i = new Intent(TrySong.this, PlayAcord.class);
                startActivity(i);
                break;
        }
        return true;
    }




    View.OnClickListener stringPlayOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (playingSong) {
                        if (playingTone.getStringValue() == (getToneFromTouch(v.getId()).getStringValue())) {
                            playToneFromTouch(v, pokus.get(numberTone + 1).getStringTouch(), null);
                            Globals.setValueUser(Globals.getValueUser() + 1);
                            money.setText(Integer.toString(Globals.getValueUser()));
                            numberTone++;
                            playingTone.getStringImage().clearColorFilter();
                            //   playingTone.getStringTouch().setBackgroundResource(0);
                            if (numberTone < pokus.size() - 1) {
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
    View.OnClickListener trySong = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (playingSong) {
                playingSong = false;
                btnTryMusic.setText("Zkus hrát");
                btnTryMusic.setBackgroundResource(android.R.drawable.btn_default);
                playingTone.getStringImage().clearColorFilter();
                //    playingTone.getStringTouch().setBackgroundResource(0);
            } else {
                playingSong = true;
                btnTryMusic.setText("Hrajeme...");
                btnTryMusic.setBackgroundColor(0x800a33f5);


                skladba = Songs.callByName(getApplicationContext(), Globals.getSongName());




                pokus = createMusicFromTones(skladba.getTones());
                trytrySong();
            }
        }




    };


    public void trytrySong() {
        playingTone = pokus.get(numberTone);
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
