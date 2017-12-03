package lubin.guitar;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import lubin.guitar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class Settings extends Activity
        implements AdapterView.OnItemSelectedListener {
    private TextView selection1;
    private TextView selection2;
    private String[] items1 = {"pro elisku", "bla bla bla", "jeste neco"};
    private static final String[] items2 = {"Hraj píseň", "Přehraj píseň", "Hraj akordy"};
    Button ok;
    Button load;
    Button save;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_settings);
      //  SQLiteDatabase mySongs = Songs.firstopenDB(); //první otevreni ci vytvoreni databaze

        selection1 = (TextView) findViewById(R.id.selection);
        selection2 = (TextView) findViewById(R.id.select_action);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner_action);

        spin.setOnItemSelectedListener(this);
        spin2.setOnItemSelectedListener(this);


        items1[0] = "ahoj";




        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                items1);

        ArrayAdapter<String> bb = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                items2);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        bb.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(bb);

        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(changeActivityListener);

        load = (Button) findViewById(R.id.load);


        save = (Button) findViewById(R.id.save);



    }


    public void onItemSelected(AdapterView<?> parent,
                               View v, int position, long id) {
        selection1.setText(items1[0]);
        selection2.setText(items2[position]);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        selection1.setText("");
        selection2.setText("");
    }


    View.OnClickListener changeActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (selection1.getText().equals("ahoj")){

            }

            if (selection2.getText().equals("Hraj píseň")) {
                Intent i = new Intent(Settings.this, TrySong.class);
                startActivity(i);
            }
            if (selection2.getText().equals("Přehraj píseň")) {
                Intent i = new Intent(Settings.this, PreviewSong.class);
                startActivity(i);
            }
            if (selection2.getText().equals("Hraj akordy")) {
                Intent i = new Intent(Settings.this, PlayAcord.class);
                startActivity(i);
            }


        }
    };
}
