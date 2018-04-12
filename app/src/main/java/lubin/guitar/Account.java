package lubin.guitar;

import android.content.Context;
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
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lubin.guitar.R;

public class Account extends AppCompatActivity {


    Button btnAcc;
    ImageView img;
    EditText accountPass;
    Spinner nameSpinner;
    User [] users;
    SharedPreferences settings;
    File fileUser;




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



        if (!fileUser.exists()){ //vytvoreni defaultnich uzivatelu
            FileInOut.setUsersToXML(this, FileInOut.createDefaultUsersForXML());
        }

        users = FileInOut.getUsersFromXML(fileUser); //nacteni uzivatelu

        String[] arraySpinner = FileInOut.getNameOfUsers(users);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSpinner.setAdapter(adapter);



        settings = PreferenceManager.getDefaultSharedPreferences(this);

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
            User beforeUser = new User(settings.getString("name_user", ""), Integer.parseInt(settings.getString("value_user", "0")), "pass", settings.getString("list_songs", "Pro Elisku"), settings.getString("list_instruments", "a1.wav"), settings.getBoolean("stop_before_tone", false) );


            for (User user : users) {

                if (beforeUser.getName().equals(user.getName()) && beforeUser.getValue() > user.getValue()) {

                    FileInOut.applyChangeUserToXML(view.getContext(), fileUser, beforeUser);
                    users = FileInOut.getUsersFromXML(fileUser); //nacteni uzivatelu
                }
            }


          for (User user : users){


                if (FileInOut.nameWithoutDiacritic(nameSpinner.getSelectedItem().toString().toLowerCase()).equals(FileInOut.nameWithoutDiacritic(user.getName().toLowerCase())) && accountPass.getText().toString().equals(user.getPass())){

                    settings.edit().putString("value_user", Integer.toString(user.getValue())).apply();
                    settings.edit().putString("name_user", user.getName()).apply();
                    settings.edit().putString("list_instruments", user.getChoiceInstrumentName()).apply();
                    settings.edit().putString("list_songs", user.getChoiceSongName()).apply();

                    Intent i = new Intent(Account.this, TrySong.class);
                    startActivity(i);
                    return;

                }

            }


                Toast.makeText(Account.this, "Špatné jméno nebo heslo", Toast.LENGTH_SHORT).show();
                nameSpinner.setSelection(0);
                accountPass.setText("");

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
