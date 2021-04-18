package lubin.guitar;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class SingletonSizeScreen {
    static int widthScreen;
    static int heightScreen;
    static int widthUserImage;
    static int lblSizeTextAcount;
    static int textSizeSpinner;

    public static void resizeValues (Activity activity) {
        if (widthScreen == 0) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            widthScreen = size.x;
            heightScreen = size.y;
            widthUserImage = (int) (widthScreen * 0.4);
            lblSizeTextAcount = heightScreen / 40;
            textSizeSpinner = heightScreen / 45;
        }
    }

    public static int getWidthScreen() {
        return widthScreen;
    }

    public static int getHeightScreen() {
        return heightScreen;
    }

    public static int getWidthUserImage() {
        return widthUserImage;
    }

    public static int getLblSizeTextAcount() {
        return lblSizeTextAcount;
    }

    public static int getTextSizeSpinner() {
        return textSizeSpinner;
    }
}
