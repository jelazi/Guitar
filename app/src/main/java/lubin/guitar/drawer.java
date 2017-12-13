package lubin.guitar;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    View.OnClickListener changeGuitar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(drawer.this, Guitar.class);
            startActivity(i);

        }
    };

    View.OnClickListener btnChoiceAccount = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(drawer.this, ChoiceAccount.class);
            startActivity(i);

        }
    };


    View.OnClickListener btnTrySong = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(drawer.this, TrySong.class);
            startActivity(i);

        }
    };


    View.OnClickListener btnPreviewSong = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(drawer.this, PreviewSong.class);
            startActivity(i);

        }
    };

    View.OnClickListener changeGuitar2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(drawer.this, Guitar2.class);
            startActivity(i);

        }
    };


    SoundPool.OnLoadCompleteListener soundPoolOnLoadCompleteListener =
            new SoundPool.OnLoadCompleteListener() {

                @Override
                public void onLoadComplete(SoundPool soundPool,
                                           int sampleId, int status) {
                    if (status == 0) {
                        btnPlay.setEnabled(true);
                    } else {
                        Toast.makeText(drawer.this,
                                "SoundPool.load() fail",
                                Toast.LENGTH_LONG).show();
                    }

                }
            };

    View.OnClickListener btnPlayOnClickListener =
            new View.OnClickListener() {

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


    View.OnClickListener btnChangeOnClickListener = new View.OnClickListener() { //zmena nastroje
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
