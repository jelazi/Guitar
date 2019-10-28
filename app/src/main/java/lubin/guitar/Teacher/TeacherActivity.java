package lubin.guitar.Teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lubin.guitar.EditUserActivity;
import lubin.guitar.Files.DialogType;
import lubin.guitar.R;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {

    Button editUser;
    Button recordSound;
    Button editName;
    Button editPass;
    SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        editUser = (Button) findViewById(R.id.edit_user);
        editUser.setOnClickListener(this);
        recordSound = (Button) findViewById(R.id.record_sound_btn);
        recordSound.setOnClickListener(this);
        editName = (Button) findViewById(R.id.change_name_teacher);
        editName.setOnClickListener(this);
        editPass = (Button) findViewById(R.id.change_pass_teacher);

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


    @Override
    public void onClick(View view) {
        if (view == editUser) {
            Intent i = new Intent(view.getContext(), EditUserActivity.class);
            startActivity(i);
            return;
        }
        if (view == recordSound) {
            Intent i = new Intent(view.getContext(), WavRecorderActivity.class);
            startActivity(i);
            return;
        }
        if (view == editName) {
            showDialog(DialogType.CHANGETEACHERNAME);
            return;
        }
        if (view == editPass) {
            showDialog(DialogType.CHANGETEACHERPASS);
            return;
        }
    }

    public void showDialog(DialogType dialogType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (dialogType) {
            case CHANGETEACHERNAME: {
                builder.setMessage("Nové jméno");
                final EditText edittext = new EditText(this);
                edittext.setText(settings.getString("nameTeacher", ""));
                edittext.setSelectAllOnFocus(true);
                builder.setView(edittext);
                edittext.requestFocus();
                builder.setPositiveButton("Uložit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPreferences("nameTeacher", edittext.getText().toString());
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Zrušit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
                break;
            }
            case CHANGETEACHERPASS: {
                    builder.setMessage("Nové heslo");
                    final EditText edittext = new EditText(this);
                    edittext.setText(settings.getString("passTeacher", ""));
                    edittext.setSelectAllOnFocus(true);
                    builder.setView(edittext);
                    edittext.requestFocus();
                    builder.setPositiveButton("Uložit", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setPreferences("passTeacher", edittext.getText().toString());
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("Zrušit", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                        }
                    });
                break;
            }
            default: {

            }
        }
        builder.show();

    }

    private void setPreferences (String namePreference, String valuePreference) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(namePreference, valuePreference);
        editor.commit();
        Toast.makeText(this, valuePreference + " uloženo jako nové jméno.", Toast.LENGTH_SHORT).show();

    }

}
