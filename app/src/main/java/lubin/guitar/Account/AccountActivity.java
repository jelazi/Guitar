package lubin.guitar.Account;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lubin.guitar.Files.DialogType;
import lubin.guitar.GuitarActivity.PlayChordActivity;
import lubin.guitar.GuitarActivity.PreviewSongActivity;
import lubin.guitar.R;
import lubin.guitar.GuitarActivity.TrySongActivity;
import lubin.guitar.Shop.ShopActivity;
import lubin.guitar.Users.SingletonManagerUsers;
import lubin.guitar.Users.User;

public class AccountActivity extends AppCompatActivity {
    Button btnAcc;
    ImageView img;
    EditText accountPass;
    Spinner nameSpinner;
    List<String>nameUsers;
    SharedPreferences settings;
    User currentUser;
    List<String> listActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnAcc = findViewById(R.id.btnAccount);
        btnAcc.setOnClickListener(openAccountListener);
        img = findViewById(R.id.AccGuitarist);
        android.view.animation.Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img.startAnimation(animation1);
        accountPass = findViewById(R.id.AccountPass);
        nameSpinner = findViewById(R.id.Spinner01);
        accountPass.setOnClickListener(onClickSetPass);

        nameUsers = SingletonManagerUsers.getListNamesUsers(false, this); //nacteni uzivatelu
        String[] arrayUsers = new String[nameUsers.size()];
        nameUsers.toArray(arrayUsers);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayUsers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(adapter);

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    protected void showDialog (DialogType dialogType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (dialogType == DialogType.CHOICE_ACTIVITY) {
            listActivity = new ArrayList<>();
            listActivity.add(getResources().getString(R.string.action_play_chords));
            listActivity.add(getResources().getString(R.string.action_preview_song));
            listActivity.add(getResources().getString(R.string.action_try_song));
            listActivity.add(getResources().getString(R.string.open_shop));

            String[] arrayUsers = new String[listActivity.size()];
            listActivity.toArray(arrayUsers);

            builder.setTitle(getResources().getString(R.string.choice_activity));

            builder.setItems(arrayUsers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openActivity(listActivity.get(which));
                }
            });

        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void openActivity (String activityName) {
        Intent i = new Intent(AccountActivity.this, PlayChordActivity.class);
            if (activityName.equals(getResources().getString(R.string.action_play_chords))) {
                i = new Intent(AccountActivity.this, PlayChordActivity.class);
            } else if (activityName.equals(getResources().getString(R.string.action_preview_song))) {
                i = new Intent(AccountActivity.this, PreviewSongActivity.class);
            } else if (activityName.equals(getResources().getString(R.string.action_try_song))) {
                i = new Intent(AccountActivity.this, TrySongActivity.class);
            } else if (activityName.equals(getResources().getString(R.string.open_shop))) {
                i = new Intent(AccountActivity.this, ShopActivity.class);
            }
        startActivity(i);
    }


    View.OnClickListener openAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            currentUser = SingletonManagerUsers.getUserByName(nameSpinner.getSelectedItem().toString());
                if (currentUser.getPass().equals(accountPass.getText().toString())){
                    SingletonManagerUsers.setCurrentUser(currentUser);
                    showDialog(DialogType.CHOICE_ACTIVITY);
                    return;
                }
                Toast.makeText(AccountActivity.this, getResources().getString(R.string.warning_wrong_name_pass), Toast.LENGTH_SHORT).show();
                nameSpinner.setSelection(0);
                accountPass.setText("");
        }
    };




    View.OnClickListener onClickSetPass = new View.OnClickListener(){
        @Override
        public void onClick(View view){

        }
    };








}
