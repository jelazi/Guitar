package lubin.guitar.Users;

//Trida uzivatele

import java.util.ArrayList;
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
        if (allowedFrets == null) allowedFrets = new ArrayList<>();
        return allowedFrets;
    }

    public void setAllowedFrets(List<String> allowedFrets) {
        this.allowedFrets = allowedFrets;
    }

    public List<String> getAllowedBackgrounds() {
        if (allowedBackgrounds == null) allowedBackgrounds = new ArrayList<>();
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
        if (allowedSongs == null) allowedSongs = new ArrayList<>();
        return allowedSongs;
    }

    public void setAllowedSongs(List<String> allowedSongs) {
        this.allowedSongs = allowedSongs;
    }

    public List<String> getAllowedInstruments() {
        if (allowedInstruments == null) allowedInstruments = new ArrayList<>();
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
