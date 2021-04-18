package lubin.guitar.Shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lubin.guitar.Files.DialogType;
import lubin.guitar.Files.FileManager;
import lubin.guitar.Files.FileType;
import lubin.guitar.GuitarActivity.PreviewSongActivity;
import lubin.guitar.R;
import lubin.guitar.Song.Song;
import lubin.guitar.Users.SingletonManagerUsers;
import lubin.guitar.Users.User;

import static lubin.guitar.Users.SingletonManagerUsers.getAdminUser;
import static lubin.guitar.Users.SingletonManagerUsers.setCurrentSong;
import static lubin.guitar.Users.SingletonManagerUsers.setCurrentUser;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnOpenFile;
    Button btnBuyItem;
    Button btnTrySong;
    ImageView imageFile;
    TextView lblNameFile;
    TextView lblPriceItem;
    TextView lblNameUser;
    TextView lblCoinValue;
    TextView lblPriceText;
    TextView lblChangeTypeFile;
    Item currentItem;
    List<String> listTypeFile;
    FileType fileType;
    List<Item> itemList;
    Song song;
    User currentUser;
    MediaPlayer mediaPlayer;
    boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        itemList = SingletonManagerItems.getItemList();
        currentItem = new Item();
        currentUser = SingletonManagerUsers.getCurrentUser();
        initItems();
        setStatus();
        isPlaying = false;
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

        btnBuyItem = findViewById(R.id.btn_buy_shop);
        btnBuyItem.setOnClickListener(this);

        btnTrySong = findViewById(R.id.try_song_shop);
        btnTrySong.setOnClickListener(this);

        lblNameUser = findViewById(R.id.value_name_user);

        lblCoinValue = findViewById(R.id.value_coin_user);

        imageFile = findViewById(R.id.image_file_shop);

        lblNameFile = findViewById(R.id.lbl_name_file_shop);

        lblPriceItem = findViewById(R.id.lbl_price_item_shop);

        lblPriceText = findViewById(R.id.lbl_price_text_shop);

        lblChangeTypeFile = findViewById(R.id.change_type_file_shop);
        lblChangeTypeFile.setOnClickListener(this);
        lblChangeTypeFile.setText(listTypeFile.get(0));
        fileType = FileType.BACKGROUND;
    }

    protected void setStatus() {
        itemList = SingletonManagerItems.getItemList();
        lblNameUser.setText(currentUser.getName());
        lblCoinValue.setText(Integer.toString(currentUser.getCoins()));
        if (currentItem.file == null) {
            btnBuyItem.setVisibility(View.INVISIBLE);
            imageFile.setVisibility(View.INVISIBLE);
            btnTrySong.setVisibility(View.INVISIBLE);
            lblNameFile.setText("");
            lblPriceItem.setVisibility(View.INVISIBLE);
            lblPriceText.setVisibility(View.INVISIBLE);
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
            btnBuyItem.setVisibility(View.VISIBLE);
            lblPriceItem.setText(Integer.toString(currentItem.getPrice()));
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnOpenFile) {
            showDialog(DialogType.OPEN_ITEMS);
        }
        if (view == btnBuyItem) {
            buyItem();
        }
        if (view == btnTrySong) {
            previewSong();
        }
        if (view == lblChangeTypeFile) {
            showDialog(DialogType.CHANGE_TYPE_FILE);
        }
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
            List<Item> nameItems = SingletonManagerItems.getItemList();
            final List<String> nameChoicenItems = new ArrayList<>();
            for (Item item : nameItems) {
                if (item.getFileType() == fileType) {
                    List<String> allowedItemsUser = currentUser.getListAllowedByFilename(fileType);
                    boolean isNew = true;
                    for (String nameItem : allowedItemsUser) {
                        if (nameItem.equals(item.getName())) {
                            isNew = false;
                        }
                    }
                    if (isNew) {
                        nameChoicenItems.add(item.getName());
                    }
                }
            }
            final String[] arrayTypeFile = new String[nameChoicenItems.size()];
            nameChoicenItems.toArray(arrayTypeFile);

            builder.setItems(arrayTypeFile, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = nameChoicenItems.get(which);
                    if (name.equals(ShopActivity.this.getResources().getString(R.string.new_item))) {
                        showDialog(DialogType.OPEN_FILE);
                    } else {
                        currentItem = SingletonManagerItems.getItemByName(name);
                        if (currentItem.getFileType() == FileType.SONG) {
                            song = FileManager.getSongFromXML(currentItem.getFile());
                        }
                        fileType = currentItem.getFileType();
                        lblChangeTypeFile.setText(getNameByFileType(fileType));
                        setStatus();
                        changeTypeFileByName();
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
    }

    protected void buyItem () {
        if (currentUser.getCoins() < currentItem.getPrice()) {
            Toast.makeText(this, getResources().getString(R.string.warning_coins_not_enough), Toast.LENGTH_SHORT).show();
            return;
        }
        int newCoins = currentUser.getCoins() - currentItem.getPrice();
        currentUser.setCoins(newCoins);
        switch (fileType) {
            case FRET: {
                List<String> allowedFrets = currentUser.getAllowedFrets();
                allowedFrets.add(currentItem.getFile().getName());
                currentUser.setAllowedFrets(allowedFrets);
                break;
            }
            case SONG: {
                List<String> allowedSongs = currentUser.getAllowedSongs();
                allowedSongs.add(currentItem.getFile().getName());
                currentUser.setAllowedSongs(allowedSongs);
                break;
            }
            case BACKGROUND: {
                List<String> allowedBackgrounds = currentUser.getAllowedBackgrounds();
                allowedBackgrounds.add(currentItem.getFile().getName());
                currentUser.setAllowedBackgrounds(allowedBackgrounds);
                break;
            }
            case STRING: {
                List<String> allowedStrings = currentUser.getAllowedStrings();
                allowedStrings.add(currentItem.getFile().getName());
                currentUser.setAllowedStrings(allowedStrings);
                break;
            }
            case INSTRUMENT: {
                List<String> allowedInstruments = currentUser.getAllowedInstruments();
                allowedInstruments.add(currentItem.getFile().getName());
                currentUser.setAllowedInstruments(allowedInstruments);
                break;
            }
        }
        SingletonManagerUsers.changeUser(currentUser);
        currentItem = new Item ();
        setStatus();
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
                mediaPlayer.stop();
                btnTrySong.setText("start");
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
            case INSTRUMENT: {
                name = getResources().getString(R.string.instrument);
                break;
            }
        }
        return name;
    }

    protected void showImage () {
        if(currentItem.getFile().exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(currentItem.getFile().getAbsolutePath());
            imageFile.setImageBitmap(myBitmap);
            lblNameFile.setText(currentItem.getFile().getName());
        }
    }

    protected void changeTypeFileByName () {
        String name = lblChangeTypeFile.getText().toString();
        if (name == getResources().getString(R.string.backgrounds)) {
            fileType = FileType.BACKGROUND;
        } else if (name == getResources().getString(R.string.frets)) {
            fileType = FileType.FRET;
        } else if (name == getResources().getString(R.string.strings)) {
            fileType = FileType.STRING;
        } else if (name == getResources().getString(R.string.instrument)) {
            fileType = FileType.INSTRUMENT;
        }else {
            fileType = FileType.SONG;
        }
    }
}
