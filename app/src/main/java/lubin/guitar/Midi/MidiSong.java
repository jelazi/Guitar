package lubin.guitar.Midi;

import android.content.Context;
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

import lubin.guitar.R;

public class MidiSong {

    private MidiFile midi;
    private String pathFile;
    MediaPlayer mediaPlayer;
    List<MidiTrack> midiTrackList;
    MidiTrack songTrack;

    public MidiSong (String pathFile) {
        this.pathFile = pathFile;
        File input = new File(pathFile);
        try {
            this.midi = new MidiFile(input);
            midiTrackList = midi.getTracks();
            songTrack = this.getSongTrackFromList();

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
