package lubin.guitar;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import lubin.guitar.Settings.SettingsScreenActivity;
import lubin.guitar.Account.ChoiceAccountActivity;
import lubin.guitar.GuitarActivity.PlayAcordActivity;
import lubin.guitar.GuitarActivity.PreviewSongActivity;
import lubin.guitar.GuitarActivity.TrySongActivity;


public class MainActivity extends AppCompatActivity {


    Button trySong;
    Button previewSong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trySong = (Button) findViewById(R.id.trySong);
        Intent draw = new Intent(MainActivity.this,  ChoiceAccountActivity.class);

        //
        //Intent draw = new Intent(MainActivity.this,  PreviewSongActivity.class);
        startActivity(draw);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_virtual, menu);
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

            case R.id.settings:

                Intent i = new Intent(this, SettingsScreenActivity.class);
                startActivity(i);
                break;

            case R.id.try_song:
               // soundPool.release();
                i = new Intent(this, TrySongActivity.class);
                startActivity(i);
                break;

            case R.id.preview_song:
                //soundPool.release();
                i = new Intent(this, PreviewSongActivity.class);
                startActivity(i);
                break;

            case R.id.play_chord:
               // soundPool.release();
                i = new Intent(this, PlayAcordActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }
}

