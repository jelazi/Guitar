package lubin.guitar.Song;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/// trida jedna pisen

public class Song  {
    private String nameOfSong = "Unknown";
    private String authorOfSong = "Unknown";
    private ArrayList<Tone> tones = new ArrayList<>();

    public Song() { }

    public Song(String nameOfSong) {
        this.nameOfSong = nameOfSong;
    }

    public Song(String nameOfSong, String authorOfSong) {
        this.nameOfSong = nameOfSong;
        this.authorOfSong = authorOfSong;
    }

    public void add (Tone tone) { //pridani tonu
        tones.add(tone);
    }


    public String getNameOfSong() {
        return nameOfSong;
    }

    public void setNameOfSong(String nameOfSong) {
        this.nameOfSong = nameOfSong;
    }

    public String getAuthorOfSong() {
        return authorOfSong;
    }

    public void setAuthorOfSong(String authorOfSong) {
        this.authorOfSong = authorOfSong;

    }

    public String nameWithoutDiacritic (){ //vrati String bez diakritiky z názvu písne s diakritikou
        String nameOut = "";
        String nameIn = getNameOfSong();
        String withdiacritic = "áéěíóúůÁÉĚÍÓÚŮščřžťŠČŘŽŤ";
        String withoutdiacritic = "aeeiouuAEEIOUUscrztSCRZT";

        int j = 0;
        for (char i : nameIn.toCharArray()) {
            if (withdiacritic.contains(String.valueOf(i))){
                nameOut += withoutdiacritic.charAt(j);
            }
            else {
                if (i == ' ') {
                    nameOut += '_';
                }
                else {
                    nameOut += i;
                }
            }
            j++;
        }
        return nameOut;
    }

    public ArrayList<Tone> getTones() {
        return tones;
    }



}
