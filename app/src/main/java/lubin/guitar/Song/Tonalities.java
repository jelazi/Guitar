package lubin.guitar.Song;

import java.util.ArrayList;
import java.util.Arrays;

public class Tonalities {

    private static ArrayList<String> Ctonality;
    private static ArrayList<String> Dtonality;
    private static ArrayList<String> Etonality;
    private static ArrayList<String> Ftonality;
    private static ArrayList<String> Gtonality;
    private static ArrayList<String> Atonality;
    private static ArrayList<String> Btonality;


    public static void initTonality () {
        Ctonality = new ArrayList<String>(Arrays.asList("C", "G", "F", "Ami", "Emi", "Dmi"));
        Dtonality = new ArrayList<String>(Arrays.asList("D", "A", "G", "Bmi", "Fismi", "Emi"));
        Etonality = new ArrayList<String>(Arrays.asList("E", "B", "A", "Cmi", "Gmi", "Fismi"));
        Ftonality = new ArrayList<String>(Arrays.asList("F", "C", "B", "Dmi", "Ami", "Gmi"));
        Gtonality = new ArrayList<String>(Arrays.asList("G", "D", "C", "Emi", "Bmi", "Ami"));
        Atonality = new ArrayList<String>(Arrays.asList("A", "E", "D", "Fismi", "Cmi", "Bmi"));
        Btonality = new ArrayList<String>(Arrays.asList("B", "F", "E", "Gmi", "Dmi", "Cmi"));
    }


    public static ArrayList<String> getCtonality() {
        return Ctonality;
    }

    public static ArrayList<String> getDtonality() {
        return Dtonality;
    }

    public static ArrayList<String> getEtonality() {
        return Etonality;
    }

    public static ArrayList<String> getFtonality() {
        return Ftonality;
    }

    public static ArrayList<String> getGtonality() {
        return Gtonality;
    }

    public static ArrayList<String> getAtonality() {
        return Atonality;
    }

    public static ArrayList<String> getBtonality() {
        return Btonality;
    }
}
