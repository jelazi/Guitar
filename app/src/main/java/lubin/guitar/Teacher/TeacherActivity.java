package lubin.guitar.Teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import lubin.guitar.Account.ChoiceAccountActivity;
import lubin.guitar.Files.FileManager;
import lubin.guitar.Files.ImportItemsActivity;
import lubin.guitar.Midi.MidiActivity;
import lubin.guitar.Users.EditUserActivity;
import lubin.guitar.Files.DialogType;
import lubin.guitar.R;
import lubin.guitar.Users.SingletonManagerUsers;
import lubin.guitar.Users.User;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {

    Button editUserBtn;
    Button recordSoundBtn;
    Button editNameBtn;
    Button editPassBtn;
    Button eraseUserBtn;
    Button midiPlayerBtn;
    Button importItemsBtn;
    Button factoryResetBtn;
    SharedPreferences settings;
    List<String> listUsers;
    String userForErase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        editUserBtn = findViewById(R.id.edit_user);
        editUserBtn.setOnClickListener(this);
        recordSoundBtn = findViewById(R.id.record_sound_btn);
        recordSoundBtn.setOnClickListener(this);
        editNameBtn = findViewById(R.id.change_name_teacher);
        editNameBtn.setOnClickListener(this);
        editPassBtn = findViewById(R.id.change_pass_teacher);
        editPassBtn.setOnClickListener(this);
        eraseUserBtn = findViewById(R.id.erase_user);
        eraseUserBtn.setOnClickListener(this);
        midiPlayerBtn = findViewById(R.id.midi_player);
        midiPlayerBtn.setOnClickListener(this);
        importItemsBtn = findViewById(R.id.import_items);
        importItemsBtn.setOnClickListener(this);
        factoryResetBtn = findViewById(R.id.factory_reset);
        factoryResetBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        if (view == editUserBtn) {
            showDialog(DialogType.CHOICE_USER_ACCOUNT);
            return;
        }
        if (view == recordSoundBtn) {
            Intent i = new Intent(view.getContext(), WavRecorderActivity.class);
            startActivity(i);
            return;
        }
        if (view == editNameBtn) {
            showDialog(DialogType.CHANGE_TEACHER_NAME);
            return;
        }
        if (view == editPassBtn) {
            showDialog(DialogType.CHANGE_TEACHER_PASS);
            return;
        }
        if (view == eraseUserBtn) {
            showDialog(DialogType.ERASE_USER);
            return;
        }
        if (view == midiPlayerBtn) {
            Intent i = new Intent(view.getContext(), MidiActivity.class);
            startActivity(i);
            return;
        }
        if (view == importItemsBtn) {
            openImportItemsActivity();
            return;
        }
        if (view == factoryResetBtn) {
            showDialog(DialogType.FACTORY_RESET);
        }
    }

    public void showDialog(DialogType dialogType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (dialogType) {
            case CHANGE_TEACHER_NAME: {
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

            case CHANGE_TEACHER_PASS: {
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

            case CHOICE_USER_ACCOUNT: {
                listUsers = SingletonManagerUsers.getListNamesUsers(true);
                String[] arrayUsers = new String[listUsers.size()];
                listUsers.toArray(arrayUsers);

                builder.setTitle("Vyberte uživatele");

                builder.setItems(arrayUsers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openAccount(listUsers.get(which));
                    }
                });
                break;
            }

            case ERASE_USER: {
                listUsers = SingletonManagerUsers.getListNamesUsers(true);
                String[] arrayUsers = new String[listUsers.size()];
                listUsers.toArray(arrayUsers);

                builder.setTitle("Vymazat uživatele");

                builder.setItems(arrayUsers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userForErase = listUsers.get(which);
                        showDialog(DialogType.CONFIRM_ERASE_USER);
                    }
                });
                break;
            }

            case CONFIRM_ERASE_USER: {
                builder.setTitle("Vymazání uživatele " + userForErase);
                builder.setMessage("Opravdu chcete vymazat uživatele " + userForErase + "?");
                builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eraseAccount(userForErase);
                    }
                });
                builder.setNegativeButton("Ne", null);
                break;
            }

            case NEW_USER: {
                builder.setTitle("Jméno uživatele:");
                final EditText editText = new EditText(this);
                builder.setView(editText);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isUniqueNameUser = SingletonManagerUsers.isUniqueNameUser(editText.getText().toString());
                        if (isUniqueNameUser) {
                            dialog.dismiss();
                            String newName = editText.getText().toString();
                            SingletonManagerUsers.createNewUser(newName);
                            openAccount(newName);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(TeacherActivity.this, "Nepovolené jméno", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }
            case FACTORY_RESET: {
                builder.setTitle("Vymazání všech nastavení");
                builder.setMessage("Opravdu chcete vymazat všechna nastavení?");
                builder.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eraseAllSettings();
                    }
                });
                builder.setNegativeButton("Ne", null);
                break;
            }
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openAccount(String userName) {
        if (!userName.equals("New User")) {
            Intent i = new Intent(this, EditUserActivity.class);
            i.putExtra("user_name", userName);
            startActivity(i);
        } else {
            showDialog(DialogType.NEW_USER);
        }
    }

    private void eraseAccount(String userName) {
        User user = SingletonManagerUsers.getUserByName(userName);
        if (!SingletonManagerUsers.removeUser(user)) {
            Log.e("Error:", "problem whit removing user:" + userName);
        }
    }

    private void setPreferences (String namePreference, String valuePreference) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(namePreference, valuePreference);
        editor.commit();
        Toast.makeText(this, valuePreference + " uloženo jako nové jméno.", Toast.LENGTH_SHORT).show();
    }

    private void openImportItemsActivity () {
        Intent i = new Intent(this, ImportItemsActivity.class);
        startActivity(i);
    }

    protected void eraseAllSettings () {
        FileManager.eraseAllSettings(settings, this);
        FileManager.init(this);
        finish();
        Intent i = new Intent(this, ChoiceAccountActivity.class);
        startActivity(i);
    }
}
