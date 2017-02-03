package co.jlabs.xar.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.jlabs.xar.R;

/**
 * Created by JLabs on 02/03/17.
 */

public class ButtonLeftDrawable extends LinearLayout {
    private View mValue;
    private ImageView mImage;
    public ButtonLeftDrawable(Context context, AttributeSet attrs){
        super(context,attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Options, 0, 0);
        String titleText = a.getString(R.styleable.Options_titleText);

        Drawable drawable = a.getDrawable(R.styleable.Options_valueColor);
        if (drawable != null)
            mImage.setBackgroundDrawable(drawable);
        a.recycle();
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.com_view, this, true);

        TextView title = (TextView) getChildAt(0);
        title.setText(titleText);
        title.setTextColor(Color.parseColor("#000000"));

        mValue = getChildAt(1);
        mValue.setBackground(drawable);

        mImage = (ImageView) getChildAt(2);
        mImage.setBackground(drawable);

    }
    public ButtonLeftDrawable(Context context) {
        this(context, null);
    }

    public void setValueDra(int color) {
        mValue.setBackgroundResource(color);
    }



}
