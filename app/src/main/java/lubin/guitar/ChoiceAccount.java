package lubin.guitar;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import lubin.guitar.R;

public class ChoiceAccount extends AppCompatActivity {


    ImageView img1;
    ImageView img2;
    Button btnGuitarist;
    Button btnTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_account);

        img1 = (ImageView)findViewById(R.id.imgGuitarist);
        img2 = (ImageView)findViewById(R.id.imgTeacher);

        android.view.animation.Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img1.startAnimation(animation1);
        android.view.animation.Animation animation2= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img2.startAnimation(animation2);


        btnGuitarist = (Button)findViewById(R.id.button);
        btnTeacher = (Button)findViewById(R.id.button2);
        btnGuitarist.setOnClickListener(runGuitarist);
        btnTeacher.setOnClickListener(runTeacher);


    }


    View.OnClickListener runGuitarist = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ChoiceAccount.this, TrySong.class);
            startActivity(i);

        }
    };

    View.OnClickListener runTeacher = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ChoiceAccount.this, MainActivity.class);
            startActivity(i);

        }
    };

    }

