package ir.javafundamental.android.finalprojectandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {

    /*
     * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
     */
    private static Typeface mTypeface;

    public CustomTextView(final Context context) {
        this(context, null);
        Set_Typeface(context);
    }

    public CustomTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
        Set_Typeface(context);
    }

    public CustomTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        Set_Typeface(context);
    }

    private void Set_Typeface(final Context context)
    {
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "Shabnam-Bold-FD.ttf");
        }
        setTypeface(mTypeface);
    }

}
