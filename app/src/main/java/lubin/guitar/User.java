package lubin.guitar;

//Trida uzivatele

public class User {

    private String name;
    private int value;
    private String pass;
    private String choiceSongName;
    private String choiceInstrumentName;
    private boolean choiceMultiTone;


    public User(String name, int value, String pass, String choiceSongName, String choiceInstrumentName, boolean choiceMultiTone){

        this.name = name;
        this.value = value;
        this.pass = pass;
        this.choiceSongName = choiceSongName;
        this.choiceInstrumentName = choiceInstrumentName;
        this.choiceMultiTone = choiceMultiTone;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
