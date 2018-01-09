package lubin.guitar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Settings extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    private TextView selection1;
    private TextView selection2;
    private TextView selectionInstr;
    private ArrayList<String> songName = new ArrayList<>();
    private static final String[] action = {"Přehraj píseň", "Vyzkoušej píseň", "Hraj akordy"};
    private ArrayList<String> instruments = new ArrayList<>();
    Button ok;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_settings);

        Songs songs = new Songs(this);

        songName = songs.getSongsName();
        instruments = songs.getNameInstruments();



        selection1 = (TextView) findViewById(R.id.selection);
        selection2 = (TextView) findViewById(R.id.select_action);
        selectionInstr = (TextView) findViewById(R.id.select_instrument);

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner_action);
        Spinner spinInstr = (Spinner) findViewById(R.id.spinner_instrument);

        spin.setOnItemSelectedListener(this);
        spin2.setOnItemSelectedListener(this);
        spinInstr.setOnItemSelectedListener(this);
        this.setTitle("Nastavení");



        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                songName);

        ArrayAdapter<String> bb = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                action);

        ArrayAdapter<String> instr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, instruments);

        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        bb.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(bb);

        instr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinInstr.setAdapter(instr);

        ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(changeActivityListener);





    }


    public void onItemSelected(AdapterView<?> parent,
                               View v, int position, long id) {
        if (parent.getId() == R.id.spinner)
        {
            selection1.setText(songName.get(position));
        }

            if (parent.getId() == R.id.spinner_action)
            {
                selection2.setText(action[position]);

            }

            if (parent.getId() == R.id.spinner_instrument)
            {
                selectionInstr.setText(instruments.get(position));
            }



    }



    public void onNothingSelected(AdapterView<?> parent) {
        selection1.setText("");
        selection2.setText("");
        selectionInstr.setText("");
    }


    View.OnClickListener changeActivityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {





            if (selection2.getText().equals("Vyzkoušej píseň")) {
                Intent i = new Intent(Settings.this, TrySong.class);
                i.putExtra("oldName", selection1.getText());
                i.putExtra("instrument", selectionInstr.getText());
                startActivity(i);
            }
            else
            {
                if (selection2.getText().equals("Přehraj píseň")) {
                    Intent i = new Intent(Settings.this, PreviewSong.class);
                    i.putExtra("oldName", selection1.getText());
                    i.putExtra("instrument", selectionInstr.getText());
                    startActivity(i);
                }
                else
                {
                    if (selection2.getText().equals("Hraj akordy")) {
                        Intent i = new Intent(Settings.this, PlayAcord.class);
                        i.putExtra("instrument", selectionInstr.getText());
                        startActivity(i);
                    }
                }


            }



        }
    };
}
