package lubin.guitar;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class FileInOut {

    private Context context;


    public FileInOut(Context current){

        this.context = current;
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


    protected void copyFile(String inputPath, String inputFile, String outputPath) {

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

        String withdiacritic = "áéěíóúůÁÉĚÍÓÚŮščřžťŠČŘŽŤ";
        String withoutdiacritic = "aeeiouuAEEIOUUscrztSCRZT";

        int j = 0;

        for (char i : nameIn.toCharArray()){

            if (withdiacritic.contains(String.valueOf(i))){
                nameOut += withoutdiacritic.charAt(j);

            }
            else{

                if (i == ' '){
                    nameOut += '_';
                }
                else
                {
                    nameOut += i;
                }
            }
            j++;
        }



        return nameOut;


    }


    public static boolean setUserToXML(Context context){

        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element aboutUser = doc.createElement("User");

            Element nameOfUser = doc.createElement("Name_of_User");
            nameOfUser.appendChild(doc.createTextNode(Globals.getUserName()));
            aboutUser.appendChild(nameOfUser);

            Element password = doc.createElement("Password");
            password.appendChild(doc.createTextNode(Globals.getPass()));
            aboutUser.appendChild(password);

            Element valueUser = doc.createElement("Value_User");
            valueUser.appendChild(doc.createTextNode(Integer.toString(Globals.getValueUser())));
            aboutUser.appendChild(valueUser);


            doc.appendChild(aboutUser);


            File folder = new File(context.getFilesDir() + "/Users"); //vytvoreni slozky Users
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
                StreamResult result = new StreamResult(new File(context.getFilesDir() + "/Users/" + nameWithoutDiacritic(Globals.getUserName())));
                transformer.transform(source, result);
            }

        }
        catch (Exception e)
        {
            Log.e("Error: ", e.getMessage());
        }
        return true;
    }









}
