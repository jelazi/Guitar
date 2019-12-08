package lubin.guitar.Users;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import lubin.guitar.R;


public class LevelActivity extends AppCompatActivity {


    TextView lblStepBeginner;
    TextView lblStepExpert;
    TextView lblStepProfessional;
    TextView lblStepGenius;
    TextView lblStepChampion;
    TextView lblMinusGenius;
    TextView lblMinusChampion;

    EditText editStepBeginner;
    EditText editStepExpert;
    EditText editStepProfessional;
    EditText editStepGenius;
    EditText editStepChampion;
    EditText editMinusGenius;
    EditText editMinusChampion;

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        initItems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                controlChanging();
                break;
        }
        return true;
    }

    private void initItems () {
        lblStepBeginner = findViewById(R.id.lblStepBeginner);
        lblStepExpert = findViewById(R.id.lblStepExpert);
        lblStepProfessional = findViewById(R.id.lblStepProffesional);
        lblStepGenius = findViewById(R.id.lblStepGenius);
        lblStepChampion = findViewById(R.id.lblStepChampion);
        lblMinusGenius = findViewById(R.id.lblMinusGenius);
        lblMinusChampion = findViewById(R.id.lblMinusChampion);

        editStepBeginner = findViewById(R.id.editStepBeginner);
        editStepExpert = findViewById(R.id.editStepExpert);
        editStepProfessional = findViewById(R.id.editStepProffesional);
        editStepGenius = findViewById(R.id.editStepGenius);
        editStepChampion = findViewById(R.id.editStepChampion);
        editMinusGenius = findViewById(R.id.editMinusGenius);
        editMinusChampion = findViewById(R.id.editMinusChampion);


        editStepBeginner.setText(Integer.toString(settings.getInt("stepBeginner", 1)));
        editStepExpert.setText(Integer.toString(settings.getInt("stepExpert", 2)));
        editStepProfessional.setText(Integer.toString(settings.getInt("stepProfessional", 5)));
        editStepGenius.setText(Integer.toString(settings.getInt("stepGenius", 7)));
        editStepChampion.setText(Integer.toString(settings.getInt("stepChampion", 1)));
        editMinusGenius.setText(Integer.toString(settings.getInt("minusGenius", 10)));
        editMinusChampion.setText(Integer.toString(settings.getInt("minusChampion", 2)));

    }

    private void controlChanging () {
        int stepBeginner = Integer.parseInt(editStepBeginner.getText().toString());
        int stepExpert= Integer.parseInt(editStepExpert.getText().toString());
        int stepProffesional= Integer.parseInt(editStepProfessional.getText().toString());
        int stepGenius= Integer.parseInt(editStepGenius.getText().toString());
        int stepChampion= Integer.parseInt(editStepChampion.getText().toString());
        int minusGenius= Integer.parseInt(editMinusGenius.getText().toString());
        int minusChampion= Integer.parseInt(editMinusChampion.getText().toString());


        if (stepBeginner == settings.getInt("stepBeginner", 1) &&
                stepExpert == settings.getInt("stepExpert", 1) &&
                stepProffesional == settings.getInt("stepProfessional", 1) &&
                stepGenius == settings.getInt("stepGenius", 1) &&
                stepChampion == settings.getInt("stepChampion", 1) &&
                minusGenius == settings.getInt("minusGenius", 1) &&
                minusChampion == settings.getInt("minusChampion", 1)) {
            onBackPressed();
        } else {
            setPreferences("stepBeginner", stepBeginner);
            setPreferences("stepExpert", stepExpert);
            setPreferences("stepProfessional", stepProffesional);
            setPreferences("stepGenius", stepGenius);
            setPreferences("minusGenius", minusGenius);
            setPreferences("stepChampion", stepChampion);
            setPreferences("minusChampion", minusChampion);
            onBackPressed();
        }
    }
    private void setPreferences (String namePreference, int valuePreference) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(namePreference, valuePreference);
        editor.commit();
    }

}
