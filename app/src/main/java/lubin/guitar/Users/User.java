package lubin.guitar.Users;

//Trida uzivatele

import java.util.List;

public class User {

    private String name;
    private int coins;
    private String pass;
    private List<String> allowedSongs;
    private List<String> allowedInstruments;
    private List<String> allowedFrets;
    private List<String> allowedBackgrounds;
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
        this.coins = coins;
    }

    public List<String> getAllowedFrets() {
        return allowedFrets;
    }

    public void setAllowedFrets(List<String> allowedFrets) {
        this.allowedFrets = allowedFrets;
    }

    public List<String> getAllowedBackgrounds() {
        return allowedBackgrounds;
    }

    public void setAllowedBackgrounds(List<String> allowedBackgrounds) {
        this.allowedBackgrounds = allowedBackgrounds;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<String> getAllowedSongs() {
        return allowedSongs;
    }

    public void setAllowedSongs(List<String> allowedSongs) {
        this.allowedSongs = allowedSongs;
    }

    public List<String> getAllowedInstruments() {
        return allowedInstruments;
    }

    public void setAllowedInstruments(List<String> allowedInstruments) {
        this.allowedInstruments = allowedInstruments;
    }

    public boolean isChoiceMultiTone() {
        return choiceMultiTone;
    }

    public void setChoiceMultiTone(boolean choiceMultiTone) {
        this.choiceMultiTone = choiceMultiTone;
    }
}
