package lubin.guitar;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Object;
import java.util.ArrayList;

import static lubin.guitar.R.id.parent;

public class Songs {

    private static final String NAME_DB = "mySONGDB";

    private Song song = new Song("Ovcaci, ctveraci");





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




    public Song callByName(String funcName) {
        try {
            Method method = getClass().getDeclaredMethod(funcName);
            method.invoke(this, new Object[] {});
            return song;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return song;
    }

    public Song getSong(){

        getSong1();

        return song;
    }



    public void getSong1(){

        song.setNameOfSong("Ovcaci, ctveraci");



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




    }



    public void getSong2(){

        song.setNameOfSong("Pro Elisku");



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





    }


}
