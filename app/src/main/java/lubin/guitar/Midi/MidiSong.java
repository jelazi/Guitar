package lubin.guitar.Midi;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.media.JetPlayer;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.examples.EventPrinter;
import com.leff.midi.util.MetronomeTick;
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lubin.guitar.Files.FileManager;
import lubin.guitar.R;
import lubin.guitar.Song.Song;
import lubin.guitar.Song.Tone;

public class MidiSong {

    private MidiFile midi;
    private String pathFile;
    MediaPlayer mediaPlayer;
    List<MidiTrack> midiTrackList;
    MidiTrack songTrack;
    int resolution;
    Song song;
    String songName;
    File fileSong;


    public MidiSong (String pathFile) {
        this.pathFile = pathFile;
        File input = new File(pathFile);
        fileSong = input;
        songName = input.getName();
        try {
            this.midi = new MidiFile(input);
            midiTrackList = midi.getTracks();
            songTrack = this.getSongTrackFromList();
            resolution = midi.getResolution();
            Log.d("Debug", "resolution: " + resolution);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error init MidiSong: ", e.toString());
        }
    }

    protected MidiTrack getSongTrackFromList () {
        if (midiTrackList == null || midiTrackList.size() == 0) {
            Log.e("Error", "Miditrack is null or empty");
            return null;
        }
        MidiTrack track;
        for (MidiTrack midiTrack : midiTrackList) {
            for (MidiEvent midiEvent : midiTrack.getEvents()) {
                if (midiEvent.getClass() == NoteOn.class) {
                    return midiTrack;
                }
            }
        }
        Log.e("Error", "Miditrack not contains any noteon");
        return null;
    }

    public MidiFile getMidi() {
        return midi;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void playMidi (Context context, final MidiActivity.HandlerStopMediaPlayer handlerStopMediaplayer) {
        if (midi == null) {
            Log.e("Error", "midi is not download");
            return;
        }

        mediaPlayer = MediaPlayer.create(context, Uri.parse(pathFile));
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                handlerStopMediaplayer.sendMessage(new Message());
            }
        });
        mediaPlayer.start();
    }

    public void stopMidi () {
        mediaPlayer.stop();
    }


    public void readMidi(MidiActivity.HandlerWriteMidiEnabled handlerWriteMidiEnabled) {
        if (songTrack == null || songTrack.getSize() == 0) {
            Log.e("Error", "songtrack is null or empty");
            return;
        }
        song = new Song();
        long startTone = -1;
        long stopTone = -1;
        long lenghtTone = -1;
        int value = -1;
        Tone tone = new Tone("", 0);
        long lastTime = 0;

        long getEventinTicks = songTrack.getLengthInTicks();
        Log.d("Debug", pathFile);
        Log.d("Debug:", "resolution" + midi.getResolution());
        for (MidiEvent midiEvent : songTrack.getEvents()) {
            if (midiEvent.getClass() == NoteOn.class) {
                NoteOn noteOn = (NoteOn)midiEvent;
                int noteValue = noteOn.getNoteValue();
                int noteVelocity = noteOn.getVelocity();

                if (noteVelocity == 0) {
                    if (value == noteValue) {
                        stopTone = noteOn.getTick();
                        lenghtTone = stopTone - startTone;
                        startTone = -1;
                        lastTime = stopTone;
                        stopTone = -1;
                        tone.lenghtTone = (int)noteOn.getDelta();
                        tone.nameTone = getNameToneByValue(value);
                        song.add(new Tone(tone));

                        Log.d("Debug:", value + ", " + lenghtTone);


                    } else {
                        Log.d("Debug", "New tone before ending old tone");
                    }
                } else {
                    tone = new Tone("", 0);

                    startTone = noteOn.getTick();
                    if (lastTime < startTone && (startTone - lastTime) > 30 ) {
                        Tone silent = new Tone ("silent", (int)(startTone - lastTime));
                        song.add(silent);
                    }
                    value = noteValue;
                }
            }
        }
        Log.d("Debug", pathFile);
        if (song.getTones() != null && song.getTones().size() > 0) {
            handlerWriteMidiEnabled.handleMessage(new Message());
        }
    }


    public void writeMidi (Context context, String songName, String authorName) {
        song.setNameOfSong(songName);
        song.setAuthorOfSong(authorName);
        if (song.getNameOfSong().isEmpty()) {
            song.setNameOfSong(fileSong.getName());
        }
        FileManager.setSongToXML(context, song);
    }



    private String getNameToneByValue (int value) {
        String name = "silent";
        switch (value) {
            case 52 : {
                name = "E2";
                break;
            }
            case 53 : {
                name = "F2";
                break;
            }
            case 54 : {
                name = "Fis2";
                break;
            }
            case 55 : {
                name = "G2";
                break;
            }
            case 56 : {
                name = "Gis2";
                break;
            }
            case 57 : {
                name = "A2";
                break;
            }
            case 58 : {
                name = "Ais2";
                break;
            }
            case 59 : {
                name = "B2";
                break;
            }
            case 60 : {
                name = "C3";
                break;
            }
            case 61 : {
                name = "Cis3";
                break;
            }
            case 62 : {
                name = "D3";
                break;
            }
            case 63 : {
                name = "Dis3";
                break;
            }
            case 64 : {
                name = "E3";
                break;
            }
            case 65 : {
                name = "F3";
                break;
            }
            case 66 : {
                name = "Fis3";
                break;
            }
            case 67 : {
                name = "G3";
                break;
            }
            case 68 : {
                name = "Gis3";
                break;
            }
            case 69 : {
                name = "A3";
                break;
            }
            case 70 : {
                name = "Ais3";
                break;
            }
            case 71 : {
                name = "B3";
                break;
            }
            case 72 : {
                name = "C4";
                break;
            }
            case 73 : {
                name = "Cis4";
                break;
            }
            case 74 : {
                name = "D4";
                break;
            }
            case 75 : {
                name = "Dis4";
                break;
            }
            case 76 : {
                name = "E4";
                break;
            }
            case 77 : {
                name = "F4";
                break;
            }
            case 78 : {
                name = "Fis4";
                break;
            }
            case 79 : {
                name = "G4";
                break;
            }
            case 80 : {
                name = "Gis4";
                break;
            }
            default: {
                name = "silent";
                break;
            }

        }
        return name;
    }

    public void listeningMidiEvents () {
        // Create a new MidiProcessor:
        MidiProcessor processor = new MidiProcessor(midi);

// Register for the events you're interested in:
        EventPrinter ep = new EventPrinter("Individual Listener");
        processor.registerEventListener(ep, Tempo.class);
        processor.registerEventListener(ep, NoteOn.class);
        processor.registerEventListener(ep, MetronomeTick.class);

// or listen for all events:
       /* EventPrinter ep2 = new EventPrinter("Listener For All");
        processor.registerEventListener(ep2, MidiEvent.class);*/

// Start the processor:
        processor.start();
    }

    public class EventPrinter implements MidiEventListener
    {
        private String mLabel;

        public EventPrinter(String label)
        {
            mLabel = label;
        }

        @Override
        public void onStart(boolean fromBeginning)
        {
            if(fromBeginning)
            {
                Log.d( "Debug", mLabel + " Started!");
            }
            else
            {
                Log.d( "Debug",mLabel + " resumed");
            }
        }

        @Override
        public void onEvent(MidiEvent event, long ms)
        {
            Log.d( "Debug",mLabel + " received event: " + event);
        }

        @Override
        public void onStop(boolean finished)
        {
            if(finished)
            {
                Log.d( "Debug",mLabel + " Finished!");
            }
            else
            {
                Log.d( "Debug",mLabel + " paused");
            }
        }
    }

}
