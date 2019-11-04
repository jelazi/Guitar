package lubin.guitar.Settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    public void changeBackGround(Drawable d) {
        this.layoutBackGround.setBackground(d);
    }

    public void changeFretsImages(Drawable d) {
        this.fret1.setImageDrawable(d);
        this.fret2.setImageDrawable(d);
        this.fret3.setImageDrawable(d);
        this.fret4.setImageDrawable(d);
    }

    public void changeStringImages (Drawable s1, Drawable s2, Drawable s3, Drawable s4, Drawable s5, Drawable s6) {
        this.Estring.setImageDrawable(s1);
        this.Astring.setImageDrawable(s2);
        this.Dstring.setImageDrawable(s3);
        this.Gstring.setImageDrawable(s4);
        this.Bstring.setImageDrawable(s5);
        this.E2string.setImageDrawable(s6);
    }
}
