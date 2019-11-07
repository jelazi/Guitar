package lubin.guitar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import lubin.guitar.Account.ChoiceAccountActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent draw = new Intent(MainActivity.this,  ChoiceAccountActivity.class);
        startActivity(draw);
    }
}

