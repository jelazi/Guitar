package lubin.guitar;


import android.widget.ImageButton;
import android.widget.ImageView;

public class GuitarTone {

    ImageButton stringTouch;
    float stringValue;
    ImageView stringImage;


    public GuitarTone(ImageButton stringTouch, float stringValue, ImageView stringImage) {
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
}
