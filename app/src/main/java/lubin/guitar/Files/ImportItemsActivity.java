package lubin.guitar.Files;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lubin.guitar.Account.AccountActivity;
import lubin.guitar.R;

public class ImportItemsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView changeTypeFile;
    Button btnOpenFile;
    Button btnImportFile;
    TextView lblNameFile;
    ImageView imageFile;
    List<String> listTypeFile;
    FileType fileType;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_items);
        initItems();
        setVisibleBtn();
    }

    protected void initItems () {
        listTypeFile = new ArrayList<>();
        listTypeFile.add("Pozadí");
        listTypeFile.add("Pražce");
        listTypeFile.add("Struny");

        changeTypeFile = findViewById(R.id.change_type_file);
        changeTypeFile.setOnClickListener(this);
        changeTypeFile.setText(listTypeFile.get(0));

        btnOpenFile = findViewById(R.id.btn_open_file);
        btnOpenFile.setOnClickListener(this);

        btnImportFile = findViewById(R.id.btn_import_file);
        btnImportFile.setOnClickListener(this);

        lblNameFile = findViewById(R.id.lbl_name_file);
        lblNameFile.setOnClickListener(this);

        imageFile = findViewById(R.id.image_file);
    }


    @Override
    public void onClick(View view) {
        if (view == changeTypeFile) {
            showDialog(DialogType.CHANGE_TYPE_FILE);
        }
        if (view == btnOpenFile) {
            showDialog(DialogType.FILE_DIALOG_IMAGE);
        }
        if (view == btnImportFile) {
            try {
                if (controlFile()) {
                    if (fileType != FileType.STRING) {
                        Toast.makeText(ImportItemsActivity.this, "Soubor " + lblNameFile.getText().toString() + " byl zkopírován.", Toast.LENGTH_SHORT).show();
                    } else {
                        String string = lblNameFile.getText().toString();
                        if (string.indexOf(".") > 0) {
                            string = string.substring(0, string.lastIndexOf(".") - 1);
                        }
                        Toast.makeText(ImportItemsActivity.this, "Soubory byly zkopírovány do adresáře: " + string + ".", Toast.LENGTH_SHORT).show();
                    }

                    eraseImage();
                } else {
                    return;
                }

            } catch (IOException e) {
                Toast.makeText(ImportItemsActivity.this, "Soubor " + lblNameFile.getText().toString() + " se nepodařilo zkopírovat.", Toast.LENGTH_SHORT).show();

                e.printStackTrace();

            }
        }
        if (view == lblNameFile) {
            showDialog(DialogType.RENAME_FILE);
        }
    }


    protected void showDialog (DialogType dialogType) {
        reloadTypeImage();
        switch (dialogType) {
            case FILE_DIALOG_IMAGE: {
                FileDialog fileDialog = new FileDialog(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "png", DialogType.FILE_DIALOG_IMAGE);
                String title = getTitleSpecificFile();
                fileDialog.createFileDialog(title);
                fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                    public void fileSelected(File file) {
                        if (file.exists()) {
                            if (changeTypeFile.getText().toString().equals("Struny")) {
                                ImportItemsActivity.this.file = file;
                                if (controlStringsExists()) {
                                    showImage();
                                    setVisibleBtn();
                                } else {
                                    eraseImage();
                                    return;
                                }
                            } else {
                                ImportItemsActivity.this.file = file;
                                showImage();
                                setVisibleBtn();
                            }

                        }
                    }
                });
                fileDialog.showDialog();
                return;
            }
            case CHANGE_TYPE_FILE: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Vyberte typ souborů");
                final String[] arrayTypeFile = new String[listTypeFile.size()];
                listTypeFile.toArray(arrayTypeFile);

                builder.setItems(arrayTypeFile, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeTypeFile.setText(listTypeFile.get(which));
                        reloadTypeImage();
                        eraseImage();
                        setVisibleBtn();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
            case RENAME_FILE: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Jméno souboru");
                final EditText edittext = new EditText(this);
                edittext.setText(lblNameFile.getText().toString());
                edittext.setSelectAllOnFocus(true);
                builder.setView(edittext);
                edittext.requestFocus();

                builder.setPositiveButton("Uložit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edittext.getText().toString();
                        if (!name.contains(".png")) {
                            name = name + ".png";
                        }
                        lblNameFile.setText(name);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Zrušit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
                builder.show();
                return;
            }
        }
    }

    protected void setVisibleBtn () {
        if (file != null && file.exists()) {
            btnImportFile.setVisibility(View.VISIBLE);
        } else {
            btnImportFile.setVisibility(View.INVISIBLE);
        }
    }

    protected void showImage () {
        if(file.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageFile.setImageBitmap(myBitmap);
            lblNameFile.setText(file.getName());
        }
    }

    protected void reloadTypeImage () {
        String changeFileType = changeTypeFile.getText().toString();
        switch (changeFileType) {
            case "Pozadí" : {
                fileType = FileType.BACKGROUND;
                break;
            }
            case "Pražce" : {
                fileType = FileType.FRET;
                break;
            }
            case "Struny" : {
                fileType = FileType.STRING;
                break;
            }
            default: {
                fileType = FileType.BACKGROUND;
            }
        }
    }

    protected boolean controlStringsExists() {
        String name = file.getName();
        if (name.indexOf(".") > 0) {
            name = name.substring(0, name.lastIndexOf("."));
        }
        String appendName = name.substring(name.length() - 1);
        if (!(appendName.equals("1") || appendName.equals("2") || appendName.equals("3") || appendName.equals("4") || appendName.equals("5") || appendName.equals("6"))) {
            Toast.makeText(ImportItemsActivity.this, "Jméno souboru " + lblNameFile.getText().toString() + " nemá správný formát. Na skutečný soubor strun. (například string1, string2, string3, string4, string5", Toast.LENGTH_LONG).show();
            return false;
        }
        String path = file.getAbsolutePath();
        if (path.indexOf(".") > 0) {
            path = path.substring(0, path.lastIndexOf(".") - 1);
        }
        File string1 = new File (path + "1.png");
        File string2 = new File (path + "2.png");
        File string3 = new File (path + "3.png");
        File string4 = new File (path + "4.png");
        File string5 = new File (path + "5.png");
        File string6 = new File (path + "6.png");
        if (string1.exists() && string2.exists() && string3.exists() && string4.exists() && string5.exists() && string6.exists()) {
            return true;
        }
        Toast.makeText(ImportItemsActivity.this, "V adresáři nejsou obsažený všechny soubory strun. Adresář musí obsahovat 6 souborů správně pojmenovaných (například string1, string2, string3, string4, string5", Toast.LENGTH_LONG).show();
        return false;
    }

    protected boolean controlFile () throws IOException {
        String pathDir;
        String nameFile;
        switch (fileType) {
            case FRET:{
                pathDir = FileManager.getDirFrets().getAbsolutePath();
                nameFile = pathDir + "/" + lblNameFile.getText().toString();
                break;

            }
            case STRING:{
                pathDir = FileManager.getDirStrings().getAbsolutePath();
                nameFile = pathDir + "/" + lblNameFile.getText().toString();
                if (nameFile.indexOf(".") > 0) {
                    nameFile = nameFile.substring(0, nameFile.lastIndexOf(".") - 1);
                }
                break;
            }
            case BACKGROUND:{
            pathDir = FileManager.getDirBackgrounds().getAbsolutePath();
            nameFile = pathDir + "/" + lblNameFile.getText().toString();
                break;
            }
            default: {
                pathDir = FileManager.getDirBackgrounds().getAbsolutePath();
                nameFile = pathDir + "/" + lblNameFile.getText().toString();
            }
        }
        File controlFile = new File(nameFile);
        if (controlFile.exists()) {
            Toast.makeText(ImportItemsActivity.this, "Jméno je již používáno. Nekopírujete již používaný soubor? Pokud ne, přejmenujte ho.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (fileType != FileType.STRING) {
            FileManager.copy(file, controlFile);
        } else {
            controlFile.mkdir();
            String pathInput = file.getAbsolutePath();
            if (pathInput.indexOf(".") > 0) {
                pathInput = pathInput.substring(0, pathInput.lastIndexOf(".") - 1);
            }
            String nameOutput = file.getName();
            if (nameOutput.indexOf(".") > 0) {
                nameOutput = nameOutput.substring(0, nameOutput.lastIndexOf(".") - 1);
            }
            File inputString1 = new File (pathInput + "1.png");
            File inputString2 = new File (pathInput + "2.png");
            File inputString3 = new File (pathInput + "3.png");
            File inputString4 = new File (pathInput + "4.png");
            File inputString5 = new File (pathInput + "5.png");
            File inputString6 = new File (pathInput + "6.png");

            File outputString1 = new File (controlFile + "/" + nameOutput + "1.png");
            File outputString2 = new File (controlFile + "/" + nameOutput + "2.png");
            File outputString3 = new File (controlFile + "/" + nameOutput + "3.png");
            File outputString4 = new File (controlFile + "/" + nameOutput + "4.png");
            File outputString5 = new File (controlFile + "/" + nameOutput + "5.png");
            File outputString6 = new File (controlFile + "/" + nameOutput + "6.png");

            FileManager.copy(inputString1, outputString1);
            FileManager.copy(inputString2, outputString2);
            FileManager.copy(inputString3, outputString3);
            FileManager.copy(inputString4, outputString4);
            FileManager.copy(inputString5, outputString5);
            FileManager.copy(inputString6, outputString6);
        }
        return true;
    }


    protected void eraseImage () {
        imageFile.setImageDrawable(null);
        file = null;
        lblNameFile.setText("");
    }

    protected String getTitleSpecificFile () {
        String title = "";
        String name = "";
        for (String type : listTypeFile) {
            if (changeTypeFile.getText().toString().equals(type)) {
                name = type;
            }
        }
        title = "Vyberte " + name;
        return title;
    }
}
