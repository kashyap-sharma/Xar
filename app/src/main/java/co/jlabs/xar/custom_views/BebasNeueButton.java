package co.jlabs.xar.custom_views;

/**
 * Created by JLabs on 03/14/16.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import co.jlabs.xar.R;


public class BebasNeueButton extends Button {


    public BebasNeueButton(Context context) {
        super(context);
        if(isInEditMode())
        {
            return;
        }
        Typeface tf = FontCache.get("fonts/BebasNeue.otf", context);
        if(tf != null) {
            this.setTypeface(tf);
        }
    }

    public BebasNeueButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.textfontstyle, 0, 0);
        String font_name = a.getString(R.styleable.textfontstyle_fontname);
        a.recycle();
        if(font_name==null)
        {
            font_name="ll";
        }
        if(font_name.equals("ll"))
        {
            tf= FontCache.get("fonts/BebasNeue.otf", context);
        }
        else if(font_name.equals("lb"))
        {
            tf= FontCache.get("fonts/Icebreaker.otf", context);

        }
        else if(font_name.equals("lll"))
        {
            tf= FontCache.get("fonts/BebasNeueLight.otf", context);

        }
        else
        {
            tf= FontCache.get("fonts/BebasNeueMedium.otf", context);
        }

        if(tf != null) {
            this.setTypeface(tf);
        }
    }

    public BebasNeueButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = FontCache.get("fonts/modern_M.otf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}