package lubin.guitar.Files;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lubin.guitar.R;
import lubin.guitar.Song.Song;
import lubin.guitar.Song.Tone;

public class FileManager {

    private static File[] fieldFilesSongs; //seznam vsech souborů xml nahranych do Songs
    private static File[] fieldFileInstruments;
    private static File[] fieldFileFrets;
    private static File[] fieldFileBackgrounds;
    private static File[] fieldFileStrings;
    private static File[] fieldFileMidi;
    private static ArrayList<Song> listSongs = new ArrayList<>(); //seznam vsech songu nahranych do songs
    private static ArrayList<String> nameSongs = new ArrayList<>(); //seznam jmen vsech songu nahranych do songs
    private static ArrayList<String> nameInstruments = new ArrayList<>(); //seznam jmen vsech instrumentu
    private static ArrayList<String> nameFrets;
    private static ArrayList<String> nameBackgrounds;
    private static ArrayList<String> nameStrings;
    private static ArrayList<String> nameMidi;

    private static Song song;
    private static File dirSongs;
    private static File dirInstruments;
    private static File dirFrets;
    private static File dirBackgrounds;
    private static File dirStrings;
    private static File dirMidi;

    private static Context context;
    private static Song ThisSong;

    public static void loadData(Context context){
        try {
            FileManager.fieldFilesSongs = dirSongs.listFiles();
            FileManager.fieldFileInstruments = dirInstruments.listFiles();
            FileManager.fieldFileFrets = dirFrets.listFiles();
            FileManager.fieldFileBackgrounds = dirBackgrounds.listFiles();
            FileManager.fieldFileStrings = dirStrings.listFiles();
            FileManager.fieldFileMidi = dirMidi.listFiles();

            FileManager.copyDataFromResource();

            for (File file : fieldFilesSongs){
                ThisSong = FileManager.getSongFromXML(file);
                FileManager.listSongs.add(ThisSong);
                if (!FileManager.nameSongs.contains(ThisSong.getNameOfSong())) {
                    FileManager.nameSongs.add(ThisSong.getNameOfSong());
                }
            }
            for (File file : fieldFileInstruments){
                if (!FileManager.nameInstruments.contains(file.getName())) {
                    FileManager.nameInstruments.add(file.getName());
                }
            }
            for (File file : fieldFileFrets){
                if (!FileManager.nameFrets.contains(file.getName())) {
                    FileManager.nameFrets.add(file.getName());
                }
            }
            for (File file : fieldFileBackgrounds){
                if (!FileManager.nameBackgrounds.contains(file.getName())) {
                    FileManager.nameBackgrounds.add(file.getName());
                }
            }
            for (File file : fieldFileStrings){
                if (!FileManager.nameStrings.contains(file.getName())) {
                    FileManager.nameStrings.add(file.getName());
                }
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    public static void init (Context context) {
        FileManager.context = context;
        FileManager.listSongs = new ArrayList<>();
        FileManager.nameSongs = new ArrayList<>();
        FileManager.nameInstruments = new ArrayList<>();
        FileManager.nameBackgrounds = new ArrayList<>();
        FileManager.nameFrets = new ArrayList<>();
        FileManager.nameStrings = new ArrayList<>();

        ThisSong = new Song();
        song =  new Song("Ovcaci, ctveraci");
        dirSongs = new File (context.getFilesDir()+"/Songs/");
        if (!dirSongs.exists()) {
            if (!dirSongs.mkdirs()) {
                Log.e("Error :: ", "Problem creating dirSongs");
            }
        }
        dirInstruments = new File (context.getFilesDir()+"/Instruments/");
        if (!dirInstruments.exists()) {
            if (!dirInstruments.mkdirs()) {
                Log.e("Error :: ", "Problem creating dirInstruments");
            }
        }
        dirFrets = new File (context.getFilesDir()+"/Frets/");
        if (!dirFrets.exists()) {
            if (!dirFrets.mkdirs()) {
                Log.e("Error :: ", "Problem creating dirFrets");
            }
        }
        dirBackgrounds = new File (context.getFilesDir()+"/Backgrounds/");
        if (!dirBackgrounds.exists()) {
            if (!dirBackgrounds.mkdirs()) {
                Log.e("Error :: ", "Problem creating dirBackgrounds");
            }
        }
        dirStrings = new File (context.getFilesDir()+"/Strings/");
        if (!dirStrings.exists()) {
            if (!dirStrings.mkdirs()) {
                Log.e("Error :: ", "Problem creating dirStrings");
            }
        }
        dirMidi = new File (context.getFilesDir()+"/Midi/");
        if (!dirMidi.exists()) {
            if (!dirMidi.mkdirs()) {
                Log.e("Error :: ", "Problem creating dirMidi");
            }
        }
        loadData(context);
    }

    public static void copyDataFromResource () {
        if (fieldFilesSongs == null || fieldFilesSongs.length == 0) {
            copyFiletoTarget(R.xml.song1, dirSongs.toString(), "/Pro Elisku.xml");
            copyFiletoTarget(R.xml.song2, dirSongs.toString(), "/Ovcaci, ctveraci.xml");
            fieldFilesSongs = dirSongs.listFiles();
        }
        if (fieldFileInstruments == null || fieldFileInstruments.length == 0) {
            copyFiletoTarget(R.raw.a1, dirInstruments.toString(), "/a1.wav");
            copyFiletoTarget(R.raw.s1, dirInstruments.toString(), "/s1.wav");
            copyFiletoTarget(R.raw.s2, dirInstruments.toString(), "/s2.wav");
            copyFiletoTarget(R.raw.s3, dirInstruments.toString(), "/s3.wav");
            copyFiletoTarget(R.raw.s4, dirInstruments.toString(), "/s4.wav");
            copyFiletoTarget(R.raw.s5, dirInstruments.toString(), "/s5.wav");
            copyFiletoTarget(R.raw.s6, dirInstruments.toString(), "/s6.wav");
            copyFiletoTarget(R.raw.s7, dirInstruments.toString(), "/s7.wav");
            copyFiletoTarget(R.raw.s8, dirInstruments.toString(), "/s8.wav");
            copyFiletoTarget(R.raw.s9, dirInstruments.toString(), "/s9.wav");
            copyFiletoTarget(R.raw.s11, dirInstruments.toString(), "/s11.wav");
            copyFiletoTarget(R.raw.a15, dirInstruments.toString(), "/a15.wav");
            copyFiletoTarget(R.raw.s12, dirInstruments.toString(), "/s12.wav");
            copyFiletoTarget(R.raw.wflaute, dirInstruments.toString(), "/wflaute.wav");
            fieldFileInstruments = dirInstruments.listFiles();
        }
        if (fieldFileFrets == null || fieldFileFrets.length == 0) {
            copyFiletoTarget(R.drawable.fret, dirFrets.toString(), "/fret.png");
            copyFiletoTarget(R.drawable.fret2, dirFrets.toString(), "/fret2.png");
            fieldFileFrets = dirFrets.listFiles();
        }
        if (fieldFileBackgrounds == null || fieldFileBackgrounds.length == 0) {
            copyFiletoTarget(R.drawable.rosewood, dirBackgrounds.toString(), "/rosewood.png");
            copyFiletoTarget(R.drawable.rosewood2, dirBackgrounds.toString(), "/rosewood2.png");
            FileManager.fieldFileBackgrounds = dirBackgrounds.listFiles();
        }
        if (fieldFileStrings == null || fieldFileStrings.length == 0) {
            File dirDefault = new File (context.getFilesDir()+"/Strings/defaultStrings/");
            dirDefault.mkdir();
            copyFiletoTarget(R.drawable.string1, dirBackgrounds.toString(), "/defaultStrings/string1.png");
            copyFiletoTarget(R.drawable.string2, dirBackgrounds.toString(), "/defaultStrings/string2.png");
            copyFiletoTarget(R.drawable.string3, dirBackgrounds.toString(), "/defaultStrings/string3.png");
            copyFiletoTarget(R.drawable.string4, dirBackgrounds.toString(), "/defaultStrings/string4.png");
            copyFiletoTarget(R.drawable.string5, dirBackgrounds.toString(), "/defaultStrings/string5.png");
            copyFiletoTarget(R.drawable.string6, dirBackgrounds.toString(), "/defaultStrings/string6.png");
            FileManager.fieldFileStrings = dirStrings.listFiles();
        }

        if (fieldFileMidi == null || fieldFileMidi.length == 0) {
            copyFiletoTarget(R.raw.ovcaci_ctveraci, dirMidi.toString(), "/ovcaci_ctveraci.mid");
        }
    }

    public static void copyFiletoTarget(int resourceId, String targetDir, String targetName){
        String path = targetDir + targetName;
        try{
            InputStream in = FileManager.context.getResources().openRawResource(resourceId);
            FileOutputStream out = null;
            out = new FileOutputStream(path);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Song getSongFromXML(File XML){

        Song song = new Song();

        try {
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
        catch (Exception e) {

        }
        return song;
    }

    public static boolean setSongToXML(Context context, Song song) {

        try {
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

            for (Tone tone : song.getTones()) {

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
        catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return true;
    }

    public static String nameWithoutDiacritic (String name){ //vrati String bez diakritiky z názvu písne s diakritikou
        String nameOut = "";
        String nameIn = name;

        String withdiacritic = "áéěíóúůÁÉĚÍÓÚŮňďščřžťŇĎŠČŘŽŤ";
        String withoutdiacritic = "aeeiouuAEEIOUUndscrztNDSCRZT";

        for (char i : nameIn.toCharArray()){
            int index = 0;
            for (char j : withdiacritic.toCharArray()) {


                if (i == j) {
                    nameOut += withoutdiacritic.charAt(index);
                    break;

                }
                index++;
            }
            if (index == withdiacritic.length()){
                if (i == ' ') {
                    nameOut += '_';
                } else {
                    nameOut += i;
                }
            }
        }
        return nameOut;
    }

    public static void moveFile(String inputPath, String inputFile, String outputPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();
        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public static void deleteFile(String inputPath, String inputFile) {
        try {
            // delete the original file
            new File(inputPath + inputFile).delete();
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public static void copyFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }


    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }


    public static void copyFiletoDir(int resourceId, String resourceName){


        String path = context.getFilesDir() + "/" + resourceName;
        try{
            InputStream in = context.getResources().openRawResource(resourceId);
            FileOutputStream out = null;
            out = new FileOutputStream(path);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static File createFileFromInputStream(InputStream inputStream) {

        try{
            File f = new File("File");
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            e.getMessage();
        }

        return null;
    }

    public static ArrayList<String> getNameFrets() {
        return nameFrets;
    }

    public static void setNameFrets(ArrayList<String> nameFrets) {
        FileManager.nameFrets = nameFrets;
    }

    public static ArrayList<String> getNameBackgrounds() {
        return nameBackgrounds;
    }

    public static void setNameBackgrounds(ArrayList<String> nameBackgrounds) {
        FileManager.nameBackgrounds = nameBackgrounds;
    }

    public static ArrayList<Song> getListSongs() {
        return listSongs;
    }

    public static void setListSongs(ArrayList<Song> listSongs) {
        FileManager.listSongs = listSongs;
    }

    public static ArrayList<String> getNameSongs() {
        return nameSongs;
    }

    public static void setNameSongs(ArrayList<String> nameSongs) {
        FileManager.nameSongs = nameSongs;
    }

    public static ArrayList<String> getNameInstruments() {
        return nameInstruments;
    }

    public static void setNameInstruments(ArrayList<String> nameInstruments) {
        FileManager.nameInstruments = nameInstruments;
    }

    public static ArrayList<String> getNameStrings() {
        return nameStrings;
    }

    public static void setNameStrings(ArrayList<String> nameStrings) {
        FileManager.nameStrings = nameStrings;
    }

    public static File getDirSongs() {
        return dirSongs;
    }

    public static File getDirInstruments() {
        return dirInstruments;
    }

    public static File getDirFrets() {
        return dirFrets;
    }

    public static File getDirBackgrounds() {
        return dirBackgrounds;
    }

    public static File getDirStrings() {
        return dirStrings;
    }

    public static File getDirMidi() {
        return dirMidi;
    }
}
