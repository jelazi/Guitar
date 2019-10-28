package lubin.guitar;

import android.app.Activity;
import android.view.View;

public class SingletonCurrentActivity {
    private static Activity currentActivityReference;
    static View viewMessageReference;

    public static Activity getCurrentActivity() {
        return currentActivityReference;
    }

    public static void setCurrentActivityReference(Activity mainActivity) {
        currentActivityReference = mainActivity;
    }

    public static View getViewMessageReference () {
        return viewMessageReference;
    }

    public static void setViewMessageReference (View viewMessage) {
        viewMessageReference = viewMessage;
    }
}
