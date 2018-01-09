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



    File[] listFiles; //seznam vsech souborů xml nahranych do Songs
    ArrayList<Song> listSongs = new ArrayList<>(); //seznam vsech songu nahranych do songs
    ArrayList<String> nameSongs = new ArrayList<>(); //seznam jmen vsech songu nahranych do songs



    ArrayList<String> nameInstruments = new ArrayList<>(); //seznam jmen vsech instrumentu


    private Song song = new Song("Ovcaci, ctveraci");

    Context context;


    public Songs(){

    }




    public Songs (Context context){

        this.context = context;


        fillSongs();

    }


    private void fillSongs(){

        try{
            File dirSongs = new File (this.context.getFilesDir()+"/Songs/");
            listFiles = dirSongs.listFiles();
            Song thissong = new Song();
            listSongs = new ArrayList<>();
            nameSongs = new ArrayList<>();
            nameInstruments = new ArrayList<>();

            File dirInstruments = new File (this.context.getFilesDir()+"/Instruments/");
            File [] listInstruments = dirInstruments.listFiles();


            for (File file : listFiles){
                thissong = getSongFromXML(file);
                listSongs.add(thissong);
                nameSongs.add(thissong.getNameOfSong());

            }


            for (File file : listInstruments){

                nameInstruments.add(file.getName());

            }

        }catch (Exception e){
            e.getMessage();
        }

    }

    public ArrayList<String> getSongsName(){

        fillSongs();

        return nameSongs;

    }

    public int getNumberInstrument(String name){

        //fillSongs();

        int index = nameInstruments.indexOf(name) + 1;

        return index;

    }



    public Song callByName(String name) {

        fillSongs();

        song = listSongs.get(nameSongs.indexOf(name));


        return song;
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


    public Song getSongFromXML(File XML){

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


    public boolean setSongToXML(Context context, Song song){

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

    public File[] getListSongs() {
        return listFiles;
    }

    public ArrayList<String> getNameInstruments() {
        return nameInstruments;
    }
}
