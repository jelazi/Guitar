package lubin.guitar;


import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Songs {

    private static final String NAME_DB = "mySONGDB";





    public static SQLiteDatabase firstopenDB(){

        SQLiteDatabase dbSongs = SQLiteDatabase.openOrCreateDatabase(NAME_DB, null);

        return dbSongs;

    }







//TODO dodelat getSong a saveSong

    public static Song getSongFromDB (String nameSong) {

        Song song = new Song(nameSong);













        return song;

    }


    public static boolean saveSongToDB(Song song){

        SQLiteDatabase dbSongs = firstopenDB();










        return true;
    }






    public static Song getSong1(){

        Song song = new Song("Ovcaci, ctveraci");



        song.add(new Tone("C3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("G3", 1000));
        song.add(new Tone("C3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("G3", 1000));
        song.add(new Tone("E3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("D3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("F3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("E3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("D3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("F3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("C3", 1000));
        song.add(new Tone("C3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("G3", 1000));
        song.add(new Tone("C3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("G3", 1000));
        song.add(new Tone("E3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("D3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("F3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("E3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("D3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("F3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("C3", 1000));
        song.add(new Tone("C3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("G3", 1000));
        song.add(new Tone("C3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("G3", 1000));
        song.add(new Tone("E3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("D3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("F3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("E3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("D3", 250));
        song.add(new Tone("E3", 250));
        song.add(new Tone("F3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("E3", 500));
        song.add(new Tone("D3", 500));
        song.add(new Tone("C3", 1000));


        return song;

    }



    public static Song getSong2(){

        Song song = new Song("Pro Elisku");



        song.add(new Tone("E4", 250));
        song.add(new Tone("Dis4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("Dis4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("B3", 250));
        song.add(new Tone("D4", 250));
        song.add(new Tone("C4", 250));
        song.add(new Tone("A3", 500));

        song.add(new Tone("E3", 250));
        song.add(new Tone("Gis3", 250));
        song.add(new Tone("A3", 250));
        song.add(new Tone("B3", 500));
        song.add(new Tone("E3", 250));
        song.add(new Tone("A3", 250));
        song.add(new Tone("B3", 250));
        song.add(new Tone("C4", 500));

        song.add(new Tone("E4", 250));
        song.add(new Tone("Dis4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("Dis4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("B3", 250));
        song.add(new Tone("D4", 250));
        song.add(new Tone("C4", 250));
        song.add(new Tone("A3", 500));

        song.add(new Tone("E3", 250));
        song.add(new Tone("Gis3", 250));
        song.add(new Tone("A3", 250));
        song.add(new Tone("B3", 500));

        song.add(new Tone("B3", 250));
        song.add(new Tone("C4", 250));
        song.add(new Tone("B3", 250));
        song.add(new Tone("A3", 500));

        song.add(new Tone("B3", 250));
        song.add(new Tone("C4", 250));
        song.add(new Tone("D4", 250));
        song.add(new Tone("E4", 500));

        song.add(new Tone("E4", 250));
        song.add(new Tone("F4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("D4", 500));

        song.add(new Tone("D4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("D4", 250));
        song.add(new Tone("C4", 500));

        song.add(new Tone("C4", 250));
        song.add(new Tone("D4", 250));
        song.add(new Tone("C4", 250));
        song.add(new Tone("B3", 500));
        song.add(new Tone("E4", 500));

        song.add(new Tone("E4", 250));
        song.add(new Tone("Dis4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("Dis4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("B3", 250));
        song.add(new Tone("D4", 250));
        song.add(new Tone("C4", 250));
        song.add(new Tone("A3", 500));

        song.add(new Tone("E3", 250));
        song.add(new Tone("Gis3", 250));
        song.add(new Tone("A3", 250));
        song.add(new Tone("B3", 500));
        song.add(new Tone("E3", 250));
        song.add(new Tone("A3", 250));
        song.add(new Tone("B3", 250));
        song.add(new Tone("C4", 500));

        song.add(new Tone("E4", 250));
        song.add(new Tone("Dis4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("Dis4", 250));
        song.add(new Tone("E4", 250));
        song.add(new Tone("B3", 250));
        song.add(new Tone("D4", 250));
        song.add(new Tone("C4", 250));
        song.add(new Tone("A3", 500));

        song.add(new Tone("E3", 250));
        song.add(new Tone("Gis3", 250));
        song.add(new Tone("A3", 250));
        song.add(new Tone("B3", 500));

        song.add(new Tone("B3", 250));
        song.add(new Tone("C4", 250));
        song.add(new Tone("B3", 250));
        song.add(new Tone("A3", 1000));



        return song;

    }


}
