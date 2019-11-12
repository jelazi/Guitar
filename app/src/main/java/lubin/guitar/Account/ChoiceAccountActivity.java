package lubin.guitar.Account;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import lubin.guitar.Files.FileManager;
import lubin.guitar.R;
import lubin.guitar.Teacher.TeacherAccountActivity;
import lubin.guitar.Users.SingletonManagerUsers;
import lubin.guitar.Users.User;

public class ChoiceAccountActivity extends AppCompatActivity {
    ImageView img1;
    ImageView img2;
    Button btnGuitarist;
    Button btnTeacher;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_account);
        FileManager.init(getApplicationContext());
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        SingletonManagerUsers.createSingletonManagerUsers(settings);

        img1 = findViewById(R.id.imgGuitarist);
        img2 = findViewById(R.id.imgTeacher);

        android.view.animation.Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);

        img1.startAnimation(animation1);
        android.view.animation.Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img2.startAnimation(animation2);

        btnGuitarist = findViewById(R.id.button);
        btnTeacher = findViewById(R.id.button2);
        btnGuitarist.setOnClickListener(runGuitarist);

        btnTeacher.setOnClickListener(runTeacher);

        img1.setOnClickListener(runGuitarist);
        img2.setOnClickListener(runTeacher);
    }


    View.OnClickListener runGuitarist = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<String> listUser = SingletonManagerUsers.getListNamesUsers(false);
            if (listUser == null || listUser.size() == 0) {
                Toast.makeText(ChoiceAccountActivity.this, "Nejdříve vytvořte aspoň jeden účet kytaristy.", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(ChoiceAccountActivity.this, AccountActivity.class);
                startActivity(i);
            }
        }
    };

    View.OnClickListener runTeacher = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ChoiceAccountActivity.this, TeacherAccountActivity.class);
            startActivity(i);

        }
    };
}

