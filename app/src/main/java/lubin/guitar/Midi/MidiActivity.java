package lubin.guitar.Midi;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    }

    @Override
    public void onClick(View view) {
        if (view == openMidiBtn) {
            showDialog();

        }
        if (view == playMidiBtn) {
            playMidiSong();
        }
        if (view == stopMidiBtn) {
            stopMidiSong();
        }

    }

    protected void showDialog() {
        FileDialog fileDialog = new FileDialog(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), DialogType.FILE_DIALOG_NORMAL);
        fileDialog.createFileDialog("Vyberte nahraný midi soubor");
        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
            public void fileSelected(File file) {
                openMidiSong(file);
            }
        });
        fileDialog.showDialog();
    }

    protected void openMidiSong (File file) {
        midiSong = new MidiSong(file.getAbsolutePath());
        playMidiBtn.setEnabled(true);
        labelSongName.setText(file.getName());
        midiSong.listeningMidiEvents();
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
