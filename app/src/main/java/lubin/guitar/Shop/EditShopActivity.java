package lubin.guitar.Shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lubin.guitar.Files.DialogType;
import lubin.guitar.Files.FileDialog;
import lubin.guitar.Files.FileManager;
import lubin.guitar.Files.FileType;
import lubin.guitar.GuitarActivity.PreviewSongActivity;
import lubin.guitar.R;
import lubin.guitar.Song.Song;

import static lubin.guitar.Users.SingletonManagerUsers.*;

public class EditShopActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Button btnOpenFile;
    Button btnSaveItem;
    Button btnTrySong;
    Switch switchInShop;
    ImageView imageFile;
    TextView lblNameFile;
    TextView lblPriceItem;
    TextView lblPriceText;
    TextView lblChangeTypeFile;
    Item currentItem;
    List<String> listTypeFile;
    FileType fileType;
    List<Item> itemList;
    boolean isNewItem;
    boolean isChanging;
    Song song;
    MediaPlayer mediaPlayer;
    boolean isPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemList = SingletonManagerItems.getItemList();
        currentItem = new Item();
        isNewItem = false;
        initItems();
        setStatus();
        isPlaying = false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    protected void initItems () {
        listTypeFile = new ArrayList<>();
        listTypeFile.add(getResources().getString(R.string.backgrounds));
        listTypeFile.add(getResources().getString(R.string.frets));
        listTypeFile.add(getResources().getString(R.string.strings));
        listTypeFile.add(getResources().getString(R.string.song));
        listTypeFile.add(getResources().getString(R.string.instrument));

        btnOpenFile = findViewById(R.id.btn_open_file_shop);
        btnOpenFile.setOnClickListener(this);

        btnSaveItem = findViewById(R.id.btn_save_shop);
        btnSaveItem.setOnClickListener(this);

        btnTrySong = findViewById(R.id.try_song_shop);
        btnTrySong.setOnClickListener(this);

        switchInShop = findViewById(R.id.switch_in_shop);
        switchInShop.setOnCheckedChangeListener(this);

        imageFile = findViewById(R.id.image_file_shop);

        lblNameFile = findViewById(R.id.lbl_name_file_shop);
        lblNameFile.setOnClickListener(this);

        lblPriceItem = findViewById(R.id.lbl_price_item_shop);
        lblPriceItem.setOnClickListener(this);

        lblPriceText = findViewById(R.id.lbl_price_text_shop);

        lblChangeTypeFile = findViewById(R.id.change_type_file_shop);
        lblChangeTypeFile.setOnClickListener(this);
        lblChangeTypeFile.setText(listTypeFile.get(0));
        fileType = FileType.BACKGROUND;
        isChanging = false;
    }

    protected void setStatus() {
        itemList = SingletonManagerItems.getItemList();
        if (currentItem.file == null) {
            btnSaveItem.setEnabled(false);
            imageFile.setVisibility(View.INVISIBLE);
            btnTrySong.setVisibility(View.INVISIBLE);
            lblNameFile.setText("");
            lblPriceItem.setVisibility(View.INVISIBLE);
            lblPriceText.setVisibility(View.INVISIBLE);
            switchInShop.setVisibility(View.INVISIBLE);
        } else {

            if (fileType == FileType.SONG || fileType == FileType.INSTRUMENT) {
                imageFile.setVisibility(View.INVISIBLE);
                imageFile.setImageBitmap(null);
                btnTrySong.setVisibility(View.VISIBLE);
            } else {
                imageFile.setVisibility(View.VISIBLE);
                showImage();
                btnTrySong.setVisibility(View.INVISIBLE);
            }
            lblNameFile.setText(currentItem.getName());
            lblPriceText.setVisibility(View.VISIBLE);
            lblPriceItem.setVisibility(View.VISIBLE);
            lblPriceItem.setText(Integer.toString(currentItem.getPrice()));
            switchInShop.setVisibility(View.VISIBLE);
            btnSaveItem.setEnabled(isChanging);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnOpenFile) {
            showDialog(DialogType.OPEN_ITEMS);
        }
        if (view == btnSaveItem) {
            saveItem();
        }
        if (view == btnTrySong) {
            previewSong();
        }
        if (view == lblPriceItem) {
            showDialog(DialogType.NUMBER_PICKER);
        }
        if (view == lblNameFile) {
            showDialog(DialogType.RENAME_FILE);
        }
        if (view == lblChangeTypeFile) {
            showDialog(DialogType.CHANGE_TYPE_FILE);
        }
    }

    protected void showDialog (DialogType dialogType) {
        if (dialogType == DialogType.CHANGE_TYPE_FILE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.choice_type_files));
            final String[] arrayTypeFile = new String[listTypeFile.size()];
            listTypeFile.toArray(arrayTypeFile);

            builder.setItems(arrayTypeFile, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    lblChangeTypeFile.setText(listTypeFile.get(which));
                    currentItem = new Item();
                    isChanging = false;
                    setStatus();
                    changeTypeFileByName();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        if (dialogType == DialogType.OPEN_ITEMS) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.choice_items));
            final List<String> nameItems = SingletonManagerItems.getNameItems();
            nameItems.add(this.getResources().getString(R.string.new_item));
            final String[] arrayTypeFile = new String[nameItems.size()];
            nameItems.toArray(arrayTypeFile);

            builder.setItems(arrayTypeFile, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = nameItems.get(which);
                    if (name.equals(EditShopActivity.this.getResources().getString(R.string.new_item))) {
                        showDialog(DialogType.OPEN_FILE);
                    } else {
                        currentItem = SingletonManagerItems.getItemByName(name);
                        if (currentItem.getFileType() == FileType.SONG) {
                            song = FileManager.getSongFromXML(currentItem.getFile());
                        }
                        switchInShop.setChecked(true);
                        fileType = currentItem.getFileType();
                        lblChangeTypeFile.setText(getNameByFileType(fileType));
                        isChanging = false;
                        setStatus();
                        changeTypeFileByName();
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        if (dialogType == DialogType.OPEN_FILE) {
            File dir = getFolder();
            if (dir == null) {
                Log.e("Error", "dir is null");
                return;
            }
            FileDialog fileDialog = new FileDialog(this, dir, DialogType.OPEN_FILE);
            String title = getResources().getString(R.string.open_file);
            fileDialog.createFileDialog(title);
            fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
                public void fileSelected(File file) {
                    if (file.exists()) {
                        currentItem.setFile(file);
                        currentItem.setFileType(fileType);

                        currentItem.setPrice(100);
                        if (currentItem.getFileType() == FileType.SONG) {
                            song = FileManager.getSongFromXML(currentItem.getFile());
                            if (song.getNameOfSong().isEmpty()) {
                                song.setNameOfSong("song");
                            }
                            currentItem.setName(song.getNameOfSong());
                        } else {
                            currentItem.setName(file.getName());
                        }
                        switchInShop.setChecked(true);
                        isNewItem = true;
                        isChanging = true;
                        setStatus();
                    }
                }
            });
            fileDialog.showDialog();
            return;
        }
        if (dialogType == DialogType.RENAME_FILE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.name_file));
            final EditText edittext = new EditText(this);
            edittext.setText(lblNameFile.getText().toString());
            edittext.setSelectAllOnFocus(true);
            builder.setView(edittext);
            edittext.requestFocus();

            builder.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = edittext.getText().toString();
                    lblNameFile.setText(name);
                    currentItem.setName(name);
                    isChanging = true;
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    dialog.dismiss();
                }
            });
            builder.show();
            return;
        }
        if (dialogType == DialogType.NUMBER_PICKER) {
            final AlertDialog.Builder d = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
            d.setTitle("Price");
            d.setMessage("Choice price of item");
            d.setView(dialogView);
            final NumberPicker numberPicker = dialogView.findViewById(R.id.dialog_number_picker);
            int minValue = 10;
            int maxValue = 10000;
            final int step = 10;
            String[] numberValues = new String[maxValue - minValue + 1];
            for (int i = 0; i <= maxValue - minValue; i++) {
                numberValues[i] = String.valueOf((minValue + i) * step);
            }
            numberPicker.setMaxValue(maxValue);
            numberPicker.setMinValue(minValue);
            numberPicker.setWrapSelectorWheel(false);
            numberPicker.setDisplayedValues(numberValues);

            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                }
            });
            d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int value = numberPicker.getValue() * step;
                    lblPriceItem.setText(Integer.toString(value));
                    currentItem.setPrice(value);
                    isChanging = true;
                    setStatus();
                }
            });
            d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog alertDialog = d.create();
            alertDialog.show();
            numberPicker.setValue(Integer.parseInt(lblPriceItem.getText().toString()) / step);
            return;
        }
    }

    protected void previewSong () {
        if (fileType == FileType.SONG) {
            setCurrentUser(getAdminUser());
            Intent i = new Intent(this, PreviewSongActivity.class);
            i.putExtra("is_test", true);
            i.putExtra("name_test_song", song.getNameOfSong());
            setCurrentSong(song);
            startActivity(i);
        } else {
            if (!isPlaying) {
                mediaPlayer = MediaPlayer.create(this, Uri.parse(currentItem.getFile().getPath()));
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        isPlaying = false;
                        btnTrySong.setText("start");

                    }
                });
                mediaPlayer.start();
                btnTrySong.setText("stop");
                isPlaying = true;
            } else {
                isPlaying = false;
                btnTrySong.setText("start");
                mediaPlayer.stop();
            }
        }
    }

    protected String getNameByFileType (FileType fileType) {
        String name = "";
        switch (fileType) {
            case FRET: {
                name = getResources().getString(R.string.frets);
                break;
            }
            case INSTRUMENT: {
                name = getResources().getString(R.string.instrument);
                break;
            }
            case SONG: {
                name = getResources().getString(R.string.song);
                break;
            }
            case BACKGROUND: {
                name = getResources().getString(R.string.backgrounds);
                break;
            }
            case STRING: {
                name = getResources().getString(R.string.strings);
                break;
            }
        }
        return name;
    }

    protected void saveItem () {
        if (switchInShop.isChecked()) {
            if (!isCorrectCurrentItem()) return;
            if (isNewItem) {
                SingletonManagerItems.addItem(currentItem);
            } else {
                SingletonManagerItems.changeItem(currentItem);
            }
            isNewItem = false;
            isChanging = false;
            Toast.makeText(this, "current item save", Toast.LENGTH_SHORT).show();
            setStatus();
        } else {
            if (!isNewItem) {
                SingletonManagerItems.removeItem(currentItem);
                Toast.makeText(this, "current item removed", Toast.LENGTH_SHORT).show();
                isChanging = false;
                setStatus();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton == switchInShop) {
            isChanging = true;
            setStatus();
        }
    }

    protected void showImage () {
        if(currentItem.getFile().exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(currentItem.getFile().getAbsolutePath());
            imageFile.setImageBitmap(myBitmap);
            lblNameFile.setText(currentItem.getFile().getName());
        }
    }

    protected boolean isCorrectCurrentItem () {
        if (!currentItem.isCorrectValueItem()) {
            Toast.makeText(this, "current item is wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (itemList.contains(currentItem) && isNewItem) {
            Toast.makeText(this, "current item is used", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (Item item : itemList) {
            if (item.getFile().getPath().equals(currentItem.getFile().getPath()) && isNewItem) {
                Toast.makeText(this, "current item has same path as another item", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (item.getName().equals(currentItem.getName()) && isNewItem) {
                Toast.makeText(this, "current item has same name as another item", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    protected File getFolder() {
        if (fileType == null) {
            Log.e("Error", "fileType is null");
            return null;
        }
        switch (fileType) {
            case FRET: return FileManager.getDirFrets();
            case STRING: return FileManager.getDirStrings();
            case BACKGROUND: return FileManager.getDirBackgrounds();
            case SONG: return FileManager.getDirSongs();
            case INSTRUMENT: return FileManager.getDirInstruments();

        }
        return null;
    }

    protected void changeTypeFileByName () {
        String name = lblChangeTypeFile.getText().toString();
        if (name == getResources().getString(R.string.backgrounds)) {
            fileType = FileType.BACKGROUND;
        } else if (name == getResources().getString(R.string.frets)) {
            fileType = FileType.FRET;
        } else if (name == getResources().getString(R.string.strings)) {
            fileType = FileType.STRING;
        } else if (name == getResources().getString(R.string.song)){
            fileType = FileType.SONG;
        } else {
            fileType = FileType.INSTRUMENT;
        }
    }
}
