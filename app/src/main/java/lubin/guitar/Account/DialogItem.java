package lubin.guitar.Account;

public class DialogItem {
    String text;
    int image;

    public DialogItem (String text, int imageResource) {
        this.text = text;
        this.image = imageResource;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
