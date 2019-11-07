package lubin.guitar.Midi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import lubin.guitar.Account.AccountActivity;
import lubin.guitar.Files.DialogType;
import lubin.guitar.Files.FileDialog;
import lubin.guitar.GuitarActivity.PreviewSongActivity;
import lubin.guitar.R;
import lubin.guitar.Users.SingletonManagerUsers;

public class MidiActivity extends AppCompatActivity implements View.OnClickListener {

    TextView labelSongName;
    Button openMidiBtn;
    Button playMidiBtn;
    Button stopMidiBtn;
    Button readMidiBtn;
    Button writeMidiBtn;
    Button testSongBtn;
    MidiSong midiSong;
    HandlerStopMediaPlayer handlerStopMediaplayer;
    HandlerWriteMidiEnabled handlerWriteMidiEnabled;
    HandlerWriteOk handlerWriteOk;

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
        testSongBtn = findViewById(R.id.test_song);
        testSongBtn.setOnClickListener(this);
        testSongBtn.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        if (view == openMidiBtn) {
            showDialog(DialogType.FILE_DIALOG_MIDI);

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
        if (view == testSongBtn) {
            testSong();
        }
    }

    protected void showDialog(DialogType dialogType) {
        if (dialogType == DialogType.FILE_DIALOG_MIDI) {
            FileDialog fileDialog = new FileDialog(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "mid", "midi", DialogType.FILE_DIALOG_MIDI);
            fileDialog.createFileDialog("Vyberte midi soubor");
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
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            final TextView textView1 = new TextView(this);
            textView1.setText("Jméno písně:");
            layout.addView(textView1);
            final EditText edittext1 = new EditText(this);
            edittext1.setSelectAllOnFocus(true);
            layout.addView(edittext1);
            edittext1.requestFocus();
            final TextView textView2 = new TextView(this);
            textView2.setText("Jméno autora:");
            layout.addView(textView2);
            final EditText edittext2 = new EditText(this);
            layout.addView(edittext2);
            builder.setView(layout);
            builder.setPositiveButton("Uložit", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (edittext1.getText().toString().isEmpty()) {
                        Toast.makeText(MidiActivity.this, "Pole jména písně nesmí zůstat prázdné", Toast.LENGTH_SHORT).show();
                        showDialog(DialogType.WRITE_SONG);
                    } else {
                        handlerWriteOk = new HandlerWriteOk();
                        midiSong.writeMidi(MidiActivity.this, edittext1.getText().toString(), "", handlerWriteOk);

                    }
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

    protected  void testSong () {
        SingletonManagerUsers.setCurrentUser(SingletonManagerUsers.getAdminUser());
        Intent i = new Intent(this, PreviewSongActivity.class);
        i.putExtra("is_test", true);
        i.putExtra("name_test_song", labelSongName.getText().toString());
        SingletonManagerUsers.setCurrentSong(midiSong.song);
        startActivity(i);
    }

    protected void openMidiSong (File file) {
        midiSong = new MidiSong(file.getAbsolutePath());
        playMidiBtn.setEnabled(true);
        readMidiBtn.setEnabled(true);
        stopMidiBtn.setEnabled(false);
        writeMidiBtn.setEnabled(false);
        testSongBtn.setEnabled(false);
        labelSongName.setText(file.getName());
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
        handlerWriteMidiEnabled = new HandlerWriteMidiEnabled();
        midiSong.readMidi(handlerWriteMidiEnabled);
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

    class HandlerWriteMidiEnabled implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            writeMidiBtn.setEnabled(true);
            testSongBtn.setEnabled(true);
            return true;
        }
    }

    class HandlerWriteOk implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            if (bundle.getString("message").equals("ok")) {
                Toast.makeText(MidiActivity.this, "Píseň " +  labelSongName.getText() + " byla zkopírována", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MidiActivity.this, "Chyba: píseň " +  labelSongName.getText() + " nebyla zkopírována", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
}
