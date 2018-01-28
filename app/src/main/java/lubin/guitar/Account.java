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
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    EditText accountName;
    EditText accountPass;
    User [] users;
    SharedPreferences settings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnAcc = (Button)findViewById(R.id.btnAccount);
        btnAcc.setOnClickListener(openAccountListener);
        img = (ImageView)findViewById(R.id.AccGuitarist);
        android.view.animation.Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        img.startAnimation(animation1);
        accountName = (EditText)findViewById(R.id.AccountName);
        accountPass = (EditText)findViewById(R.id.AccountPass);
        accountName.setOnClickListener(onClickSetName);
        accountPass.setOnClickListener(onClickSetPass);
        users = FileInOut.getUsersFromXML();
        settings = PreferenceManager.getDefaultSharedPreferences(this);


    }


    View.OnClickListener openAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            for (User user : users){


                if (accountName.getText().toString().equals(user.getName()) && accountPass.getText().toString().equals(user.getPass())){
                    settings.edit().putString("value_user", Integer.toString(user.getValue())).apply();
                    settings.edit().putString("name_user", user.getName()).apply();
                    settings.edit().putString("list_instruments", user.getChoiceInstrumentName()).apply();
                    settings.edit().putString("list_songs", user.getChoiceSongName()).apply();


                    Intent i = new Intent(Account.this, TrySong.class);
                    startActivity(i);
                    return;

                }

            }


//                new AlertDialog.Builder(Account.this)
//                        .setTitle("Chyba")
//                        .setMessage("Špatné jméno nebo heslo")
//                        .setNeutralButton("Znovu", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();


                Toast.makeText(Account.this, "Špatné jméno nebo heslo", Toast.LENGTH_SHORT).show();
                accountName.setText("");
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
