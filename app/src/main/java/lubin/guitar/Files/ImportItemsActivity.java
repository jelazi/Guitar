package lubin.guitar.Files;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lubin.guitar.R;

public class ImportItemsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView changeTypeFile;
    Button btnOpenFile;
    TextView lblNameFile;
    ImageView imageFile;
    List<String> listTypeFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_items);
        initItems();
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

        lblNameFile = findViewById(R.id.lbl_name_file);

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
    }


    protected void showDialog (DialogType dialogType) {
        switch (dialogType) {
            case FILE_DIALOG_IMAGE: {
                FileDialog fileDialog = new FileDialog(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "png", DialogType.FILE_DIALOG_IMAGE);
                String title = getTitleSpecificFile();
                fileDialog.createFileDialog(title);
                fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                    public void fileSelected(File file) {

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
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
        }
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
