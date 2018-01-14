package lubin.guitar;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent draw = new Intent(MainActivity.this,  TrySong.class);
        startActivity(draw);

    }
}

