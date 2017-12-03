package lubin.guitar;


import java.util.ArrayList;

/// trida jedna pisen

public class Song {




    private String nameOfSong = "Unknown";
    private String authorOfSong = "Unknown";



    private ArrayList<Tone> tones = new ArrayList<>();


    public Song(){



    }


    public Song(String nameOfSong){

        this.nameOfSong = nameOfSong;

    }





    public Song(String nameOfSong, String authorOfSong){

        this.nameOfSong = nameOfSong;
        this.authorOfSong = authorOfSong;

    }


    public void add (Tone tone){
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

    public ArrayList<Tone> getTones() {
        return tones;
    }

}
