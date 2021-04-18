package lubin.guitar.Song;


import android.widget.ImageButton;
import android.widget.ImageView;

public class GuitarTone {

    String name;
    ImageButton stringTouch;
    float stringValue;
    ImageView stringImage;

// kytarový ton, stringTouch - neviditelne tlacitko, kde se uzivatel dotkl, stringValue - ton, ktery je na strune zmacknuty, stringImage - struna, ktera hraje
    public GuitarTone(String name, ImageButton stringTouch, float stringValue, ImageView stringImage) {
        this.name = name;
        this.stringTouch = stringTouch;
        this.stringValue = stringValue;
        this.stringImage = stringImage;
    }

    public ImageButton getStringTouch() {
        return stringTouch;
    }

    public void setStringTouch(ImageButton stringTouch) {
        this.stringTouch = stringTouch;
    }

    public float getStringValue() {
        return stringValue;
    }

    public void setStringValue(float stringValue) {
        this.stringValue = stringValue;
    }

    public ImageView getStringImage() {
        return stringImage;
    }

    public void setStringImage(ImageView stringImage) {
        this.stringImage = stringImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
