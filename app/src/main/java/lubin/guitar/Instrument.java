package lubin.guitar;

import java.io.File;

/**
 * Trida instrument vytvari hraci nastroj.
 */

public class Instrument {


    String name = "Kytara";
    String fileName = "a1.wav";
    int price = 0;
    boolean usePermited = true;



    public Instrument(){


    }

    public Instrument(String name, String fileName, int price, boolean usePermited){

        this.name = name;
        this.fileName = fileName;
        this.price = price;
        this.usePermited = usePermited;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isUsePermited() {
        return usePermited;
    }

    public void setUsePermited(boolean usePermited) {
        this.usePermited = usePermited;
    }




}
