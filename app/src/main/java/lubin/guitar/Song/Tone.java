package lubin.guitar.Song;


import android.os.Parcel;
import android.os.Parcelable;

public class Tone {

    public int lenghtTone;
    public String nameTone;

    public Tone(String nameTone, int lenghtTone) {
        this.lenghtTone = lenghtTone;
        this.nameTone = nameTone;
    }

    public Tone(Tone otherTone) {
        this.lenghtTone = otherTone.lenghtTone;
        this.nameTone = otherTone.nameTone;
    }
}
