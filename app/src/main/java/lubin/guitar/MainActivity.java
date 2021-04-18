package lubin.guitar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import lubin.guitar.Account.ChoiceAccountActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageGuitarist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        imageGuitarist = findViewById(R.id.img_guitarist);
        imageGuitarist.setOnClickListener(this);


        openChoiceAccountActivity();

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public void openChoiceAccountActivity () {
        Intent draw = new Intent(MainActivity.this,  ChoiceAccountActivity.class);
        startActivity(draw);
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == imageGuitarist) {
            openChoiceAccountActivity();
        }
    }
}



