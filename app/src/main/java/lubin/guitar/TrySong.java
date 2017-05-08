package lubin.guitar;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class TrySong extends VirtualGuitar {
    Button btnTryMusic; //tlacitko hraj skladbu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_song);
        createView();
        money = (TextView) findViewById(R.id.valueMoney);
        money.setText(Integer.toString(moneyValue));

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
    }


    View.OnClickListener stringPlayOnClickListener =
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (playingSong) {
                        if (playingTone.getStringValue() == (getToneFromTouch(v.getId()).getStringValue())) {
                            playToneFromTouch(v, pokus.get(numberTone + 1).getStringTouch(), null);
                            moneyValue++;
                            money.setText(Integer.toString(moneyValue));
                            numberTone++;
                            playingTone.getStringImage().clearColorFilter();
                            //   playingTone.getStringTouch().setBackgroundResource(0);
                            if (numberTone <= pokus.size() - 1) {
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
                skladba = Songs.getSong2();
                pokus = createMusicFromTones(skladba);
                trytrySong();
            }
        }

        ;


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
