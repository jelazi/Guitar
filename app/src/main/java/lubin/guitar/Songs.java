package lubin.guitar;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Object;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//TODO vytvorit tuto moznost tridy
//trida - pri vytvoreni nahraje vsechny pisne z xml


public class Songs {



    private static File[] listFiles; //seznam vsech souborů xml nahranych do Songs
    private static ArrayList<Song> listSongs = new ArrayList<>(); //seznam vsech songu nahranych do songs
    private static ArrayList<String> nameSongs = new ArrayList<>(); //seznam jmen vsech songu nahranych do songs
    private static ArrayList<String> nameInstruments = new ArrayList<>(); //seznam jmen vsech instrumentu

    private static Song song = new Song("Ovcaci, ctveraci");





    public static void fillSongs(Context context){

        try{
            File dirSongs = new File (context.getFilesDir()+"/Songs/");
            Songs.listFiles = dirSongs.listFiles();
            Song thissong = new Song();
            Songs.listSongs = new ArrayList<>();
            Songs.nameSongs = new ArrayList<>();
            Songs.nameInstruments = new ArrayList<>();

            File dirInstruments = new File (context.getFilesDir()+"/Instruments/");
            File [] listInstruments = dirInstruments.listFiles();


            for (File file : listFiles){
                thissong = Songs.getSongFromXML(file);
                Songs.listSongs.add(thissong);
                Songs.nameSongs.add(thissong.getNameOfSong());
            }


            for (File file : listInstruments){
                Songs.nameInstruments.add(file.getName());
            }

        }catch (Exception e){
            e.getMessage();
        }

    }

    public static ArrayList<String> getSongsName(Context context){

        Songs.fillSongs(context);

        return Songs.nameSongs;
    }

    public static int getNumberInstrument(String name){

        //fillSongs();

        int index = Songs.nameInstruments.indexOf(name) + 1;

        return index;
    }



    public static Song callByName(Context context, String name) {

        fillSongs(context);

        Songs.song = Songs.listSongs.get(Songs.nameSongs.indexOf(name));

        return Songs.song;
    }



   /* public void ovcaci(){

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



    public void proElisku(){

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
    }*/


    public static Song getSongFromXML(File XML){

        Song song = new Song();

        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(XML);

            Node root = doc.getDocumentElement();
            root.normalize();

            NodeList SongList = root.getChildNodes();

            NodeList description = SongList.item(1).getChildNodes();

            Node nameOfSong = description.item(1);
            Node nameOfAuthor = description.item(3);

            song.setNameOfSong(nameOfSong.getTextContent());
            song.setAuthorOfSong(nameOfAuthor.getTextContent());

            NodeList tones = SongList.item(3).getChildNodes();

            for (int i = 0; i < tones.getLength(); i++){

                Node tone = tones.item(i);

                if (tone.getNodeType() == Node.ELEMENT_NODE && tone.getNodeName().equals("NameTone")){

                    String nameTone = tone.getChildNodes().item(0).getTextContent();
                    int valueTone = Integer.parseInt(tone.getChildNodes().item(1).getChildNodes().item(0).getTextContent());
                    song.add(new Tone(nameTone, valueTone));
                }
            }
        }
        catch (Exception e)
        {

        }
        return song;
    }


    public static boolean setSongToXML(Context context, Song song){

        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element aboutSong = doc.createElement("Song");

            Element description = doc.createElement("Description");

            Element nameOfSong = doc.createElement("Name_of_Song");
            nameOfSong.appendChild(doc.createTextNode(song.getNameOfSong()));
            description.appendChild(nameOfSong);

            Element nameOfAuthor = doc.createElement("Name_of_Author");
            nameOfAuthor.appendChild(doc.createTextNode(song.getAuthorOfSong()));
            description.appendChild(nameOfAuthor);

            aboutSong.appendChild(description);

            Element tones = doc.createElement("Tones");

            for (Tone tone : song.getTones()){

                Element nameTone = doc.createElement("NameTone");
                nameTone.appendChild(doc.createTextNode(tone.nameTone));

                Element valueTone = doc.createElement("ValueTone");
                valueTone.appendChild(doc.createTextNode(Integer.toString(tone.lenghtTone)));
                nameTone.appendChild(valueTone);

                tones.appendChild(nameTone);
            }

            aboutSong.appendChild(tones);

            doc.appendChild(aboutSong);


            File folder = new File(context.getFilesDir() + "/Songs"); //vytvoreni slozky Songs
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }

            if (success){

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");


                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(context.getFilesDir() + "/Songs/" + song.nameWithoutDiacritic()));
                transformer.transform(source, result);
            }

        }
        catch (Exception e)
        {
            Log.e("Error: ", e.getMessage());
        }
        return true;
    }

    public static File[] getListSongs() {
        return Songs.listFiles;
    }

    public static ArrayList<String> getNameInstruments() {
        return Songs.nameInstruments;
    }

    public static File[] getListFiles() {
        return Songs.listFiles;
    }

    public static void setListFiles(File[] listFiles) {
        Songs.listFiles = listFiles;
    }

    public static void setListSongs(ArrayList<Song> listSongs) {
        Songs.listSongs = listSongs;
    }

    public static ArrayList<String> getNameSongs() {
        return Songs.nameSongs;
    }

    public static void setNameSongs(ArrayList<String> nameSongs) {
        Songs.nameSongs = nameSongs;
    }

    public static void setNameInstruments(ArrayList<String> nameInstruments) {
        Songs.nameInstruments = nameInstruments;
    }

    public static Song getSong() {
        return Songs.song;
    }

    public static void setSong(Song song) {
        Songs.song = song;
    }
}
