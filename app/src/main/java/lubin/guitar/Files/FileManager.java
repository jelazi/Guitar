package lubin.guitar.Files;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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
import lubin.guitar.Users.SingletonManagerUsers;

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

            FileManager.copyDataFromResource(context);

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

    public static void copyAssets(Context context, String nameFile, String pathOut) {
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
                in = assetManager.open(nameFile);
                out = new FileOutputStream(pathOut);
                copyStream(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;

        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public static void copyDataFromResource (Context context) {
        if (fieldFilesSongs == null || fieldFilesSongs.length == 0) {
            copyAssets(context, "Songs/song1", dirSongs.toString() + "/song1");
            copyAssets(context, "Songs/song2", dirSongs.toString() + "/song2");
            fieldFilesSongs = dirSongs.listFiles();
        }
        if (fieldFileInstruments == null || fieldFileInstruments.length == 0) {
            copyAssets(context, "Instruments/1_kytara_klasicka.wav", dirInstruments.toString() + "/1_kytara_klasicka.wav");
            copyAssets(context, "Instruments/kytara_kovove_struny_1.wav", dirInstruments.toString() + "/kytara_kovove_struny_1.wav");
            copyAssets(context, "Instruments/kytara_kovove_struny_2.wav", dirInstruments.toString() + "/kytara_kovove_struny_2.wav");
            copyAssets(context, "Instruments/basa_1.wav", dirInstruments.toString() + "/basa_1.wav");
            copyAssets(context, "Instruments/basa_2.wav", dirInstruments.toString() + "/basa_2.wav");
            copyAssets(context, "Instruments/kytara_echo.wav", dirInstruments.toString() + "/kytara_echo.wav");
            copyAssets(context, "Instruments/hlas.wav", dirInstruments.toString() + "/hlas.wav");
            copyAssets(context, "Instruments/kytara_echo_chorus.wav", dirInstruments.toString() + "/kytara_echo_chorus.wav");
            copyAssets(context, "Instruments/kytara_tlumena.wav", dirInstruments.toString() + "/kytara_tlumena.wav");
            copyAssets(context, "Instruments/kytara_kovove_struny_3.wav", dirInstruments.toString() + "/kytara_kovove_struny_3.wav");
            copyAssets(context, "Instruments/kytara_kovove_struny_3", dirInstruments.toString() + "/kytara_kovove_struny_3.wav");
            copyAssets(context, "Instruments/fletna.wav", dirInstruments.toString() + "/fletna.wav");
            fieldFileInstruments = dirInstruments.listFiles();
        }
        if (fieldFileFrets == null || fieldFileFrets.length == 0) {
            copyFiletoTarget(R.drawable.fret, dirFrets.toString(), "/prazec_1.png");
            copyFiletoTarget(R.drawable.fret2, dirFrets.toString(), "/prazec_2.png");
            fieldFileFrets = dirFrets.listFiles();
        }
        if (fieldFileBackgrounds == null || fieldFileBackgrounds.length == 0) {
            copyFiletoTarget(R.drawable.rosewood, dirBackgrounds.toString(), "/pozadi_1.png");
            copyFiletoTarget(R.drawable.rosewood2, dirBackgrounds.toString(), "/pozadi_2.png");
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
                File is = new File (targetDir + targetName);
                if (!is.exists()) {
                    Log.e("Error", "problem creating file");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Song getSongFromXML(File XML){


        if (!XML.exists()) {
            Log.d("XML", XML.toString());
        }
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
            Log.e("Error ", e.toString());
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
            return false;
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

    public static boolean eraseAllSettings (SharedPreferences settings, Context context){
        SingletonManagerUsers.removeAllUsers(settings);
        clearApplicationData(context);
        SharedPreferences.Editor prefsEditor = settings.edit();
        prefsEditor.clear();
        prefsEditor.commit();
        return true;

    }

    protected static void clearApplicationData(Context context) {
        File applicationDirectory = new File(String.valueOf(context.getFilesDir()));
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    protected static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }
        return deletedAll;
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
