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
    private List<String> allowedStrings;
    private String currentNameSong;
    private String currentNameInstrument;
    private String currentNameFret;
    private String currentNameBackground;
    private String currentNameString;
    private boolean choiceMultiTone;
    private int ID;
    private UserLevel allowedLevel;
    private UserLevel currentLevel;


    public User(String name, int coins, String pass, List<String>  allowedSongs, List<String>  allowedInstruments, List<String>  allowedFrets, List<String>  allowedBackgrounds, List<String> allowedStrings, UserLevel allowedLevel, UserLevel currentLevel, boolean choiceMultiTone){
        this.name = name;
        this.coins = coins;
        this.pass = pass;
        this.allowedSongs = allowedSongs;
        this.allowedInstruments = allowedInstruments;
        this.allowedFrets = allowedFrets;
        this.allowedBackgrounds = allowedBackgrounds;
        this.allowedStrings = allowedStrings;
        this.choiceMultiTone = choiceMultiTone;
        this.allowedLevel = allowedLevel;
        this.currentLevel = currentLevel;
        this.ID = SingletonManagerUsers.getNewID();
        String nameSong;
        String nameInstrument;
        String nameFret;
        String nameBackground;
        String nameString;
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
        if (allowedStrings != null && allowedStrings.size() > 0) {
            nameString = allowedStrings.get(0);
        } else {
            Log.e("Error", "allowedStrings is broken");
            nameString = "";
        }
        this.currentNameSong = nameSong;
        this.currentNameInstrument = nameInstrument;
        this.currentNameFret = nameFret;
        this.currentNameBackground = nameBackground;
        this.currentNameString = nameString;
    }

    public User (User anotherUser) {
        this.name = anotherUser.name;
        this.coins = anotherUser.coins;
        this.pass = anotherUser.pass;
        this.allowedSongs = anotherUser.allowedSongs;
        this.allowedInstruments = anotherUser.allowedInstruments;
        this.allowedFrets = anotherUser.allowedFrets;
        this.allowedBackgrounds = anotherUser.allowedBackgrounds;
        this.allowedStrings = anotherUser.allowedStrings;
        this.choiceMultiTone = anotherUser.choiceMultiTone;
        this.allowedLevel = anotherUser.allowedLevel;
        this.currentLevel = anotherUser.currentLevel;
        this.ID = anotherUser.ID;
        this.currentNameSong = anotherUser.currentNameSong;
        this.currentNameInstrument = anotherUser.currentNameInstrument;
        this.currentNameFret = anotherUser.currentNameFret;
        this.currentNameBackground = anotherUser.currentNameBackground;
        this.currentNameString = anotherUser.currentNameString;
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
        if (allowedFrets.isEmpty()) setAllowedFrets(allowedFrets);

        return allowedFrets;
    }

    public void setAllowedFrets(List<String> allowedFrets) {
        if (allowedFrets.isEmpty()) {
            if (FileManager.getNameFrets() == null || FileManager.getNameFrets().isEmpty()) {
                Log.e("Error", "nameFrets is empty or null");
                return;
            }
            allowedFrets = new ArrayList<>(allowedFrets);

            allowedFrets.add(FileManager.getNameFrets().get(0));
        }
        this.allowedFrets = allowedFrets;
    }

    public List<String> getAllowedBackgrounds() {
        if (allowedBackgrounds == null) allowedBackgrounds = new ArrayList<>();
        if (allowedBackgrounds.isEmpty()) setAllowedBackgrounds(allowedBackgrounds);

        return allowedBackgrounds;
    }

    public void setAllowedBackgrounds(List<String> allowedBackgrounds) {
        if (allowedBackgrounds.isEmpty()) {
            if (FileManager.getNameBackgrounds() == null || FileManager.getNameBackgrounds().isEmpty()) {
                Log.e("Error", "nameBackground is empty or null");
                return;
            }
            allowedBackgrounds = new ArrayList<>(allowedBackgrounds);

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
        if (allowedSongs.isEmpty()) setAllowedSongs(allowedSongs);

        return allowedSongs;
    }

    public void setAllowedSongs(List<String> allowedSongs) {

        if (allowedSongs.isEmpty()) {
            if (FileManager.getNameSongs() == null || FileManager.getNameSongs().isEmpty()) {
                Log.e("Error", "nameSongs is empty or null");
                return;
            }
            allowedSongs = new ArrayList<>(allowedSongs);
            allowedSongs.add(FileManager.getNameSongs().get(0));
        }
        this.allowedSongs = allowedSongs;
    }

    public List<String> getAllowedInstruments() {
        if (allowedInstruments == null) allowedInstruments = new ArrayList<>();
        if (allowedInstruments.isEmpty()) setAllowedInstruments(allowedInstruments);

        return allowedInstruments;
    }

    public void setAllowedInstruments(List<String> allowedInstruments) {
        if (allowedInstruments.isEmpty()) {
            if (FileManager.getNameInstruments() == null || FileManager.getNameInstruments().isEmpty()) {
                Log.e("Error", "nameInstruments is empty or null");
                return;
            }
            allowedInstruments = new ArrayList<>(allowedInstruments);

            allowedInstruments.add(FileManager.getNameInstruments().get(0));
        }
        this.allowedInstruments = allowedInstruments;
    }

    public List<String> getAllowedStrings() {
        if (allowedStrings == null) allowedStrings = new ArrayList<>();
        if (allowedStrings.isEmpty()) setAllowedStrings(allowedStrings);
        return allowedStrings;
    }

    public void setAllowedStrings(List<String> allowedStrings) {
        if (allowedStrings.isEmpty()) {
            if (FileManager.getNameStrings() == null || FileManager.getNameStrings().isEmpty()) {
                Log.e("Error", "nameInstruments is empty or null");
                return;
            }
            allowedStrings = new ArrayList<>(allowedStrings);

            allowedStrings.add(FileManager.getNameStrings().get(0));
        }
        this.allowedStrings = allowedStrings;
    }

    public boolean isChoiceMultiTone() {
        return choiceMultiTone;
    }

    public void setChoiceMultiTone(boolean choiceMultiTone) {
        this.choiceMultiTone = choiceMultiTone;
    }

    public String getCurrentNameSong() {
        if (currentNameSong.isEmpty()) currentNameSong = getAllowedSongs().get(0);
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
        if (currentNameInstrument.isEmpty()) currentNameInstrument = getAllowedInstruments().get(0);
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

        if (currentNameFret.isEmpty()) currentNameFret = getAllowedFrets().get(0);
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
        if (currentNameBackground.isEmpty()) currentNameBackground = getAllowedBackgrounds().get(0);

        return currentNameBackground;
    }

    public void setCurrentNameBackground(String currentNameBackground) {
        if (allowedBackgrounds.contains(currentNameBackground)) {
            this.currentNameBackground = currentNameBackground;
        } else {
            Log.e("Error", "allowedBackgrounds not contains this currentNameBackground.");
        }
    }

    public String getCurrentNameString() {
        if (currentNameString.isEmpty()) currentNameString = getAllowedStrings().get(0);

        return currentNameString;
    }

    public void setCurrentNameString(String currentNameString) {
        if (allowedStrings.contains(currentNameString)) {
            this.currentNameString = currentNameString;
        } else {
            Log.e("Error", "allowedStrings not contains this currentNameString.");
        }
    }

    public UserLevel getAllowedLevel() {
        if (this.allowedLevel == null) {
            this.allowedLevel = UserLevel.BEGINNER;
        }
        return allowedLevel;
    }

    public void setAllowedLevel(UserLevel allowedLevel) {
        this.allowedLevel = allowedLevel;
    }

    public UserLevel getCurrentLevel() {
        if (currentLevel == null) {
            currentLevel = UserLevel.BEGINNER;
        }
        return currentLevel;
    }

    public void setCurrentLevel(UserLevel currentLevel) {
        List<String> allowedLevel = SingletonManagerUsers.getListAllowLevel(getAllowedLevel());
        String nameLevel = SingletonManagerUsers.getNameUserLevel(currentLevel);
        if (!allowedLevel.contains(nameLevel)) {
            Log.e("Error", "allowedLevels not contains this currentLevel.");
            this.currentLevel = UserLevel.BEGINNER;
        } else {
            this.currentLevel = currentLevel;
        }
    }
}
