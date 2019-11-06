package lubin.guitar.Midi;

import android.content.DialogInterface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import lubin.guitar.Files.DialogType;
import lubin.guitar.Files.FileDialog;
import lubin.guitar.R;

public class MidiActivity extends AppCompatActivity implements View.OnClickListener {

    TextView labelSongName;
    Button openMidiBtn;
    Button playMidiBtn;
    Button stopMidiBtn;
    Button readMidiBtn;
    Button writeMidiBtn;
    MidiSong midiSong;
    HandlerStopMediaPlayer handlerStopMediaplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midi);

        openMidiBtn = findViewById(R.id.open_midi);
        openMidiBtn.setOnClickListener(this);
        playMidiBtn = findViewById(R.id.play_midi);
        playMidiBtn.setOnClickListener(this);
        playMidiBtn.setEnabled(false);
        stopMidiBtn = findViewById(R.id.stop_midi);
        stopMidiBtn.setOnClickListener(this);
        stopMidiBtn.setEnabled(false);
        labelSongName = findViewById(R.id.label_song);
        readMidiBtn = findViewById(R.id.read_midi);
        readMidiBtn.setOnClickListener(this);
        readMidiBtn.setEnabled(false);
        writeMidiBtn = findViewById(R.id.write_midi);
        writeMidiBtn.setOnClickListener(this);
        writeMidiBtn.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        if (view == openMidiBtn) {
            showDialog(DialogType.FILE_DIALOG_NORMAL);

        }
        if (view == playMidiBtn) {
            playMidiSong();
        }
        if (view == stopMidiBtn) {
            stopMidiSong();
        }
        if (view == writeMidiBtn) {
            writeMidiSong();
        }
        if (view == readMidiBtn) {
            readMidiSong();
        }

    }

    protected void showDialog(DialogType dialogType) {
        if (dialogType == DialogType.FILE_DIALOG_NORMAL) {
            FileDialog fileDialog = new FileDialog(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), DialogType.FILE_DIALOG_NORMAL);
            fileDialog.createFileDialog("Vyberte nahraný midi soubor");
            fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                public void fileSelected(File file) {
                    openMidiSong(file);
                }
            });
            fileDialog.showDialog();
        }
        if (dialogType == DialogType.WRITE_SONG) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Zapsat píseň z midi");
         /*   final TextView textView1 = new TextView(this);
            textView1.setText("Jméno písně:");
            builder.setView(textView1);*/
            final EditText edittext1 = new EditText(this);
            edittext1.setSelectAllOnFocus(true);
            builder.setView(edittext1);
            edittext1.requestFocus();
        /*    final TextView textView2 = new TextView(this);
            textView2.setText("Jméno autora:");
            builder.setView(textView2);
            final EditText edittext2 = new EditText(this);
            builder.setView(edittext2);*/
            builder.setPositiveButton("Uložit", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    midiSong.writeMidi(MidiActivity.this, edittext1.getText().toString(), "");
                }
            });

            builder.setNegativeButton("Zrušit", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

    protected void openMidiSong (File file) {
        midiSong = new MidiSong(file.getAbsolutePath());
        playMidiBtn.setEnabled(true);
        readMidiBtn.setEnabled(true);
        writeMidiBtn.setEnabled(true);
        labelSongName.setText(file.getName());
     //   midiSong.listeningMidiEvents();
    }

    protected void playMidiSong () {
        handlerStopMediaplayer = new HandlerStopMediaPlayer();
        midiSong.playMidi(this, handlerStopMediaplayer);
        playMidiBtn.setEnabled(false);
        stopMidiBtn.setEnabled(true);
    }

    protected void stopMidiSong () {
        midiSong.stopMidi();
        playMidiBtn.setEnabled(true);
        stopMidiBtn.setEnabled(false);
    }

    protected void writeMidiSong () {
        showDialog(DialogType.WRITE_SONG);
    }

    protected void readMidiSong () {
        midiSong.readMidi();
    }


    class HandlerStopMediaPlayer implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            playMidiBtn.setEnabled(true);
            stopMidiBtn.setEnabled(false);
            return true;
        }

        public void sendMessage(Message message) {
            playMidiBtn.setEnabled(true);
            stopMidiBtn.setEnabled(false);
        }
    }


}
