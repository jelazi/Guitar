package lubin.guitar.Users;

//Trida uzivatele

public class User {

    private String name;
    private int coins;
    private String pass;
    private String choiceSongName;
    private String choiceInstrumentName;
    private boolean choiceMultiTone;
    private int ID;


    public User(String name, int coins, String pass, String choiceSongName, String choiceInstrumentName, boolean choiceMultiTone){
        this.name = name;
        this.coins = coins;
        this.pass = pass;
        this.choiceSongName = choiceSongName;
        this.choiceInstrumentName = choiceInstrumentName;
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



    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public String getChoiceSongName() {
        return choiceSongName;
    }

    public void setChoiceSongName(String choiceSongName) {
        this.choiceSongName = choiceSongName;
    }

    public String getChoiceInstrumentName() {
        return choiceInstrumentName;
    }

    public void setChoiceInstrumentName(String choiceInstrumentName) {
        this.choiceInstrumentName = choiceInstrumentName;
    }

    public boolean isChoiceMultiTone() {
        return choiceMultiTone;
    }

    public void setChoiceMultiTone(boolean choiceMultiTone) {
        this.choiceMultiTone = choiceMultiTone;
    }
}
