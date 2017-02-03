package co.jlabs.xar.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class Fontasm extends TextView {


    public Fontasm(Context context) {
      super(context);
        Typeface tf = FontCache.get("fonts/fontawesome.ttf", context);
        if(tf != null) {
            this.setTypeface(tf, Typeface.NORMAL);
        }
    }

    public Fontasm(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = FontCache.get("fonts/fontawesome.ttf", context);
        if(tf != null) {
            this.setTypeface(tf, Typeface.NORMAL);
        }
    }

    public Fontasm(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = FontCache.get("fonts/fontawesome.ttf", context);
        if(tf != null) {
            this.setTypeface(tf, Typeface.NORMAL);
        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}