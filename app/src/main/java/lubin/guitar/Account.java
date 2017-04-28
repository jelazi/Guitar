package lubin.guitar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lubin.guitar.R;

public class Account extends AppCompatActivity {


    Button btnAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnAcc = (Button)findViewById(R.id.btnAccount);
        btnAcc.setOnClickListener(openAccountListener);


    }


    View.OnClickListener openAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(Account.this, TrySong.class);
            startActivity(i);

        }
    };


}
