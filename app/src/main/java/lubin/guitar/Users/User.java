package lubin.guitar.Users;

//Trida uzivatele

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lubin.guitar.Files.FileManager;

public class User {

    private String name;
    private int coins;
    private String pass;
    private List<String> allowedSongs;
    private List<String> allowedInstruments;
    private List<String> allowedFrets;
    private List<String> allowedBackgrounds;
    private String currentNameSong;
    private String currentNameInstrument;
    private String currentNameFret;
    private String currentNameBackground;
    private boolean choiceMultiTone;
    private int ID;


    public User(String name, int coins, String pass, List<String>  allowedSongs, List<String>  allowedInstruments, List<String>  allowedFrets, List<String>  allowedBackgrounds, boolean choiceMultiTone){
        this.name = name;
        this.coins = coins;
        this.pass = pass;
        this.allowedSongs = allowedSongs;
        this.allowedInstruments = allowedInstruments;
        this.allowedFrets = allowedFrets;
        this.allowedBackgrounds = allowedBackgrounds;
        this.choiceMultiTone = choiceMultiTone;
        this.ID = SingletonManagerUsers.getNewID();
        String nameSong;
        String nameInstrument;
        String nameFret;
        String nameBackground;
        if (allowedSongs != null && allowedSongs.size() > 0) {
            nameSong = allowedSongs.get(0);
        } else {
            Log.e("Error", "allowedSong is broken");
            nameSong = "";
        }
        if (allowedInstruments != null && allowedInstruments.size() > 0) {
            nameInstrument = allowedInstruments.get(0);
        } else {
            Log.e("Error", "allowedInstruments is broken");
            nameInstrument = "";
        }
        if (allowedFrets != null && allowedFrets.size() > 0) {
            nameFret = allowedFrets.get(0);
        } else {
            Log.e("Error", "allowedFrets is broken");
            nameFret = "";
        }
        if (allowedBackgrounds != null && allowedBackgrounds.size() > 0) {
            nameBackground = allowedBackgrounds.get(0);
        } else {
            Log.e("Error", "allowedBackgrounds is broken");
            nameBackground = "";
        }
        this.currentNameSong = nameSong;
        this.currentNameInstrument = nameInstrument;
        this.currentNameFret = nameFret;
        this.currentNameBackground = nameBackground;

    }

    public User (User anotherUser) {
        this.name = anotherUser.name;
        this.coins = anotherUser.coins;
        this.pass = anotherUser.pass;
        this.allowedSongs = anotherUser.allowedSongs;
        this.allowedInstruments = anotherUser.allowedInstruments;
        this.allowedFrets = anotherUser.allowedFrets;
        this.allowedBackgrounds = anotherUser.allowedBackgrounds;
        this.choiceMultiTone = anotherUser.choiceMultiTone;
        this.ID = anotherUser.ID;
        this.currentNameSong = anotherUser.currentNameSong;
        this.currentNameInstrument = anotherUser.currentNameInstrument;
        this.currentNameFret = anotherUser.currentNameFret;
        this.currentNameBackground = anotherUser.currentNameBackground;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        if (coins < 0) coins = 0;
        this.coins = coins;
    }

    public List<String> getAllowedFrets() {
        if (allowedFrets == null) allowedFrets = new ArrayList<>();
        return allowedFrets;
    }

    public void setAllowedFrets(List<String> allowedFrets) {
        if (allowedFrets.isEmpty()) {
            if (FileManager.getNameFrets() == null || FileManager.getNameFrets().isEmpty()) {
                Log.e("Error", "nameFrets is empty or null");
                return;
            }

            allowedFrets.add(FileManager.getNameFrets().get(0));
        }
        this.allowedFrets = allowedFrets;
    }

    public List<String> getAllowedBackgrounds() {
        if (allowedBackgrounds == null) allowedBackgrounds = new ArrayList<>();
        return allowedBackgrounds;
    }

    public void setAllowedBackgrounds(List<String> allowedBackgrounds) {
        if (allowedBackgrounds.isEmpty()) {
            if (FileManager.getNameBackgrounds() == null || FileManager.getNameBackgrounds().isEmpty()) {
                Log.e("Error", "nameBackground is empty or null");
                return;
            }

            allowedBackgrounds.add(FileManager.getNameBackgrounds().get(0));
        }
        this.allowedBackgrounds = allowedBackgrounds;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<String> getAllowedSongs() {

        if (allowedSongs == null) allowedSongs = new ArrayList<>();
        return allowedSongs;
    }

    public void setAllowedSongs(List<String> allowedSongs) {
        if (allowedSongs.isEmpty()) {
            if (FileManager.getNameSongs() == null || FileManager.getNameSongs().isEmpty()) {
                Log.e("Error", "nameSongs is empty or null");
                return;
            }
            allowedSongs.add(FileManager.getNameSongs().get(0));
        }
        this.allowedSongs = allowedSongs;
    }

    public List<String> getAllowedInstruments() {
        if (allowedInstruments == null) allowedInstruments = new ArrayList<>();
        return allowedInstruments;
    }

    public void setAllowedInstruments(List<String> allowedInstruments) {
        if (allowedInstruments.isEmpty()) {
            if (FileManager.getNameInstruments() == null || FileManager.getNameInstruments().isEmpty()) {
                Log.e("Error", "nameInstruments is empty or null");
                return;
            }
            allowedInstruments.add(FileManager.getNameInstruments().get(0));
        }
        this.allowedInstruments = allowedInstruments;
    }

    public boolean isChoiceMultiTone() {
        return choiceMultiTone;
    }

    public void setChoiceMultiTone(boolean choiceMultiTone) {
        this.choiceMultiTone = choiceMultiTone;
    }

    public String getCurrentNameSong() {
        return currentNameSong;
    }

    public void setCurrentNameSong(String currentNameSong) {
        if (allowedSongs.contains(currentNameSong)) {
            this.currentNameSong = currentNameSong;
        } else {
            Log.e("Error", "allowedSongs not contains this CurrentNameSong ");
        }
    }

    public String getCurrentNameInstrument() {
        return currentNameInstrument;
    }

    public void setCurrentNameInstrument(String currentNameInstrument) {
        if (allowedInstruments.contains(currentNameInstrument)) {
            this.currentNameInstrument = currentNameInstrument;

        } else {
            Log.e("Error", "allowedInstruments not contains this currentNameInstrument ");
        }
    }

    public String getCurrentNameFret() {
        return currentNameFret;
    }

    public void setCurrentNameFret(String currentNameFret) {
        if (allowedFrets.contains(currentNameFret)) {
            this.currentNameFret = currentNameFret;

        } else {
            Log.e("Error", "allowedFrets not contains this currentNameFret.");
        }

    }

    public String getCurrentNameBackground() {
        return currentNameBackground;
    }

    public void setCurrentNameBackground(String currentNameBackground) {
        if (allowedBackgrounds.contains(currentNameBackground)) {
            this.currentNameBackground = currentNameBackground;
        } else {
            Log.e("Error", "allowedBackgrounds not contains this currentNameBackground.");
        }
    }
}
