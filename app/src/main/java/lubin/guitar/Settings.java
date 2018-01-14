package lubin.guitar;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Settings extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    private TextView selection1;
    private TextView selection2;
    private TextView selectionInstr;
    private ArrayList<String> songNames = new ArrayList<>();
    private static final String[] action = {"Přehraj píseň", "Vyzkoušej píseň", "Hraj akordy"};
    private ArrayList<String> instruments = new ArrayList<>();
    Button ok;
    private Button record;
    private Button stop;
    private Button write;
    Record recorder;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_settings);
        Globals.setFirstStart(false);

        songNames = Songs.getSongsName(this);
        instruments = Songs.getNameInstruments();


        record = (Button) findViewById(R.id.record);
        stop = (Button) findViewById(R.id.stop);
        write = (Button) findViewById(R.id.write);


        record.setOnClickListener(recordListener);
        stop.setOnClickListener(stopListener);
        write.setOnClickListener(writeListener);



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
                songNames);

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



        recorder = new Record(this, getFilesDir() + "/Instruments/" + getDate() + ".wav");


    }


    private String getDate(){

        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss");
        String time = dfTime.format(Calendar.getInstance().getTime());
        return time;
    }


    public void onItemSelected(AdapterView<?> parent,
                               View v, int position, long id) {
        if (parent.getId() == R.id.spinner)
        {
            selection1.setText(songNames.get(position));
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

            Globals.setInstrument(selectionInstr.getText().toString());
            Globals.setSongName(selection1.getText().toString());
            Songs.setSong(Songs.callByName(getApplicationContext(), Globals.getSongName()));



            if (selection2.getText().equals("Vyzkoušej píseň")) {
                Intent i = new Intent(Settings.this, TrySong.class);
                startActivity(i);
            }
            else
            {
                if (selection2.getText().equals("Přehraj píseň")) {
                    Intent i = new Intent(Settings.this, PreviewSong.class);
                    startActivity(i);
                }
                else
                {
                    if (selection2.getText().equals("Hraj akordy")) {
                        Intent i = new Intent(Settings.this, PlayAcord.class);
                        startActivity(i);
                    }
                }


            }



        }
    };

    View.OnClickListener recordListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            recorder.startRecording();






        }
    };

    View.OnClickListener stopListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            recorder.stopRecording();



        }
    };

    View.OnClickListener writeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent();

            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 3);


        }
    };





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        InputStream input;
        String realPath = "";


        switch (requestCode) {

            case 3:

                if (resultCode == RESULT_OK) {

                    File file = new File(data.getData().getPath());
                    boolean is = file.exists();
                    String fileName = FileInOut.queryName(this.getContentResolver(), data.getData());  //jméno souboru
                    File outputDir = new File(getFilesDir() + "/" + fileName);  //zkopírování

                    try {
                        FileInOut.copyFileByUri(this, data.getData(), outputDir);
                        realPath = FileInOut.queryName(this.getContentResolver(), data.getData());



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //String PathHolder = data.getData().getPath();

                    Toast.makeText(this, realPath, Toast.LENGTH_LONG).show();

                }
                break;

        }
    }




}
