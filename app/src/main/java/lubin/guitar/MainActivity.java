package lubin.guitar;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    Button trySong;
    Button previewSong;
    Button playAkord;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trySong = (Button) findViewById(R.id.trySong);






        Intent draw = new Intent(MainActivity.this,  TrySong.class);
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
                case R.id.settings:
                Intent i = new Intent(MainActivity.this, Settings.class);
                startActivity(i);
                break;

            case R.id.change_instrument:

                break;
        }
        return true;
    }
}

