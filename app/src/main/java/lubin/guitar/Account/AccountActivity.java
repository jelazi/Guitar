package lubin.guitar.Account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

import lubin.guitar.Files.FileInOut;
import lubin.guitar.R;
import lubin.guitar.GuitarActivity.TrySongActivity;
import lubin.guitar.Users.SingletonManagerUsers;
import lubin.guitar.Users.User;

public class AccountActivity extends AppCompatActivity {


    Button btnAcc;
    ImageView img;
    EditText accountPass;
    Spinner nameSpinner;
    List<String>nameUsers;
    SharedPreferences settings;
    File fileUser;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnAcc = (Button)findViewById(R.id.btnAccount);
        btnAcc.setOnClickListener(openAccountListener);
        img = (ImageView)findViewById(R.id.AccGuitarist);
        android.view.animation.Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img.startAnimation(animation1);
        accountPass = (EditText)findViewById(R.id.AccountPass);
        nameSpinner = (Spinner) findViewById(R.id.Spinner01);
        accountPass.setOnClickListener(onClickSetPass);

        fileUser = new File(getFilesDir()+"/users.xml");

       // if (!fileUser.exists()){ //vytvoreni defaultnich uzivatelu
            FileInOut.setUsersToXML(this, FileInOut.createDefaultUsersForXML());

       // }

        nameUsers = SingletonManagerUsers.getListNamesUsers(); //nacteni uzivatelu
        String[] arrayUsers = new String[nameUsers.size()];
        nameUsers.toArray(arrayUsers);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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


    View.OnClickListener openAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            currentUser = SingletonManagerUsers.getUserByName(nameSpinner.getSelectedItem().toString());

                if (currentUser.getPass().equals(accountPass.getText().toString())){

                    settings.edit().putString("value_user", Integer.toString(currentUser.getCoins())).apply();
                    settings.edit().putString("name_user", currentUser.getName()).apply();
                    settings.edit().putString("list_instruments", currentUser.getChoiceInstrumentName()).apply();
                    settings.edit().putString("list_songs", currentUser.getChoiceSongName()).apply();

                    Intent i = new Intent(AccountActivity.this, TrySongActivity.class);
                    startActivity(i);
                    return;

                }
                Toast.makeText(AccountActivity.this, "Špatné jméno nebo heslo", Toast.LENGTH_SHORT).show();
                nameSpinner.setSelection(0);
                accountPass.setText("");
        }
    };




    View.OnClickListener onClickSetPass = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            //accountPass.setText("");

        }
    };









}
