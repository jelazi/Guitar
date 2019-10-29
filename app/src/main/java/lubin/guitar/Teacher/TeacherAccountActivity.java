package lubin.guitar.Teacher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import lubin.guitar.Files.FileInOut;
import lubin.guitar.R;

public class TeacherAccountActivity extends AppCompatActivity {
    SharedPreferences settings;
    Button btnAcc;
    ImageView img;
    EditText accountName;
    EditText accountPass;
    String name;
    String pass;
    boolean createName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_account);
        btnAcc = (Button)findViewById(R.id.btnAccountTeacher);
        btnAcc.setOnClickListener(openAccountListener);
        img = (ImageView)findViewById(R.id.accTeacher);
        android.view.animation.Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img.startAnimation(animation1);
        accountName = (EditText)findViewById(R.id.AccNameTeacher);
        accountPass = (EditText)findViewById(R.id.AccountPassTeacher);
        accountName.setOnClickListener(onClickSetName);
        accountPass.setOnClickListener(onClickSetPass);
        img.setOnClickListener(openAccountListener);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        name = settings.getString("nameTeacher", "");
        pass = settings.getString("passTeacher", "");
        if (name.equals("")) { //is empty nameTeacher, passTeacher
            btnAcc.setText("Vytvořit");
            createName = true;
        } else {
            createName = false;
            btnAcc.setText("Přihlas se");
        }
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id)
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    View.OnClickListener openAccountListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String correctName = FileInOut.nameWithoutDiacritic(accountName.getText().toString().toLowerCase());
            String nameWithoutDiacritic = FileInOut.nameWithoutDiacritic(name.toLowerCase());
            String correctPass = accountPass.getText().toString();

            if (createName) { //new name and password teacher
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("nameTeacher", correctName);
                editor.putString("passTeacher", correctPass);
                editor.commit();
                name = settings.getString("nameTeacher", "");
                pass = settings.getString("passTeacher", "");
                Toast.makeText(view.getContext(), "Nové heslo a jméno uloženo", Toast.LENGTH_SHORT).show();
                createName = false;
                btnAcc.setText("Přihlas se");
            } else {
                if (correctName.equals(nameWithoutDiacritic) && correctPass.equals(pass)) {
                    Intent i = new Intent(view.getContext(), TeacherActivity.class);
                    startActivity(i);
                    return;
                }
                Toast.makeText(view.getContext(), "Špatné jméno nebo heslo", Toast.LENGTH_SHORT).show();
                accountName.setText("");
                accountPass.setText("");
            }
        }
    };


    View.OnClickListener onClickSetName = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            //accountName.setText("");
        }
    };

    View.OnClickListener onClickSetPass = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            //accountPass.setText("");

        }
    };







}