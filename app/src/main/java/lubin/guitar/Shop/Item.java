package lubin.guitar.Shop;

import java.io.File;

import lubin.guitar.Files.FileType;

public class Item {
    String name;
    File file;
    int price;
    FileType fileType;


    public Item (String name, FileType fileType, File file, int price ) {
        this.name = name;
        this.file = file;
        this.price = price;
        this.fileType = fileType;
    }

    public Item () {
        this.name = "";
        this.fileType = null;
        this.price = 0;
        this.file = null;
    }

    public Item (Item anotherItem) {
        this.name = anotherItem.getName();
        this.fileType = anotherItem.getFileType();
        this.file = anotherItem.getFile();
        this.price = anotherItem.getPrice();
    }



    public boolean isCorrectValueItem () {
        if (name == null || name.isEmpty() || price == 0 || fileType == null || file == null) return false;
        return true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
