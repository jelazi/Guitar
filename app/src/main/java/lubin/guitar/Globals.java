package lubin.guitar;

public class Globals {

    private static String userName;
    private static String pass;
    private static int valueUser;
    private static String instrument;
    private static int numberInstrument = 0; //cislo nastroje
    private static String songName;
    private static boolean firstStart = true; //nastaveni pro prvni spusteni







    public static int getNumberInstrument() {
        return numberInstrument;
    }

    public static void setNumberInstrument(int numberInstrument) {
        Globals.numberInstrument = numberInstrument;
    }

    public static String getInstrument() {
        return instrument;
    }

    public static void setInstrument(String instrument) {
        Globals.instrument = instrument;
    }

    public static String getSongName() {
        return songName;
    }

    public static void setSongName(String songName) {
        Globals.songName = songName;
    }



    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String Name) {
        Globals.userName = Name;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String passw) {
        Globals.pass = passw;
    }

    public static int getValueUser() {
        return valueUser;
    }

    public static void setValueUser(int value) {
        Globals.valueUser = value;
    }

    public static void addValueUser (){
        Globals.valueUser = Globals.valueUser + 1;
    }

    public static boolean isFirstStart() {
        return firstStart;
    }

    public static void setFirstStart(boolean firstStart) {
        Globals.firstStart = firstStart;
    }

}
