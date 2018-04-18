package lubin.guitar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class FileInOut {

    private Context context;
    public static SharedPreferences settings;


    public FileInOut(Context current){

        this.context = current;
        settings = PreferenceManager.getDefaultSharedPreferences(current);

    }


    protected void moveFile(String inputPath, String inputFile, String outputPath) {

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



    public void deleteFile(String inputPath, String inputFile) {
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





    protected void copyFiletoDir(int resourceId, String resourceName){


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

    public File createFileFromInputStream(InputStream inputStream) {

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


    public static void copyFromAssets (Context context, String inputDirPath, String outputDirPath ) { //kopirovani jednoho adresare z assets files do jineho adresare
        AssetManager assetManager = context.getAssets();
        try {

            String [] assetsList = assetManager.list(inputDirPath);

            for (String files : assetsList)
            {
                InputStream in = assetManager.open(inputDirPath + "/" + files);


                File outputDir = new File(outputDirPath); //vytvoreni adresare
                if (!(outputDir.exists())){
                    outputDir.mkdir();
                }

                OutputStream out = new FileOutputStream(outputDirPath + files);
                byte[] buffer = new byte[1024];
                int read = in.read(buffer);
                while (read != -1) {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }
                in.close();
                out.flush();
                out.close();
            }



        } catch (Exception e) {
            e.getMessage();
        }
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


    public static boolean setUsersToXML(Context context, User[] users){

        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            int ID = 0;

            Element allUsers = doc.createElement("Users");


            for (User user : users){

                String passEncoded = FileInOut.encryption(user.getPass());

                Element userID = doc.createElement("ID:_" + String.valueOf(ID));


                Element nameOfUser = doc.createElement("Name_of_User");
                nameOfUser.appendChild(doc.createTextNode(user.getName()));
                userID.appendChild(nameOfUser);

                Element password = doc.createElement("Password");
                password.appendChild(doc.createTextNode(passEncoded));
                userID.appendChild(password);

                Element valueUser = doc.createElement("Value_User");
                valueUser.appendChild(doc.createTextNode(String.valueOf(user.getValue())));
                userID.appendChild(valueUser);

                Element choiceInstrument = doc.createElement("Choice_Instrument");
                choiceInstrument.appendChild(doc.createTextNode(user.getChoiceInstrumentName()));
                userID.appendChild(choiceInstrument);

                Element choiceSong = doc.createElement("Choice_Song");
                choiceSong.appendChild(doc.createTextNode(user.getChoiceSongName()));
                userID.appendChild(choiceSong);




                allUsers.appendChild(userID);

                ID++;
            }


            doc.appendChild(allUsers);

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");


                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(context.getFilesDir() + "/users.xml"));
                transformer.transform(source, result);


        }
        catch (Exception e)
        {
            Log.e("Error: ", e.getMessage());
        }
        return true;
    }

    public static String[] getNameOfUsers (User [] users) {

        List<String> names = new ArrayList<String>();
        for (User user : users){

            names.add(user.getName());

        }

        String[] nameArray = new String[names.size()];
        names.toArray(nameArray);


        return nameArray;





    }



    public static User [] getUsersFromXML(File XML){


        ArrayList<User> users = new ArrayList<>();



        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(XML);

            Node root = doc.getDocumentElement();
            root.normalize();

            NodeList allUser = root.getChildNodes();


            for (int i = 0; i < allUser.getLength(); i++){


                User user = new User("", 0, "", "", "", false);

                Node node = allUser.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().contains("ID:_")) {

                    NodeList nodeList = allUser.item(i).getChildNodes();
                    user.setName(nodeList.item(1).getTextContent());
                    user.setPass(decryption(nodeList.item(3).getTextContent()));
                    user.setValue(Integer.parseInt(nodeList.item(5).getTextContent()));
                    user.setChoiceInstrumentName(nodeList.item(7).getTextContent());
                    user.setChoiceSongName(nodeList.item(9).getTextContent());

                    users.add(user);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("Error: ", e.getMessage());

        }
        return users.toArray(new User[users.size()]);
    }


    public static boolean applyChangeUserToXML(Context context, File XML, User user){
        User[] users = getUsersFromXML(XML);
        boolean change = false;

        for (User u : users){

            if (u.getName().equals(user.getName())){
                u.setPass(user.getPass());
                u.setValue(user.getValue());
                u.setChoiceSongName(user.getChoiceSongName());
                u.setChoiceInstrumentName(user.getChoiceInstrumentName());
                change = true;
                break;
            }

        }

        if (!change){
            ArrayList <User> listUsers = new ArrayList<>(Arrays.asList(users));
            listUsers.add(user);
            return setUsersToXML(context, listUsers.toArray(new User[listUsers.size()]));
        }


        return setUsersToXML(context, users);




    }

    public static User[] createDefaultUsersForXML(){

        ArrayList<User> users = new ArrayList<>();



        User user1 = new User("Roman", 0, "pass", "Ovcaci, ctveraci", "a1.wav", true);
        users.add(user1);
        User user2 = new User("Karlik", 0, "pass", "Pro Elisku", "a1.wav", true);
        users.add(user2);


        return users.toArray(new User[users.size()]);

    }



    public static void copyFileByUri(Context context, Uri uri, File dst) throws IOException { //zkopiruje soubor podle Uri


        try {
            FileInputStream in = (FileInputStream) context.getContentResolver().openInputStream( uri);
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
                in.close();
            }
        } finally {

        }
    }

    public static String queryName(ContentResolver resolver, Uri uri) { //vrati jmeno souboru otevrenem a ulozenem v prohlizeci
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


//TODO - vyhazuje chybu - nepreklada
    public static String encryption(String strNormalText){
        String seedValue = "MyGuitarPass";
        String normalTextEnc="";
        try {
            normalTextEnc = AESHelper.encrypt(seedValue, strNormalText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalTextEnc;
    }



    public static String decryption(String strEncryptedText){
        String seedValue = "MyGuitarPass";
        String strDecryptedText="";
        try {
            strDecryptedText = AESHelper.decrypt(seedValue, strEncryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDecryptedText;
    }
}
