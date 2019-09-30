package lubin.guitar.Settings;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//hmatník
public class Fretboard {
    RelativeLayout layoutBackGround;
    ImageView fret1;
    ImageView fret2;
    ImageView fret3;
    ImageView fret4;

    ImageView Estring;
    ImageView Astring;
    ImageView Dstring;
    ImageView Gstring;
    ImageView Bstring;
    ImageView E2string;


    public Fretboard(RelativeLayout layoutBackGround, Context context) {
        this.layoutBackGround = layoutBackGround;
        this.fret1 = new ImageView(context);
        this.fret2 = new ImageView(context);
        this.fret3 = new ImageView(context);
        this.fret4 = new ImageView(context);

        this.Estring = new ImageView(context);
        this.Astring = new ImageView(context);
        this.Dstring = new ImageView(context);
        this.Gstring = new ImageView(context);
        this.Bstring = new ImageView(context);
        this.E2string = new ImageView(context);
    }


    public void addFretImages(ImageView fret1, ImageView fret2, ImageView fret3, ImageView fret4) {
        this.fret1 = fret1;
        this.fret2 = fret2;
        this.fret3 = fret3;
        this.fret4 = fret4;
    }

    public void addStringImages(ImageView Estring, ImageView Astring, ImageView Dstring, ImageView Gstring, ImageView Bstring, ImageView E2string) {
        this.Estring = Estring;
        this.Astring = Astring;
        this.Dstring = Dstring;
        this.Gstring = Gstring;
        this.Bstring = Bstring;
        this.E2string = E2string;
    }

    public void changeBackGround(int resId) {
        this.layoutBackGround.setBackgroundResource(resId);
    }

    public void changeFretsImages(int resId) {
        this.fret1.setImageResource(resId);
        this.fret2.setImageResource(resId);
        this.fret3.setImageResource(resId);
        this.fret4.setImageResource(resId);
    }

    public void changeStringImages (int resId) {
        this.Estring.setImageResource(resId);
        this.Astring.setImageResource(resId);
        this.Dstring.setImageResource(resId);
        this.Gstring.setImageResource(resId);
        this.Bstring.setImageResource(resId);
        this.E2string.setImageResource(resId);
    }
}
